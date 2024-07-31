package com.lgsk.imgreet.admin.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GreetReportDTO {

	private Long reportId;

	private Long greetId;

	private String greetTitle;

	private String greetUrl;

	private String reportReason;

	private Long userId;

}
