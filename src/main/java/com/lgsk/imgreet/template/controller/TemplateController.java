package com.lgsk.imgreet.template.controller;

import com.lgsk.imgreet.base.commonUtil.Rq;
import com.lgsk.imgreet.component.dto.ComponentDTO;
import com.lgsk.imgreet.component.dto.ComponentResponseDTO;
import com.lgsk.imgreet.component.service.ComponentService;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.template.dto.TemplateDTO;
import com.lgsk.imgreet.template.dto.TemplateResponseDTO;
import com.lgsk.imgreet.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class TemplateController {

    private final TemplateService templateService;
    private final Rq rq;

    @PostMapping("/template/save")
    public ResponseEntity saveTemplate(@RequestBody TemplateDTO templateDTO) {
        User user = rq.getUser();

        if (user == null) throw new NullPointerException("로그인 한 user만 사용할 수 있습니다.");

        templateService.saveTemplate(templateDTO.getTitle(), templateDTO.getImageUrl(), user.getId(), templateDTO.getComponentList());

        return ResponseEntity.ok("template 저장 성공");
    }

    @GetMapping("/templates")
    public String getAllTemplates(Model model) {
        List<TemplateResponseDTO> templates = templateService.getAllTemplates();
        model.addAttribute("templates", templates);
        return "main";
    }

    @GetMapping("/template/{template_id}")
    public String getTemplate(@PathVariable("template_id") Long templateId, Model model) {
        List<ComponentResponseDTO> components = templateService.getTemplate(templateId);
        log.info("component : " + components.get(0).getContent());
        model.addAttribute("components", components);
        model.addAttribute("templateId", templateId);
        return "createGreet";
    }
}
