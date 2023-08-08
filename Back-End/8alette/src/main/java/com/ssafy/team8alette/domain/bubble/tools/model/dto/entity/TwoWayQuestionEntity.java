package com.ssafy.team8alette.domain.bubble.tools.model.dto.entity;

import java.util.Date;

import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "two_way_question")
public class TwoWayQuestionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "two_way_question_number", nullable = false)
	private Long twoWayQuestionNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bubble_number", nullable = false)
	private BubbleEntity bubbleEntity;

	@Column(name = "left_context", nullable = false)
	private String leftContext;

	@Column(name = "right_context", nullable = false)
	private String rightContext;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

	@Override
	public String toString() {
		return "TwoWayQuestionEntity{" +
			"twoWayQuestionNumber=" + twoWayQuestionNumber +
			", bubbleEntity=" + bubbleEntity +
			", leftContext='" + leftContext + '\'' +
			", rightContext='" + rightContext + '\'' +
			", createDate=" + createDate +
			'}';
	}
}
