package com.lgsk.imgreet.admin.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReportResponseDTO {

	private Long commentId;
	private String reason;

	public CommentReportResponseDTO(Long commentId, String reason) {
		this.commentId = commentId;
		this.reason = reason;
	}

}
