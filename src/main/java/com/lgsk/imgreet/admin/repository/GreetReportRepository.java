package com.lgsk.imgreet.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgsk.imgreet.entity.GreetReport;

public interface GreetReportRepository extends JpaRepository<GreetReport, Long> {
	List<GreetReport> findAllByDone(boolean done);
}
