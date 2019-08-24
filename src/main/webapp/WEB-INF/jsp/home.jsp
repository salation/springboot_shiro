<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>用户列表</title>
    <script  type="text/javascript" >
        function user() {
            location.assign("user/userMenu.jsp")
        }
    </script>
</head>
<body>
    <div>请选择：</div>
    <shiro:hasAnyRoles name="superman,highmanage">
        <a type="button" id="userControl" href="/user/userList">用户管理</a></br></br>
    </shiro:hasAnyRoles>
    <shiro:hasRole name="superman">
        <a type="button" id="roleControl" href="/role/roleList">角色權限管理</a></br></br>
    </shiro:hasRole>
    <%--<shiro:hasAnyRoles name="superman,highmanage">--%>
        <%--<button id="permControl" onclick="perm()">权限管理</button></br></br>--%>
    <%--</shiro:hasAnyRoles>--%>
    <shiro:authenticated>
        (测试）用户已经登录显示此内容
    </shiro:authenticated><br/>
</body>
</html>