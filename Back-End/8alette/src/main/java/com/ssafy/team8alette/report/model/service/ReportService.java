package com.ssafy.team8alette.report.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.report.model.dao.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;

	public void sendReport() {

	}
}
