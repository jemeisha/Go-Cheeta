<%--
  Created by IntelliJ IDEA.
  User: jEMEISHA
  Date: 8/20/2022
  Time: 12:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="https://fonts.googleapis.com/css?family=Assistant:400,700" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/style.css"/>
</head>
<body>
<section class="login" id="login">
    <div class="head">
        <h1 class="company">Go Cheeta</h1>
    </div>
    <p class="msg">Welcome back</p>
    <div class="form">
        <form>
            <input type="text" placeholder="Username" class="text" id="username" required><br>
            <input type="password" placeholder="••••••••••••••" class="password"><br>
            <center> <a href="#" class="btn-login" id="do-login">Login</a></center>

        </form>
    </div>
</section>
</body>
</html>
