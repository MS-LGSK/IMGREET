package com.lgsk.imgreet.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GreetReportResponseDTO {

	private Long greetId;
	private String reason;

	public GreetReportResponseDTO(Long greetId, String reason) {
		this.greetId = greetId;
		this.reason = reason;
	}

}
