package com.lgsk.imgreet.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lgsk.imgreet.admin.DTO.TemplateReportResponseDTO;
import com.lgsk.imgreet.entity.TemplateReport;

public interface TemplateReportRepository extends JpaRepository<TemplateReport, Long> {

	@Query(" SELECT DISTINCT new com.lgsk.imgreet.admin.DTO.TemplateReportResponseDTO(tr.template.id, tr.reason) "
		+ "    FROM TemplateReport tr "
		+ "   WHERE tr.done = false "
		+ " 	AND (tr.template.id, tr.reason) IN ( SELECT tr2.template.id, tr2.reason "
		+ "										    FROM TemplateReport tr2"
		+ "									    GROUP BY tr2.template.id, tr2.reason )"
		+ " ORDER BY tr.template.id")
	List<TemplateReportResponseDTO> findDistinctByDone();
}
