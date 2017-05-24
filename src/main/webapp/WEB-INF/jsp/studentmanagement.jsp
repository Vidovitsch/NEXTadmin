<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%! int fontSize;%> 
<%! int maxfontSize = 3;%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <spring:url value="/css/index.css" var="mainCSS" />
        <link href="${mainCSS}" rel="stylesheet" />
    </head>
    <body>
        <div class="wrapper">
            <div class="leftpart">
                <button class="button_base b01_simple_rollover copymailsbutton" id="buttoncopytoclipboard" onclick="copyToClipboard();">Copy student mails</button>
                <input type="text" id="searchText" class="studentsearchbar" onkeyup="searchfunction()" placeholder="Search for students..">
                <button class="button_base b01_simple_rollover middlepartitem middleparthalfbutton" id="buttonApplyNoGroupFilter" onclick="setstudentlistNoGroup()">No Group</button>
                <button class="button_base b01_simple_rollover middlepartitem middleparthalfbutton" id="buttonAllStudents" onclick="setstudentlist()">All Students</button></br>

                <ol id="studentlist" class="borderedlist" type="1">
                </ol>
            </div>
            <div class="middlepart">
                <div id='editaccountpart' class='middlepartcontainer'>
                    <h3>Edit account</h3>
                    <label id="selectedstudent" class="selectedstudentlabel middlepartitem">Selected student: none</label>
                    <button class="button_base b01_simple_rollover middlepartitem" id="buttonremovestudent">Remove selected student</button></br>    
                </div>
            </div>
            <div class="rightpart">
                <h3>Workshops</h3>
                <ol id="eventlist" class="borderedlist" type="1">
                </ol>
                <label id="selectedWorkshop" class="selectedstudentlabel">Selected workshop: none</label></br>
                <button onclick='addStudentToWorkshop();' class="button_base b01_simple_rollover" id="buttonaddstudent">Add student to workshop</button>
            </div>
        </div>
        <textarea readonly id="adminlog" class="adminlog" rows="10" cols="70">Log:&#13;&#10;</textarea>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-app.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-auth.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-database.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.2/firebase.js"></script>
        <script src="initfirebase.js"></script> <!-- initialize firebase -->t>
        <script src="dist/cpexcel.js"></script>
        <script src="shim.js"></script>
        <script src="jszip.js"></script>
        <script src="xlsx.js"></script>
        <script>
                    /*jshint browser:true */
                    /*global XLSX */
                    var clipboardstudents = "";
                    var clipboardstudentsNoGroup = "";
                    var selectedstudent = "";
                    var selectedeventname = "";
                    var studentlist = [];
                    var nogroupfilter = false;
                    var eventlist = [];
                    function copyToClipboard() {
                        var aux = document.createElement("input");
                        if (nogroupfilter)
                        {
                            aux.setAttribute("value", clipboardstudentsNoGroup);
                            updatelog("Attending students with no group copied to clipboard");
                        } else
                        {
                            aux.setAttribute("value", clipboardstudents);
                            updatelog("All attending students copied to clipboard");
                        }
                        document.body.appendChild(aux);
                        aux.select();
                        document.execCommand("copy");
                        document.body.removeChild(aux);
                        updatelog("Ready to paste emails in your email-client");
                    }

                    function updatelog(logtext)
                    {
                        document.getElementById('adminlog').innerHTML += logtext + "&#13;&#10;";
                    }

                    function hidelog() {
                        document.getElementById("adminlog").style.visibility = "hidden";
                    }

                    function showlog() {
                        document.getElementById("adminlog").style.visibility = "visible";
                    }

                    function getEventTarget(e) {
                        e = e || window.event;
                        return e.target || e.srcElement;
                    }

                    function updateselectedstudent(studentmail) {
                        selectedstudent = studentmail;
                        document.getElementById('selectedstudent').innerHTML = "Selected student: " + selectedstudent;
                        updatelog(studentmail + ' is now selected and ready for editing');
                    }

                    function updateselectedworkshop(eventName) {
                        selectedeventname = eventName;
                        document.getElementById('selectedWorkshop').innerHTML = "Selected workshop: " + selectedeventname;
                        updatelog(eventName + ' is now selected');
                    }

                    var olstudents = document.getElementById('studentlist');
                    olstudents.onclick = function (event) {
                        var target = getEventTarget(event);
                        updateselectedstudent(target.innerHTML);
                    };

                    var olWorkshops = document.getElementById('eventlist');
                    olWorkshops.onclick = function (event) {
                        var target = getEventTarget(event);
                        updateselectedworkshop(target.innerHTML);
                    };

                    function searchfunction() {
                        // Declare variables
                        var input, filter, ul, li, a, i;
                        input = document.getElementById('searchText');
                        filter = input.value.toUpperCase();
                        ul = document.getElementById("studentlist");
                        li = ul.getElementsByTagName('li');
                        // Loop through all list items, and hide those who don't match the search query
                        for (i = 0; i < li.length; i++) {
                            a = li[i].getElementsByTagName("a")[0];
                            if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
                                li[i].style.display = "";
                            } else {
                                li[i].style.display = "none";
                            }
                        }
                    }

                    function setstudentlistNoGroup() {
                        clipboardstudentsNoGroup = "";
                        nogroupfilter = true;
                        document.getElementById('studentlist').innerHTML = "";
                        var studentswithoutgroup = 0;
                        var arrayLength = studentlist.length;
                        for (var i = 0; i < arrayLength; i++) {
                            if (parseFloat(studentlist[i].GroupID) === parseFloat(-1))
                            {
                                studentswithoutgroup++;
                                clipboardstudentsNoGroup += studentlist[i].email + ";";
                                document.getElementById('studentlist').innerHTML += studentlist[i].getlistitemhtml();
                            }
                        }
                        updatelog(studentswithoutgroup + ' student do not have a group assigned yet');
                    }

                    function setstudentlist() {
                        clipboardstudents = "";
                        studentlist = [];
                        nogroupfilter = false;
                        var updatedrecords = 0;
                        firebase.database().ref('/User').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                var mail = childSnapshot.val().Mail;
                                var groupid = childSnapshot.val().GroupID;
                                var uid = childSnapshot.key.toString();
                                var student = {
                                    email: mail,
                                    GroupID: groupid,
                                    id: uid,

                                    executesearch: function (searchtext, htmlelement) {
                                        if (this.mail.indexOf(searchtext) !== -1)
                                        {
                                            var curstudenthtml = "<li>" + email + "</li>";
                                            htmlelement.innerHTML += curstudenthtml;
                                        }
                                    },
                                    getlistitemhtml: function () {
                                        return "<li><a href='#'>" + this.email + "</li>";
                                    }
                                };
                                studentlist.push(student);
                                updatedrecords++;
                                document.getElementById('studentlist').innerHTML += student.getlistitemhtml();
                                clipboardstudents += mail + ";";
                            });
                            var updatetext = updatedrecords + " students loaded";
                            updatelog(updatetext);
                        });
                    }
                    setstudentlist();

                    function seteventlist() {
                        var updatedrecords = 0;
                        var eventhtml;
                        firebase.database().ref('/Event').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                var eventName = childSnapshot.val().EventName;
                                var uid = childSnapshot.key.toString();
                                var eventType = childSnapshot.val().EventType;
                                if (eventType === 'Workshop') {
                                    updatedrecords++;
                                    eventhtml = "<li id=&quot;" + uid + "&quot;><a href='#'>" + eventName + "</li>";
                                    document.getElementById('eventlist').innerHTML += eventhtml;
                                }
                            });
                            var updatetext = updatedrecords + " events loaded";
                            updatelog(updatetext);
                        });
                    }
                    seteventlist();

                    function setstudentlistgroup() {
                        var updatedrecords = 0;
                        var studenthtml;
                        var student;
                        firebase.database().ref('/User').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                var mail = childSnapshot.val().Mail;
                                student = {
                                    email: mail,
                                    executesearch: function (searchtext, htmlelement) {
                                        if (this.mail.indexOf(searchtext) !== -1)
                                        {
                                            var curstudenthtml = "<li>" + mail + "</li>";
                                            htmlelement.innerHTML += curstudenthtml;
                                        }
                                    }};
                                updatedrecords++;
                                studenthtml = "<li><a href='#'>" + mail + "</li>";
                                document.getElementById('studentlist').innerHTML += studenthtml;
                                clipboardstudents += mail + ";";
                            });
                            var updatetext = updatedrecords + " students loaded";
                            updatelog(updatetext);
                        });
                    }

                    function addStudentToWorkshop() {
                        if (selectedstudent && selectedeventname) {
                            firebase.database().ref('/Event').once("value", function (snapshot) {
                                snapshot.forEach(function (childSnapshot) {
                                    var wsName = childSnapshot.val().EventName;
                                    if (wsName === selectedeventname) {
                                        var wsKey = childSnapshot.key.toString();
                                        var uid = getStudentID(selectedstudent);
                                        firebase.database().ref('/Event/' + wsKey + "/Attending/" + uid).set({
                                            Status: 'Attending'
                                        }).then(updatelog(selectedstudent + " added to " + selectedeventname));
                                    }
                                });
                            });
                        } else {
                            alert("Select a student and a workshop");
                        }
                    }

                    function getStudentID(email) {
                        var uid;
                        studentlist.forEach(function (student) {
                            if (student.email === email) {
                                uid = student.id;
                            }
                        });
                        return uid;
                    }
        </script>
    </body>
</html>
