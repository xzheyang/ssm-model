<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/9/9
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login_user</title>
</head>
<body>

<h3>学生登录</h3>

<form action="loginUser" method="post">
    <input type="hidden" name="id" value="1"><br>
    un:<input type="text" name="username"><br>
    ps:<input type="password" name="password"><br>
    <input type="submit" value="提交">
</form>

</body>
</html>
