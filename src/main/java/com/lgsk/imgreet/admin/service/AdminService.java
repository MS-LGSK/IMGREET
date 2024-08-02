package com.lgsk.imgreet.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgsk.imgreet.admin.DTO.CommentReportDTO;
import com.lgsk.imgreet.admin.DTO.CommentReportResponseDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportResponseDTO;
import com.lgsk.imgreet.admin.DTO.TemplateReportDTO;
import com.lgsk.imgreet.admin.DTO.TemplateReportResponseDTO;
import com.lgsk.imgreet.admin.repository.CommentReportRepository;
import com.lgsk.imgreet.admin.repository.GreetReportRepository;
import com.lgsk.imgreet.admin.repository.TemplateReportRepository;
import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.comment.repository.CommentRepository;
import com.lgsk.imgreet.entity.Comment;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import com.lgsk.imgreet.login.repository.UserRepository;
import com.lgsk.imgreet.template.repository.TemplateRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final GreetReportRepository greetReportRepository;
	private final CommentReportRepository commentReportRepository;
	private final TemplateReportRepository templateReportRepository;
	private final GreetRepository greetRepository;
	private final CommentRepository commentRepository;
	private final TemplateRepository templateRepository;
	private final UserRepository userRepository;

	// 신고 초대장 목록 가져오기
	@Transactional
	public Page<GreetReportDTO> getGreetReportList(Pageable pageable) {
		return greetReportRepository.findDistinctByDone(pageable).map(this::convertToGreetReportDTO);
	}

	private GreetReportDTO convertToGreetReportDTO(GreetReportResponseDTO greetReport) {
		Greet greet = greetRepository.findById(greetReport.getGreetId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 초대장입니다."));

		return GreetReportDTO.builder()
			.greetId(greetReport.getGreetId())
			.greetTitle(greet.getTitle())
			.greetUrl(greet.getUrl())
			.reason(greetReport.getReason())
			.userId(greet.getUser().getId())
			.build();
	}


	// 신고 템플릿 목록 가져오기
	@Transactional
	public Page<TemplateReportDTO> getTemplateReportList(Pageable pageable) {
		return templateReportRepository.findDistinctByDone(pageable).map(this::convertToTemplateDTO);
	}

	private TemplateReportDTO convertToTemplateDTO (TemplateReportResponseDTO templateReport) {
		Template template = templateRepository.findById(templateReport.getTemplateId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 템플릿입니다."));

		return TemplateReportDTO.builder()
			.templateId(template.getId())
			.templateTitle(template.getTitle())
			.templateURL("/template/" + template.getId())
			.creatorId(template.getCreatorId())
			.reason(templateReport.getReason())
			.build();
	}


	// 신고 댓글 목록 가져오기
	@Transactional
	public Page<CommentReportDTO> getCommentReportList(Pageable pageable) {
		return commentReportRepository.findDistinctByDone(pageable).map(this::convertToCommentDTO);
	}
	private CommentReportDTO convertToCommentDTO (CommentReportResponseDTO commentReport) {
		Comment comment = commentRepository.findById(commentReport.getCommentId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

		return CommentReportDTO.builder()
			.greetId(comment.getGreet().getId())
			.greetTitle(comment.getGreet().getTitle())
			.greetUrl(comment.getGreet().getUrl())
			.commentId(comment.getId())
			.commentContent(comment.getContent())
			.reportReason(commentReport.getReason())
			.build();
	}


	// 신고 초대장 삭제
	@Transactional
	public void deleteGreet(Long greetId, Long userId) {
		greetReportRepository.greetReportDoneByGreetId(greetId);
		// greetRepository.deleteById(greetId);

		int updateUserBan = userRepository.updateBanByUserId(userId, Role.BANNED_USER);
		if(updateUserBan == 0) {
			throw new EntityNotFoundException("유저 접근 제한에 실패했습니다.");
		}
	}

	// 신고 초대장 취소
	@Transactional
	public void updateGreet(Long greetId) {
		greetReportRepository.greetReportDoneByGreetId(greetId);
	}


	// 신고 템플릿 삭제
	@Transactional
	public void deleteTemplate(Long templateId, Long userId) {
		templateReportRepository.templateReportDoneByTemplateId(templateId);
		// templateReportRepository.deleteById(templateId);

		int updateUserBan = userRepository.updateBanByUserId(userId, Role.BANNED_USER);
		if(updateUserBan == 0) {
			throw new EntityNotFoundException("유저 접근 제한에 실패했습니다.");
		}
	}

	// 신고 템플릿 취소
	@Transactional
	public void updateTemplate (Long templateId) {
		templateReportRepository.templateReportDoneByTemplateId(templateId);
	}


	// 신고 댓글 삭제
	@Transactional
	public void deleteComment(Long commentId) {
		commentReportRepository.commentReportDoneByCommentId(commentId);
		// commentRepository.deleteById(commentId);
	}

	// 신고 댓글 취소
	@Transactional
	public void updateComment(Long commentId) {
		commentReportRepository.commentReportDoneByCommentId(commentId);
	}
}
