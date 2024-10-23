<%@ page contentType="text/html;charset=gb2312" language="java" %>
<%@ taglib uri="/calculator" prefix="calc" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="gb2312">
    <title>Calculator</title>
    <style>
        .container {
            position: absolute;
            top: 45%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 10px;
            text-align: center;
            width: 800px; /*�������*/
            height: 200px;
        }
        form {
            display: inline-block;
        }
        input[type="text"], input[type="submit"] ,select{
            padding: 12px; /*�����Ͱ�ť���ڱ߾�*/
            font-size: 18px;
            border-radius: 10px;
        }
        select option {
            font-size: 16px; /* ���������б�ѡ��������С */
        }
    </style>
</head>
<body>
<h1>Calculator</h1>
<div class="container">
<form action="#" method="post" >
    <input type="text" name="num1" placeholder="Enter number 1">
    <select name="operator">
        <option value="+">+</option>
        <option value="-">-</option>
        <option value="*">*</option>
        <option value="/">/</option>
    </select>
    <input type="text" name="num2" placeholder="Enter number 2">
    <input type="submit" value="Calculate">
</form>

<%
    //�����Ƿ����ύ
    if (request.getMethod().equalsIgnoreCase("POST")) {
        //��ȡ�����������м���
        String num1Str = request.getParameter("num1");
        String num2Str = request.getParameter("num2");
        String operator = request.getParameter("operator");

        if (num1Str != "" && num2Str != "") {
            double num1 = Double.parseDouble(num1Str.trim());
            double num2 = Double.parseDouble(num2Str.trim());
            pageContext.setAttribute("num1", num1);
            pageContext.setAttribute("num2", num2);
            pageContext.setAttribute("operator", operator);
%>
<calc:calculate num1="${num1}" num2="${num2}" operator="${operator}" />
<%
        } else {
            out.println("<p>Please enter valid numbers and operator!</p>");
        }
    }
%>
</body>
</html>
