package com.lgsk.imgreet.greet.controller;

import com.lgsk.imgreet.greet.dto.GreetRequestDTO;
import com.lgsk.imgreet.greet.service.GreetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.naming.LimitExceededException;

@Controller
@RequiredArgsConstructor
public class GreetController {

    private final GreetService greetService;

    @PostMapping("/greet")
    public String saveGrit(@RequestBody GreetRequestDTO greetRequestDTO) {
        try {
            greetService.saveGreet(greetRequestDTO);
        } catch (LimitExceededException e) {
            // TODO: global exception handler 처리 필요
        }
        // TODO: 초대장 저장 후 redirection url 재설정 필요
        return "redirect:/";
    }
}
