package com.lgsk.imgreet.login.repository;

import com.lgsk.imgreet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByOauthId(String oauthId);
}
