package com.lgsk.imgreet.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgsk.imgreet.admin.DTO.CommentReportDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportDTO;
import com.lgsk.imgreet.admin.repository.CommnetReportRepository;
import com.lgsk.imgreet.admin.repository.GreetReportRepository;
import com.lgsk.imgreet.comment.repository.CommentRepository;
import com.lgsk.imgreet.entity.Comment;
import com.lgsk.imgreet.entity.CommentReport;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.GreetReport;
import com.lgsk.imgreet.greet.repository.GreetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final GreetReportRepository greetReportRepository;
	private final CommnetReportRepository commnetReportRepository;
	private final GreetRepository greetRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public List<GreetReportDTO> getGreetReportList() {
		List<GreetReportDTO> greetReportDTOList = new ArrayList<>();

		List<GreetReport> greetReportList = greetReportRepository.findAllByDone(false);
		for (GreetReport greetReport : greetReportList) {
			Long greetId = greetReport.getGreet().getId();
			Greet greet = greetRepository.findById(greetId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 초대장입니다."));

			GreetReportDTO greetReportDTO = GreetReportDTO.builder()
				.reportId(greetReport.getId())
				.greetId(greetId)
				.greetTitle(greet.getTitle())
				.greetUrl(greet.getTitle())
				.reportReason(greetReport.getReason())
				.userId(greet.getUser().getId())
				.build();

			greetReportDTOList.add(greetReportDTO);
		}

		return greetReportDTOList;
	}


	public List<CommentReportDTO> getCommentReportList() {
		List<CommentReportDTO> commentReportDTOList = new ArrayList<>();

		List<CommentReport> commentReportList = commnetReportRepository.findAllByDone(false);
		for (CommentReport commentReport : commentReportList) {
			Long commentId = commentReport.getComment().getId();
			Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
			Greet greet = comment.getGreet();

			CommentReportDTO commentReportDTO = CommentReportDTO.builder()
				.reportId(commentReport.getId())
				.greetId(greet.getId())
				.greetTitle(greet.getTitle())
				.greetUrl(greet.getTitle())
				.commentId(commentId)
				.commentContent(comment.getContent())
				.reportReason(commentReport.getReason())
				.build();

			commentReportDTOList.add(commentReportDTO);
		}

		return commentReportDTOList;
	}
}
