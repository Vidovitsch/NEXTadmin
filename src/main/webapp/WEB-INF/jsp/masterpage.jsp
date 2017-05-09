<%-- 
    Document   : masterpage
    Created on : Mar 20, 2017, 10:27:23 AM
    Author     : mmjan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fn" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <spring:url value="/css/containerMaster.css" var="containerCSS" />
        <link href="${containerCSS}" rel="stylesheet" />
        
        <decorator:head />
                
        <title>
            <decorator:title />
        </title>
        
    </head>
    <body>
        <div id="next-header">
            <a href="index"><img id="nav-logo" src="/images/next_logo.png" /></a>
            <ul class="nav-list">
                <li class="nav-item"><a href="/qrcodes">QR-Codes</a></li>
                <li class="nav-item"><a href="/mapcreation">Map Tool</a></li>
                <li class="nav-item"><a href="/adminpanel">Activity panel</a></li>
            </ul>
        </div>
        
        <decorator:body />
        
    </body>
</html>