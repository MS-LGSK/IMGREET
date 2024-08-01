package com.lgsk.imgreet.greet.controller;

import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.greet.dto.CreateGreetRequestDTO;
import com.lgsk.imgreet.greet.dto.UpdateGreetRequestDTO;
import com.lgsk.imgreet.greet.service.GreetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GreetController {

    private final GreetService greetService;

    @PostMapping("/greet/draft")
    @PreAuthorize("hasAnyAuthority()")
    public ResponseEntity<Object> temporarySaveGrit(@RequestBody CreateGreetRequestDTO createGreetRequestDTO) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Greet greet = greetService.temporarySaveGreet(createGreetRequestDTO);
            responseMap.put("success", true);
            responseMap.put("message", "초대장을 성공적으로 임시 저장하였습니다.");
            responseMap.put("greetId", greet.getId());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping("/greet/save")
    @PreAuthorize("hasAnyAuthority()")
    public ResponseEntity<Object> saveGrit(@RequestBody UpdateGreetRequestDTO updateGreetRequestDTO) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Greet greet = greetService.saveGreet(updateGreetRequestDTO);
            responseMap.put("greetId", greet.getId());
        } catch (UserPrincipalNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        responseMap.put("success", true);
        responseMap.put("message", "초대장을 성공적으로 저장하였습니다.");
        return ResponseEntity.ok(responseMap);
    }
}