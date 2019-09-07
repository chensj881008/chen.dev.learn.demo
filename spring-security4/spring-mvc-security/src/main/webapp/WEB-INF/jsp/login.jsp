<%--
  Created by IntelliJ IDEA.
  User: chensj
  Date: 2019/9/2
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/user/login" method="post">
    <h2>登录页面</h2>
    <label for="username">用户名</label><input id="username" name="user"> <br>
    <label for="password">密 码</label><input id="password" name="pass" type="password"> <br>
    <%--  Spring Security4默认是开启CSRF的，所以需要请求中包含CSRF的token信息
    --%>
<%--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
    <button type="submit">登录</button>
</form>
</body>
</html>
