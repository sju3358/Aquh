package com.ssafy.team8alette.category.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.category.model.dto.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
