package com.ssafy.team8alette.category.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.category.model.dao.CategoryRepository;
import com.ssafy.team8alette.category.model.dto.Category;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}
}
