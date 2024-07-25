package com.lgsk.imgreet.login.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String id;

    @GetMapping("/login")
    public String loginPage(){
        return "redirect:/oauth2/authorization/kakao";
    }

    @PostMapping("/logout")
    public String logout() {
        System.out.println("##########logout");
        return "redirct:http://kauth.kakao.com/oauth/logout?client_id="+id+"&logout_redirect_uri=http://localhost:8080/";
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
