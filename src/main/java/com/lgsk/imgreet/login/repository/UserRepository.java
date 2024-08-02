package com.lgsk.imgreet.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByOauthId(String oauthId);

    User findByNickname(String nickname);

    @Modifying
    @Transactional
    @Query(" UPDATE User u"
        + "     SET u.role = :role "
        + "   WHERE u.id = :userId ")
    int updateBanByUserId(Long userId, Role role);
}
