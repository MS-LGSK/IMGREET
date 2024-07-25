package com.lgsk.imgreet.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "redirect:/oauth2/authorization/kakao";
    }
}
