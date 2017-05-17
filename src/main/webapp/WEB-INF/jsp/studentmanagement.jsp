<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%! int fontSize;%> 
<%! int maxfontSize = 3;%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
                <ol id="eventlist" class="borderedlist" type="1">
                </ol>
            </div>
        </div>
        <textarea readonly id="adminlog" class="adminlog" rows="10" cols="70">Log:&#13;&#10;</textarea>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-app.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-auth.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-database.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.2/firebase.js"></script>
        <script>
                    // Initialize Firebase
                    var config = {
                        apiKey: "AIzaSyCRi0Ma5ekQxhwg-BfQCa6684hMzvR3Z1o",
                        authDomain: "nextweek-b9a58.firebaseapp.com",
                        databaseURL: "https://nextweek-b9a58.firebaseio.com",
                        storageBucket: "nextweek-b9a58.appspot.com",
                        messagingSenderId: "488624254338"
                    };
                    firebase.initializeApp(config);
        </script>
        <script src="dist/cpexcel.js"></script>
        <script src="shim.js"></script>
        <script src="jszip.js"></script>
        <script src="xlsx.js"></script>
        <script>
                    /*jshint browser:true */
                    /*global XLSX */
                    var clipboardstudents = "";
                    var selectedstudent = "";
                    var selecteventname = "";
                    var studentlist = [];
                    var eventlist = [];
                    function copyToClipboard() {
                        var aux = document.createElement("input");
                        aux.setAttribute("value", clipboardstudents);
                        document.body.appendChild(aux);
                        aux.select();
                        document.execCommand("copy");
                        document.body.removeChild(aux);
                        updatelog("Attending students copied to clipboard");
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

                    var olstudents = document.getElementById('studentlist');
                    olstudents.onclick = function (event) {
                        var target = getEventTarget(event);
                        updateselectedstudent(target.innerHTML);
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

                    function setstudentlist() {
                        var updatedrecords = 0;
                        firebase.database().ref('/User').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                var mail = childSnapshot.val().Mail;
                                var student = {
                                    email: mail,
                                    GroupID: childSnapshot.val().GroupID,
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
                        var event;
                        firebase.database().ref('/Event').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                var eventname = childSnapshot.val().EventName;
                                event = {
                                    Name: eventname
                                };
                                updatedrecords++;
                                eventhtml = "<li><a href='#'>" + eventname + "</li>";
                                document.getElementById('eventlist').innerHTML += eventhtml;
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
        </script>
    </body>
</html>
