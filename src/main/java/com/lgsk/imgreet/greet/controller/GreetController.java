package com.lgsk.imgreet.greet.controller;

import com.lgsk.imgreet.base.commonUtil.JwtUtil;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.greet.dto.CreateGreetRequestDTO;
import com.lgsk.imgreet.greet.dto.GreetResponseDTO;
import com.lgsk.imgreet.greet.dto.UpdateGreetRequestDTO;
import com.lgsk.imgreet.greet.service.GreetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GreetController {

    private final GreetService greetService;
    private final JwtUtil jwtUtil;

    @ResponseBody
    @GetMapping("/share/{token}")
    public ResponseEntity<String> sharePage(@PathVariable String token, Model model) {
        long greetId = Long.parseLong(jwtUtil.getClaims(token).get("greetId").toString());
        if (greetId == 0L) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
        model.addAttribute("greetId", greetId);

        // TODO: remove hard coding
        String greetUrl = "http://localhost:8080/greet/share/" + greetId;
        // TODO: RestTemplate DI 적용
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(
                greetUrl,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                String.class
        );
        return ResponseEntity.status(responseEntity.getStatusCode())
                .body(responseEntity.getBody());
    }

    @GetMapping("/greet/share/{id}")
    public String showGreet(@PathVariable Long id, Model model) {
        GreetResponseDTO responseDTO = greetService.getGreetById(id);
        model.addAttribute("greet", responseDTO);
        return "share";
    }

    @ResponseBody
    @GetMapping("/greet/myGreet")
    public ResponseEntity<Object> getMyGreet(@Param("id") Long userId) {
        List<Greet> greetList = greetService.getGreetByUserId(userId);
        if (greetList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(greetList);
    }

    @ResponseBody
    @PostMapping("/greet/draft")
    @PreAuthorize("hasAnyAuthority()")
    public ResponseEntity<Object> temporarySaveGrit(@RequestBody CreateGreetRequestDTO createGreetRequestDTO) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Greet greet = greetService.temporarySaveGreet(createGreetRequestDTO);
            responseMap.put("greetId", greet.getId());
            responseMap.put("userId", greet.getUser().getId());
        } catch (Exception e) {
            responseMap.put("success", false);
            responseMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(responseMap);
        }
        responseMap.put("success", true);
        responseMap.put("message", "초대장을 성공적으로 임시 저장하였습니다.");
        return ResponseEntity.ok(responseMap);
    }

    @ResponseBody
    @PostMapping("/greet/save")
    @PreAuthorize("hasAnyAuthority()")
    public ResponseEntity<Object> saveGrit(@RequestBody UpdateGreetRequestDTO updateGreetRequestDTO) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Greet greet = greetService.saveGreet(updateGreetRequestDTO);
            responseMap.put("greetId", greet.getId());
            responseMap.put("redirectUrl", greet.getUrl());
        } catch (UserPrincipalNotFoundException e) {
            responseMap.put("success", false);
            responseMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(responseMap);
        }
        responseMap.put("success", true);
        responseMap.put("message", "초대장을 성공적으로 저장하였습니다.");
        return ResponseEntity.ok(responseMap);
    }
}