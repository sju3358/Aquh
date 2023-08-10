package com.ssafy.team8alette.domain.bubble.tools.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.request.TodoRequestDTO;
import com.ssafy.team8alette.domain.bubble.tools.service.TodoService;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bubble/todo")
public class TodoController {
	private final TodoService todoService;

	// 투두 전체 조회
	@LoginRequired
	@GetMapping("/{bubble_number}")
	public ResponseEntity<Map<String, Object>> findAllTodoList(@PathVariable long bubble_number) {
		Map<String, Object> responseData = new HashMap<>();

		responseData.put("message", "todo list 조회 성공");
		responseData.put("status", 200);
		responseData.put("todo_list", todoService.getTodoList(bubble_number));

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 투두 생성
	@LoginRequired
	@PostMapping
	public ResponseEntity<Map<String, Object>> createTodo(
		@RequestBody TodoRequestDTO todoRequestDTO) {
		Map<String, Object> responseData = new HashMap<>();

		todoService.registTodo(todoRequestDTO);

		responseData.put("message", "todo 등록 성공");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 투두 상태 변경
	@LoginRequired
	@PutMapping("/{todo_number}")
	public ResponseEntity<Map<String, Object>> updateTodo(
		@PathVariable Long todo_number) {
		Map<String, Object> responseData = new HashMap<>();

		todoService.updateTodo(todo_number);

		responseData.put("message", "todo 상태 변경 성공");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 투두 삭제
	@LoginRequired
	@DeleteMapping("/{todo_number}")
	public ResponseEntity<Map<String, Object>> deleteTodo(
		@PathVariable Long todo_number) {
		Map<String, Object> responseData = new HashMap<>();

		todoService.deleteTodo(todo_number);

		responseData.put("message", "todo 삭제 성공");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}