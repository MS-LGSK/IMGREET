package com.lgsk.imgreet.greet.service;

import com.lgsk.imgreet.base.commonUtil.JwtUtil;
import com.lgsk.imgreet.base.commonUtil.Rq;
import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.greet.dto.CreateGreetRequestDTO;
import com.lgsk.imgreet.greet.dto.GreetResponseDTO;
import com.lgsk.imgreet.greet.dto.UpdateGreetRequestDTO;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.LimitExceededException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GreetService {

    private final GreetRepository greetRepository;

    private final JwtUtil jwtUtil;

    private final Rq rq;

    @Transactional
    public Greet saveGreet(UpdateGreetRequestDTO dto) throws UserPrincipalNotFoundException {
        User user = rq.getUser();
        if (user == null) {
            throw new UserPrincipalNotFoundException("로그인된 유저 정보가 존재하지 않습니다.");
        }
        String token = jwtUtil.generateGreetToken(dto.getId());
        System.out.println("encodedToken = " + token);
        dto.setUrl("/share/%s".formatted(token));
        System.out.println("dto.getUrl() = " + dto.getUrl());
        return greetRepository.save(
                Greet.builder()
                        .id(dto.getId())
                        .user(user)
                        .title(dto.getTitle())
                        .url(dto.getUrl())
                        .imageUrl(dto.getImageUrl())
                        .expireDate(dto.getExpireDate())
                        .allowComments(dto.getAllowComments())
                        .build());
    }

    @Transactional
    public Greet temporarySaveGreet(CreateGreetRequestDTO dto) throws LimitExceededException {
        User user = rq.getUser();
        if (user == null) {
            throw new RuntimeException("사용자 정보가 존재하지 않습니다.");
        }

        int userGreetCount = countGreetsByUserId(user.getId());
        if (isFreeUser(user.getRole()) &&  userGreetCount >= 3) {
            throw new LimitExceededException("무료 사용자의 초대장 생성 제한을 초과하였습니다.");
        }

        return greetRepository.save(
                Greet.builder()
                        .user(user)
                        .title(dto.getTitle())
                        .expireDate(dto.getExpireDate())
                        .allowComments(dto.getAllowComments())
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
                                .allowComments(e.getAllowComments())
                                .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Greet> getGreetByUserId(Long userId) {
        return greetRepository.findByUser_Id(userId);
    }

    private int countGreetsByUserId(Long userId) {
        return getGreetByUserId(userId).size();
    }

    private boolean isFreeUser(Role userRole) {
        return !userRole.equals(Role.PAID_USER);
    }

    @Transactional(readOnly = true)
    public GreetResponseDTO getGreetById(Long id) {
        Optional<Greet> greet = greetRepository.findById(id);
        if(greet.isEmpty()) {
            throw new RuntimeException("초대장이 존재하지 않습니다.");
        }

        Greet responseGreet = greet.get();

        return GreetResponseDTO.builder()
                .id(responseGreet.getId())
                .user(responseGreet.getUser())
                .title(responseGreet.getTitle())
                .url(responseGreet.getUrl())
                .imageUrl(responseGreet.getImageUrl())
                .expireDate(responseGreet.getExpireDate())
                .allowComments(responseGreet.getAllowComments())
                .build();
    }
}
