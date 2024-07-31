package com.lgsk.imgreet.template.controller;

import com.lgsk.imgreet.base.commonUtil.Rq;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final Rq rq;

    @PostMapping("/template/save")
    public String saveTemplate() {
        User user = rq.getUser();

        if(user != null) {
            templateService.saveTemplate(user.getId());
        }
        return "main";
    }
}
