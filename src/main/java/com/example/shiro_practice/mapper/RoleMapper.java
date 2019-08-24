package com.example.shiro_practice.mapper;

import com.example.shiro_practice.entity.Role;
import com.example.shiro_practice.entity.RoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);
    Role selectByCode(String code);
    /**
     * 获取角色相关的数据
     *
     * @param id
     * @return
     */
       RoleVO findRoleAndPerms(Integer id);


/**
//     * 根据用户id获取角色数据
//     *
//     * @param userId
//     * @return
//     */
    List<Role> getRoleByUserId(Integer userId);

    List<Role> getRoles();

}