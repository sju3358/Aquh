package com.ssafy.team8alette.domain.symbol.model.dto.grant.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GrantResponseDTO {
	Long symbolNumber;
	String symbolName;

	String symbolImgName;
	String symbolCode;
	int symbolConditionCnt;
	boolean isSymbolActive;

	Date createDate;

}
