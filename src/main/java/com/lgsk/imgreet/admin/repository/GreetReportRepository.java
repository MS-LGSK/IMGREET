package com.lgsk.imgreet.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lgsk.imgreet.admin.DTO.GreetReportResponseDTO;
import com.lgsk.imgreet.entity.GreetReport;

public interface GreetReportRepository extends JpaRepository<GreetReport, Long> {

	@Query(" SELECT DISTINCT new com.lgsk.imgreet.admin.DTO.GreetReportResponseDTO(gr.greet.id, gr.reason) "
		+ "    FROM GreetReport gr "
		+ "   WHERE gr.done = false "
		+ " 	AND (gr.greet.id, gr.reason) IN ( SELECT gr2.greet.id, gr2.reason "
		+ "										    FROM GreetReport gr2"
		+ "									    GROUP BY gr2.greet.id, gr2.reason )"
		+ " ORDER BY gr.greet.id")
	Page<GreetReportResponseDTO> findDistinctByDone(Pageable pageable);

}
