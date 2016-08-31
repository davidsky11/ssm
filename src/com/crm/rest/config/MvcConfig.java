package com.crm.rest.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.crm.authorization.interceptor.AuthorizationInterceptor;
import com.crm.authorization.resolvers.CurrentAccountMethodArgumentResolver;

/** 
 * @ClassName	MvcConfig.java
 * @Description 配置类，增加自定义拦截器和解析器
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午2:31:20
 * @Version 	V1.0    
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
    private AuthorizationInterceptor authorizationInterceptor;
	
	@Autowired
    private CurrentAccountMethodArgumentResolver currentUserMethodArgumentResolver;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }
    
}
 