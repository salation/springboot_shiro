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
    <h1>用户管理</h1>
    <div>
        <table>
            <tr>
                <th>id</th>
                <th>用户名</th>
                <th>角色</th>
                <th>手机号</th>
                <th>操作</th>
            </tr>
        <c:forEach items="${userList}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.roleName}</td>
                <td>${user.mobile}</td>
                <td>
                    <a type="button" href="/user/update/${user.id}">修改權限</a>
                    <a type="button" href="/user/delet/${user.id}">删除用戶</a>
                </td>
            </tr>
        </c:forEach>
        </table>
    </div>
</body>
</html>