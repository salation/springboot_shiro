package com.example.shiro_practice.mapper;

import com.example.shiro_practice.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PermissionMapper {

    /**
     * 根据角色id获取权限数据
     *
     * @param roleId
     * @return
     */
    List<Permission> findPermsByRole(Integer roleId);
}