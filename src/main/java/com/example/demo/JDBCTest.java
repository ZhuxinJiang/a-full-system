package com.example.demo;

import java.sql.*;

class JDBCTest{
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
            Connection conn = DriverManager.getConnection(url,"root","123456");
            Statement statement=conn.createStatement(); //建立 SQL 语句对象
            String sql="select * from users";
            ResultSet rs=statement.executeQuery(sql);//执行查询
/*String sql="select * from users where userName=?";
PreparedStatement prepStmt=conn.prepareStatement(sql);
prepStmt.setString(1, "test");//设置要传入的第一个参数(?)的值
ResultSet rs=prepStmt.executeQuery();*/
/*CallableStatement callStmt=conn.prepareCall("{call userQuery(?)}");
callStmt.setString(1,"test"); //设置要传入的第一个输入参数(?)的值是 7788
32
ResultSet rs=callStmt.executeQuery();*/
            while(rs.next()){//处理结果集
                System.out.print(rs.getString(1)+" ");
                System.out.print(rs.getString(2)+" ");
                System.out.print(rs.getString(3)+" ");
                System.out.println(rs.getString(4));
            }
/*sql = "insert into users (userName, userPwd) " + "values('test1','12345')";
int num = statement.executeUpdate(sql);
if(num > 0) System.out.println("数据插入成功！");
else System.out.println("数据插入失败！"); */
/*sql="update users set userPwd='54321' where userName='admin'";
int num = statement.executeUpdate(sql);
if(num > 0) System.out.println("数据修改成功！");
else System.out.println("数据修改失败！");*/
            rs.close();
            statement.close();
            conn.close();
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }catch(ClassNotFoundException classNotFound){
            classNotFound.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}