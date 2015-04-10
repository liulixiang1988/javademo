<%@ page import="java.net.URLEncoder" %>
<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 2015/4/10
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h1>登录成功</h1>
<hr/>
<%
  request.setCharacterEncoding("utf-8");
  String[] isUseCookies = request.getParameterValues("isUseCookie");
  if (isUseCookies != null && isUseCookies.length > 0){
    //保存cookie
    String username = URLEncoder.encode(request.getParameter("username"), "utf-8") ;
    String password = URLEncoder.encode(request.getParameter("password"), "utf-8");
    Cookie usernameCookie = new Cookie("username", username);
    Cookie passwordCookie = new Cookie("password", password);
    usernameCookie.setMaxAge(864000); //10天过期
    passwordCookie.setMaxAge(864000);
    response.addCookie(usernameCookie);
    response.addCookie(passwordCookie);
  }else{
    //清楚cookie
    Cookie[] cookies = request.getCookies();
    if(cookies != null && cookies.length > 0){
      for(Cookie c : cookies){
        if(c.getName().equals("username") || c.getName().equals("password")){
          c.setMaxAge(0);
          response.addCookie(c); //设置好cookie后还要添加进去
        }
      }
    }
  }
%>

<a href="user.jsp">查看用户</a>
</body>
</html>
