<%--
  Created by IntelliJ IDEA.
  User: liulixiang
  Date: 2015/4/9
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<html>
<head>
    <title>九九乘法表</title>
</head>
<body>
<%!
  String printMultiTable(){
    String s = "";
    for(int i = 1; i <= 9; i++){
      for(int j=1; j<=i; j++){
        s += j + "*" + i + "=" + i*j+"&nbsp;&nbsp;";
      }
      s+="<br/>";
    }
    return s;
  }

  void printMultiTable2(JspWriter out){
    for(i=1; i<=9; i++){
      for(j=1; j<=i; j++){
        out.println()
      }
    }
  }
%>

<h1>九九乘法表</h1>
<hr/>
<%=printMultiTable()%>
</body>
</html>
