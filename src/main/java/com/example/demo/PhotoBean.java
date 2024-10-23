package com.example.demo;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PhotoBean {
    private Connection conn;

    public boolean storePhoto(String filePath, String memo, String fileName, String fileExtName, int filesize, String shortpath) {
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
            conn= DriverManager.getConnection(url,"root","123456");

            String sql = "INSERT INTO file_addresses (file_path_name, memo, file_name, file_extname, file_size, shortpath) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, filePath);
            stmt.setString(2, memo);
            stmt.setString(3, fileName);
            stmt.setString(4, fileExtName);
            stmt.setInt(5, filesize);
            stmt.setString(6, shortpath);

            int num = stmt.executeUpdate();
            return num >0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                closeConnection();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }
    }

    public boolean getPhoto(String selected, JspWriter out) {
        String[] photos = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
            conn = DriverManager.getConnection(url,"root","123456");

            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT shortpath FROM file_addresses";
            ResultSet rs = stmt.executeQuery(query);

            rs.last();
            int numRows = rs.getRow();
            rs.beforeFirst();

            photos = new String[numRows];

            int i = 0;
            while (rs.next()) {
                photos[i] = rs.getString("shortpath");
                i++;
            }
            int index = Integer.parseInt(selected);

            if (index < 0) {
                index = (index % photos.length) + photos.length;
            }
            else {
                index = index % photos.length;
            }

            out.println(photos[index]);
            out.flush();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println("Error occurred while fetching shortpaths: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
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
