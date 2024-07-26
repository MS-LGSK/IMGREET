package com.lgsk.imgreet.base.commonUtil;

import groovy.util.logging.Log4j2;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Log4j2
@Component
@RequestScope
public class Rq {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    @Getter
    private HttpSession session;
    private OAuth2User user;

    public Rq(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        this.req = req;
        this.resp = resp;
        this.session = session;

        // 현재 로그인한 회원의 인증정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof OAuth2User oUser) {
            this.user = oUser;
        } else {
            this.user = null;
        }
    }

    public boolean isLogin() {
        return this.user != null;
    }

    public boolean isLogout() {
        return !isLogin();
    }
}
