package com.example.demo;

import java.sql.*;

public class ShoppingCartBean {
    private static final String URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    //添加商品到购物车
    public void addToCart(String sp_Name, String username, int sp_Price, int sp_Inv,int sp_No) throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sql1 = "SELECT * FROM cart WHERE sp_Name=? AND username=?";
        PreparedStatement pstmt1 = conn.prepareStatement(sql1);
        pstmt1.setString(1, sp_Name);
        pstmt1.setString(2, username);
        ResultSet rs1 = pstmt1.executeQuery();
        if (rs1.next()) {
            // 如果存在相同商品，则更新购买数量
            int existingNum = rs1.getInt("buy_Num");
            int existingPrice = rs1.getInt("sp_Price");
            int newNum = existingNum + 1;
            int newPrice = existingPrice * newNum;
            String sql2 = "UPDATE cart SET buy_Num=?, buy_Count=? WHERE sp_Name=? AND username=?";
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1, newNum);
            pstmt2.setInt(2, newPrice);
            pstmt2.setString(3, sp_Name);
            pstmt2.setString(4, username);
            pstmt2.executeUpdate();
        } else {
            // 如果不存在相同商品，则插入新行
            String sql3 = "INSERT INTO cart(sp_Name, sp_Price, buy_Num, buy_Count, username,sp_No) VALUES ( ?, ?, ?, ?, ?,?)";
            PreparedStatement pstmt3 = conn.prepareStatement(sql3);
            pstmt3.setString(1, sp_Name);
            pstmt3.setInt(2, sp_Price);
            pstmt3.setInt(3, 1);
            pstmt3.setInt(4, sp_Price);
            pstmt3.setString(5, username);
            pstmt3.setInt(6,sp_No);
            pstmt3.executeUpdate();

        }
        String sql4 = "UPDATE shop SET sp_Inv =? WHERE sp_Name =?";
        PreparedStatement pstmt7 = conn.prepareStatement(sql4);
        int newinv = sp_Inv - 1;
        pstmt7.setInt(1, newinv);
        pstmt7.setString(2, sp_Name);
        pstmt7.executeUpdate();
    }

    //更新购物车中商品的数量和金额和商品库存
    public void updateCartItem(int id, int num, int price, int sp_No) throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql5 = "SELECT buy_Num FROM cart WHERE id = ?";
        PreparedStatement pstmt8 = conn.prepareStatement(sql5);
        pstmt8.setInt(1, id);
        ResultSet rs2 = pstmt8.executeQuery();

        if (rs2.next()) {
            int buyNum = rs2.getInt("buy_Num");

            String sql7 = "UPDATE cart SET buy_Num = ?, buy_Count = ? WHERE id=?";
            PreparedStatement pstmt4 = conn.prepareStatement(sql7);
            pstmt4.setInt(1, num);
            pstmt4.setInt(2, price * num);
            pstmt4.setInt(3, id);
            pstmt4.executeUpdate();

            int invx = buyNum - num;
            String sql6 = "UPDATE shop SET sp_Inv = sp_Inv + ? WHERE sp_No =?";
            PreparedStatement pstmt9 = conn.prepareStatement(sql6);
            pstmt9.setInt(1, invx);
            pstmt9.setInt(2, sp_No);
            pstmt9.executeUpdate();
        }
        // 关闭资源
        rs2.close();
        pstmt8.close();
        conn.close();
    }

    //从购物车中删除商品
    public void deleteCartItem(int id, int num, int sp_No) throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql10 = "UPDATE shop SET sp_Inv = sp_Inv + ? WHERE sp_No = ?";
        PreparedStatement pstmt10 = conn.prepareStatement(sql10);
        pstmt10.setInt(1, num);
        pstmt10.setInt(2, sp_No);
        pstmt10.executeUpdate();

        String sql8 = "DELETE FROM cart WHERE id = ?";
        PreparedStatement pstmt5 = conn.prepareStatement(sql8);
        pstmt5.setInt(1, id);
        pstmt5.executeUpdate();

    }

    // 清空购物车
    public void clearCart(String username) throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql11 = "SELECT sp_No, buy_Num FROM cart WHERE username = ?";
        PreparedStatement pstmt11 = conn.prepareStatement(sql11);
        pstmt11.setString(1, username);
        ResultSet cartItems = pstmt11.executeQuery();

        String updateShopQuery = "UPDATE shop SET sp_Inv = sp_Inv + ? WHERE sp_No = ?";
        PreparedStatement pstmt12 = conn.prepareStatement(updateShopQuery);

        while (cartItems.next()) {
            int sp_No = cartItems.getInt("sp_No");
            int buy_Num = cartItems.getInt("buy_Num");

            pstmt12.setInt(1, buy_Num);
            pstmt12.setInt(2, sp_No);
            pstmt12.executeUpdate();
        }

        String sql9 = "DELETE FROM cart WHERE username = ?";
        PreparedStatement pstmt6 = conn.prepareStatement(sql9);
        pstmt6.setString(1, username);
        pstmt6.executeUpdate();

        cartItems.close();
        pstmt11.close();
        pstmt12.close();
        pstmt6.close();
        conn.close();

    }

}
