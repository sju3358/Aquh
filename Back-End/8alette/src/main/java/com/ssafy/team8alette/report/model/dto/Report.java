package com.ssafy.team8alette.report.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "report")
@RequiredArgsConstructor
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_number")
	private Long reportNumber;

	@Column(name = "reporter", nullable = false)
	private Long reportReporter;
	@Column(name = "suspect", nullable = false)
	private Long reportSuspect;

	@Column(name = "reason", nullable = false)
	private String reportReason;
}
