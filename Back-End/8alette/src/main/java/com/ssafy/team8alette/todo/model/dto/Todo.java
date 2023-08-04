package com.ssafy.team8alette.todo.model.dto;

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
@Table(name = "todo")
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "todo_number", nullable = false)
	private Long todoNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_number", nullable = false)
	private Room room;

	@Column(name = "todo_context", nullable = false)
	private String todoContext;

	@Column(name = "is_todo_done", nullable = false)
	private boolean TodoDoneStatus;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

}
