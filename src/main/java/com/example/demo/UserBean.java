package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserBean {
    private Connection conn;

    public boolean registerUser(String username, String password, String email, String tel) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
            conn= DriverManager.getConnection(url,"root","123456");

            String sql = "INSERT INTO users (username, password, email, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, tel);

            int num = statement.executeUpdate();
            return num >0;
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean loginUser(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
            conn= DriverManager.getConnection(url,"root","123456");

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            boolean loggedIn =statement.executeQuery().next();
            return loggedIn;
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
