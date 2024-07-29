package com.lgsk.imgreet.mainPage.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeyController {

    @Value("${kakao-javascript-key}")
    private String key;

    // Kakao Javascript Key
    @PostMapping("/getJSKey")
    public String getJSKey() {
        return key;
    }

}
