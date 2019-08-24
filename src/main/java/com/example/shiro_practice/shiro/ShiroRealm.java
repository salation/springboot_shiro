package com.example.shiro_practice.shiro;

import com.example.shiro_practice.entity.Permission;
import com.example.shiro_practice.entity.Role;
import com.example.shiro_practice.entity.User;
import com.example.shiro_practice.service.AuthService;
import com.example.shiro_practice.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    public AuthService authService;
    @Autowired
    public UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("doGetAuthorizationInfo");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user =(User)subject.getPrincipal();
        if (user.getUsername().equals("admin")){
            simpleAuthorizationInfo.addRole("*");
            simpleAuthorizationInfo.addStringPermission("*");
        }else{
            Integer integer = user.getId();
            List<Role> roleList = authService.getRoleByUser(integer);
            for(Role role : roleList){
                simpleAuthorizationInfo.addRole(role.getCode());
                List<Permission> permissionList = authService.findPermsByRoleId(role.getId());
                for(Permission permission : permissionList){
                    simpleAuthorizationInfo.addStringPermission(permission.getCode());
                }
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        String mobile =usernamePasswordToken.getUsername();
        User user = userService.findUserByMobile(mobile);
        if(user==null){
            return null;
        }else{
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user, DigestUtils.md5Hex(user.getPassword()),getName()
            );
            //System.out.println(DigestUtils.md5Hex(user.getPassword()));
            return authenticationInfo;
        }
    }
}
