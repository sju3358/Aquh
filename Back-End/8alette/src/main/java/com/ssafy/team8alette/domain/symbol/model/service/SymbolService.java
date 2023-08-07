package com.ssafy.team8alette.domain.symbol.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.symbol.model.dao.SymbolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SymbolService {

	private final SymbolRepository symbolRepository;

	// public List<Symbol> getAllSymbols(){
	// 	return
	// }

}
