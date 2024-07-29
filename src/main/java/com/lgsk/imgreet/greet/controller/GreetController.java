package com.lgsk.imgreet.greet.controller;

import com.lgsk.imgreet.greet.dto.GreetDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetController {

    @PostMapping("/greet")
    public String saveGrit(GreetDTO greetDTO) {
        return "redirect:/";
    }
}
