package com.lgsk.imgreet.component.controller;

import com.lgsk.imgreet.component.dto.ComponentDTO;
import com.lgsk.imgreet.component.dto.ComponentResponseDTO;
import com.lgsk.imgreet.component.service.ComponentService;
import com.lgsk.imgreet.greet.service.GreetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;
    private final GreetService greetService;

    @GetMapping("/start")
    public String getCreateGreetPage() {
        return "createGreet";
    }

    @PostMapping("/greet/{greet_id}/component/save")
    public void saveGreetComponent(@PathVariable("greet_id") Long greetId, @RequestBody List<ComponentDTO> componentDTO) {
        componentService.saveGreetComponent(greetId, componentDTO);
    }

    @GetMapping("/greet/{greet_id}/component/get")
    public String getGreetComponent(@PathVariable("greet_id") Long greetId, Model model) {
        model.addAttribute("title", greetService.getGreetTitle(greetId));
        List<ComponentResponseDTO> components = componentService.getGreetComponent(greetId);

        String componentHtml = components.stream()
                .map(ComponentResponseDTO::getContent)
                .collect(Collectors.joining());

        model.addAttribute("component", componentHtml);
        model.addAttribute("greetId", greetId);
        return "createGreet";
    }

    @PostMapping("/template/{template_id}/component/save")
    public String saveTemplateComponent(@PathVariable("template_id") Long templateId, @RequestBody List<ComponentDTO> componentDTO) {
        componentService.saveTemplateComponent(templateId, componentDTO);
        return "main";
    }

    @GetMapping("/template/{template_id}/component/get")
    public String getTemplateComponent(@PathVariable("template_id") Long templateId, Model model) {
        List<ComponentResponseDTO> components = componentService.getTemplateComponent(templateId);

        String componentHtml = components.stream()
                .map(ComponentResponseDTO::getContent)
                .collect(Collectors.joining());

        model.addAttribute("component", componentHtml);
        model.addAttribute("templateId", templateId);
        return "createGreet";
    }

}