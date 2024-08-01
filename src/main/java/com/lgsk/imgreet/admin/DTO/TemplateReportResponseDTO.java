package com.lgsk.imgreet.admin.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateReportResponseDTO {

	private Long templateId;
	private String reason;

	public TemplateReportResponseDTO(Long templateId, String reason) {
		this.templateId = templateId;
		this.reason = reason;
	}

}
