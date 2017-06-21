<%-- 
    Document   : adminpanel
    Created on : 27-mrt-2017, 11:04:05
    Author     : Arno Dekkers Los
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <spring:url value="/css/container.css" var="containerCSS" />
        <spring:url value="/css/adminpanel.css" var="adminpanelCSS" />

        <link href="${containerCSS}" rel="stylesheet" />
        <link href="${adminpanelCSS}" rel="stylesheet" />
    </head>
    <body>
        <div class="wrapper">
            <div class="information-list">
                <div class="information-list-cb">
                    <span class="fieldSpan">Apply Filter:</span>
                    <select name="cbSearchType" class="cbSearchType">
                        <option value="All" selected="selected" >All</option>
                        <c:forEach items="${types}" var="id">
                            <option value="${id}">${id}</option>
                        </c:forEach>
                    </select>
                </div>
                <div id="list-spacer"></div>
                <div class="itemList">
                    <c:forEach var="ScheduledItem" items="${scheduledItems}">
                        <div onclick="post('editItem', {id: ${ScheduledItem.AddSpecialChars(ScheduledItem.getId())}, string: ${ScheduledItem.AddSpecialChars(ScheduledItem.getString())}});" class="scheduledItemDiv"><span>${ScheduledItem.getString()}</span></div>
                    </c:forEach>
                </div>
            </div>    
            <div class="create-modify-event">
                <div class="create-mod-list-cb" id="cb-left">
                    <span class="fieldSpan" id="newfieldSpan">Select the type of event that you want to create:</span>
                    <span class="fieldSpan" id="oldfieldSpan" style="display: none;">Go back to create a new event:</span>
                    <select class="cbSearchType" id="dbType" onchange="dbTypeChanged()">
                        <option></option>
                        <c:forEach items="${types}" var="id">
                            <option value="${id}">${id}</option>
                        </c:forEach>
                    </select>
                    <input id="newEvent-button" onclick="window.location.href='/adminpanel'" class="btn-style" type="button" value="Navigate to New event" style="display: none;"/>
                </div>
                <div id="input-form">
                    <c:forEach var="row" items="${fields}">
                        <div class="fieldDiv" class="${row}div fieldDiv" style="display: none;" id="${row}div">
                            <label class="fieldLabel">${row}:</label><input class="fieldInput" type="text" id="${row}" name="${row}" required></br>
                        </div>
                    </c:forEach>
                    <div class="fieldDiv" id="descriptiondiv" style="display: none;">
                        <label class="fieldLabel">Description:</label>
                        <textarea class="fieldInput" id="description" name="description"></textarea></br>
                        <div class="buttons-console">
                            <input id="create-button" onclick="SentToDB(true)" class="btn-style" type="submit" value="create Event" style="display: none;"/>      
                            <input id="discard-button" onclick="window.location.href='/adminpanel'" class="btn-style" type="button" value="discard changes" style="display: none;"/>
                            <input id="save-button" onclick="SentToDB(false)" class="btn-style" type="submit" value="save changes" style="display: none;"/>
                            <input id="delete-button" class="btn-style" type="submit" value="Delete selected event" style="display: none;"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" ></script>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>‌​
        <script src="../../../javascript/moment.js"></script>
        <script>
        function pageLoad() {
            if ((window.location.href.indexOf("/editItem") > -1)) {
                $("#discard-button").show();
                $("#save-button").show();
                $("#delete-button").show();
                $("#dbType").hide();
                $("#newEvent-button").show();
                $("#newfieldSpan").hide();
                $("#oldfieldSpan").show();
                document.getElementById("id").value = ${selectedItem.AddSpecialChars(selectedItem.getId())};
                document.getElementById("dbType").value = ${selectedItem.AddSpecialChars(selectedItem.getType())};
                document.getElementById("dbType").disabled = true;
                dbTypeChanged();
                $("#create-button").hide();
                document.getElementById("eventName").value = ${selectedItem.AddSpecialChars(selectedItem.getEventName())};
                document.getElementById("startTime").value = ${selectedItem.AddSpecialChars(selectedItem.getStartTime())};
                document.getElementById("endTime").value = ${selectedItem.AddSpecialChars(selectedItem.getEndTime())};
                document.getElementById("date").value = ${selectedItem.AddSpecialChars(selectedItem.getDate())};
                document.getElementById("locationName").value = ${selectedItem.AddSpecialChars(selectedItem.getLocationName())};
                document.getElementById("description").value = ${selectedItem.AddSpecialChars(selectedItem.getDescription())};
                if (${selectedItem.AddSpecialChars(selectedItem.getPresenter())} !== '-1') {
                    document.getElementById("presenter").value = ${selectedItem.AddSpecialChars(selectedItem.getPresenter())};
                }
                if (${selectedItem.AddSpecialChars(selectedItem.getImageURL())} !== '-1') {
                    document.getElementById("imageURL").value = ${selectedItem.AddSpecialChars(selectedItem.getImageURL())};
                }
                if (${selectedItem.AddSpecialChars(selectedItem.getMaxUsers())} !== '-1') {
                    document.getElementById("maxUsers").value = ${selectedItem.AddSpecialChars(selectedItem.getMaxUsers())};
                }
            }
        };
        
        pageLoad();
        $('#cbSearchType').on('change', function () {
            var selection = $(this).val();
            if (selection === "" || selection === "all") {
                post("adminpanel", null, "get");
            } else {
                post('filterList', {type: selection});
            }
        });

        function dbTypeChanged() {
            var selection = document.getElementById("dbType").value;
            switch (selection) {
                case "Workshop":
                    unHideEventDateFields();
                    $("#iddiv").hide();
                    $("#imageURLdiv").show();
                    $("#presenterdiv").show();
                    $("#maxUsersdiv").show();
                    break;
                case "Lecture":
                    unHideEventDateFields();
                    $("#iddiv").hide();
                    $("#imageURLdiv").show();
                    $("#presenterdiv").show();
                    $("#maxUsersdiv").hide();
                    break;
                case "Performance":
                    unHideEventDateFields();
                    $("#iddiv").hide();
                    $("#imageURLdiv").show();
                    $("#presenterdiv").hide();
                    $("#maxUsersdiv").hide();
                    break;
                case "None":
                    unHideEventDateFields();
                    $("#iddiv").hide();
                    $("#imageURLdiv").hide();
                    $("#presenterdiv").hide();
                    $("#maxUsersdiv").hide();
                    break;
                case "":
                    HideEventDateFields();
                    $("#iddiv").hide();
                    $("#imageURLdiv").hide();
                    $("#presenterdiv").hide();
                    $("#maxUsersdiv").hide();
                    break;
            }
        };

        function unHideEventDateFields() {
            $("#eventNamediv").show();
            $("#startTimediv").show();
            $("#endTimediv").show();
            $("#datediv").show();
            $("#locationNamediv").show();
            $("#iddiv").show();
            $("#descriptiondiv").show();
            $("#create-button").show();
        };

        function HideEventDateFields() {
            $("#eventNamediv").hide();
            $("#startTimediv").hide();
            $("#endTimediv").hide();
            $("#datediv").hide();
            $("#locationNamediv").hide();
            $("#descriptiondiv").hide();
            $("#create-button").hide();
        };

        function SentToDB(newEvent) {
            var type = document.getElementById("dbType").value;
            var id = document.getElementById("id").value;
            var eventName = document.getElementById("eventName").value;
            var startTime = document.getElementById("startTime").value;
            var endTime = document.getElementById("endTime").value;
            var date = document.getElementById("date").value;
            var locationName = document.getElementById("locationName").value;
            var imageURL = document.getElementById("imageURL").value;
            var presenter = document.getElementById("presenter").value;
            var maxUsers = document.getElementById("maxUsers").value;
            var description = document.getElementById("description").value;
            if (validateEventDateFields(type, eventName, startTime, endTime, date, locationName, description)) {
                switch (type) {
                    case "Workshop":
                        if (validateEventFields(imageURL)) {
                            if (presenter === "") {
                                alert("enter the name of the one presenting the workshop.");
                            } else if (maxUsers === "") {
                                alert("enter the maximal amount of people that can attend the workshop");
                            } else if (!isFinite(String(maxUsers))) {
                                alert("enter a number in the maxUsers field");
                            } else {
                                if (newEvent === true) {
                                    post('createWorkshop', {eventName: eventName, description: description, startTime: startTime, endTime: endTime, date: date, locationName: locationName, imageURL: imageURL, presenter: presenter, maxUsers: maxUsers});
                                } else {
                                    post('editEvent', {type: type, id: ${selectedItem.AddSpecialChars(selectedItem.getId())}, eventName: eventName, description: description, startTime: startTime, endTime: endTime, date: date, locationName: locationName, imageURL: imageURL, presenter: presenter, maxUsers: maxUsers});
                                }
                            }
                        }
                        break;
                    case "Lecture":
                        if (validateEventFields(imageURL)) {
                            if (presenter === "") {
                                alert("enter the name of the one presenting the Lecture.");
                            } else {
                                if (newEvent === true) {
                                    post('createLecture', {eventName: eventName, description: description, startTime: startTime, endTime: endTime, date: date, locationName: locationName, imageURL: imageURL, presenter: presenter});
                                } else {
                                    post('editEvent', {type: type, id: ${selectedItem.AddSpecialChars(selectedItem.getId())}, eventName: eventName, description: description, startTime: startTime, endTime: endTime, date: date, locationName: locationName, imageURL: imageURL, presenter: presenter});
                                }
                            }
                        }
                        break;
                    case "Performance":
                        if (validateEventFields(imageURL)) {
                            if (newEvent === true) {
                                post('createPerformance', {eventName: eventName, description: description, startTime: startTime, endTime: endTime, date: date, locationName: locationName, imageURL: imageURL});
                            } else {
                                post('editEvent', {type: type, id: ${selectedItem.AddSpecialChars(selectedItem.getId())}, eventName: eventName, description: description, startTime: startTime, endTime: endTime, date: date, locationName: locationName, imageURL: imageURL});
                            }
                        }
                        break;
                    case "None":
                        if (newEvent === true) {
                            post('createSchoolday', {eventName: eventName, description: description, startTime: startTime, endTime: endTime, date: date, locationName: locationName});
                        } else {
                            post('editEvent', {type: type, id: ${selectedItem.AddSpecialChars(selectedItem.getId())}, eventName: eventName, description: description, startTime: startTime, endTime: endTime, date: date, locationName: locationName});
                        }
                        break;
                }
            }
        };

        document.getElementById("delete-button").onclick = function () {
            if(${selectedItem.AddSpecialChars(selectedItem.getId())} !== "-1"){
                post('deleteEvent', {id: ${selectedItem.AddSpecialChars(selectedItem.getId())}, string: ${selectedItem.AddSpecialChars(selectedItem.getType())}});
            }else{
                alert('no item was selected');
            }
        };

        function validateEventDateFields(type, eventName, startTime, endTime, date, locationName, description) {
            var re = /^0[0-9]|1[0-9]|2[0-3]:[0-5][0-9]$/;
            if (type === "") {
                alert("select the type of event you want to create");
            } else if (eventName === "") {
                alert("EventName cannot be null");
            } else if (description === "") {
                alert("Give the event a description");
            } else if (locationName === "") {
                alert("provice the name of the location where the event takes place");
            } else if (startTime === "") {
                alert("The startTime cannot be null");
            } else if (!re.test(startTime)) {
                alert("The format of the startime should be hh:mm")
            } else if (endTime === "") {
                alert("The endTime cannot be null");
            } else if (!re.test(endTime)) {
                alert("The format of the endtime should be hh:mm");
            } else if (new Date("November 13, 2013 " + startTime) > new Date("November 13, 2013 " + endTime)) {
                alert("The startTime should be before the EndTime");
            } else if (!moment(date, 'DD-MM-YYYY', true).isValid()) {
                alert("The date should have a valid format: dd/mm/yyyy");
            } else {
                return true;
            }
            return false;
        };

        function validateEventFields(imageURL) {
            if (imageURL === "") {
                alert("Assign an immage URL to the event that you want to create");
            } else {
                return true;
            }
            return false;
        };

        function post(path, params, method) {
            method = method || "post"; // Set method to post by default if not specified.
            // The rest of this code assumes you are not using a library.
            // It can be made less wordy if you use one.
            var form = document.createElement("form");
            form.setAttribute("method", method);
            form.setAttribute("action", path);
            for (var key in params) {
                if (params.hasOwnProperty(key)) {
                    var hiddenField = document.createElement("input");
                    hiddenField.setAttribute("type", "hidden");
                    hiddenField.setAttribute("name", key);
                    hiddenField.setAttribute("value", params[key]);
                    form.appendChild(hiddenField);
                }
            }
            document.body.appendChild(form);
            form.submit();
        };
        </script>
    </body>
</html>
