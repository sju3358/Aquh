package com.ssafy.team8alette.report.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.member.util.NullValueChecker;
import com.ssafy.team8alette.report.model.dao.ReportRepository;
import com.ssafy.team8alette.report.model.dto.Report;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final NullValueChecker nullValueChecker;

	public void sendReport(Map<String, String> param) {
		Long reportReporter = Long.parseLong(param.get("reporter"));
		Long reportSuspect = Long.parseLong(param.get("suspect"));
		String reportReason = param.get(reportReporter);

		Report report = new Report();
		report.setReportReporter(reportReporter);
		report.setReportSuspect(reportSuspect);
		report.setReportReason(reportReason);

		reportRepository.save(report);

	}
}
