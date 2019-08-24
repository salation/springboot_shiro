package com.example.shiro_practice.mapper;

import com.example.shiro_practice.entity.UserRoleKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    int updateUserRoleByUserId(UserRoleKey userRoleKey);
}
