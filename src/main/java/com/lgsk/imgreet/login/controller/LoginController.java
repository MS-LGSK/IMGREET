package com.lgsk.imgreet.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/")
    @ResponseBody()
    public String mainPage() {
        return "main";
    }

    @GetMapping("/open")
    @ResponseBody
    public String openPage() {
        return "open";
    }

    @GetMapping("/close")
    public String closePage() {
        return "close";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "redirect:oauth2/authorization/kakao";
    }
}
