<%-- 
    Document   : editBook
    Created on : Oct 26, 2015, 7:40:09 PM
    Book     : Keiji
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
        <h1>Book List</h1>
        
        <form method="POST" action="BookController?action=submitRequest">
            <sec:csrfInput />
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <table width="500" border="1" cellspacing="0" cellpadding="4">
                <!--
                    In the EL expression below using 'not empty' is better than
                    book != null because it tests for both null and empty string
                -->
                <c:choose>
                    <c:when test="${not empty book}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">ID</td>
                            <td align="left"><input type="text" value="${book.bookId}" name="bookId" readonly/></td>
                        </tr>  
                        <tr>
                            <td style="background-color: black;color:white;" align="left">Title</td>
                            <td align="left"><input type="text" value="${book.bookTitle}" name="bookName" /></td>
                        </tr>
                        <tr>
                            <td style="background-color: black;color:white;" align="left">ISBN</td>
                            <td align="left"><input type="text" value="${book.bookISBN}" name="bookName" /></td>
                        </tr>
                    </c:when>
                </c:choose>
                
                <tr>
                    <input type="submit" value="Cancel" name="submit" />&nbsp;
                    <input type="submit" value="Save" name="submit" />
                </tr>
            </table>
            </sec:authorize>
        </form>
    </body>
</html>
