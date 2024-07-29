package com.lgsk.imgreet.greet.service;

import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.greet.dto.GreetDTO;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GreetService {

    private final GreetRepository greetRepository;

    public void saveGreet(GreetDTO dto) {
        greetRepository.save(
                Greet.builder()
                        .user(dto.getUser())
                        .title(dto.getTitle())
                        .url(dto.getUrl())
                        .imageUrl(dto.getImageUrl())
                        .expireDate(dto.getExpireDate())
                        .allowComments(dto.isAllowComments())
                        .build());
    }
}
