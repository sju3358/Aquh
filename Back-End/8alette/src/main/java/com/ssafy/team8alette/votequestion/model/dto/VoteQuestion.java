package com.ssafy.team8alette.votequestion.model.dto;

import java.util.Date;

import com.ssafy.team8alette.room.model.dto.Room;

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
@Table(name = "vote_question")
public class VoteQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vote_question_number", nullable = false)
	private Long voteQuestionNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_number", nullable = false)
	private Room room;

	@Column(name = "vote_question_context", nullable = false)
	private String voteQuestionContext;

	@Column(name = "is_active", nullable = false)
	private boolean activeStatus;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

	@Column(name = "delete_dttm", nullable = false)
	private Date deleteDate;

}
