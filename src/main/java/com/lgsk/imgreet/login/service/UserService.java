package com.lgsk.imgreet.login.service;

import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional(readOnly = true)
    public User getUserByOauthId(String oAuthId) { return userRepository.findByOauthId(oAuthId);}
}
