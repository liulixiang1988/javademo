<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 2015/4/10
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试Java Bean Scope</title>
</head>
<body>
<h1>测试Java Bean Scope</h1>
<hr/>
<jsp:useBean id="myUsers" class="liulx.javabean.User" scope="application" />
用户名：<jsp:getProperty name="myUsers" property="username"/>
密码：<jsp:getProperty name="myUsers" property="password" />
</body>
</html>
