package com.ssafy.team8alette.domain.auth.report.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.auth.member.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.auth.member.model.dto.Member;
import com.ssafy.team8alette.domain.auth.report.model.dao.ReportRepository;
import com.ssafy.team8alette.domain.auth.report.model.dto.entity.Report;
import com.ssafy.team8alette.global.exception.NullValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final MemberRepository memberRepository;

	public void sendReport(Map<String, String> param) {
		Long reportReporter = Long.parseLong(param.get("reporter"));
		Long reportSuspect = Long.parseLong(param.get("suspect"));
		String reportReason = param.get("reason");
		String reportContent = param.get("resultContent");

		Member reporter = memberRepository.findById(reportReporter).orElseThrow();
		Member reportedMember = memberRepository.findById(reportSuspect).orElseThrow();
		if (reporter.getMemberNumber() == reportedMember.getMemberNumber()) {
			throw new NullValueException("같은 회원끼리는 신고할 수 없습니다.");
		}

		Report report = new Report();
		report.setReportReporter(reporter);
		report.setReportSuspect(reportedMember);
		report.setReportReason(reportReason);
		report.setResultContent(reportContent);

		reportRepository.save(report);

	}
}
