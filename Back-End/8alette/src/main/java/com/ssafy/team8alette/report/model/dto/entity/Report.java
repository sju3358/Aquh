package com.ssafy.team8alette.report.model.dto.entity;

import java.util.Date;

import com.ssafy.team8alette.member.model.dto.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_number")
	private Long reportNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reporter", nullable = false)
	private Member reportReporter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "suspect", nullable = false)
	private Member reportSuspect;

	@Column(name = "reason", nullable = false)
	private String reportReason;

	@Column(name = "result_state", nullable = false)
	private int resultState;

	@Column(name = "result_content")
	private String resultContent;

	@Column(name = "report_result_dttm")
	private Date reportResultDate;

	// @Column(name = "create_dttm")
	// private Date createDate;
}
