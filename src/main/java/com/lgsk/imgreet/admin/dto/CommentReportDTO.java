package com.lgsk.imgreet.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReportDTO {

	private Long greetId;
	private String greetTitle;
	private String greetUrl;
	private Long commentId;
	private String commentContent;
	private String reportReason;

}
