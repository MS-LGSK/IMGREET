package com.lgsk.imgreet.mainPage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ("anonymousUser".equals(authentication.getPrincipal())) {
            model.addAttribute("auth", false);
        } else {
            model.addAttribute("auth", true);
        }
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
