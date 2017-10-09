package com.AdesK.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author AdesKng
 * @version 1.0
 * @TIME 2017/10/07-21:51
 * @E-mail 109
 */

public class MyUserDetailService implements UserDetailsService {
    /* 登陆验证时，通过username获取用户的所有权限信息，即通过数据库查找user
	*  并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
	*  <!--在这个类中，你就可以从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期等 -->
	*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("-----------MyUserDetailServiceImpl loadUserByUsername ----------- ");
        Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
        /*
        * 这里的数据应该从数据库读取
        * */

         SimpleGrantedAuthority auth1=new SimpleGrantedAuthority("ROLE_USER");
         System.out.println("13234d"+username);
        if(username.equals("admin")){
            auths=new ArrayList<GrantedAuthority>();
            auths.add(auth1);

        }
        // 封装成spring security的user
        User user = new User(username, "admin", true, true, true, true, auths);
        return user;

    }
}
