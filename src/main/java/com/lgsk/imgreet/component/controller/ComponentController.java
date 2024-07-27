package com.lgsk.imgreet.component.controller;

import com.lgsk.imgreet.component.service.ComponentService;
import com.lgsk.imgreet.component.model.ComponentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/start")
    public String getCreateGreetPage() {
        return "createGreet";
    }

    @PostMapping("/greet/{greet_id}/component")
    public void saveComponent(@PathVariable("greet_id") Long greetId, @RequestBody List<ComponentDTO> componentDTO) {
        componentService.saveComponent(greetId, componentDTO);
    }
}