package com.example.shiro_practice.mapper;

import com.example.shiro_practice.entity.User;

import com.example.shiro_practice.entity.UserRoleDto;
import com.example.shiro_practice.entity.UserRoleKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    User selectByPrimaryKey(Integer id);

    /**
     * 分页查询用户数据
     *
     * @return
     */
    List<UserRoleDto> getUsers();


    /**
     * 根据手机号获取用户数据
     *
     * @param mobile
     * @return
     */
    User findUserByMobile(String mobile);
//
    /**
     * 根据用户名获取用户数据
     *
     * @param username
     * @return
     */
    User findUserByName(String username);



}