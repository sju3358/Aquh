package com.ssafy.team8alette.domain.bubble.tools.model.dto.response;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Category {

	private String categoryName;

	private Date createDate;

}