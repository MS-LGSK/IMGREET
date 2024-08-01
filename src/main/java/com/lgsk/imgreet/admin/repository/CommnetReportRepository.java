package com.lgsk.imgreet.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lgsk.imgreet.admin.DTO.CommentReportResponseDTO;
import com.lgsk.imgreet.entity.CommentReport;

public interface CommnetReportRepository extends JpaRepository<CommentReport, Long> {

	@Query("  SELECT DISTINCT new com.lgsk.imgreet.admin.DTO.CommentReportResponseDTO(cr.comment.id, cr.reason)"
		+ " 	FROM CommentReport cr "
		+ "	   WHERE cr.done = false "
		+ "		 AND (cr.comment.id, cr.reason) IN (  SELECT cr2.comment.id, cr2.reason "
		+ "												FROM CommentReport cr2"
		+ "											GROUP BY cr2.comment.id, cr2.reason )"
		+ "	ORDER BY cr.comment.id")
	List<CommentReportResponseDTO> findDistinctByDone();
}
