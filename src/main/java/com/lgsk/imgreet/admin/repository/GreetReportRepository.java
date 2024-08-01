package com.lgsk.imgreet.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lgsk.imgreet.admin.DTO.GreetReportResponseDTO;
import com.lgsk.imgreet.entity.GreetReport;

public interface GreetReportRepository extends JpaRepository<GreetReport, Long> {

	@Query(" SELECT DISTINCT new com.lgsk.imgreet.admin.DTO.GreetReportResponseDTO(gr.greet.id, gr.reason) "
		+ "    FROM GreetReport gr "
		+ "   WHERE gr.done = false "
		+ " 	AND (gr.reason, gr.greet.id) IN ( SELECT gr2.reason, gr2.greet.id"
		+ "										    FROM GreetReport gr2"
		+ "									    GROUP BY gr2.reason, gr2.greet.id )"
		+ " ORDER BY gr.greet.id")
	List<GreetReportResponseDTO> findDistinctByDone();

}
