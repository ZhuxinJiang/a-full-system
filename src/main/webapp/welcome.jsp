<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>

    <style>
        body, html {
            height: 100%;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        h1 {
            text-align: center;
        }
    </style>
</head>
<body>
<%
// 获取 session 中的用户名
String username = (String) session.getAttribute("username");
%>
<h1>Welcome, <%= username %>!</h1>
</body>
</html>