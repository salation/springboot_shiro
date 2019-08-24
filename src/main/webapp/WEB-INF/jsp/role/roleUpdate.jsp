<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
</head>
<body>
    <h1>用戶權限修改</h1>
    <h2>${roleName}</h2>
    <form method="post" action="/role/updateP" role="form">
        <input type="hidden" id="id" name="id" value="${id}"/>
        <input type="checkbox" name="perms" value="1">系统管理</br>
        <input type="checkbox" name="perms" value="2">用户管理</br>
        <input type="checkbox" name="perms" value="3">角色管理</br>
        <input type="checkbox" name="perms" value="4">权限管理</br>
        <input type="checkbox" name="perms" value="5">商品管理</br>
        <input type="checkbox" name="perms" value="6">渠道管理</br>
        <input type="checkbox" name="perms" value="8">订单管理</br>
        <input type="checkbox" name="perms" value="10">渠道信息列表</br>
        <input type="checkbox" name="perms" value="11">渠道会员列表</br>
        <input type="checkbox" name="perms" value="13">商品列表</br>
        <input type="checkbox" name="perms" value="14">商品订单列表</br>
        <input type="submit"value="submit">
    </form>
</body>