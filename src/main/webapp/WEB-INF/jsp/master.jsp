<%-- 
    Document   : master
    Created on : 18-mrt-2017, 12:37:48
    Author     : David
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <spring:url value="/css/containerMaster.css" var="masterCSS" />
        <link href="${masterCSS}" rel="stylesheet" />
    </head>
    <body>
        <div id="next-header">
            <a href="index.htm"><img id="nav-logo" src="/images/next_logo.png" /></a>
            <ul class="nav-list">
                <li class="nav-item"><a href="/adminpanel">Panel</a></li>
            </ul>
        </div>
        <div id="spacer"></div>
    </body>
</html>
