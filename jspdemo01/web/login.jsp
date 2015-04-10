<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 15/4/10
  Time: 下午1:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<form action="dologin.jsp" method="post">
  <label for="username">user name</label>
  <input type="text" name="username" placeholder="用户名"/>
  <label for="password">password</label>
  <input type="password" name="password"/>
  <input type="submit" value="login"/>
</form>
</body>
</html>
