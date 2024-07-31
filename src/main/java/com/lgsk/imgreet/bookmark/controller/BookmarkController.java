package com.lgsk.imgreet.bookmark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgsk.imgreet.base.commonUtil.Rq;
import com.lgsk.imgreet.bookmark.service.BookmarkService;
import com.lgsk.imgreet.entity.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookmarkController {

	private final Rq rq;
	private final BookmarkService bookmarkService;

	@PostMapping("/bookmarks")	//Restful API 개발에는 @PathVariable이 더 좋음
	public String saveBookark(@RequestParam("templateId") Long templateId) {
		User user = rq.getUser();
		bookmarkService.saveBookmark(user, templateId);

		return "redirect:/";
	}
}
