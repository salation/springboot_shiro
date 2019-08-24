package com.example.shiro_practice.service;

import com.example.shiro_practice.entity.*;
import com.example.shiro_practice.mapper.RoleMapper;
import com.example.shiro_practice.mapper.RolePermissionMapper;
import com.example.shiro_practice.mapper.UserMapper;
import com.example.shiro_practice.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> getRoles() {
        return roleMapper.getRoles();
    }

    @Override
    public RoleVO findRoleAndPerms(Integer id) {
        return roleMapper.findRoleAndPerms(id);
    }

    @Override
    public Role selectByCode(String code) {
        return roleMapper.selectByCode(code);
    }

    @Override
    public int deleteByRoleId(Integer id) {
        return rolePermissionMapper.deleteByRoleId(id);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(RolePermissionKey record) {
        return rolePermissionMapper.insert(record);
    }

    @Override
    public int insertRole(Role role) {
        return roleMapper.insert(role);
    }
}
