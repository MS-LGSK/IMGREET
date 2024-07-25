package com.lgsk.imgreet.mainPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    // 로그인 페이지 Mapping
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 회원가입 페이지 Mapping
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    // 템플릿 페이지 Mapping
    @GetMapping("/template")
    public String templatePage() {
        return "template";
    }

}
