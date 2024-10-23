package com.example.demo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class XML {
    public static void main(String[] args) {
        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
        String username = "root";
        String password = "123456";

        // 查询语句
        String query = "SELECT * FROM users";

        try {
            // 连接到数据库
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // 创建 XML 文档
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // 创建根元素
            Element rootElement = doc.createElement("users");
            doc.appendChild(rootElement);

            // 遍历结果集并将每个用户添加到 XML 中
            while (rs.next()) {
                String userName = rs.getString("username");
                String userPassword = rs.getString("password");
                String userEmail = rs.getString("email");
                String userPhone = rs.getString("phone");

                // 创建用户元素
                Element userElement = doc.createElement("user");
                rootElement.appendChild(userElement);

                Element nameElement = doc.createElement("username");
                nameElement.appendChild(doc.createTextNode(userName));
                userElement.appendChild(nameElement);

                Element passwordElement = doc.createElement("password");
                passwordElement.appendChild(doc.createTextNode(userPassword));
                userElement.appendChild(passwordElement);

                Element emailElement = doc.createElement("email");
                emailElement.appendChild(doc.createTextNode(userEmail));
                userElement.appendChild(emailElement);

                Element phoneElement = doc.createElement("phone");
                phoneElement.appendChild(doc.createTextNode(userPhone));
                userElement.appendChild(phoneElement);
            }

            // 将 XML 写入文件
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream("D:\\demo\\src\\main\\webapp\\users.xml"));
            StreamResult result2 = new StreamResult(new FileOutputStream("D:\\demo\\target\\demo-1.0-SNAPSHOT\\users.xml"));
            transformer.transform(source, result);
            transformer.transform(source, result2);

            System.out.println("XML 文件已创建成功！");

            // 关闭资源
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
