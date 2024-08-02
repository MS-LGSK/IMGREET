package com.lgsk.imgreet.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


	// 신고 템플릿 관리 - 신고 템플릿 목록 가져오기
	@GetMapping("/TemplateReport")
	public String getTemplateReport(@RequestParam(defaultValue = "0") int page, Model model) {

		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		Page<TemplateReportDTO> templateReportDTOPage = adminService.getTemplateReportList(pageable);

		model.addAttribute("templateReportPage", templateReportDTOPage);
		model.addAttribute("current", "Template");

		return "adminPage";
	}


	// 신고 댓글 관리 - 신고 댓글 목록 가져오기
	@GetMapping("/CommentReport")
	public String getCommentReport(@RequestParam(defaultValue = "0") int page, Model model) {

		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		Page<CommentReportDTO> commentReportDTOPage = adminService.getCommentReportList(pageable);

		model.addAttribute("commentReportPage", commentReportDTOPage);
		model.addAttribute("current", "Comment");

		return "adminPage";
	}


	// 신고 초대장 삭제
	@PostMapping("/deleteGreet/{greetId}")
	public String deleteGreet(@PathVariable Long greetId, @RequestParam Long userId) {
		adminService.deleteGreet(greetId, userId);
		return "redirect:/adminPage/GreetReport";
	}

	// 신고 초대장 취소
	@PostMapping("/updateGreet/{greetId}")
	public String updateGreet(@PathVariable Long greetId) {
		adminService.updateGreet(greetId);
		return "redirect:/adminPage/GreetReport";
	}


	// 신고 템플릿 삭제
	@PostMapping("/deleteTemplate/{templateId}")
	public String deleteTemplate(@PathVariable Long templateId, @RequestParam Long userId) {
		adminService.deleteTemplate(templateId, userId);
		return "redirect:/adminPage/TemplateReport";
	}

	// 신고 템플릿 취소
	@PostMapping("/updateTemplate/{templateId}")
	public String updateTemplate(@PathVariable Long templateId) {
		adminService.updateTemplate(templateId);
		return "redirect:/adminPage/TemplateReport";
	}


	// 신고 댓글 삭제
	@PostMapping("/deleteComment/{commentId}")
	public String deleteComment(@PathVariable Long commentId) {
		adminService.deleteComment(commentId);
		return "redirect:/adminPage/CommentReport";
	}

	// 신고 댓글 취소
	@PostMapping("/updateComment/{commentId}")
	public String updateComment(@PathVariable Long commentId) {
		adminService.updateComment(commentId);
		return "redirect:/adminPage/CommentReport";
	}
}
