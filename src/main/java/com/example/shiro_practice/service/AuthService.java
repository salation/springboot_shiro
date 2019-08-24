package com.example.shiro_practice.service;

import com.example.shiro_practice.entity.Permission;
import com.example.shiro_practice.entity.Role;


import java.util.List;

/**
 * @项目名称：wyait-manage
 * @包名：com.wyait.manage.service
 * @类描述：
 * @创建人：wyait
 * @创建时间：2017-12-20 15:52
 * @version：V1.0
 */
public interface AuthService {


    /**
     * 根据用户获取角色列表
     *
     * @param userId
     * @return
     */
    List<Role> getRoleByUser(Integer userId);

   /**
     * 根据角色id获取权限数据
     *
     * @param id
     * @return
     */
    List<Permission> findPermsByRoleId(Integer id);

/*    *//**
     * 根据用户id获取权限数据
     *
     * @param id
     * @return
     *//*
//    List<PermissionVO> getUserPerms(Integer id);*/
}
