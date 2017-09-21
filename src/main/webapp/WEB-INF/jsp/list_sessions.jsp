<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/9/21
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<html>
<head>
    <title>ListSessions</title>
</head>
<body>

    <table>
        <tr>
            <td>
                sessionId
            </td>
            <td>
                userName
            </td>
        </tr>

        <c:forEach items="${sessions}" var="session" begin="0" varStatus="status" >
            <tr >
                <td>
                    ${session.sessionId}
                </td>
                <td>
                    ${session.userName}
                </td>
                <form action="/forceLogOutSession" >
                <td>
                    <input type="hidden" name="outSessionId" value="${session.sessionId}">
                    <input type="submit" value="下线">
                </td>
                </form><form action="/forbidLogin">
                <td>
                    <input type="hidden" name="outSession" value="${session}">
                    <input type="submit" value="禁止登录">
                </td>
                </form>
            </tr>
        </c:forEach>
    </table>
</form>



</body>
</html>
