<%-- 
    Document   : qrcodes
    Created on : 3-mei-2017, 9:51:51
    Author     : David
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <spring:url value="/css/qrcodes.css" var="qrcodes" />
        <link href="${qrcodes}" rel="stylesheet" />
        <title>QR-codes</title>
    </head>
    <body>
        <div class="wrapper">
            <h1>QR-codes</h1>
        </div>
    </body>
</html>
