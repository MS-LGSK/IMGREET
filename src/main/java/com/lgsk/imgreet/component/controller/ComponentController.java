package com.lgsk.imgreet.component.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lgsk.imgreet.component.dto.ComponentDTO;
import com.lgsk.imgreet.component.dto.ComponentResponseDTO;
import com.lgsk.imgreet.component.service.ComponentService;
import com.lgsk.imgreet.greet.service.GreetService;

import lombok.RequiredArgsConstructor;


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
        // 컴포넌트 목록을 서비스에서 가져옵니다.
        List<ComponentResponseDTO> components = componentService.getTemplateComponent(templateId);

        // SVG와 텍스트 컴포넌트용 HTML을 담을 StringBuilder를 초기화합니다.
        StringBuilder svgHtml = new StringBuilder();
        StringBuilder textHtml = new StringBuilder();

        // 컴포넌트를 순회하면서 HTML을 생성합니다.
        for (ComponentResponseDTO component : components) {
            String content = component.getContent();

            if (content.contains("<circle") || content.contains("<rect") || content.contains("<svg")) {
                svgHtml.append(content); // SVG 컴포넌트의 HTML을 추가합니다.
            } else if (content.contains("<textarea") || content.contains("<img")) {
                textHtml.append(content); // 텍스트 컴포넌트의 HTML을 추가합니다.
            }
        }

        // SVG와 텍스트 HTML을 합칩니다.
        String componentHtml = svgHtml.toString() + textHtml.toString();

        // 모델에 HTML과 템플릿 ID를 추가합니다.
        model.addAttribute("componentHtml", componentHtml);
        model.addAttribute("templateId", templateId);

        // 뷰 이름을 반환합니다.
        return "createGreet";
    }

}