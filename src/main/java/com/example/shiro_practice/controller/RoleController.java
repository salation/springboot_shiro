package com.example.shiro_practice.controller;


import com.example.shiro_practice.entity.*;
import com.example.shiro_practice.mapper.MenuMapper;
import com.example.shiro_practice.service.AuthService;
import com.example.shiro_practice.service.PermissionService;
import com.example.shiro_practice.service.RoleService;
import com.example.shiro_practice.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private MenuMapper menuMapper;

    @RequestMapping("/test")
    @ResponseBody
    @RequiresRoles(value = { "superman" }, logical = Logical.OR)
    public String test1(){
        return "11111";
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

    /**
     * 获取tree权限列表
     * @return
     */
    @RequestMapping("/getRoleList")
    public String getRoleList(){
        return "/role/roleUpdateTree";
    }
    @RequestMapping("/getMenuTestListP")
    @ResponseBody
    public Object getRoleListP(){
        Map<String,Object> map =new HashMap<String, Object>();
        List<Permission> permissions = permissionService.findPerm();
        //List<MenuTest> menuTests = menuMapper.findPerms();
        map.put("permissions",permissions);
        return map;
    }

    /**
     * 获取tree权限修改列表页面
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/updateTree/{id}")
    public String updateTreeUser(@PathVariable("id")Integer id,ModelMap modelMap){
        modelMap.addAttribute("id",id);
        return "/role/roleUpdateTree";
    }

    /**
     * tree权限修改列表根据role默认选中
     * @param id
     * @return
     */
    @RequestMapping("/getSelectPerm/{id}")
    @ResponseBody
    public Object getSelectPerm(@PathVariable("id")Integer id){
        Map<String,Object> map =new HashMap<String, Object>();
        //List<MenuTest> menuTests =menuMapper.findSelectPermById(id);
        List<Permission> permissions = permissionService.findPermsByRole(id);
        map.put("permissions",permissions);
        return map;
    }

    /**
     * 修改页面提交
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @RequestMapping("/updateMenuTestList")

    public String updateMenuTestList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Integer roleId = Integer.valueOf(httpServletRequest.getParameter("roleId").toString());
        String nodesJson =httpServletRequest.getParameter("nodesJson");
        JSONArray jsonArray = JSONArray.fromObject(nodesJson);
        JSONObject jsonObject;
        RolePermissionKey rolePermissionKey =new RolePermissionKey();
        roleService.deleteByRoleId(roleId);
        //List<Map<String,String>> mao =new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            //Map<String,String> updateMap = new HashMap<String,String>();
            jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.get("id")!=null){
                rolePermissionKey.setPermitId(Integer.valueOf(jsonObject.get("id").toString()));
            }else{
                break;
            }
            rolePermissionKey.setRoleId(roleId);
            roleService.insert(rolePermissionKey);
            //updateMap.put("name",jsonObject.get("name").toString());
            //updateMap.put("id",jsonObject.get("id").toString());
            //mao.add(updateMap);
        }
        //Map<String,List> map = new HashMap<>();
        //map.put("mao",mao);
        return "redirect:/role/roleList";
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
