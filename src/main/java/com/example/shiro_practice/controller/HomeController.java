package com.example.shiro_practice.controller;

import com.example.shiro_practice.entity.ResponseResult;
import com.example.shiro_practice.entity.User;
import com.example.shiro_practice.service.UserService;
import com.example.shiro_practice.util.IStatusMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class HomeController {


    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private EhCacheManager ecm;

    @RequestMapping("/toLogin")
    public String login(){
        return "/login";
    }

    @RequestMapping("/403")
    public String fail(){
        return "/403";
    }


    @RequestMapping("/home")
    public String toHome() {
        return "/home";
    }


    @RequestMapping("/login")
    public String doLogin(@RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  @RequestParam(value = "rememberMe", required = false) boolean rememberMe,
                                  @RequestParam("mobile") String mobile) {
        ResponseResult responseResult = new ResponseResult();
        System.out.println("username:"+username+",password:"+password);
        AuthenticationToken token = new UsernamePasswordToken(mobile, DigestUtils.md5Hex(password),rememberMe);
        //主体
        Subject subject = SecurityUtils.getSubject();

        //认证逻辑可能出现异常
        try{
            //主体进行登陆
            subject.login(token);
            //获取登陆用户
            User user = (User) subject.getPrincipal();
            responseResult.setCode(IStatusMessage.SystemStatus.SUCCESS
                    .getCode());
            User existUser = this.userService.findUserByMobile(user.getMobile());
            Cache<String, AtomicInteger> passwordRetryCache = ecm
                    .getCache("passwordRetryCache");
            if (null != passwordRetryCache) {
                int retryNum = (passwordRetryCache.get(existUser.getMobile()) == null ? 0
                        : passwordRetryCache.get(existUser.getMobile())).intValue();
                logger.debug("输错次数：" + retryNum);
                if (retryNum > 0 && retryNum < 6) {
                    responseResult.setMessage("用户名或密码错误" + retryNum + "次,再输错"
                            + (6 - retryNum) + "次账号将锁定");
                }
            }

            return "/home";
        }catch (Exception e){
            responseResult.setMessage("用户名或密码不正确");
            return "/403";
        }
    }
}
