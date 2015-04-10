<%@ page import="java.net.URLDecoder" %>
<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 2015/4/10
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>cookie登录</title>
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
  <form action="dologin.jsp" method="post">
    <input type="text" name="username" placeholder="用户名" value="<%=username%>"/><br/>
    <input type="password" name="password" placeholder="密码" value="<%=password%>"/><br/>
    <input type="checkbox" name="isUseCookie" checked="checked"/>记住我
    <input type="submit" value="登录"/>
  </form>
  </body>
</html>
