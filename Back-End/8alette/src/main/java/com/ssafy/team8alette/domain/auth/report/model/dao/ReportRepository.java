package com.ssafy.team8alette.domain.auth.report.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.auth.report.model.dto.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
