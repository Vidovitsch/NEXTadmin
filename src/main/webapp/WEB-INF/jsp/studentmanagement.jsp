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
                <div class="studentsearch">
                    <button class="button_base b01_simple_rollover copymailsbutton" id="buttoncopytoclipboard" onclick="copyToClipboard();">Copy student mails</button>
                    <input type="text" id="studentsearchbar" class="searchText" onkeyup="searchfunctionstudents()" placeholder="Search for students..">
                    <ol id="studentlist" class="borderedlist" type="1">
                    </ol>
                </div>
                <div id='editaccountpart'>
                    <h3 id="selectedstudent">Edit account</h3>
                    <button class="button_base b01_simple_rollover " id="buttonremovestudent">Remove student</button>
                    <button class="button_base b01_simple_rollover " id="buttonshowgroup" onclick="showgroup();">Show group</button>
                    <br>
                    <button class="button_base b01_simple_rollover " id="buttondeletefromgroup" onclick="deletefromgroup();">Delete from</button>
                    <button class="button_base b01_simple_rollover " id="buttonaddtogroup" onclick="addtogroup();">Add to</button>
                </div>
            </div>
            <div class="middlepart">
                <input type="text" id="groupsearchbar" class="searchText" onkeyup="searchfunctiongroups()" placeholder="Search for groups..">
                <ol id="grouplist" class="borderedlist" type="1">
                </ol>
                <div id='editaccountpart'>
                    <h3 id="selectedgroup">Edit group</h3>
                    <ol id="studentsingroup" class="borderedlist" type="1">
                    </ol>
                </div>
            </div>
            <div class="rightpart">
                <div class="groupsearchbar">
                    <ol id="eventlist" class="borderedlist" type="1">
                    </ol>
                </div>
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
                    
                    function showgroup()
                    {
                        var user = getuser();
                        var groupID;
                        firebase.database().ref('/Group/' + user.GroupID).once("value", function (snapshot) {
                            groupID = snapshot.val().Name + "(" + user.GroupID + ")";
                            updateselectedgroup(groupID);
                        });
                    }                   
                    
                    function deletefromgroup()
                    {
                        var user = getuser();
                        if (user.UID != -1)
                        {
                            firebase.database().ref('/User/' + user.UID + '/GroupID').set("-1");
                            firebase.database().ref('/Group/' + user.GroupID + '/Members/' + user.UID).remove();
                            user.GroupID = "-1";
                        }
                        updateselectedstudent(user.email);
                        updateselectedgroup(document.getElementById("selectedgroup").innerHTML);
                    }
                   
                    function addtogroup()
                    {
                        var user = getuser();
                        if (user.UID != -1)
                        {
                            var group = document.getElementById('selectedgroup').innerHTML;
                            var groupID = group.substring(group.indexOf("(") + 1, group.indexOf(")"));
                            firebase.database().ref('/User/' + user.UID + '/GroupID').set(groupID);
                            firebase.database().ref('/Group/' + groupID + '/Members/' + user.UID).set("NS");
                            user.GroupID = groupID;
                        }
                        updateselectedstudent(user.email);
                        updateselectedgroup(user.GroupID);
                    }
                    
                    function getuser()
                    {
                        var email = document.getElementById('selectedstudent').innerHTML.toString();
                        email = email.substring(5);
                        for (var i = 0; i < studentlist.length; i++)
                        {
                            if (studentlist[i].email == email)
                            {
                                return studentlist[i];
                            }
                        }
                        return -1;
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
                        var groupname;
                        document.getElementById('selectedstudent').innerHTML = "Edit " + selectedstudent;
                        groupID = getuser().GroupID;
                        if (groupID == "-1") {
                            document.getElementById("buttonshowgroup").style.display = "none";
                            document.getElementById("buttondeletefromgroup").style.display = "none";
                            document.getElementById("buttonaddtogroup").style.display = "";
                        }
                        else{
                            firebase.database().ref('/Group/' + groupID).once("value", function (snapshot) {
                                groupname = snapshot.val().Name;
                                document.getElementById("buttonshowgroup").style.display = "";
                                document.getElementById("buttonshowgroup").innerHTML = "Show " + groupname + "(" + groupID + ")";
                                document.getElementById("buttondeletefromgroup").style.display = "";
                                document.getElementById("buttondeletefromgroup").innerHTML = "Delete from " + groupname + "(" + groupID + ")";
                                document.getElementById("buttonaddtogroup").style.display = "none";
                            });
                        }
                        updatelog(studentmail + ' is now selected and ready for editing');
                    }
                    
                    var olstudents = document.getElementById('studentlist');
                    olstudents.onclick = function (event) {
                        var target = getEventTarget(event);
                        if ((target.innerHTML.match(/@/g) || []).length > 2) {
                            return;
                        }
                        updateselectedstudent(target.innerHTML);
                    };
                    
                    function addstudent(student) {
                        document.getElementById('studentsingroup').innerHTML += "<li><a href='#'>" + student + "</li>";
                    }
                    
                    function updateselectedgroup(groupID) {
                        var uids = [];
                        var groupname = groupID;
                        groupID = groupID.substring(groupID.indexOf("(") + 1, groupID.indexOf(")"));
                        document.getElementById('studentsingroup').innerHTML = "";
                        firebase.database().ref('/Group/' + groupID + '/Members').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                uids.push(childSnapshot.key);
                            });
                            document.getElementById('selectedgroup').innerHTML = "Edit " + groupname;
                            for(var i = 0; i < uids.length; i++)
                            {
                                firebase.database().ref('/User/' + uids[i]).once("value", function (data) {
                                    addstudent(data.val().Mail);
                                });
                            }
                            updatelog(groupname + ' is now selected and ready for editing');
                        });
                    }
                    
                    document.getElementById('studentsingroup').onclick = function (event) {
                        var target = getEventTarget(event);
                        if ((target.innerHTML.match(/@/g) || []).length > 2) {
                            return;
                        }
                        updateselectedstudent(target.innerHTML);
                    };
                    
                    document.getElementById('grouplist').onclick = function (event) {
                        var target = getEventTarget(event);
                        if (target.innerHTML.indexOf("<li>") > 0) {
                            return;
                        }
                        updateselectedgroup(target.innerHTML);
                    };
                    
                    function searchfunctiongroups() {
                        // Declare variables
                        var input, filter, ul, li, a, i;
                        input = document.getElementById('groupsearchbar');
                        filter = input.value.toUpperCase();
                        ul = document.getElementById("grouplist");
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
                    
                    function setgrouplist() {
                        var updatedrecords = 0;
                        firebase.database().ref('/Group').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                var group = {
                                    GroupID : childSnapshot.key,
                                    Name: childSnapshot.val().Name,
                                    executesearch: function (searchtext, htmlelement) {
                                        if (this.Name.indexOf(searchtext) !== -1)
                                        {
                                            htmlelement.innerHTML += "<li>" + this.Name + " (" + this.GroupID + ")" + "</li>";
                                        }
                                    },
                                    getlistitemhtml: function () {
                                        return "<li><a href='#'>" + this.Name + " (" + this.GroupID + ")" + "</li>";
                                    }
                                    
                                };
                                updatedrecords++;
                                document.getElementById('grouplist').innerHTML += group.getlistitemhtml();
                            });
                            var updatetext = updatedrecords + " groups loaded";
                            updatelog(updatetext);
                        });
                    }
                    setgrouplist();

                    function searchfunctionstudents() {
                        // Declare variables
                        var input, filter, ul, li, a, i;
                        input = document.getElementById('studentsearchbar');
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
                                var uid = childSnapshot.key;
                                var student = {
                                    email: mail,
                                    GroupID: childSnapshot.val().GroupID,
                                    UID: uid,
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
