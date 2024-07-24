package com.lgsk.imgreet.component.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
public class ComponentController {

    @GetMapping("/start")
    public String getCreateGreetPage() {
        return "createGreet";
    }
}
