package com.lgsk.imgreet.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.lgsk.imgreet.admin.dto.CommentReportResponseDTO;
import com.lgsk.imgreet.entity.CommentReport;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

	@Query("  SELECT DISTINCT new com.lgsk.imgreet.admin.dto.CommentReportResponseDTO(cr.comment.id, cr.reason)"
		+ " 	FROM CommentReport cr "
		+ "	   WHERE cr.done = false "
		+ "		 AND (cr.comment.id, cr.reason) IN (  SELECT cr2.comment.id, cr2.reason "
		+ "												FROM CommentReport cr2"
		+ "											GROUP BY cr2.comment.id, cr2.reason )"
		+ "	ORDER BY cr.comment.id")
	Page<CommentReportResponseDTO> findDistinctByDone(Pageable pageable);

	@Modifying
	@Transactional
	@Query(" UPDATE CommentReport cr "
		+ " 	SET cr.done = true "
		+ "   WHERE cr.comment.id = :commentId ")
	void commentReportDoneByCommentId(Long commentId);
}
