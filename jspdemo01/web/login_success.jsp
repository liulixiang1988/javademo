<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 15/4/10
  Time: 下午1:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>login success!</h1>
<%
  String loginUser = "";
  if(session.getAttribute("loginUser") != null){
    loginUser = session.getAttribute("loginUser").toString();
  }
%>

<p>欢迎，<%=loginUser%></p>
</body>
</html>
