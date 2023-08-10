package com.ssafy.team8alette.domain.symbol.model.dto.grant.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SymbolListResponseDTO {
	private Long symbolNumber;
	private String symbolName;
	private String symbolImgName;
	private String symbolCode;
	private int symbolConditionCnt;

	//여기서는 심볼에따른 활성화여부
	private boolean symbolActive;
}
