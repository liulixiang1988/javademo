<%@ page import="java.net.URLDecoder" %>
<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 2015/4/10
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息</title>
</head>
<body>
<%
  request.setCharacterEncoding("utf-8");
  String username = "";
  String password = "";
  Cookie[] cookies = request.getCookies();
  if(cookies != null && cookies.length > 0){
    for(Cookie c : cookies){
      if(c.getName().equals("username")){
        username = URLDecoder.decode(c.getValue(), "utf-8") ;
      }
      if(c.getName().equals("password")){
        password = URLDecoder.decode(c.getValue(), "utf-8") ;
      }
    }
  }
%>
<input type="text" name="username" placeholder="用户名" value="<%=username%>"/><br/>
<input type="password" name="password" placeholder="密码" value="<%=password%>"/><br/>
</body>
</html>
