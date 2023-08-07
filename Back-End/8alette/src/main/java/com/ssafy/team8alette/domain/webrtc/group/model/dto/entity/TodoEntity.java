package com.ssafy.team8alette.domain.webrtc.group.model.dto.entity;

import com.ssafy.team8alette.domain.webrtc.openvidu.model.dto.entity.BubbleEntity;

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
@Table(name = "todo")
public class TodoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "todo_number", nullable = false)
	private Long todoNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bubble_number", nullable = false)
	private BubbleEntity bubbleEntity;

	@Column(name = "todo_context", nullable = false)
	private String todoContext;

	@Column(name = "is_todo_done", nullable = false)
	private boolean TodoDoneStatus;

}
