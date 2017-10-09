package com.AdesK.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author AdesKng
 * @version 1.0
 * @TIME 2017/10/07-15:55
 * @E-mail 109
 */
@Controller
public class UserController {
    @RequestMapping("/index")
    public String index(String username){
        System.out.println("首页");

        return "/index";
    }
    @RequestMapping("/login")
    public String login(String username){
        System.out.println("登陆页");

        return "/login";
    }
    @RequestMapping("/access_Denied")
    public String access_Denied(String username){
        System.out.println(username);
        System.out.println("错误页");

        return "/access_Denied";
    }
}
