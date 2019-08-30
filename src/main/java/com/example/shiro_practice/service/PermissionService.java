package com.example.shiro_practice.service;

import com.example.shiro_practice.entity.Permission;
import com.example.shiro_practice.entity.Role;
import com.example.shiro_practice.entity.RoleVO;

import java.util.List;

public interface PermissionService {
    List<Permission> findPermsByRole(Integer roleId);
    List<Permission> findPerm();
}
