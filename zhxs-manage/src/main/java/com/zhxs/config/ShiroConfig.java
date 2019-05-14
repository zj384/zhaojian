package com.zhxs.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhxs.sys.service.realm.ShiroUserRealm;


@Configuration
public class ShiroConfig {
	@Bean
    public ShiroUserRealm myShiroRealm() {
		ShiroUserRealm myShiroRealm = new ShiroUserRealm();
        return myShiroRealm;
    }
 
    // 创建 SecurityManager 对象
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }
 
    // Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        // 登出
        // 对所有用户认证

        // 对登录跳转接口进行释放
        map.put("/assets/**", "anon");
        map.put("/js/**", "anon");
        map.put("/layuiadmin/**", "anon");
        map.put("/res/**", "anon");
        map.put("/user/doLogin", "anon");
        map.put("/user/doSaveUser", "anon");
        map.put("/doRegUI", "anon");
        map.put("/doLogout", "logout");
        map.put("/**", "authc");
        
        // 登录
        // 注意：这里配置的 /login 是指到 @RequestMapping(value="/login")中的 /login
        shiroFilterFactoryBean.setLoginUrl("/doLoginUI");
        // 首页
        shiroFilterFactoryBean.setSuccessUrl("/doIndexUI");
        // 错误页面，认证不通过跳转
        //shiroFilterFactoryBean.setUnauthorizedUrl("/err");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
 
    // 加入注解的使用，不加这个，注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    // 跟上面的注解配置搭配使用，有时候加了上面的配置后注解不生效，需要加入下面的配置
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;
    }

}
