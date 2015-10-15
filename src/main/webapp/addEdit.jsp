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

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List</title>
    </head>
    <body>
        <h1>Author List</h1>
        
        <form method="POST" action="AuthorController">
            <table width="500" border="1" cellspacing="0" cellpadding="4">
                <!--
                    In the EL expression below using 'not empty' is better than
                    author != null because it tests for both null and empty string
                -->
                <c:choose>
                    <c:when test="${not empty author}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">ID</td>
                            <td align="left"><input type="text" value="${author.authorId}" name="authorId" readonly/></td>
                        </tr>         
                    </c:when>
                </c:choose>
                
                <tr>
                    <td style="background-color: black;color:white;" align="left">Name</td>
                    <td align="left"><input type="text" value="${author.authorName}" name="authorName" /></td>
                </tr>
                
                <c:choose>
                    <c:when test="${not empty author}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">Date Added</td>
                    <td align="left"><input type="text" value="${author.dateAdded}" name="dateAdded" readonly /></td>
                        </tr>         
                    </c:when>
                </c:choose>
                
                <tr>
                    <input type="submit" value="Cancel" name="action" />&nbsp;
                    <input type="submit" value="Save" name="action" />
                </tr>
            </table>
        </form>
    </body>
</html>
