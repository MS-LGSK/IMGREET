package com.lgsk.imgreet.template.controller;

import com.lgsk.imgreet.base.commonUtil.Rq;
import com.lgsk.imgreet.component.dto.ComponentDTO;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity saveTemplate(@RequestBody List<ComponentDTO> componentDTOList) {
        User user = rq.getUser();

        if (user == null) throw new NullPointerException("로그인 한 user만 사용할 수 있습니다.");

        templateService.saveTemplate(user.getId(), componentDTOList);

        return ResponseEntity.ok("template 저장 성공");
    }
}
