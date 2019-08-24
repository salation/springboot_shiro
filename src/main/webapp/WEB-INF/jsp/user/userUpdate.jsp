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
    <h2>${username}</h2>
    <form method="post" action="/user/updateP" role="form">
        <input type="hidden" id="id" name="id" value="${id}"/>
        <input type="radio" name="roles" value="1">超级管理</br>
        <input type="radio" name="roles" value="2">高级管理员</br>
        <input type="radio" name="roles" value="3">经理</br>
        <input type="radio" name="roles" value="4">质检员</br>
        <input type="radio" name="roles" value="5">客维员</br>
        <input type="submit"value="submit">
    </form>
</body>