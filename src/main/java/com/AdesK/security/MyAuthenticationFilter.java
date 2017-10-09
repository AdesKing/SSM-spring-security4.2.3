package com.AdesK.security;

import com.AdesK.model.User;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author AdesKng
 * @version 1.0
 * @TIME 2017/10/07-19:17
 * @E-mail 109
 */
public class MyAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {
/*
* USERNAME，PASSWORD表单中的名字
* Myusername，Mypassword先固定用户名和密码，这是应该从数据库中取出。
* */
    private static final String USERNAME = "username";
    private static final String PASSWORD = "passwor";
    private static final String Myusername = "admin";
    private static final String Mypassword = "admin";

    /**
     * 登录成功后跳转的地址
     */
    private String successUrl = "/index";
    /**
     * 登录失败后跳转的地址
     */
    private String errorUrl = "/login";

    public void init() {
        System.out.println(" ---------------  MyAuthenticationFilter init--------------- ");
        this.setUsernameParameter(USERNAME);
        this.setPasswordParameter(PASSWORD);
        // 验证成功，跳转的页面
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl(successUrl);
        this.setAuthenticationSuccessHandler(successHandler);

        // 验证失败，跳转的页面
        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl(errorUrl);
        this.setAuthenticationFailureHandler(failureHandler);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println(" ---------------  MyAuthenticationFilter attemptAuthentication--------------- ");
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            //获取表单中的用户名和密码。
            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            password = password.trim();
            /*
            * 相当于从数据库中取数据查询一个user
            * */
            User users=new User();
            users.setPasswor(Mypassword);
            users.setUsename(Myusername);
            /*
            * 进行密码和用户名的验证
            * */
            if (users == null || !users.getPasswor().equals(password)) {
                BadCredentialsException exception = new BadCredentialsException(
                        "用户名或密码不匹配！");// 在界面输出自定义的信息！！
                // request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                // exception);
                throw exception;
            }
            // 当验证都通过后，把用户信息放在session里
            request.getSession().setAttribute("userSession", users);
            // 记录登录信息

            System.out.println("userId----" + users.getId()+ "---ip--"
                    + request.getLocalAddr());


            // 运行UserDetailsService的loadUserByUsername 再次封装Authentication

            // 允许子类设置详细属性
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);

        }
    }
}