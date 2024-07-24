package com.lgsk.imgreet.login.repository;

import com.lgsk.imgreet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);
}
