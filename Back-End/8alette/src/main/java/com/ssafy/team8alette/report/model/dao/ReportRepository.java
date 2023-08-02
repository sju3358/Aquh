package com.ssafy.team8alette.report.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.report.model.dto.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
