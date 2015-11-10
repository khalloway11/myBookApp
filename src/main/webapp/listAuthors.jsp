<%-- 
    Document   : listAuthors
    Created on : Sep 21, 2015, 9:36:05 PM
    Author     : jlombardo
    Purpose    : display list of author records and (in the future) provide
                 a way to add/edit/delete records
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
        <title>Author List</title>
    </head>
    <body>
        <h1>Author List</h1>
        <sec:authorize access="hasAnyRole('ROLE_USER')">Logged in as User</sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_MGR')">Logged in as Manager
    
        <form method="POST" action="AuthorController?action=submitRequest">
        <input type="submit" value="Add" name="submit" />&nbsp;
        <input type="submit" value="Edit" name="submit" />&nbsp;
        <input type="submit" value="Delete" name="submit" />
        <br><br>
        </sec:authorize>
        <table width="500" border="1" cellspacing="0" cellpadding="4">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead">ID</th>
                <th align="left" class="tableHead">Author Name</th>
                <th align="right" class="tableHead">Date Added</th>
            </tr>
        <c:forEach var="a" items="${authors}" varStatus="rowCount">
            <c:choose>
                <c:when test="${rowCount.count % 2 == 0}">
                    <tr style="background-color: white;">
                </c:when>
                <c:otherwise>
                    <tr style="background-color: #ccffff;">
                </c:otherwise>
            </c:choose>
            <td><input type="checkbox" name="authorId" value="${a.authorId}" /></td>
            <td align="left">${a.authorName}</td>
            <td align="right">
                <fmt:formatDate pattern="M/d/yyyy" value="${a.dateCreated}"></fmt:formatDate>
            </td>
        </tr>
        </c:forEach>
        </table>
        <br>
        <sec:authorize access="hasAnyRole('ROLE_MGR')">
        <input type="submit" value="Add" name="submit" />&nbsp;
        <input type="submit" value="Edit" name="submit" />&nbsp;
        <input type="submit" value="Delete" name="submit" />
        </sec:authorize>
        </form>
        
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
        <a href="index.html">Back to Index</a>
    </body>
</html>
