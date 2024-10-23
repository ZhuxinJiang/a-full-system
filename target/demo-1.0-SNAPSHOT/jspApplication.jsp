<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>网站计数器</title>
</head>
<body>
<%
  if(application.getAttribute("count") ==null){
    application.setAttribute("count", "1");
    out.println("欢迎，您是第 1 位访客！");
  }else{
    int i=Integer.parseInt((String)application.getAttribute("count"));
    i++;
    application.setAttribute("count", String.valueOf(i));
    out.println("欢迎，您是第"+i+"位访客！");
  }
%>
</body>
</html>