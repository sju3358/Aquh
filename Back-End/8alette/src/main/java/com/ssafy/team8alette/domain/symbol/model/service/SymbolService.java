package com.ssafy.team8alette.domain.symbol.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.symbol.model.dao.SymbolRepository;
import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SymbolService {

	private final SymbolRepository symbolRepository;

	public List<Symbol> getAllSymbols() {
		return symbolRepository.findAll();
	}

	public Symbol getCharacter(Long number) {
		return symbolRepository.findSymbolBySymbolNumber(number);
	}

}
