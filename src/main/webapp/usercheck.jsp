<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="com.example.demo.UserBean " %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*,javax.servlet.http.*" %>

<!--接受用户登录信息-->

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    UserBean user = new UserBean();
    boolean loggedin = user.loginUser(username, password);

    // 验证通过，重定向到主页面，并将用户名作为参数传递
    if (loggedin) {
        session = request.getSession(true);
        session.setAttribute("username", username);
        response.sendRedirect("main.html");
    }
    // 验证失败，返回重新登录页面
    else {
        out.println("<script>");
        out.println("alert('登录失败:\\n用户名或密码不正确，请重试。');");
        out.println("window.location.href = 'login.html';");
        out.println("</script>");
    }
%>