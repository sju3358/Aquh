package com.ssafy.team8alette.domain.symbol.model.dto.grant.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrantResponseDTO {
	Long symbolNumber;
	String symbolName;

	String symbolImgName;
	String symbolCode;
	int symbolConditionCnt;
	boolean isSymbolActive;
}
