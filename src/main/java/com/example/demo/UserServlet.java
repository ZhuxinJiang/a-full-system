package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//接受用户注册信息

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");

        UserBean userBean = new UserBean();
        boolean registered = userBean.registerUser(username, password, email, tel);

        //注册成功，重定向到登录页面
        if (registered) {
            response.sendRedirect("login.html");
        }

        //注册失败，返回注册页面重新注册
        else {
            response.sendRedirect("register.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
