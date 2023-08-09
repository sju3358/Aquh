package com.ssafy.team8alette.domain.symbol.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.symbol.model.dto.grant.response.GrantResponseDTO;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.response.SymbolListResponseDTO;
import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;
import com.ssafy.team8alette.domain.symbol.model.service.SymbolGrantService;
import com.ssafy.team8alette.domain.symbol.model.service.SymbolService;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/symbol")
public class SymbolController {

	private final SymbolService symbolService;
	private final SymbolGrantService grantService;

	@LoginRequired
	@GetMapping("/list/{memberNumber}")
	public ResponseEntity<Map<String, Object>> getAllSymbol(@PathVariable Long memberNumber) {

		List<Symbol> list = symbolService.getAllSymbols();

		List<GrantResponseDTO> grantResponseDTOList = grantService.getGrantList(memberNumber);

		List<Long> activeSymbolNumbers = new ArrayList<>();

		for (GrantResponseDTO dto : grantResponseDTOList) {
			if (dto.isSymbolActive()) {
				activeSymbolNumbers.add(dto.getSymbolNumber());
			}
		}

		List<SymbolListResponseDTO> dtoList = new ArrayList<>();
		for (Symbol symbol : list) {
			boolean isSymbolActive = activeSymbolNumbers.contains(symbol.getSymbolNumber());
			SymbolListResponseDTO responseDTO = new SymbolListResponseDTO(
				symbol.getSymbolNumber(),
				symbol.getSymbolName(),
				"https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/" + symbol.getSymbolImgName(),
				symbol.getSymbolCode(),
				symbol.getSymbolConditionCnt(),
				symbol.getCreateDate(),
				isSymbolActive);
			dtoList.add(responseDTO);

			//수정
		}

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("symbolList", dtoList);
		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
