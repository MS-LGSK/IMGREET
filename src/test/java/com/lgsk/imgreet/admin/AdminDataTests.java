package com.lgsk.imgreet.admin;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lgsk.imgreet.admin.repository.CommentReportRepository;
import com.lgsk.imgreet.admin.repository.GreetReportRepository;
import com.lgsk.imgreet.admin.repository.TemplateReportRepository;
import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.comment.repository.CommentRepository;
import com.lgsk.imgreet.entity.Comment;
import com.lgsk.imgreet.entity.CommentReport;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.GreetReport;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.entity.TemplateReport;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import com.lgsk.imgreet.login.repository.UserRepository;
import com.lgsk.imgreet.template.repository.TemplateRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class AdminDataTests {

	@Autowired private UserRepository userRepository;
	@Autowired private GreetRepository greetRepository;
	@Autowired private GreetReportRepository greetReportRepository;
	@Autowired private TemplateRepository templateRepository;
	@Autowired private TemplateReportRepository templateReportRepository;
	@Autowired private CommentRepository commentRepository;
	@Autowired private CommentReportRepository commentReportRepository;

	User badUser;

	@BeforeEach
	void setup() {

		badUser = userRepository.save(User.builder()
			.oauthId("kakao_403")
			.nickname("Bad Creator")
			.role(Role.FREE_USER)
			.build());
	}

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

	@Test
	@Transactional
	@DisplayName("초대장 신고 더미 생성")
	void makeGreetReport() {
		for (int i = 0; i < 10; i++) {
			Greet greet = greetRepository.save(Greet.builder()
				.user(badUser)
				.title("더미" + i)
				.url("url" + i)
				.expireDate(LocalDateTime.now().plusDays(5))
				.allowComments(true)
				.build());

			greetReportRepository.save(GreetReport.builder()
				.reason("신고")
				.ipAddress("ip" + i)
				.greet(greet)
				.done(false)
				.build());
		}
	}

	@Test
	// @Transactional
	@DisplayName("템플릿 신고 더미 생성")
	void makeTemplateReport() {
		for (int i = 0; i < 10; i++) {
			Template template = templateRepository.save(Template.builder()
				.title("메롱메롱" + i)
				.imageUrl("imageUrl" + i)
				.creatorId(badUser.getId())
				.build());

			templateReportRepository.save(TemplateReport.builder()
				.template(template)
				.ipaddress("ipAddress" + i)
				.reason("조롱")
				.done(false)
				.build());
		}
	}

	@Test
	// @Transactional
	@DisplayName("댓글 신고 더미 생성")
	void makeCommentReport() {
		Greet greet = greetRepository.save(Greet.builder()
			.user(badUser)
			.title("더미")
			.url("url")
			.expireDate(LocalDateTime.now().plusDays(5))
			.allowComments(true)
			.build());

		for (int i = 0; i < 10; i++) {
			Comment comment = commentRepository.save(Comment.builder()
				.greet(greet)
				.ipAddress("ipAddress" + i)
				.nickname("BadUser" + i)
				.password("password" + i)
				.content("나쁜 소문")
				.build());

			commentReportRepository.save(CommentReport.builder()
				.comment(comment)
				.reason("비방")
				.done(false)
				.build());
		}
	}
}
