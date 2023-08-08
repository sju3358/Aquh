package com.ssafy.team8alette.domain.symbol.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;
import com.ssafy.team8alette.domain.symbol.model.service.SymbolService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/symbol")
public class SymbolController {

	private final SymbolService symbolService;

	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getAllSymbol() {
		List<Symbol> list = symbolService.getAllSymbols();
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("symbolList", list);
		responseData.put("message", "success");
		responseData.put("status", 200);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
