package com.example.demo;

import java.sql.*;

public class CheckBean {
    private Connection conn;

    public String check(String registerName) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
            conn= DriverManager.getConnection(url,"root","123456");

            String sql = "SELECT username FROM users";
            PreparedStatement statement = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery(sql);
            rs.last();
            int numRows = rs.getRow();
            rs.beforeFirst();

            String[] registered = new String[numRows];

            int i = 0;
            while (rs.next()) {
                registered[i] = rs.getString("username");
                i++;
            }

            String responseContext = "true";
            for (int j=0;j<registered.length;j++){
//遍历已注册用户列表，如果发现提交的注册名已存在，则修改响应内容
                if (registerName.equals(registered[j])) {
                    responseContext = "false";
                    break;
                }
            }
            return responseContext;
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return "false";
        }finally {
            conn.close();
        }
    }
}
