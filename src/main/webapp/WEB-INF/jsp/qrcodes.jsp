<%-- 
    Document   : qrcodes
    Created on : 3-mei-2017, 9:51:51
    Author     : David
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <spring:url value="/css/qrcodes.css" var="qrcodes" />
        <link href="${qrcodes}" rel="stylesheet" />
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-app.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-auth.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-database.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.2/firebase.js"></script>
        <title>QR-codes</title>
    </head>
    <body>
        <div class="wrapper">
            <c:choose>
                <c:when test="${vModel.generated == 'false'}">
                    <div id="not-generated-wrapper">
                        <form action="/qrcodes" method="post" id="form-gen-codes">
                            <input type="submit" value="Generate Codes" name="Submit" id="btn-gen-codes" />
                        </form>
                    </div>
                </c:when>    
                <c:otherwise>
                    <div id="forms-default" />
                        <form action="javascript:downloadCodes();" method="post" id="form-default">
                            <input type="submit" value="Download Codes" name="Submit" class="btn-form-default" />
                        </form>
                    </div>
                <br>
                <c:forEach var="row" items="${vModel.codes}">
                    <img src="${row}" />
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <script src="jszip.js"></script>
    <script src="FileSaver.js"></script>
    <script>
        var config = {
            apiKey: "AIzaSyCRi0Ma5ekQxhwg-BfQCa6684hMzvR3Z1o",
            authDomain: "nextweek-b9a58.firebaseapp.com",
            databaseURL: "https://nextweek-b9a58.firebaseio.com",
            storageBucket: "nextweek-b9a58.appspot.com",
            messagingSenderId: "488624254338"
        };
        firebase.initializeApp(config);
        var database = firebase.database();
        var zip = new JSZip();
        var img = zip.folder("QR-codes");
        function downloadCodes() {
            firebase.database().ref('/QRCode/').once('value').then(function (snapshot) {
                snapshot.forEach(function (childSnapshot) {
                    var key = childSnapshot.key.toString();
                    var value = childSnapshot.val().toString().split(",")[1];
                    img.file(key + ".png", value, {base64: true});
                });
                zip.generateAsync({type: "blob"}).then(function (content) {
                    saveAs(content, "QR-codes.zip");
                });
            });
        }
    </script>
</body>
</html>
