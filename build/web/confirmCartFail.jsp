<%-- 
    Document   : confirmCartFail
    Created on : 18-Jan-2021, 16:54:51
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hana Shop</title>
    </head>
    <body>
        <c:set var="listItemErr" value="${requestScope.LIST_ITEM_OUT_STORAGE}"/>

        <h1>Error</h1>

        <br/>

        <c:forEach var="itemName" items="${listItemErr}" >
            <h2>
                ${itemName} is not enough quantity in storage
            </h2>
        </c:forEach>
        <br/>
    </body>
</html>
