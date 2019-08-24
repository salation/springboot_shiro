package com.example.shiro_practice.service;

import com.example.shiro_practice.entity.Permission;
import com.example.shiro_practice.entity.Role;

import com.example.shiro_practice.mapper.PermissionMapper;
import com.example.shiro_practice.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @项目名称：wyait-manage
 * @包名：com.wyait.manage.service
 * @类描述：
 * @创建人：wyait
 * @创建时间：2017-12-20 15:53
 * @version：V1.0
 */
@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory
            .getLogger(AuthServiceImpl.class);
    @Autowired
    private PermissionMapper permissionMapper ;
    @Autowired
    private RoleMapper roleMapper;




    @Override
    public List<Role> getRoleByUser(Integer userId) {
        return this.roleMapper.getRoleByUserId(userId);
    }

    @Override
    public List<Permission> findPermsByRoleId(Integer id) {
        return this.permissionMapper.findPermsByRole(id);
    }

}
