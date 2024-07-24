package com.lgsk.imgreet.login.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "redirect:/oauth2/authorization/kakao";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirct:/kauth.kakao.com/oauth/logout";
    }

    @GetMapping("/open")
    public String openPage(){
        return "open";
    }

    @GetMapping("/close")
    @PreAuthorize("isAuthenticated()")
    public String closePage(){
        return "close";
    }
}
