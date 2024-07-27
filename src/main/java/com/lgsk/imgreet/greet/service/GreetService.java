package com.lgsk.imgreet.greet.service;

import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.greet.model.GetGreetTitleDTO;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GreetService {

    private final GreetRepository greetRepository;

    @Transactional(readOnly = true)
    public String getGreetTitle(Long greetId) {
        Greet greet = greetRepository.findById(greetId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 초대장입니다."));

        return greet.getTitle();
    }
}
