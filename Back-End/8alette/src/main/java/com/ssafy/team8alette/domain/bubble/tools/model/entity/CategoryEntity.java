package com.ssafy.team8alette.domain.bubble.tools.model.entity;

import java.util.Date;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_number", nullable = false)
	private Long categoryNumber;

	@Column(name = "category_name", nullable = false)
	private String categoryName;

	@Column(name = "create_dt", nullable = false)
	private Date createDate;

	public Category convertToDTO() {
		return Category.builder()
			.categoryName(this.categoryName)
			.createDate(this.createDate)
			.build();
	}

}
