package com.lgsk.imgreet.bookmark;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.bookmark.repository.BookmarkRepository;
import com.lgsk.imgreet.entity.Bookmark;
import com.lgsk.imgreet.entity.BookmarkId;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.login.repository.UserRepository;
import com.lgsk.imgreet.template.repository.TemplateRepository;

@SpringBootTest
class BookmarkRepositoryTests {

	@Autowired
	private BookmarkRepository bookmarkRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TemplateRepository templateRepository;

	Template template;
	User user;
	User creator;

	@BeforeEach
	public void setup() {
		// user 정보 저장
		creator = userRepository.save(User.builder()
			.oauthId("kakao_3")
			.nickname("creator")
			.role(Role.FREE_USER)
			.build());

		user = userRepository.save(User.builder()
			.oauthId("kakao_4")
			.nickname("like")
			.role(Role.FREE_USER)
			.build());

		// template 정보 저장
		template = templateRepository.save(Template.builder()
			.creatorId(creator.getId())
			.title("템플릿")
			.imageUrl("imgUrl")
			.build());
	}

	@Test
	@Transactional
	@DisplayName("즐겨찾기 저장 및 템플릿 즐겨찾기 수")
	void saveAndGetTemplateBookmark() {

		bookmarkRepository.save(Bookmark.builder()
			.id(new BookmarkId(user.getId(), template.getId()))
			.user(user)
			.template(template)
			.build());

		List<Bookmark> findBookmark = bookmarkRepository.findAllByTemplateId(template.getId());
		assertThat(findBookmark).hasSize(1);

	}

	@Test
	@Transactional
	@DisplayName("나의 즐겨찾기")
	void saveAndGetMyBookmark() {

		bookmarkRepository.save(Bookmark.builder()
			.id(new BookmarkId(user.getId(), template.getId()))
			.user(user)
			.template(template)
			.build());

		List<Bookmark> findBookmark = bookmarkRepository.findAllByUserId(user.getId());
		assertThat(findBookmark).hasSize(1);

	}

}
