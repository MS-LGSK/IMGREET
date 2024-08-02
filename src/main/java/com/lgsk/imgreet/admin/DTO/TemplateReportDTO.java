package com.lgsk.imgreet.admin.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateReportDTO {

	private Long templateId;
	private String templateTitle;
	private String templateURL;
	private String reason;
	private Long creatorId;

}