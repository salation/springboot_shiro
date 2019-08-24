package com.example.shiro_practice.service;

import com.example.shiro_practice.entity.User;
import com.example.shiro_practice.entity.UserRoleDto;
import com.example.shiro_practice.entity.UserRoleKey;

import java.util.List;

public interface UserService {
    User findUserByName(String userName);
    User findUserByMobile(String mobile);
    User selectUserByPrimary(Integer id);
    List<UserRoleDto> getUsers();

    int updateUserRoleByUserId (UserRoleKey userRoleKey);

    int deleteByPrimaryKey(Integer id);

}
