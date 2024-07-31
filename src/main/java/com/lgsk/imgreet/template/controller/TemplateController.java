package com.lgsk.imgreet.template.controller;

import com.lgsk.imgreet.base.commonUtil.Rq;
import com.lgsk.imgreet.component.service.ComponentService;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.template.service.TemplateService;
import com.lgsk.imgreet.component.model.ComponentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final Rq rq;

    @PostMapping("/template/save")
    public String saveTemplate(@RequestBody List<ComponentDTO> componentDTOList) {
        User user = rq.getUser();

        if (user == null) throw new NullPointerException("로그인 한 user만 사용할 수 있습니다.");

        templateService.saveTemplate(user.getId(), componentDTOList);

        return "main";
    }
}
