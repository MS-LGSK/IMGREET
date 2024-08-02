package com.lgsk.imgreet.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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
	Page<TemplateReportResponseDTO> findDistinctByDone(Pageable pageable);

	@Modifying
	@Transactional
	@Query(" UPDATE TemplateReport tr "
		+ " 	SET tr.done = true "
		+ "   WHERE tr.template.id = :templateId ")
	void templateReportDoneByTemplateId(Long templateId);
}
