package com.ssafy.team8alette.domain.symbol.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;

@Repository
public interface SymbolRepository extends JpaRepository<Symbol, Long> {
}
