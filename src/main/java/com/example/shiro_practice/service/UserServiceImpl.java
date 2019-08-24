package com.example.shiro_practice.service;

import com.example.shiro_practice.entity.User;
import com.example.shiro_practice.entity.UserRoleDto;
import com.example.shiro_practice.entity.UserRoleKey;
import com.example.shiro_practice.mapper.UserMapper;
import com.example.shiro_practice.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public User findUserByName(String userName) {
        return userMapper.findUserByName(userName);
    }

    @Override
    public User findUserByMobile(String mobile) {
        return userMapper.findUserByMobile(mobile);
    }

    @Override
    public User selectUserByPrimary(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserRoleDto> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public int updateUserRoleByUserId(UserRoleKey userRoleKey) {
        return userRoleMapper.updateUserRoleByUserId(userRoleKey);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }
}
