package com.ssafy.team8alette.domain.symbol.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;

public interface SymbolRepository extends JpaRepository<Symbol, Long> {
}
