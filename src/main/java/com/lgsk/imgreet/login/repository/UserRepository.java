package com.lgsk.imgreet.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgsk.imgreet.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByOauthId(String oauthId);

    User findByNickname(String nickname);
}
