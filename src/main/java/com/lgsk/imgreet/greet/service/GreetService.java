package com.lgsk.imgreet.greet.service;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.greet.dto.GreetRequestDTO;
import com.lgsk.imgreet.greet.model.GreetResponseDTO;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.LimitExceededException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GreetService {

    private final GreetRepository greetRepository;

    private final GreetService self;

    @Transactional
    public Greet saveGreet(GreetRequestDTO dto) throws LimitExceededException {
        Long userGreetCount = countGreetsByUserId(dto.getUser().getId());
        if (isFreeUser(dto.getUser().getRole()) &&  userGreetCount >= 3) {
            throw new LimitExceededException("무료 사용자의 초대장 생성 제한을 초과하였습니다.");
        }
        return greetRepository.save(
                Greet.builder()
                        .user(dto.getUser())
                        .title(dto.getTitle())
                        .url(dto.getUrl())
                        .imageUrl(dto.getImageUrl())
                        .expireDate(dto.getExpireDate())
                        .allowComments(dto.isAllowComments())
                        .build());
    }

    @Transactional(readOnly = true)
    public String getGreetTitle(Long greetId) {
        Greet greet = greetRepository.findById(greetId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 초대장입니다."));

        return greet.getTitle();
    }

    @Transactional(readOnly = true)
    public List<GreetResponseDTO> getAllGreets() {
        return greetRepository.findAll().stream().map(e ->
                        GreetResponseDTO.builder()
                                .id(e.getId())
                                .user(e.getUser())
                                .title(e.getTitle())
                                .url(e.getUrl())
                                .imageUrl(e.getImageUrl())
                                .expireDate(e.getExpireDate())
                                .allowComments(e.isAllowComments())
                                .build())
                .toList();
    }

    private int countGreetsByUserId(Long userId) {
        return self.getGreetByUserId(userId).size();
    }

    @Transactional(readOnly = true)
    public List<Greet> getGreetByUserId(Long userId) {
        return greetRepository.findByUser_Id(userId);
    }

    private boolean isFreeUser(Role userRole) {
        return userRole.equals(Role.FREE_USER);
    }
}
