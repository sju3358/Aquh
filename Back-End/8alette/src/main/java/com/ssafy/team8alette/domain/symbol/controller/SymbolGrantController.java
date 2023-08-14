package com.ssafy.team8alette.domain.symbol.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.request.SymbolGrantRequestDTO;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.response.GrantResponseDTO;
import com.ssafy.team8alette.domain.symbol.model.service.SymbolGrantService;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/symbol/grant")
public class SymbolGrantController {

	private final SymbolGrantService symbolGrantService;
	private final JwtTokenProvider jwtTokenProvider;

	@LoginRequired
	@GetMapping("/{memberNumber}")
	public ResponseEntity<Map<String, Object>> getMemberSymbolList(@PathVariable Long memberNumber) {

		List<GrantResponseDTO> list = symbolGrantService.getGrantList(memberNumber);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("symbolList", list);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PutMapping
	public ResponseEntity<Map<String, Object>> putSymbolConfirmed(@RequestBody SymbolGrantRequestDTO symbolGrant) {

		symbolGrantService.putActiveTrue(symbolGrant);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PutMapping("/{symbolNumber}")
	public ResponseEntity<Map<String, Object>> putSymbolConverse(@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@PathVariable Long symbolNumber) throws
		ParseException {
		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);
		symbolGrantService.putSymbolIsActiveConverse(memberNumber, symbolNumber);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
