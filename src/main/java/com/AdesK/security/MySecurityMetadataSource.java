package com.AdesK.security;


import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;


import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author AdesKng
 * @version 1.0
 * @TIME 2017/10/07-21:19
 * @E-mail 109
 */
/*<!--资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问 -->*/

public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    @PostConstruct
    private void loadResourceDefine() {
        System.out.println("--------------- MySecurityMetadataSource   init--------------- ");
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        //应该从数据库读取权限
        ConfigAttribute ca = new SecurityConfig("ROLE_USER");
        atts.add(ca);
        resourceMap.put("/index.jsp", atts);

        Collection<ConfigAttribute> attsno =new ArrayList<ConfigAttribute>();
        ConfigAttribute cano = new SecurityConfig("ROLE_NO");
        attsno.add(cano);
        resourceMap.put("/other.jsp", attsno);
    }
    //参数是要访问的url，返回这个url对于的所有权限（或角色）
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl =((FilterInvocation)o).getRequestUrl();
        if(resourceMap == null) {
            loadResourceDefine();
        }
        if(requestUrl.indexOf("?")>-1){
            requestUrl=requestUrl.substring(0,requestUrl.indexOf("?"));
        }
        System.out.println("--------------- "+requestUrl+"   --------------- ");
        return resourceMap.get(requestUrl);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
