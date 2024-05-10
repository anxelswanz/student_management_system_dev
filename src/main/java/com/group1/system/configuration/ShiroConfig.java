package com.group1.system.configuration;

import com.group1.system.realm.MyRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ronghui Zhong
 * @description: This class is the Shiro framework (Authentication & Authorization) Configuration
 * @date 2024/3/29 18:36
 * @ProjectName Dyson Student Management System
 **/
@Slf4j
@Configuration
public class ShiroConfig {

    @Bean
    public MyRealm myRealm(){
        return new MyRealm();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")
                                                         DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean factoryBean
                = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(defaultWebSecurityManager);
        factoryBean.setLoginUrl("/user/index");
        // Authentication setting
        Map<String, String> map = new HashMap<>();
        map.put("/user/login","anon");
        map.put("/user/registerStudent","anon");
        map.put("/user/registerEmp","anon");
        map.put("/user/logout", "logout");
        factoryBean.setFilterChainDefinitionMap(map);

        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("myRealm") MyRealm myRealm) {
        //1. create DefaultWebSecurityManager instance
        DefaultWebSecurityManager defaultWebSecurityManager
                = new DefaultWebSecurityManager();
        //2. create credential instance
        HashedCredentialsMatcher matcher
                = new HashedCredentialsMatcher();
        //2.1 encrypt
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(3);
        myRealm.setCredentialsMatcher(matcher);
        defaultWebSecurityManager.setRealm(myRealm);
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        log.info("DefaultWebSecurityManager init success");
        return defaultWebSecurityManager;
    }

    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setMaxAge(30*24*60*60);
        return cookie;
    }

    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie key
        cookieRememberMeManager.setCipherKey("StudentManagementKey".getBytes());
        return cookieRememberMeManager;
    }
}
