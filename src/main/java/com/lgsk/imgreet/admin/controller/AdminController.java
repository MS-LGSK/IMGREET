package com.lgsk.imgreet.admin.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgsk.imgreet.admin.DTO.CommentReportDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportDTO;
import com.lgsk.imgreet.admin.DTO.TemplateReportDTO;
import com.lgsk.imgreet.admin.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/adminPage")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	private static final int PAGE_SIZE = 10;

	@GetMapping()
	public String adminPage() {
		return "redirect:/adminPage/GreetReport";
	}

	// 신고 초대장 관리 - 신고 초대장 목록 가져오기
	@GetMapping("/GreetReport")
	public String getGreetReportList (@RequestParam(defaultValue = "0") int page, Model model) {

		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		Page<GreetReportDTO> greetReportDTOPage = adminService.getGreetReportList(pageable);

		model.addAttribute("greetReportPage", greetReportDTOPage);
		model.addAttribute("current", "Greet");

		return "adminPage";
	}


	// 신고 템플릿 관리
	@GetMapping("/TemplateReport")
	public String getTemplateReport(Model model) {

		List<TemplateReportDTO> templateReportDTOList = adminService.getTemplateReportList();

		model.addAttribute("templateReportList", templateReportDTOList);
		model.addAttribute("current", "Template");

		return "adminPage";
	}


	// 신고 댓글 관리 - 신고 댓글 목록 가져오기
	@GetMapping("/CommentReport")
	public String getCommentReport(Model model) {

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
