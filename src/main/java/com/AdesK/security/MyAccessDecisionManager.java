package com.AdesK.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author AdesKng
 * @version 1.0
 * @TIME 2017/10/07-16:43
 * @E-mail 109
 */
        /*accessDecisionManager这个
        也称为授权器，通过登录用户的权限信息、资源、获取资源所需的
        权限来根据不同的授权策略来判断用户是否有权限访问资源。*/

public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * @deprecated
     * 检查用户是否够权限访问资源
     * 参数authentication是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息
     * 参数object是url
     * 参数configAttributes所需的权限
     * @param authentication
     * @param o
     * @param collection
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        System.out.println(" ---------------  MyAccessDecisionManager --------------- ");
        if (collection==null){
            return;
        }
        Iterator<ConfigAttribute> iterator=collection.iterator();
        while (iterator.hasNext()){
            ConfigAttribute configAttribute=iterator.next();
            //访问所请求资源所需要的权限
            String needPermission=configAttribute.getAttribute();
            System.out.println(" ---------------"+needPermission+" --------------- ");
            //用户所拥有的权限authentication
            for (GrantedAuthority grantedAuthority: authentication.getAuthorities()){
                System.out.println(grantedAuthority.getAuthority());
                if (needPermission.equals(grantedAuthority.getAuthority())){

                    return;
                }
            }

        }
        //没有权限
        //注意：执行这里，后台是会抛异常的，但是界面会跳转到所配的access-denied-page页面
        throw new AccessDeniedException(" 没有权限访问！ ");


    }
/*
* 为什么要返回true？？？
* */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
