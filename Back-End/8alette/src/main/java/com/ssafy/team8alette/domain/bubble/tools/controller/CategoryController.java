package com.ssafy.team8alette.domain.bubble.tools.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.bubble.tools.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getCategoriesRequest() {

		Map<String, Object> responseData = new HashMap<>();

		responseData.put("category", categoryService.getCategories());
		responseData.put("status", 200);
		responseData.put("message", "OK");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
