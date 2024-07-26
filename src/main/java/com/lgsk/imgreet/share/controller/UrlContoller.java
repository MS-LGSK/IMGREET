package com.lgsk.imgreet.share.controller;

import com.lgsk.imgreet.share.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/share")
public class UrlContoller {

    private final UrlService urlService;

    // url 형식 : http://localhost:8080/url?id={id값 암호화}
    @PostMapping("/api/{greet_id}")
    public void shareKakao(@PathVariable long greet_id) {
        // 받아온 id 값 암호화
    }

    @PostMapping("/api/{greet_id}")
    public void shareQR(@PathVariable long greet_id) {
    }


}
