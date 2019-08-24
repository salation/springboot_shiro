<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Login-templates</title>
</head>
<body>
templates：
<form action="/login" method="post">
    <p>账号：<input type="text" name="username" /></p>
    <p>密码：<input type="text" name="password" /></p>
    <p>手机号：<input type="text" name="mobile" /></p>
    <input type="checkbox" name="rememberMe"  title="记住我"/>记住我</br>
    <p><input type="submit" value="登录"/></p>
    超级管理员 wyait 654321 12316596566 角色、权限管理</br>
    高级管理员 ee 654321 12345678919 角色管理</br>
    经理 wy1 654321 11155556667</br>
</form>
</body>
</html>