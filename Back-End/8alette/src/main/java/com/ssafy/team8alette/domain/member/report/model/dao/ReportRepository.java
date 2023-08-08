package com.ssafy.team8alette.domain.member.report.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.member.report.model.dto.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
