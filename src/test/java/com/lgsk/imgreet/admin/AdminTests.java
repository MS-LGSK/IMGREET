package com.lgsk.imgreet.admin;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.login.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class AdminTests {

	@Autowired private UserRepository userRepository;

	@Test
	@Transactional
	@DisplayName("관리자 권한 부여하기")
	void assignAdmin() {

		String name = "이름";
		User user = userRepository.findByNickname(name);

		User updateUser = user.toBuilder()
							.role(Role.ADMIN)
							.build();
		userRepository.save(updateUser);

		User checkUser = userRepository.findByNickname(name);
		assertThat(checkUser.getRole()).isEqualTo(Role.ADMIN);
	}

	@Test
	@Transactional
	@DisplayName("관리자 권한 철회하기")
	void revokeAdmin() {

		String name = "이름";
		User user = userRepository.findByNickname(name);

		User updateUser = user.toBuilder()
			.role(Role.FREE_USER)
			.build();
		userRepository.save(updateUser);

		User checkUser = userRepository.findByNickname(name);
		assertThat(checkUser.getRole()).isEqualTo(Role.FREE_USER);
	}
}