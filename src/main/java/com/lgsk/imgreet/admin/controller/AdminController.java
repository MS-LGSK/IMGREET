package com.lgsk.imgreet.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgsk.imgreet.admin.DTO.CommentReportDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportDTO;
import com.lgsk.imgreet.admin.service.AdminService;
import com.lgsk.imgreet.base.commonUtil.Rq;
import com.lgsk.imgreet.base.entity.Role;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/adminPage")
@RequiredArgsConstructor
public class AdminController {

	private final Rq rq;
	private final AdminService adminService;

	// 신고 초대장 관리
	@GetMapping("/getGreetReport")
	public String getGreetReportList (Model model) throws IllegalAccessException {

		if (rq.getUser().getRole() != Role.ADMIN) {
			throw new IllegalAccessException("잘못된 접근입니다.");
		}

		List<GreetReportDTO> greetReportDTOList = adminService.getGreetReportList();

		model.addAttribute("greetReportDTOList", greetReportDTOList);
		model.addAttribute("current", "GreetReport");

		return "adminPage";
	}


	// 신고 댓글 관리
	@GetMapping("/getCommentReport")
	public String getCommentReport(Model model) throws IllegalAccessException {

		if (rq.getUser().getRole() != Role.ADMIN) {
			throw new IllegalAccessException("잘못된 접근입니다.");
		}

		List<CommentReportDTO> commentReportDTOList = adminService.getCommentReportList();

		model.addAttribute("commentReportDTOList", commentReportDTOList);
		model.addAttribute("current", "CommentReport");

		return "adminPage";
	}
}
