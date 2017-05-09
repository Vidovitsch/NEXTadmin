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
                        <form action="/qrcodes" method="post" id="form-default">
                            <input type="submit" value="Re-generate Codes" name="Submit" class="btn-form-default" />
                        </form>
                        <form action="/qrcodes" method="post" id="form-default">
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
    </body>
</html>
