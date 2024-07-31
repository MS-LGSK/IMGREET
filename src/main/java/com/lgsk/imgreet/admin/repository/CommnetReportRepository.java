package com.lgsk.imgreet.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgsk.imgreet.entity.CommentReport;

public interface CommnetReportRepository extends JpaRepository<CommentReport, Long> {
	List<CommentReport> findAllByDone(boolean done);
}
