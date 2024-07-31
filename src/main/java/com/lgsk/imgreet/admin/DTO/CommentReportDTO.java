package com.lgsk.imgreet.admin.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReportDTO {

	Long reportId;
	Long greetId;
	String greetTitle;
	String greetUrl;
	Long commentId;
	String commentContent;
	String reportReason;

}
