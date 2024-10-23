<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.demo.PhotoBean" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>在线交互式电子相册</title>
    <meta http-equiv="content-type" content="text/html; charset=gb2312">
    <style>
        .container{
            display: flex;
            justify-content: center;
            margin-top: -30px;
        }

        #photo {
            width: 500px;
            height: 400px;
        }

        button {
            padding: 5px; /* 增加内边距使按钮稍微大一些 */
            width: 100px;
            height: 29px;
            background-color: rgba(255, 255, 255, 0.3);
            border: 1px solid white ;
            border-radius: 15px; /* 添加圆角 */
            font-size: 14px;
        }
    </style>

    <meta name="keywords" content="keyword1,keyword2,keyword3">
    <meta name="description" content="this is my page">
    <meta name="content-type" content="text/html; charset=gb2312">

</head>
<script language="javascript">
    var xmlHttp;
    var selected = 0;

    function createXMLHttpRequest() {
        if (window.ActiveXObject)
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        else if (window.XMLHttpRequest)
            xmlHttp = new XMLHttpRequest();
    }

    function getPhoto() {
        createXMLHttpRequest();
        xmlHttp.onreadystatechange = processor;
        xmlHttp.open("GET", "getphoto.jsp?selected=" + selected);
        xmlHttp.send(null);
    }

    function processor() {
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                document.getElementById("photo").src = xmlHttp.responseText;
            }
        }
    }

    function prev() {
        selected = selected - 1;
        getPhoto();
    }

    function next() {
        selected = selected + 1;
        getPhoto();
    }
</script>
<body>
<h1>Album</h1>
<div class="container">
    <table border="0" >
        <tr>
            <td colspan="2" align="center"><img id="photo" src="uploads/0.jpg"></td>
        </tr>
        <tr>
            <td align="center"><button onclick="prev()">上一张</button></td>
            <td align="center"><button onclick="next()">下一张</button></td>
        </tr>
    </table>
</div>
</body>

</html>
