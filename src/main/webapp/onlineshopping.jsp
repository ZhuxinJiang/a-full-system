<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*"%>
<html>
<head>
  <title>在线购物</title>

  <style>
    .container{
      display: flex;
      justify-content: center;
    }

    table {
      width: 600px; /* 固定表格宽度 */
      table-layout: fixed; /* 使用固定布局 */
      height: 350px;
      margin-top: -15px;
    }

    td, th {
      word-wrap: break-word; /* 如果单元格内容过长，自动换行 */
      border: 1px solid #ddd;
      padding: 6px;
      text-align: center;
      border-radius: 8px;
      background-color: rgba(255,255,255,0.1);
    }


  </style>
</head>
<%
  Class.forName("com.mysql.jdbc.Driver").newInstance();
  String url="jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
  Connection conn = DriverManager.getConnection(url,"root","123456");
  Statement stmt=conn.createStatement();

//  String[] imageNames = {"Java基础入门", "Java面向对象程序设计", "Java程序设计", "test"};
//  Integer[] imagePrices = {45, 50, 35, 45};
//  String[] imagePaths = {"books/book1.png", "books/book2.png", "books/book3.png"};
//
//  for (int i = 0; i < imagePaths.length; i++){
//    String sql = "INSERT INTO shop (sp_No, sp_Name, sp_Price, sp_Info, sp_Pic) VALUES (?, ?, ?, ?, ?)";
//    PreparedStatement pstmt = conn.prepareStatement(sql);
//    pstmt.setInt(1, i+1); // 设置商品编号
//    pstmt.setString(2, imageNames[i]); // 设置商品名称
//    pstmt.setInt(3, imagePrices[i]); // 设置商品价格
//    pstmt.setString(4, "Java基础学习"); // 设置商品简介
//    pstmt.setString(5, imagePaths[i]); // 设置商品图片路径
//    pstmt.executeUpdate();
//  }

  //输出查询结果到页面
  String sql="select * from shop";
  ResultSet rs=stmt.executeQuery(sql);

%>
<body>
<h1>Online Shopping</h1>
<center>
  <div class="container">
  <table>
    <tr>
      <td align="center" width="80">商品缩图</td>
      <td align="center">商品摘要</td>
      <td align="center" width="100">在线购买</td>
    </tr>
<%
      String sp_Name,sp_Info,sp_Pic;
      int sp_No,sp_Price,sp_Inv;

//将查询结果集中的记录输出到页面上
      while (rs.next()){
//从当前记录中读取各字段的值
        sp_No = rs.getInt("sp_No");
        sp_Name = rs.getString("sp_Name").trim();
        sp_Price = rs.getInt("sp_Price");
        sp_Info = rs.getString("sp_Info").trim();
        sp_Pic = rs.getString("sp_Pic").trim();
        sp_Inv = rs.getInt("sp_Inv");
%>
    <tr>
      <td><img src='<%=sp_Pic%>' border=0 height=60 width=60></td>
      <td>商品编号：<%=sp_No%><br>
        商品名称：<%=sp_Name%><br>
        商品价格：<%=sp_Price%><br>
        商品简介：<%=sp_Info%><br>
        商品库存：<%=sp_Inv%></td>
      <td><a href='buy.jsp?op=add&sp_No=<%=sp_No%>&sp_Name=<%=sp_Name%>&sp_Price=<%=sp_Price%>&sp_Inv=<%=sp_Inv%>'>放入购物车</a></td>
    </tr>
<%
      }
%>
  </table>
  </div>
  <div style="margin-top: 15px;">
<a href="cart.jsp">查看购物车</a>&nbsp;&nbsp;
  <a href="buy.jsp?op=clear">清空购物车</a>
  </div>
</center>
</body>
</html>