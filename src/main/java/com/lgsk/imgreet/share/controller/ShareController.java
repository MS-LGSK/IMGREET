package com.lgsk.imgreet.share.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShareController {

    @GetMapping("/share")
    public String sharePage() {
        return "share";
    }
}