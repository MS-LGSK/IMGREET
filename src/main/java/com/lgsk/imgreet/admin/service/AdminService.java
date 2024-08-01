package com.lgsk.imgreet.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgsk.imgreet.admin.DTO.CommentReportDTO;
import com.lgsk.imgreet.admin.DTO.CommentReportResponseDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportResponseDTO;
import com.lgsk.imgreet.admin.DTO.TemplateReportDTO;
import com.lgsk.imgreet.admin.DTO.TemplateReportResponseDTO;
import com.lgsk.imgreet.admin.repository.CommnetReportRepository;
import com.lgsk.imgreet.admin.repository.GreetReportRepository;
import com.lgsk.imgreet.admin.repository.TemplateReportRepository;
import com.lgsk.imgreet.comment.repository.CommentRepository;
import com.lgsk.imgreet.entity.Comment;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import com.lgsk.imgreet.template.repository.TemplateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final GreetReportRepository greetReportRepository;
	private final CommnetReportRepository commentReportRepository;
	private final TemplateReportRepository templateReportRepository;
	private final GreetRepository greetRepository;
	private final CommentRepository commentRepository;
	private final TemplateRepository templateRepository;

	// 신고 초대장 목록 가져오기
	@Transactional
	public List<GreetReportDTO> getGreetReportList() {
		List<GreetReportDTO> greetReportDTOList = new ArrayList<>();

		List<GreetReportResponseDTO> greetReportResponseDTOList = greetReportRepository.findDistinctByDone();
		for (GreetReportResponseDTO greetReport : greetReportResponseDTOList) {
			Greet greet = greetRepository.findById(greetReport.getGreetId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 초대장입니다."));

			GreetReportDTO greetReportDTO = GreetReportDTO.builder()
				.greetId(greetReport.getGreetId())
				.greetTitle(greet.getTitle())
				.greetUrl(greet.getTitle())
				.reason(greetReport.getReason())
				.userId(greet.getUser().getId())
				.build();

			greetReportDTOList.add(greetReportDTO);
		}

		return greetReportDTOList;
	}


	// 신고 템플릿 목록 가져오기
	@Transactional
	public List<TemplateReportDTO> getTemplateReportList() {
		List<TemplateReportDTO> templateReportDTOList = new ArrayList<>();

		List<TemplateReportResponseDTO> templateReportList = templateReportRepository.findDistinctByDone();
		for (TemplateReportResponseDTO templateReport : templateReportList) {
			Template template = templateRepository.findById(templateReport.getTemplateId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 템플릿입니다."));

			TemplateReportDTO templateReportDTO = TemplateReportDTO.builder()
				.templateId(template.getId())
				.templateTitle(template.getTitle())
				.templateURL("templateUrl")
				.reason(templateReport.getReason())
				.creatorId(template.getCreatorId())
				.build();

			templateReportDTOList.add(templateReportDTO);
		}

		return templateReportDTOList;
	}


	// 신고 댓글 목록 가져오기
	@Transactional
	public List<CommentReportDTO> getCommentReportList() {
		List<CommentReportDTO> commentReportDTOList = new ArrayList<>();

		List<CommentReportResponseDTO> commentReportList = commentReportRepository.findDistinctByDone();
		for (CommentReportResponseDTO commentReport : commentReportList) {
			Comment comment = commentRepository.findById(commentReport.getCommentId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
			Greet greet = comment.getGreet();

			CommentReportDTO commentReportDTO = CommentReportDTO.builder()
				.greetId(greet.getId())
				.greetTitle(greet.getTitle())
				.greetUrl(greet.getUrl())
				.commentId(comment.getId())
				.commentContent(comment.getContent())
				.reportReason(commentReport.getReason())
				.build();

			commentReportDTOList.add(commentReportDTO);
		}

		return commentReportDTOList;
	}


	// 신고 초대장 삭제
	@Transactional
	public void deleteGreet(Long greetId) {

	}
}
