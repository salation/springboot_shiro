package com.example.shiro_practice.service;

import com.example.shiro_practice.entity.Permission;
import com.example.shiro_practice.entity.Role;
import com.example.shiro_practice.entity.RoleVO;
import com.example.shiro_practice.mapper.PermissionMapper;
import com.example.shiro_practice.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermsByRole(Integer roleId) {
        return permissionMapper.findPermsByRole(roleId);
    }

    @Override
    public List<Permission> findPerm() {
        return permissionMapper.findPerm();
    }


}
