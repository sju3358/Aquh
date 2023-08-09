package com.ssafy.team8alette.domain.member.report.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.member.report.model.service.ReportService;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
@RestController
public class ReportController {

	private final ReportService reportService;

	@LoginRequired
	@PostMapping
	public ResponseEntity<Map<String, Object>> reportRequest(@RequestBody Map<String, String> param) {

		reportService.sendReport(param);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
