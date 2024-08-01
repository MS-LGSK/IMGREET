package com.lgsk.imgreet.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgsk.imgreet.admin.DTO.CommentReportDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportDTO;
import com.lgsk.imgreet.admin.DTO.TemplateReportDTO;
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

	// 신고 초대장 관리 - 신고 초대장 목록 가져오기
	@GetMapping("/GreetReport")
	public String getGreetReportList (Model model) throws IllegalAccessException {

		if (rq.getUser().getRole() != Role.ADMIN) {
			throw new IllegalAccessException("잘못된 접근입니다.");
		}

		List<GreetReportDTO> greetReportDTOList = adminService.getGreetReportList();

		model.addAttribute("greetReportList", greetReportDTOList);
		model.addAttribute("current", "Greet");

		return "adminPage";
	}


	// 신고 템플릿 관리
	@GetMapping("/TemplateReport")
	public String getTemplateReport(Model model) throws IllegalAccessException {
		if (rq.getUser().getRole() != Role.ADMIN) {
			throw new IllegalAccessException("잘못된 접근입니다.");
		}

		List<TemplateReportDTO> templateReportDTOList = adminService.getTemplateReportList();

		model.addAttribute("templateReportList", templateReportDTOList);
		model.addAttribute("current", "Template");

		return "adminPage";
	}


	// 신고 댓글 관리 - 신고 댓글 목록 가져오기
	@GetMapping("/CommentReport")
	public String getCommentReport(Model model) throws IllegalAccessException {

		if (rq.getUser().getRole() != Role.ADMIN) {
			throw new IllegalAccessException("잘못된 접근입니다.");
		}

		List<CommentReportDTO> commentReportDTOList = adminService.getCommentReportList();

		model.addAttribute("commentReportList", commentReportDTOList);
		model.addAttribute("current", "Comment");

		return "adminPage";
	}




	// 신고 초대장 삭제
	@DeleteMapping("/deleteGreet/{greetId}")
	public String deleteGreet(@PathVariable Long GreetId) {
		return "adminPage";
	}
}
