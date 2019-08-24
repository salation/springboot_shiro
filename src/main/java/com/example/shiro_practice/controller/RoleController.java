package com.example.shiro_practice.controller;


import com.example.shiro_practice.entity.*;
import com.example.shiro_practice.service.AuthService;
import com.example.shiro_practice.service.PermissionService;
import com.example.shiro_practice.service.RoleService;
import com.example.shiro_practice.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @项目名称：wyait-manage
 * @包名：com.wyait.manage.web.user
 * @类描述：
 * @创建人：wyait
 * @创建时间：2017-12-31 14:22
 * @version：V1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    private static final Logger logger = LoggerFactory
            .getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/test")
    @RequiresRoles(value = { "superman" }, logical = Logical.OR)
    public String test1(){
        return "user/userMenu";
    }

    @RequestMapping("/roleList")
    @RequiresRoles(value = { "superman"}, logical = Logical.OR)
    public String getUsers(ModelMap modelMap) {
        List<Role> roleList = roleService.getRoles();
        for(Role role : roleList){
            String perms="";
            List<Permission> permissions = permissionService.findPermsByRole(role.getId());
            for (Permission permission:permissions){
                perms = perms +" "+permission.getName();
            }
            role.setPerms(perms);
        }
        modelMap.addAttribute("roleList", roleList);
        // 返回pages目录下的admin/users.jsp页面
        return "/role/roleMenu";
    }

    @GetMapping(value = "update/{id}")
    @RequiresRoles(value = { "superman"}, logical = Logical.OR)
    public String updateUser(@PathVariable("id")Integer id,ModelMap modelMap){
        Role role = roleService.selectByPrimaryKey(id);
        modelMap.addAttribute("roleName",role.getRoleName());
        modelMap.addAttribute("id",role.getId());
        return "/role/roleUpdate";
    }

    @PostMapping(value = "/updateP")
    @RequiresRoles(value = { "superman"}, logical = Logical.OR)
    public String updateUserP (@RequestParam("id")Integer roleId,@RequestParam("perms")String[] perms,ModelMap modelMap){

        roleService.deleteByRoleId(roleId);
        for(String perm:perms){
            RolePermissionKey rolePermissionKey =new RolePermissionKey();
            if(perm!=null){
                rolePermissionKey.setPermitId(Integer.valueOf(perm));
            }else{
               break;
            }
            rolePermissionKey.setRoleId(roleId);
            roleService.insert(rolePermissionKey);
        }
        return "redirect:/role/roleList";
    }


    @RequestMapping("/add")
    @RequiresRoles(value = { "superman"}, logical = Logical.OR)
    public String addRole (){
        return "/role/roleAdd";
    }

    @RequestMapping("/addP")
    @RequiresRoles(value = { "superman"}, logical = Logical.OR)
    public String addRoleP (@RequestParam("roleName")String roleName,
                            @RequestParam("perms")String[] perms,
                            @RequestParam("descripe")String descripe,
                            @RequestParam("code")String code){
        Role role =new Role ();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        role.setRoleName(roleName);
        role.setDescpt(descripe);
        role.setCode(code);
        role.setInsertTime(d);
        role.setUpdateTime(d);
        roleService.insertRole(role);
        Integer id = roleService.selectByCode(code).getId();
        for(String perm:perms){
            RolePermissionKey rolePermissionKey = new RolePermissionKey();
            rolePermissionKey.setRoleId(id);
            if(perm!=null){
                rolePermissionKey.setPermitId(Integer.valueOf(perm));
            }
            roleService.insert(rolePermissionKey);
        }
        return "redirect:/role/roleList";
    }

}
