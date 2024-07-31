package com.lgsk.imgreet.base.commonUtil;

import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.login.service.UserService;
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
    private final UserService userService;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    @Getter
    private HttpSession session;
    private OAuth2User oAuth2User;
    private User user;

    public Rq(HttpServletRequest req, HttpServletResponse resp, HttpSession session, UserService userService) {
        this.req = req;
        this.resp = resp;
        this.session = session;

        // 현재 로그인한 회원의 인증정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof OAuth2User oUser) {
            this.oAuth2User = oUser;
        } else {
            this.oAuth2User = null;
        }
        this.userService = userService;
    }

    public boolean isLogin() {
        return this.oAuth2User != null;
    }

    public boolean isLogout() {
        return !isLogin();
    }

    public String getClientIpAddress() {
        return req.getRemoteAddr();
    }

    public User getUser() {
        if (isLogout()) {
            return null;
        }

        if (user == null) {
            String oAuthId = "kakao_" + oAuth2User.getAttribute("id");
            user = userService.getUserByOauthId(oAuthId);
        }

        return user;
    }
}
