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
                <select name="cbSearchType">
                    <c:forEach items="${types}" var="id">
                        <option value="${id}">${id}</option>
                    </c:forEach>
                </select>
                <table>
                    
                </table>
            </div>
            <div class="create-modify-event">
                <div class="createType">
                    <select class="dbType" id="dbType">
                        <option></option>/>
                        <c:forEach items="${types}" var="id">
                            <option value="${id}">${id}</option>
                        </c:forEach>
                    </select>
                </div>
                <c:forEach var="row" items="${fields}">
                    <div class="${row}div fieldDiv" id="${row}div">
                        <span class="fieldSpan">${row}:</span>
                        <input class="form-control fieldInput" type="text" id="${row}" name="${row}" required></br>
                    </div>
                </c:forEach>    
                <div class="descriptiondiv fieldDiv" id="${row}div">
                    <span class="descriptionSpan">Description:</span><br>
                    <textarea class="form-control" id="description" name="description"></textarea></br>
                </div>
                <input id="create-button" class="form-control" type="submit" value="createEvent" onclick/>      
            </div>
        </div>
        
        <script type="text/javascript" src="jquery-ui-1.10.0/tests/jquery-1.9.0.js"></script>
        <script src="jquery-ui-1.10.0/ui/jquery-ui.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" ></script>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>‌​
        <script src="../../../javascript/moment.js"></script>
        <script>    
        $('#dbType').on('change',function(){
        var selection = $(this).val();
        switch(selection){
            case "Workshop":
                $("#iddiv").hide();
                $("#imageURLdiv").show();
                $("#presenterdiv").show();
                $("#maxUsersdiv").show();
                break;
            case "Lecture":
                $("#iddiv").hide();
                $("#imageURLdiv").show();
                $("#presenterdiv").show();
                $("#maxUsersdiv").hide();
                break;
            case "Performance":
                $("#iddiv").hide();
                $("#imageURLdiv").show();
                $("#presenterdiv").hide();
                $("#maxUsersdiv").hide();
                break;
            case "None":
                $("#iddiv").hide();
                $("#imageURLdiv").hide();
                $("#presenterdiv").hide();
                $("#maxUsersdiv").hide();
                break;
       }
   });
   
   document.getElementById("create-button").onclick = function () {
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
       if(validateEventDateFields(type, eventName, startTime, endTime, date, locationName, description)){
            if("".localeCompare(id) === 0){
                switch(type) {
                     case "Workshop":
                         if(validateEventFields(imageURL)){
                             if(presenter === ""){
                                 alert("enter the name of the one presenting the workshop.");
                             }else if(maxUsers === ""){
                                 alert("enter the maximal amount of people that can attend the workshop");
                             }else if(!isFinite(String(maxUsers))){
                                 alert("enter a number in the maxUsers field");
                             }else{
                                 alert("event is being created");
                                 post('createWorkshop', {eventName : eventName, description : description, startTime : startTime, endTime : endTime, date : date, locationName : locationName, imageURL : imageURL, presenter: presenter, maxUsers: maxUsers});
                             }
                         }
                         break;
                     case "Lecture":
                         if(validateEventFields(imageURL)){
                             if(presenter === ""){
                                 alert("enter the name of the one presenting the Lecture.");
                             }else{
                                 post('createLecture', {eventName : eventName, description : description, startTime : startTime, endTime : endTime, date : date, locationName : locationName, imageURL : imageURL, presenter : presenter});
                             }
                         }
                         break;
                     case "Performance":
                         if(validateEventFields(imageURL)){
                             post('createPerformance', {eventName : eventName, description : description, startTime : startTime, endTime : endTime, date : date, locationName : locationName, imageURL : imageURL});
                         }
                         break;
                     case "None":
                         post('createSchoolday', {eventName : eventName, description : description, startTime : startTime, endTime : endTime, date : date, locationName : locationName});
                         break;
                 }
            }
        }
    }
   
   function validateEventDateFields(type, eventName, startTime, endTime, date, locationName, description){
        var re=/^0[0-9]|1[0-9]|2[0-3]:[0-5][0-9]$/;
        if(type === ""){
            alert("select the type of event you want to create");
        }else if(eventName === ""){
           alert("EventName cannot be null");
       }else if(description === ""){
           alert("Give the event a description");
       }else if(locationName === ""){
           alert("provice the name of the location where the event takes place");
       }else if(startTime === ""){
           alert("The startTime cannot be null");
       }else if(!re.test(startTime)){
           alert("The format of the startime should be hh:mm")
       }else if(endTime === ""){
           alert("The endTime cannot be null");
       }else if(!re.test(endTime)){
           alert("The format of the endtime should be hh:mm");
       }else if(new Date("November 13, 2013 " + startTime) > new Date("November 13, 2013 " + endTime)){
           alert("The startTime should be before the EndTime");
       }else if(!moment(date, 'DD-MM-YYYY',true).isValid()){
           alert("The date should have a valid format: dd/mm/yyyy")
       }else{
           return true;
       }
       return false;
   }
   
   function validateEventFields(imageURL){
       if(imageURL === ""){
           alert("Assign an immage URL to the event that you want to create");
       }else{
           return true;
       }
       return false;
   }
   
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
            }
        </script>
    </body>
</html>
