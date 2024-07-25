package com.lgsk.imgreet.login.controller;

import com.lgsk.imgreet.login.oauth2.KakaoAPI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    private KakaoAPI kakaoapi;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String id;

    @GetMapping("/login")
    public String loginPage(){
        return "redirect:/oauth2/authorization/kakao";
    }

    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String accessToken = (String)session.getAttribute("access_token");

        if (accessToken != null && !"".equals(accessToken)) {
            kakaoapi.logout(accessToken);
            session.removeAttribute("access_token");
            session.removeAttribute("user");

            System.out.println("logout success");
        }

        return "redirect:/";
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
