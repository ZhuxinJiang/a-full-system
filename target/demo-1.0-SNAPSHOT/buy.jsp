<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.demo.ShoppingCartBean" %>
<%
  ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
  String op = request.getParameter("op");
  String userIdentifier = (String) session.getAttribute("username");
  request.setCharacterEncoding("UTF-8"); // 设置请求的字符编码为UTF-8

  if (op != null && userIdentifier != null) {
    if (op.equals("add")) {
      String sp_Name = request.getParameter("sp_Name");
      int sp_Price = Integer.parseInt(request.getParameter("sp_Price"));
      int sp_Inv = Integer.parseInt(request.getParameter("sp_Inv"));
      int sp_No = Integer.parseInt(request.getParameter("sp_No"));
      if (sp_Name != null && sp_Price!=0 && sp_Inv!= 0) {
        shoppingCartBean.addToCart(sp_Name, userIdentifier, sp_Price, sp_Inv,sp_No);
        response.sendRedirect("cart.jsp");
      } else {
        // 参数为空时给出错误提示
        out.println("<script>alert('商品参数为空！');</script>");
      }
    }

    else if (op.equals("update")) {
      int id = Integer.parseInt(request.getParameter("id"));
      int buy_Num = Integer.parseInt(request.getParameter("buy_Num"));
      int price = Integer.parseInt(request.getParameter("sp_Price"));
      int sp_No = Integer.parseInt(request.getParameter("sp_No"));

      if (id != 0 && buy_Num != 0 && price != 0) {
        shoppingCartBean.updateCartItem(id, buy_Num, price, sp_No);
        response.sendRedirect("cart.jsp");
      } else {
        out.println("更新购物车项失败：参数不完整！");
      }
    }

    else if (op.equals("del")) {
      int id = Integer.parseInt(request.getParameter("id"));
      int sp_No = Integer.parseInt(request.getParameter("sp_No"));
      int buy_Num = Integer.parseInt(request.getParameter("buy_Num"));
      if (id != 0 && sp_No != 0 && buy_Num != 0) {
        shoppingCartBean.deleteCartItem(id, buy_Num, sp_No);
        response.sendRedirect("cart.jsp");
      } else {
        out.println("删除购物车项失败：参数不完整！");
      }
    }

    else if (op.equals("clear")) {
      shoppingCartBean.clearCart(userIdentifier);
      response.sendRedirect("cart.jsp");
    }
  }

  else {
    out.println("操作失败：缺少必要参数！");
  }
%>
