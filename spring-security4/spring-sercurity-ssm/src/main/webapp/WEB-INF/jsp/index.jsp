<%--
  Created by IntelliJ IDEA.
  User: chensj
  Date: 2019/8/28
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
显示网页的一些功能：<br>
<a href="${pageContext.request.contextPath}/product/add">商品新增</a><br>
<a href="${pageContext.request.contextPath}/product/update">商品修改</a><br>
<a href="${pageContext.request.contextPath}/product/delete">商品删除</a><br>
<a href="${pageContext.request.contextPath}/product/list">商品查询</a><br>

</body>
</html>
