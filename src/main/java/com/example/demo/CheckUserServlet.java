package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CheckUserServlet")
public class CheckUserServlet extends HttpServlet {
    public void destroy() {super.destroy();}
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String registerName = request.getParameter("registerName");
        CheckBean checkBean = new CheckBean();
        String responseContext = null;
        try {
            responseContext = checkBean.check(registerName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println(responseContext);
        out.flush();
        out.close();
    }
    public void init() throws ServletException {}
}