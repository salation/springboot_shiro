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
<h1>增加用戶權限:</h1></br>
id, role_name, descpt,
code, insert_uid, insert_time,
update_time
    <form method="post" action="/role/addP" role="form">

        名稱：<input type="text" id="roleName" name="roleName" /></br>
        描述：<input type="text" id="descripe" name="descripe" /></br>
        代碼：<input type="text" id="code" name="code" /></br>

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