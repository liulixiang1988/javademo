<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 2015/4/10
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>使用usebean</title>
</head>
<body>
  <jsp:useBean id="myUsers" class="liulx.javabean.User" scope="page" />
  <h1>使用useBean动作创建javaBeans</h1>
  <hr/>
用户名：<%=myUsers.getUsername()%>
密码：<%=myUsers.getPassword()%>
</body>
</html>
