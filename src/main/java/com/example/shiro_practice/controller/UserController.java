package com.example.shiro_practice.controller;


import com.example.shiro_practice.entity.User;
import com.example.shiro_practice.entity.UserRoleDto;
import com.example.shiro_practice.entity.UserRoleKey;
import com.example.shiro_practice.service.AuthService;
import com.example.shiro_practice.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory
            .getLogger(UserController.class);

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;


    @RequestMapping("/test")
    @RequiresRoles(value = { "superman", "highmanage" }, logical = Logical.OR)
    public String test1(){
        return "user/userMenu.jsp";
    }


    @RequestMapping("/userList")
    @RequiresRoles(value = { "superman", "highmanage" }, logical = Logical.OR)
    public String getUsers(ModelMap modelMap) {
        // 查询user表中所有记录
        List<UserRoleDto> userList = userService.getUsers();
        // 将所有记录传递给要返回的jsp页面，放在userList当中
        modelMap.addAttribute("userList", userList);
        // 返回pages目录下的admin/users.jsp页面
        return "/user/userMenu";
    }

    @GetMapping(value = "update/{id}")
    @RequiresRoles(value = { "superman", "highmanage" }, logical = Logical.OR)
    public String updateUser(@PathVariable("id")Integer id,ModelMap modelMap){
        User user =userService.selectUserByPrimary(id);
        String username =user.getUsername();
        modelMap.addAttribute("username",username);
        modelMap.addAttribute("id",user.getId());
        return "/user/userUpdate";
    }

    @PostMapping(value = "/updateP")
    @RequiresRoles(value = { "superman", "highmanage" }, logical = Logical.OR)
    public String updateUserP (@RequestParam("id")Integer userId,@RequestParam("roles")Integer roleId,ModelMap modelMap){
        UserRoleKey userRoleKey =new UserRoleKey();
        userRoleKey.setUserId(userId);
        userRoleKey.setRoleId(roleId);
        userService.updateUserRoleByUserId(userRoleKey);
        return "redirect:/user/userList";
    }

    @RequestMapping(value = "delet/{id}")
    @RequiresRoles(value = { "superman", "highmanage" }, logical = Logical.OR)
    public String deletUser (@PathVariable("id")Integer id){
        userService.deleteByPrimaryKey(id);
        return "redirect:/user/userList";
    }

}
