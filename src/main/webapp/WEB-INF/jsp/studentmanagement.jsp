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
                    <input type="text" id="studentsearchbar" class="searchText" onkeyup="searchfunctionstudents()" placeholder="Search for students..">
                    <ol id="studentlist" class="borderedlist" type="1">
                    </ol>
                </div>
                <button class="button_base b01_simple_rollover middlepartitem middleparthalfbutton" id="buttonApplyNoGroupFilter" onclick="setstudentlistNoGroup()">No Group</button>
                <button class="button_base b01_simple_rollover middlepartitem middleparthalfbutton" id="buttonAllStudents" onclick="setstudentlist()">All Students</button></br>
                <div id='editaccountpart'>
                    <h3 id="selectedstudent">Edit account</h3>
                    <div id='buttons'>
                    
                    </div>
                </div>
                <br>
                <button class="button_base b01_simple_rollover copymailsbutton" id="buttoncopytoclipboard" onclick="copyToClipboard();">Copy student mails</button>
            </div>
            <div class="middlepart">
                <input type="text" id="groupsearchbar" class="searchText" onkeyup="searchfunctiongroups()" placeholder="Search for groups..">
                <ol id="grouplist" class="borderedlist" type="1">
                </ol>
                <div id='editgrouppart'>
                    <h3 id="selectedgroup">Edit group</h3>
                    <ol id="studentsingroup" class="borderedlist" type="1">
                    </ol>
                </div>
            </div>
            <div class="rightpart">
                <div class="groupsearchbar">
                    <ol id="eventlist" class="borderedlist" type="1">
                    </ol>
                    <label id="selectedWorkshop" class="selectedstudentlabel">Selected workshop: none</label></br>
                    <button onclick='addStudentToWorkshop();' class="button_base b01_simple_rollover" id="buttonaddstudent">Add student to workshop</button>
                </div>
            </div>
        </div>
        <textarea readonly id="adminlog" class="adminlog" rows="10" cols="70">Log:&#13;&#10;</textarea>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-app.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-auth.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-database.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.2/firebase.js"></script>
        <script src="initfirebase.js"></script>
        <script src="dist/cpexcel.js"></script>
        <script src="shim.js"></script>
        <script src="jszip.js"></script>
        <script src="xlsx.js"></script>
        <script>
                    /*jshint browser:true */
                    /*global XLSX */
                    var clipboardstudents = "";
                    var selectedstudent;
                    var selectedgroup;
                    var selecteventname = "";
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

                    function showgroup()
                    {
                        if (!selectedstudent) {
                            return;
                        }
                        firebase.database().ref('/Group/' + selectedstudent.GroupID).once("value", function (snapshot) {
                            selectedgroup = {
                                ID: selectedstudent.GroupID,
                                Name: snapshot.val().Name + "(" + selectedstudent.GroupID + ")"};
                            updategroup();
                        });
                    }

                    function deletefromgroup()
                    {
                        if (!selectedstudent) {
                            return;
                        }
                        if (selectedstudent.GroupID != -1)
                        {
                            firebase.database().ref('/User/' + selectedstudent.UID + '/GroupID').set("-1");
                            firebase.database().ref('/Group/' + selectedstudent.GroupID + '/Members/' + selectedstudent.UID).remove();
                            selectedstudent.GroupID = "-1";
                            document.getElementById(selectedstudent.email).setAttribute('data', JSON.stringify(selectedstudent));
                        }
                        updateselectedstudent();
                        updategroup();
                    }

                    function addtogroup()
                    {
                        if (!selectedstudent || selectedstudent.GroupID !== "-1" || !selectedgroup) {
                            return;
                        }
                        firebase.database().ref('/User/' + selectedstudent.UID + '/GroupID').set(selectedgroup.ID);
                        firebase.database().ref('/Group/' + selectedgroup.ID + '/Members/' + selectedstudent.UID).set("NS");
                        selectedstudent.GroupID = selectedgroup.ID;
                        document.getElementById(selectedstudent.email).setAttribute('data', JSON.stringify(selectedstudent));
                        updateselectedstudent();
                        updategroup();
                    }

                    function updatelog(logtext)
                    {
                        document.getElementById('adminlog').innerHTML = logtext + "&#13;&#10;" + document.getElementById('adminlog').innerHTML;
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

                    function updateselectedstudent() {
                        document.getElementById('selectedstudent').innerHTML = "Edit " + selectedstudent.email;
                        updatelog(selectedstudent.email + ' is now selected and ready for editing');
                        showbuttons();
                    }

                    function updateselectedworkshop(eventName) {
                        selectedeventname = eventName;
                        document.getElementById('selectedWorkshop').innerHTML = "Selected workshop: " + selectedeventname;
                        updatelog(eventName + ' is now selected');
                    }
                    
                    function liStudentClicked(el) {
                        selectedstudent = JSON.parse(el.getAttribute('data'));
                        updateselectedstudent();
                    }     
                    
                                   
                       
                    function updategroup() {
                        var uids = [];
                        firebase.database().ref('/Group/' + selectedgroup.ID + '/Members').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                uids.push(childSnapshot.key);
                            });
                            document.getElementById('selectedgroup').innerHTML = "Edit " + selectedgroup.Name;
                            document.getElementById('studentsingroup').innerHTML = "";
                            for (var i = 0; i < uids.length; i++)
                            {
                                firebase.database().ref('/User/' + uids[i]).once("value", function (data) {
                                    var mail = data.val().Mail;
                                    if (mail.indexOf("@student.fontys.nl") > 0)
                                    {
                                         document.getElementById('studentsingroup').innerHTML += "<li id='2"+mail+"' onclick='liGroupStudentClicked(this)'>" + mail + "</li>";
                                    }
                                    for (var i = 0; i < studentlist.length; i++)
                                    {
                                        if (studentlist[i].email == mail)
                                        {
                                            document.getElementById('2'+mail).setAttribute('data', JSON.stringify(studentlist[i]));
                                        }
                                    }
                                    if (i === uids.length - 1)
                                    {
                                        updatelog(selectedgroup.Name + ' is now selected and ready for editing');
                                    }
                                });
                            }                         
                        });
                        showbuttons();
                    }
                    
                    function showbuttons() {
                        var el = document.getElementById('buttons');
                        if (selectedstudent)
                        {
                            if (selectedstudent.GroupID !== "-1")
                            {
                                var groupname = "";
                                firebase.database().ref('/Group/' + selectedstudent.GroupID).once("value", function (snapshot) {
                                    groupname = snapshot.val().Name + "(" + selectedstudent.GroupID + ")";
                                    el.innerHTML = '<button class="button_base b01_simple_rollover buttoninvis" id="buttonshowgroup" onclick="showgroup();" >Show '+groupname+'</button>' +
                        '<button class="button_base b01_simple_rollover buttoninvis" id="buttondeletefromgroup" onclick="deletefromgroup();" >Delete from '+groupname+'</button>';
                                });
                            }
                            else if (!selectedgroup)
                            {
                                el.innerHTML = '<button class="button_base b01_simple_rollover buttoninvis" id="buttonaddtogroup" onclick="addtogroup();" disabled>Add to group</button>';
                            }
                            else
                            {
                                el.innerHTML = '<button class="button_base b01_simple_rollover buttoninvis" id="buttonaddtogroup" onclick="addtogroup();">Add to '+selectedgroup.Name+'</button>';
                            }
                        }
                    }
                    
                    function liGroupStudentClicked(el) {
                        selectedstudent = JSON.parse(el.getAttribute('data'));
                        updateselectedstudent();
                    }

                    function liGroupClicked(el) {
                        selectedgroup = JSON.parse(el.getAttribute('data'));
                        updategroup();
                    }

                    function searchfunctiongroups() {
                        // Declare variables
                        var input, filter, ul, li, a, i;
                        input = document.getElementById('groupsearchbar');
                        filter = input.value.toUpperCase();
                        ul = document.getElementById("grouplist");
                        li = ul.getElementsByTagName('li');
                        // Loop through all list items, and hide those who don't match the search query
                        for (i = 0; i < li.length; i++) {
                            if (li[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
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
                                    ID: childSnapshot.key,
                                    Name: childSnapshot.val().Name + "(" + childSnapshot.key + ")",
                                    executesearch: function (searchtext, htmlelement) {
                                        if (this.Name.indexOf(searchtext) !== -1)
                                        {
                                            htmlelement.innerHTML += "<li>" + this.Name + "</li>";
                                        }
                                    },
                                    getlistitemhtml: function () {
                                        return "<li id='group "+this.ID+"' onclick='liGroupClicked(this)'>" + this.Name +"</li>";
                                    }

                                };
                                updatedrecords++;
                                document.getElementById('grouplist').innerHTML += group.getlistitemhtml();
                                document.getElementById('group ' + group.ID).setAttribute('data', JSON.stringify(group));
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
                            if (li[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
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
                                document.getElementById(studentlist[i].email).setAttribute('data', JSON.stringify(studentlist[i]));
                            }
                        }
                        updatelog(studentswithoutgroup + ' student do not have a group assigned yet');
                    }

                    function setstudentlist() {
                        document.getElementById('studentlist').innerHTML = "";
                        clipboardstudents = "";
                        studentlist = [];
                        nogroupfilter = false;
                        var updatedrecords = 0;
                        firebase.database().ref('/User').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                var mail = childSnapshot.val().Mail;
                                if (mail.indexOf("@student.fontys.nl") > 0)
                                {
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
                                            return "<li id='"+this.email+"' onclick='liStudentClicked(this)'>" + this.email + "</li>";
                                        }
                                    };
                                    studentlist.push(student);
                                    updatedrecords++;
                                    document.getElementById('studentlist').innerHTML += student.getlistitemhtml();
                                    document.getElementById(student.email).setAttribute('data', JSON.stringify(student));
                                    clipboardstudents += mail + ";";
                                }
                            });
                            var updatetext = updatedrecords + " students loaded";
                            updatelog(updatetext);
                        });
                    }
                    setstudentlist();

                    function seteventlist() {
                        var updatedrecords = 0;
                        firebase.database().ref('/Event').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                var eventname = childSnapshot.val().EventName;
                                updatedrecords++;
                                var eventhtml = document.createElement("li");
                                eventhtml.innerHTML = eventname;
                                eventhtml.onclick = function () {
                                    selectedevent = this.getAttribute('data');
                                    updateselectedworkshop(selectedevent);
                                }  
                                eventhtml.setAttribute('data', eventname);
                                document.getElementById('eventlist').appendChild(eventhtml);
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
                                studenthtml = "<li>" + mail + "</li>";
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
                                        var uid = selectedstudent.UID;
                                        firebase.database().ref('/Event/' + wsKey + "/Attending/" + uid).set({
                                            Status: 'Attending'
                                        }).then(updatelog(selectedstudent.email + " added to " + selectedeventname));
                                    }
                                });
                            });
                        } else {
                            alert("Select a student and a workshop");
                        }
                    }
        </script>
    </body>
</html>
