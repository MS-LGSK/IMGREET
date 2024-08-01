package com.lgsk.imgreet.bookmark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgsk.imgreet.base.commonUtil.Rq;
import com.lgsk.imgreet.bookmark.service.BookmarkService;
import com.lgsk.imgreet.entity.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

	private final Rq rq;
	private final BookmarkService bookmarkService;

	@PostMapping("/{templateId}")
	public String saveBookark(@PathVariable("templateId") Long templateId) {

		User user = rq.getUser();

		// 로그인 상태 확인
		if (user == null) {
			return "redirect:/login";
		}

		bookmarkService.saveBookmark(user, templateId);

		return "redirect:/";
	}
}
