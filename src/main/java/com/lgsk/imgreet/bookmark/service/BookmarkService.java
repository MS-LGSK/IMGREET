package com.lgsk.imgreet.bookmark.service;

import org.springframework.stereotype.Service;

import com.lgsk.imgreet.bookmark.repository.BookmarkRepository;
import com.lgsk.imgreet.entity.Bookmark;
import com.lgsk.imgreet.entity.BookmarkId;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.template.repository.TemplateRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final TemplateRepository templateRepository;

	@Transactional
	public void saveBookmark(User user, Long templateId) {
		BookmarkId bookmarkId = new BookmarkId(user.getId(), templateId);
		Template template = templateRepository.findById(templateId)
			.orElseThrow(() -> new IllegalArgumentException("Template을 찾을 수 없습니다."));

		// 중복 방지
		if (bookmarkRepository.existsById(bookmarkId)) {
			throw new IllegalStateException("이미 추가된 즐겨찾기 입니다.");
		}

		Bookmark bookmark = Bookmark.builder()
			.id(bookmarkId)
			.user(user)
			.template(template)
			.build();

		bookmarkRepository.save(bookmark);
	}
}
