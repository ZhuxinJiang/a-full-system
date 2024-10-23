package com.example.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class JSON {

    // JDBC连接参数
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {
        // 从数据库中读取数据并保存为 JSON 文件
        readDataAndSaveToJson();

    }

    // 从数据库中读取数据并保存为 JSON 文件
    public static void readDataAndSaveToJson() {
        Connection conn = null;
        PreparedStatement stmt = null;
        JSONArray userData = new JSONArray();

        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 打开连接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            String sql = "SELECT * FROM users";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // 处理结果集
            while (rs.next()) {
                // 将每个用户的信息存储在一个 JSON 对象中
                JSONObject user = new JSONObject();
                user.put("username", rs.getString("username"));
                user.put("password", rs.getString("password"));
                user.put("email", rs.getString("email"));
                user.put("phone", rs.getString("phone"));
                userData.add(user);
            }
            rs.close();

            // 将用户数据保存到 JSON 文件中
            saveToJSON(userData, "D:\\demo\\src\\main\\webapp\\users.json");
            saveToJSON(userData,"D:\\demo\\target\\demo-1.0-SNAPSHOT\\users.json");

            System.out.println("JSON文件中已创建成功！");


        } catch (SQLException | ClassNotFoundException | IOException se) {
            // 处理错误
            se.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {}
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    // 将用户数据保存到 JSON 文件中的方法
    public static void saveToJSON(JSONArray data, String filename) throws IOException {
        try (FileWriter file = new FileWriter(filename)) {
            file.write(data.toJSONString());
        }
    }

}
