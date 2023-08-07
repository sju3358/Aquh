package com.ssafy.team8alette.domain.symbol.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.symbol.model.dto.symbol.SymbolEntity;

public interface SymbolRepository extends JpaRepository<SymbolEntity, Long> {
}
