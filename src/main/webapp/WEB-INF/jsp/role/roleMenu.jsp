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
<a type="button" href="/role/add" >添加</a>
<div>
    <table>
        <tr>
            <th>id</th>
            <th>角色名稱</th>
            <th>權限</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${roleList}" var="role">
            <tr>
                <td>${role.id}</td>
                <td>${role.roleName}</td>
                <td>${role.perms}</td>
                <td>
                    <a type="button" href="/role/update/${role.id}">修改權限範圍</a>
                    <a type="button" href="/role/updateTree/${role.id}">修改權限範圍(ztree版)</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>