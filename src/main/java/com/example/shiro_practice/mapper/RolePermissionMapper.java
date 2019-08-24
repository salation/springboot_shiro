package com.example.shiro_practice.mapper;


import com.example.shiro_practice.entity.RolePermissionKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePermissionMapper {
    int deleteByRoleId(Integer id);

    int insert(RolePermissionKey record);

}