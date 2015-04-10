<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 2015/4/10
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>setProperty使用</title>
</head>
<body>
<h1>用户登录</h1>
<hr/>
<form action="dologin.jsp" method="post">
  <table>
    <tr>
      <td>用户名</td>
      <td><input type="text" name="username"/></td>
    </tr>
    <tr>
      <td>密码</td>
      <td><input type="password" name="password"/></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="提交"/></td>
    </tr>
  </table>
</form>
</body>
</html>
