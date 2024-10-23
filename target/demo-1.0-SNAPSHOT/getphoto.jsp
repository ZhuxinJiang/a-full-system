<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="com.example.demo.PhotoBean" %>


<%
    String selected = request.getParameter("selected");

    if (selected != null) {
        PhotoBean photoBean = new PhotoBean();
        try {
            photoBean.getPhoto(selected, out);
        } finally {
        }
    } else {
        out.println("未选择照片。");
        }
%>
