<%-- 
    Document   : listBooks
    Created on : Oct 26, 2015, 7:40:21 PM
    Author     : Keiji
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Author List</h1>
        
        <form method="POST" action="BookController?action=submitRequest">
        <input type="submit" value="Add" name="submit" />&nbsp;
        <input type="submit" value="Edit" name="submit" />&nbsp;
        <input type="submit" value="Delete" name="submit" />
        <br><br>
        <table width="500" border="1" cellspacing="0" cellpadding="4">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead">ID</th>
                <th align="left" class="tableHead">Book Title</th>
                <th align="left" class="tableHead">ISBN</th>
            </tr>
        <c:forEach var="a" items="${books}" varStatus="rowCount">
            <c:choose>
                <c:when test="${rowCount.count % 2 == 0}">
                    <tr style="background-color: white;">
                </c:when>
                <c:otherwise>
                    <tr style="background-color: #ccffff;">
                </c:otherwise>
            </c:choose>
            <td><input type="checkbox" name="bookId" value="${a.bookId}" /></td>
            <td align="left">${a.bookTitle}</td>
            <td align="left">${a.bookIsbn}</td>
        </tr>
        </c:forEach>
        </table>
        <br>
        <input type="submit" value="Add" name="submit" />&nbsp;
        <input type="submit" value="Edit" name="submit" />&nbsp;
        <input type="submit" value="Delete" name="submit" />
        </form>
        
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
                    <a href="index.html">Back to Index</a>
    </body>
</html>
