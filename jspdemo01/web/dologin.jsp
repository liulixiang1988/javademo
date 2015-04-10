<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 15/4/10
  Time: 下午1:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String username = "";
  String password = "";
  request.setCharacterEncoding("utf-8");
  username = request.getParameter("username");
  password = request.getParameter("password");
  if ("admin".equals(username) &&  "admin".equals(password)){
    session.setAttribute("loginUser", username);
    request.getRequestDispatcher("login_success.jsp").forward(request, response);
  }
  else{
    response.sendRedirect("login_failure.jsp");
  }
%>