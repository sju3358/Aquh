package com.ssafy.team8alette.domain.bubble.tools.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.session.repository.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.request.TodoRequestDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.TodoResponseDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.TodoEntity;
import com.ssafy.team8alette.domain.bubble.tools.repository.TodoRepository;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
	private final TodoRepository todoRepository;
	private final BubbleRepository bubbleRepository;
	private final MemberRepository memberRepository;

	// 투두 전체 조회
	public List<TodoResponseDTO> getTodoList(Long bubbleNumber) {
		BubbleEntity bubbleEntity = bubbleRepository.findById(bubbleNumber).orElseThrow();
		List<TodoEntity> todoEntities = todoRepository.findAllByBubbleEntity(bubbleEntity);
		List<TodoResponseDTO> dtoList = new ArrayList<>();
		for (TodoEntity entity : todoEntities) {
			TodoResponseDTO dto = new TodoResponseDTO();
			dto.setTodo_number(entity.getTodoNumber());
			dto.setTodo_context(entity.getTodoContext());
			dto.setTodo_done_state(entity.isTodoDoneStatus());
			dtoList.add(dto);
		}
		return dtoList;
	}

	// 투두 등록
	public void registTodo(TodoRequestDTO todoRequestDTO) {
		TodoEntity todoEntity = new TodoEntity();
		todoEntity.setBubbleEntity(bubbleRepository.findById(todoRequestDTO.getBubble_number()).orElseThrow());
		todoEntity.setTodoContext(todoRequestDTO.getTodo_context());
		todoEntity.setTodoDoneStatus(false);
		todoRepository.save(todoEntity);
	}

	// 투두 상태 변경
	public void updateTodo(Long todo_number) {
		TodoEntity todoEntity = todoRepository.findById(todo_number).orElseThrow();
		todoEntity.setTodoDoneStatus(!todoEntity.isTodoDoneStatus());
		todoRepository.save(todoEntity);
	}

	// 양자택일 질문 삭제
	public void deleteTodo(Long todo_number) {
		if (todoRepository.existsById(todo_number))
			todoRepository.deleteById(todo_number);
	}
}
