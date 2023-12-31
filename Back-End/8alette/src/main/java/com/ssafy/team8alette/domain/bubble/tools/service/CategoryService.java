package com.ssafy.team8alette.domain.bubble.tools.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.tools.model.entity.CategoryEntity;
import com.ssafy.team8alette.domain.bubble.tools.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<CategoryEntity> getCategories() {
		return categoryRepository.findAll();
	}
}
