package com.example.shiro_practice.shiro;



import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
@Configuration
@EnableTransactionManagement
public class ShiroConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean (SecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        logger.debug("-----------------Shiro拦截器工厂类注入开始");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setSuccessUrl("/home");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        Map<String,String>filterChainDefinationMap = new LinkedHashMap<String,String>();
        filterChainDefinationMap.put("/toLogin","anon");
        filterChainDefinationMap.put("/403","anon");
        filterChainDefinationMap.put("/logout","logout");
        filterChainDefinationMap.put("/login","anon");


        filterChainDefinationMap.put("/role/getRoleList","anon");
        filterChainDefinationMap.put("/role/getMenuTestListP","anon");
        filterChainDefinationMap.put("/role/updateMenuTestList","anon");
        filterChainDefinationMap.put("/role/getSelectPerm/*","anon");
        filterChainDefinationMap.put("/role/updateTree/*","anon");

        filterChainDefinationMap.put("/zTree_v3/*","anon");
        filterChainDefinationMap.put("/zTree_v3/*/**","anon");

        filterChainDefinationMap.put("/","user");
        filterChainDefinationMap.put("/home","user");
        //filterChainDefinationMap.put("/role/test","user");
        filterChainDefinationMap.put("/*", "authc");
        filterChainDefinationMap.put("/*/*", "authc");
        filterChainDefinationMap.put("/*/*/*", "authc");
        filterChainDefinationMap.put("/*/*/*/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinationMap);
        logger.debug("-----------------Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(){
        System.out.println("securityManager");
        //DefaultWebSecurityManager使用的默认实现，用于Web环境，其直接使用Servlet容器的会话；
        DefaultSecurityManager securityManager =new DefaultWebSecurityManager();
        securityManager.setCacheManager(ehCacheManager());
        securityManager.setRealm(shiroRealm());
        securityManager.setRememberMeManager(rememberMeManager());
        //securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public ShiroRealm shiroRealm(){
        logger.info("shiroRealm");
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        System.out.println("hashedCredentialsMatcher");
        HashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager());
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    /**
     * ehcache缓存管理器；shiro整合ehcache：
     * 通过安全管理器：securityManager
     * 单例的cache防止热部署重启失败
     *
     * @return EhCacheManager
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        //logger.debug("=====shiro整合ehcache缓存：ShiroConfiguration.getEhCacheManager()");
        EhCacheManager ehcache = new EhCacheManager();
        CacheManager cacheManager = CacheManager.getCacheManager("es");

        if (cacheManager == null) {
            try {

                cacheManager = CacheManager.create(ResourceUtils
                        .getInputStreamForPath("classpath:ehcache.xml"));

            } catch (CacheException | IOException e) {
                e.printStackTrace();
            }
        }
        ehcache.setCacheManager(cacheManager);
        return ehcache;
    }


    /**
     * 配置cookie记住我管理器
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        logger.debug("配置cookie记住我管理器！");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(remeberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * 设置记住我cookie过期时间
     *
     * @return
     */
    @Bean
    public SimpleCookie remeberMeCookie() {
        logger.debug("记住我，设置cookie过期时间！");
        //cookie名称;对应前端的checkbox的name = rememberMe
        SimpleCookie scookie = new SimpleCookie("rememberMe");
        //记住我cookie生效时间30天 ,单位秒  [10天]
        scookie.setMaxAge(864000);
        return scookie;
    }


    /**
     * @return
     * @描述：sessionManager添加session缓存操作DAO
     * @创建人：wyait
     * @创建时间：2018年4月24日 下午8:13:52
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //sessionManager.setCacheManager(ehCacheManager());
        sessionManager.setSessionDAO(enterCacheSessionDAO());
        sessionManager.setSessionIdCookie(sessionIdCookie());
        //设置全局会话超时时间
        //sessionManager.setGlobalSessionTimeout(360000);
        return sessionManager;
    }
    /**
     * EnterpriseCacheSessionDAO shiro sessionDao层的实现；
     * 提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     */
    @Bean
    public EnterpriseCacheSessionDAO enterCacheSessionDAO() {
        EnterpriseCacheSessionDAO enterCacheSessionDAO = new EnterpriseCacheSessionDAO();
        //添加缓存管理器
        //enterCacheSessionDAO.setCacheManager(ehCacheManager());
        //添加ehcache活跃缓存名称（必须和ehcache缓存名称一致）
        enterCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        return enterCacheSessionDAO;
    }
    /**
     * @return
     * @描述：自定义cookie中session名称等配置
     * @创建人：wyait
     * @创建时间：2018年5月8日 下午1:26:23
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        //DefaultSecurityManager
        SimpleCookie simpleCookie = new SimpleCookie();
        //sessionManager.setCacheManager(ehCacheManager());
        //如果在Cookie中设置了"HttpOnly"属性，那么通过程序(JS脚本、Applet等)将无法读取到Cookie信息，这样能有效的防止XSS攻击。
        simpleCookie.setHttpOnly(true);
        simpleCookie.setName("SHRIOSESSIONID");
        //单位秒
        simpleCookie.setMaxAge(86400);
        return simpleCookie;
    }



    /**
     * @return
     * @描述：开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * </br>Enable Shiro Annotations for Spring-configured beans. Only run after the lifecycleBeanProcessor(保证实现了Shiro内部lifecycle函数的bean执行) has run
     * </br>不使用注解的话，可以注释掉这两个配置
     *
     *
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
