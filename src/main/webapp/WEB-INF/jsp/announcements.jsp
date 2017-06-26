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

                        function editCreateAnnouncement() {
                            var announcementText = document.getElementById('editannouncementtext').value
                            if (!selectedAnnouncement)
                            {
                                if (!announcementText)
                                {
                                    alert("Can't create empty announcements.");
                                    return;
                                }

                                // Generate a reference to a new location and add some data using push()
                                var newAnnouncementRef = firebase.database().ref('/Announcement').push({
                                    Text: announcementText,
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
                            } else
                            {
                                firebase.database().ref('/Announcement/' + selectedAnnouncement.UID + '/Text').set(announcementText);
                            }
                            setannouncements();
                        }
            </script>
    </body>
</html>
