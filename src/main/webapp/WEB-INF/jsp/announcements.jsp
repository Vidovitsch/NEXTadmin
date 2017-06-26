<%-- 
    Document   : announcements
    Created on : 7-jun-2017, 9:28:10
    Author     : Bert
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <spring:url value="/css/index.css" var="mainCSS" />
        <link href="${mainCSS}" rel="stylesheet" />
    </head>
    <body>
        <div class="wrapper">
            <div class="leftpart">
                <div class="announcements">
                    <ol id="announcementlist" class="borderedlist" type="1">
                    </ol>
                </div>
                <div id='editannouncementpart'>
                    <input type="text" name="" id="editannouncementtext" class="searchText clearable" placeholder="Create new announcement here">
                    <button class="button_base b01_simple_rollover editannouncement" id="buttoneditannouncement" onclick="editCreateAnnouncement();">Create!</button>
                    <button class="button_base b01_simple_rollover deleteannouncement" id="buttondeleteannouncement" onclick="deleteAnnouncement();">Delete announcement</button>
                </div>
            </div>
            <div class="middlepart">
                <h1>Polling</h1>
                <div class="middlepartcontainer">
                    <h2 id="currentPhaseText"></h2>
                    <div class="dropdown">
                        <button onclick="handleDropdown()" class="dropbtn">Set phase</button>
                        <div id="myDropdown" class="dropdown-content">
                            <a href="#" onclick="setPhase(0)">Ideas</a>
                            <a href="#" onclick="setPhase(1)">Voting</a>
                            <a href="#" onclick="setPhase(2)">Finish</a>
                        </div>
                    </div>
                </div>
                <div class="middlepartcontainer">
                    <div class="pollIdeas">
                        <ol id="pollIdeaList" class="borderedlist" type="1">
                        </ol>
                    </div>
                    <button class="button_base b01_simple_rollover deletePollIdea" id="buttondeletepoll" onclick="deletePoll();" disabled>Select poll</button>
                </div>
            </div>
            <div class="rightpart">
            </div>
            <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-app.js"></script>
            <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-auth.js"></script>
            <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-database.js"></script>
            <script src="https://www.gstatic.com/firebasejs/3.7.2/firebase.js"></script>
            <script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>

            <script src="initfirebase.js"></script>
            <script>
                        var selectedAnnouncement;
                        var selectedPoll;
                        var previousPoll;
                        var deletepollselection = [];

                        jQuery(function ($) {
                            // /////
                            // CLEARABLE INPUT
                            function tog(v) {
                                return v ? 'addClass' : 'removeClass';
                            }
                            $(document).on('input', '.clearable', function () {
                                $(this)[tog(this.value)]('x');
                            }).on('mousemove', '.x', function (e) {
                                $(this)[tog(this.offsetWidth - 18 < e.clientX - this.getBoundingClientRect().left)]('onX');
                            }).on('touchstart click', '.onX', function (ev) {
                                ev.preventDefault();
                                setCreateAnnouncement();
                                $(this).removeClass('x onX').val('').change();
                            });
                        });

                        function setannouncements() {
                            announcements = [];
                            document.getElementById('announcementlist').innerHTML = "";
                            firebase.database().ref('/Announcement').once("value", function (snapshot) {
                                snapshot.forEach(function (childSnapshot) {
                                    var uid = childSnapshot.key;
                                    var announcement = {
                                        text: childSnapshot.val().Text,
                                        UID: uid,
                                        getlistitemhtml: function () {
                                            return "<li id='" + this.UID + "' onclick='liAnnouncementClicked(this)'>" + this.text + "</li>";
                                        }
                                    };
                                    announcements.push(announcement);
                                    document.getElementById('announcementlist').innerHTML += announcement.getlistitemhtml();
                                    document.getElementById(announcement.UID).setAttribute('data', JSON.stringify(announcement));
                                });
                            });
                        }
                        setannouncements();

                        function setpollideas() {
                            pollideas = [];
                            document.getElementById('pollIdeaList').innerHTML = "";
                            firebase.database().ref('/Poll/Ideas').once("value", function (snapshot) {
                                snapshot.forEach(function (childSnapshot) {
                                    var uid = childSnapshot.key;
                                    var pollIdea = {
                                        text: childSnapshot.val().Content,
                                        votes: childSnapshot.val().Votes,
                                        UID: uid,
                                        getlistitemhtml: function () {
                                            return "<li id='poll" + this.UID + "' onclick='liPollClicked(this)'>" + this.text + "</li>";
                                        }
                                    };
                                    pollideas.push(pollIdea);
                                    document.getElementById('pollIdeaList').innerHTML += pollIdea.getlistitemhtml();
                                    document.getElementById('poll' + pollIdea.UID).setAttribute('data', JSON.stringify(pollIdea));
                                });
                            });
                        }
                        setpollideas();

                        function setPhase(phaseNumber) {
                            firebase.database().ref('/Poll/Phase').set(phaseNumber);
                            updateCurrentPhase();
                        }

                        /* When the user clicks on the button, 
                         toggle between hiding and showing the dropdown content */
                        function handleDropdown() {
                            document.getElementById("myDropdown").classList.toggle("show");
                        }

                        // Close the dropdown menu if the user clicks outside of it
                        window.onclick = function (event) {
                            if (!event.target.matches('.dropbtn')) {

                                var dropdowns = document.getElementsByClassName("dropdown-content");
                                var i;
                                for (i = 0; i < dropdowns.length; i++) {
                                    var openDropdown = dropdowns[i];
                                    if (openDropdown.classList.contains('show')) {
                                        openDropdown.classList.remove('show');
                                    }
                                }
                            }
                        }

                        function setCreateAnnouncement() {
                            selectedAnnouncement = null;
                            document.getElementById('editannouncementtext').value = "";
                            document.getElementById('buttoneditannouncement').innerHTML = "Create!";
                        }

                        function liAnnouncementClicked(el) {
                            selectedAnnouncement = JSON.parse(el.getAttribute('data'));
                            document.getElementById('editannouncementtext').value = selectedAnnouncement.text;
                            console.log("test");
                            document.getElementById('buttoneditannouncement').innerHTML = "Edit!";
                        }

                        function liPollClicked(el) {
                            if(previousPoll){
                                previousPoll.style.color = "White";
                            }
                            previousPoll = el;
                            selectedPoll = JSON.parse(el.getAttribute('data'));
                            el.style.color = "Orange";
                            document.getElementById('buttondeletepoll').disabled = false;
                            document.getElementById('buttondeletepoll').innerHTML = "Delete Poll";
                        }

                        function deleteAnnouncement() {
                            if (!selectedAnnouncement) {
                                alert("No announcement selected");
                            } else if (confirm("Are you sure you want to delete " + selectedAnnouncement.text + "?")) {
                                firebase.database().ref('/Announcement/' + selectedAnnouncement.UID).remove();
                                selectedAnnouncement = null;
                                document.getElementById('editannouncementtext').value = "";
                                document.getElementById('buttoneditannouncement').innerHTML = "Create!";
                                setannouncements();
                            }
                        }

                        function deletePoll() {
                            if (!selectedPoll) {
                                alert("No Poll selected");
                            } else if (confirm("Are you sure you want to delete " + selectedPoll.text + "?")) {
                                firebase.database().ref('/Poll/Ideas/' + selectedPoll.UID).remove();
                                firebase.database().ref('/User/' + selectedPoll.UID + '/Submitted').set(0);
                                selectedPoll = null;
                                document.getElementById('buttondeletepoll').disabled = true;
                                document.getElementById('buttondeletepoll').innerHTML = "Select Poll";
                                setpollideas();
                            }
                        }

                        function editCreateAnnouncement() {
                            var announcementText = document.getElementById('editannouncementtext').value;
                            if (!selectedAnnouncement) {
                                if (!announcementText) {
                                    alert("Can't create empty announcements.");
                                    return;
                                }

                                // Generate a reference to a new location and add some data using push()
                                var newAnnouncementRef = firebase.database().ref('/Announcement').push({
                                    Text: announcementText,
                                    DateTime: getCurrentDateTime()
                                });
                                // Get the unique ID generated by push() by accessing its key
                                var announcementUID = newAnnouncementRef.key;
                                var announcement = {
                                    text: announcementText,
                                    UID: announcementUID,
                                    getlistitemhtml: function () {
                                        return "<li id='" + this.uid + "' onclick='liAnnouncementClicked(this)'>" + this.text + "</li>";
                                    }
                                };
                                selectedAnnouncement = announcement;
                                document.getElementById('buttoneditannouncement').innerHTML = "Edit!";
                            } else {
                                firebase.database().ref('/Announcement/' + selectedAnnouncement.UID + '/Text').set(announcementText);
                            }
                            setannouncements();
                        }

                        function getCurrentDateTime() {
                            var currentdate = new Date();
                            var datetime = currentdate.getDate() + "-"
                                    + (currentdate.getMonth() + 1) + "-"
                                    + currentdate.getFullYear() + " "
                                    + currentdate.getHours() + ":"
                                    + currentdate.getMinutes() + ":"
                                    + currentdate.getSeconds();
                            return datetime;
                        }

                        function setCurrentPhaseLabel(phasenumber) {
                            var text = "";
                            switch (phasenumber) {
                                case 0:
                                    text = "Creating ideas";
                                    break;
                                case 1:
                                    text = "Voting poll";
                                    break;
                                case 2:
                                    text = "Poll closed";
                                    break;
                            }
                            document.getElementById('currentPhaseText').innerHTML = "Current status: " + text;
                        }

                        function getCurrentPollingPhase() {
                            firebase.database().ref('/Poll').once("value", function (snapshot) {
                                var currentphase = snapshot.val().Phase;
                                setCurrentPhaseLabel(currentphase);
                            });
                        }

                        function updateCurrentPhase() {
                            getCurrentPollingPhase();
                        }

                        updateCurrentPhase();
            </script>
    </body>
</html>
