<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@ page import="java.sql.*"%>
<html>
<head><title>�ҵĹ��ﳵ</title></head>
<%
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    String url="jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    Connection conn = DriverManager.getConnection(url,"root","123456");
    String userIdentifier = (String) session.getAttribute("username");
    String sql = "SELECT * FROM cart WHERE username=?";
    PreparedStatement pstat = conn.prepareStatement(sql);
    pstat.setString(1, userIdentifier);
    ResultSet rs = pstat.executeQuery();
%>
<body>
<center>
    <h2>�ҵĹ��ﳵ</h2><hr>
    <table border="1" width="600">
        <tr bgcolor="#dddddd">
            <td align="center" width="80">��Ʒ����</td>
            <td align="center">��Ʒ����</td>
            <td align="center" width="100">��������</td>
            <td align="center" width="100">���</td>
            <td align="center" width="100">�༭</td>
        </tr>
        <%
            String sp_Name;
            int id,buy_Num,buy_Count,sp_Price,sp_No;
            while (rs.next()){
                id = rs.getInt("id");
                sp_Name = rs.getString("sp_Name").trim();
                sp_Price = rs.getInt("sp_Price");
                buy_Num = rs.getInt("buy_Num");
                buy_Count = rs.getInt("buy_Count");
                sp_No = rs.getInt("sp_No");
        %>
        <tr>
            <td><%=sp_Name%></td>
            <td><%=sp_Price%></td>
            <td><input type=text value=<%=buy_Num%>
                    onChange="updateNum(<%=id%>,this.value,<%=sp_Price%>,<%=sp_No%>)"></td>
            <td><%=buy_Count%></td>
            <td><a href='buy.jsp?op=del&id=<%=id%>&buy_Num=<%=buy_Num%>&sp_No=<%=sp_No%>'>�˻���Ʒ��</a></td>
        </tr>
        <%}%>
    </table>
    <br>
    <a href="onlineshopping.jsp">��������</a>&nbsp;&nbsp;
    <a href="buy.jsp?op=clear">��չ��ﳵ</a>
</center>
</body>
<script language="javascript">
    function updateNum(id,buy_Num,sp_Price,sp_No){
        var url = "buy.jsp?op=update&id="+id+"&buy_Num="+buy_Num+"&sp_Price="+sp_Price+"&sp_No="+sp_No;
        window.location = url;
    }
</script>
</html>