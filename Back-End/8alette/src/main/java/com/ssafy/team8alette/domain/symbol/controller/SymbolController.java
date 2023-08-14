package com.ssafy.team8alette.domain.symbol.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.symbol.model.dto.grant.response.AllSymbolListResponseDTO;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.response.GrantResponseDTO;
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

		List<AllSymbolListResponseDTO> symbolList = new ArrayList<>();

		List<GrantResponseDTO> requiredSymbolList = symbolService.getRequiredSymbolList(memberNumber);
		List<GrantResponseDTO> remainList = symbolService.getRemainList(memberNumber);

		for (GrantResponseDTO requiredSymbol : requiredSymbolList) {
			symbolList.add(new AllSymbolListResponseDTO(
				requiredSymbol.getSymbolNumber(),
				requiredSymbol.getSymbolName(),
				requiredSymbol.getSymbolImgName(),
				requiredSymbol.getSymbolCode(),
				requiredSymbol.getSymbolConditionCnt(),
				true, // acquiredActive
				requiredSymbol.isSymbolActive()
			));
		}

		for (GrantResponseDTO remainSymbol : remainList) {
			symbolList.add(new AllSymbolListResponseDTO(
				remainSymbol.getSymbolNumber(),
				remainSymbol.getSymbolName(),
				remainSymbol.getSymbolImgName(),
				remainSymbol.getSymbolCode(),
				remainSymbol.getSymbolConditionCnt(),
				false, // acquiredActive
				remainSymbol.isSymbolActive()
			));
		}

		Collections.sort(symbolList,
			(symbol1, symbol2) -> Long.compare(symbol1.getSymbolNumber(), symbol2.getSymbolNumber()));

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("symbolList", symbolList);
		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
