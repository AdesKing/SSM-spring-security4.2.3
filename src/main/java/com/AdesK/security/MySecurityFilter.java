package com.AdesK.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;

import org.springframework.security.web.FilterInvocation;


import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * @author AdesKng
 * @version 1.0
 * @TIME 2017/10/07-21:10
 * @E-mail 109
 */
public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {
    //与applicationContext-security.xml里的myFilter的属性securityMetadataSource对应，
    //其他的两个组件，已经在AbstractSecurityInterceptor定义
    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;


    @PostConstruct
    public void init(){
	System.out.println(" ---------------  MySecurityFilter init--------------- ");
        /*super.setAuthenticationManager(myAuthenticationManager);
        super.setAccessDecisionManager(myAccessDecisionManager);*/
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(" ---------------  doFilter--------------- ");
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        //fi里面有一个被拦截的url
        //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.mySecurityMetadataSource;
    }
}
