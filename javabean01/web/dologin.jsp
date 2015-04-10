<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 2015/4/10
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<jsp:useBean id="myUsers" class="liulx.javabean.User" scope="application" />
<h1>根据表单自动匹配所有属性</h1>
<hr/>
<%request.setCharacterEncoding("utf-8");%>
<jsp:setProperty name="myUsers" property="*" />
用户名：<%=myUsers.getUsername()%>
密码：<%=myUsers.getPassword()%>

<a href="testScope.jsp">测试JavaBean链接</a>
</body>
</html>
