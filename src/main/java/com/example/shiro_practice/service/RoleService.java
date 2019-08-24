package com.example.shiro_practice.service;

import com.example.shiro_practice.entity.*;

import java.util.List;

public interface RoleService {
    Role selectByPrimaryKey(Integer id);
    List<Role> getRoles();
    RoleVO findRoleAndPerms(Integer id);
    Role selectByCode (String code);

    int deleteByRoleId(Integer id);
    int deleteByPrimaryKey(Integer id);

    int insert (RolePermissionKey record);
    int insertRole (Role role);
}
