<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>注册页面</title>

  <link rel="stylesheet" type="text/css" href="register.css">

  <script>
    window.onload = function() {
      updateCheckCodeImage();

      var form = document.getElementById("form");
      var username = document.getElementById("username");
      var password = document.getElementById("password");
      var email = document.getElementById("email");
      var tel = document.getElementById("tel");
      var checkcodeInput = document.getElementById("checkcode");

      function handleMouseEvents(element) {
        element.addEventListener('mouseover', function() {
          element.style.backgroundColor = 'rgba(255, 255, 255, 0.7)';
        });
        element.addEventListener('mouseout', function() {
          element.style.backgroundColor = 'rgba(255, 255, 255, 0.6)';
        });
      }
      handleMouseEvents(username);
      handleMouseEvents(password);
      handleMouseEvents(email);
      handleMouseEvents(tel);
      handleMouseEvents(checkcodeInput);

      //提交键检查
      document.getElementById("form").onsubmit = function(){
        return checkUsername() && checkPassword() && checkCode();
      };

      //实时用户名检查
      document.getElementById("username").addEventListener('blur', function() {
        checkUsername();
      });

      //实时密码检查
      document.getElementById("password").addEventListener('blur', function() {
        checkPassword();
      });

      //实时验证码检查
      document.getElementById("checkcode").addEventListener('blur', function() {
        checkCode();
      });
    };

    //检查用户名
    function checkUsername(){
      var username = document.getElementById("username").value;
      var reg_username = /^\w{6,12}$/;
      var flag = reg_username.test(username);
      var s_username = document.getElementById("s_username");
      if(flag){
        document.getElementById("username").style.borderColor = '#A6A6A6'; // 边框恢复原色
        s_username.innerHTML = " ";
      }else{
        s_username.innerHTML = "用户名格式有误";
        document.getElementById("username").style.borderColor = 'red'; // 边框变红色
      }
      return flag;
    }

    //检查密码
    function checkPassword(){
      var password = document.getElementById("password").value;
      var reg_password = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\W)\S{6,12}$/;
      var flag = reg_password.test(password);
      var s_password = document.getElementById("s_password");
      if(flag){
        document.getElementById("password").style.borderColor = '#A6A6A6'; // 边框恢复原色
        s_password.innerHTML = " ";
      }else{
        s_password.innerHTML = "密码格式有误";
        document.getElementById("password").style.borderColor = 'red'; // 边框变红色
      }
      return flag;
    }

    //生成随机验证码
    function generateCheckCode() {
      var checkCode = '';
      var checkCodeLength = 4;
      var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      for (var i = 0; i < checkCodeLength; i++) {
        var randomIndex = Math.floor(Math.random() * characters.length);
        checkCode += characters.charAt(randomIndex);
      }
      return checkCode;
    }

    //更新验证码图片
    function updateCheckCodeImage() {
      var checkCode = generateCheckCode();
      var imgCheck = document.getElementById("img_check");
      // 替换验证码图片的 src 属性为包含验证码文本的图片链接
      imgCheck.src = "https://dummyimage.com/100x40/000/fff&text=" + checkCode;
    }

    //检查验证码
    function checkCode(){
      var userInput = document.getElementById("checkcode").value.toLowerCase(); // 用户输入的验证码，转为小写方便比较
      var generatedCode = document.getElementById("img_check").src.split("&text=")[1].toLowerCase(); // 从验证码图片链接中获取生成的验证码并转为小写

      var s_checkcode = document.getElementById("s_checkcode");

      if(userInput === generatedCode){
        s_checkcode.innerHTML = ""; // 清空错误提示
        s_checkcode.classList.remove("error");
        document.getElementById("checkcode").style.borderColor = ''; // 边框恢复原色
        return true;
      } else {
        s_checkcode.innerHTML = "验证码输入错误";
        s_checkcode.classList.add("error");
        document.getElementById("checkcode").style.borderColor = 'red'; // 边框变红色
        return false;
      }
    }

    //定义一个变量用于存放 XMLHttpRequest 对象
    var xmlHttp;
    //该函数用于创建一个 XMLHttpRequest 对象
    function createXMLHttpRequest() {
      if (window.ActiveXObject)
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
      else if (window.XMLHttpRequest)
        xmlHttp = new XMLHttpRequest();
    }
    //这是一个启动 AJAX 异步通信的方法
    function beginCheck(){
      var tempRegisterName = document.all.username.value;
      if (tempRegisterName == ""){//如果尚未输入注册名
        alert("对不起，请您输入注册名!");
        return;
      }
      createXMLHttpRequest();//创建一个 XMLHttpRequest 对象
      xmlHttp.onreadystatechange = processor; //将状态触发器绑定到一个函数
//通过 GET 方法向指定的 URL 建立服务器的调用
      xmlHttp.open("GET", "CheckUserServlet?registerName="+tempRegisterName);
      xmlHttp.send(null); //发送请求
    }
    //这是一用来处理状态改变的函数
    function processor () {
//定义一个变量用于存放从服务器返回的响应结果
      var responseContext;
      if(xmlHttp.readyState == 4) { //如果响应完成
        if(xmlHttp.status == 200) {//如果返回成功
//取出服务器的响应内容
          responseContext = xmlHttp.responseText;
//如果注册名检查有效
          if (responseContext.indexOf("true")!=-1)
            alert("恭喜您，该注册名有效！");
          else{
            alert("对不起，该注册名已被使用！");
            document.all.username.value="";
          }
        }
      }
    }
  </script>

  <meta name="keywords" content="keyword1,keyword2,keyword3">
  <meta name="description" content="this is my page">
  <meta name="content-type" content="text/html; charset=gb2312">

</head>

<body>

<div class="rg_layout">

  <div class="rg_left">
    <p>新用户注册</p>
    <p>USER REGISTER</p>
  </div>

  <div class="rg_center">
    <div class="rg_form">
      <form action="UserServlet" id="form" method="post">
        <table>
          <!-- 用户名 -->
          <tr>
            <td>
              <input type="text" name="username" id="username" placeholder="Username">
              <span id="s_username" class="error"></span>
            </td>
          </tr>

          <!-- 密码 -->
          <tr>
            <td>
              <input type="password" name="password" id="password" placeholder="Password">
              <span id="s_password" class="error"></span>
            </td>
          </tr>

          <!-- 邮箱 -->
          <tr>
            <td>
              <input type="email" name="email" id="email" placeholder="Email">
            </td>
          </tr>

          <!-- 手机号 -->
          <tr>
            <td>
              <input type="tel" name="tel" id="tel" placeholder="Phonenumber">
            </td>
          </tr>

          <!-- 验证码 -->
          <tr>
            <td>
              <input type="text" name="checkcode" id="checkcode" placeholder="Checkcode">
              <img id="img_check" src="img/verify_code.jpg"onclick="updateCheckCodeImage()" style="cursor: pointer;">
              <span id="s_checkcode" class="error"></span>
            </td>
          </tr>

          <!-- 注册 -->
          <tr>
            <td colspan="2" id="td_sub"><input type="submit" id="btn_sub" value="Register"></td>
          </tr>
        </table>

      </form>
    </div>
  </div>

  <div class="rg_right">
    <p>已有账号?<a href="login.html">立即登录</a></p>
  </div>

  <div class="button-container">
    <input type="button" id="btn_chk" name="checkRegisterName" value="check" onclick="beginCheck()">
  </div>
</div>

</body>
</html>