package com.ssafy.team8alette.domain.symbol.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.symbol.model.dto.grant.entity.Grant;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.key.GrantID;

@Repository
public interface SymbolGrantRepository extends JpaRepository<Grant, GrantID> {

	List<Grant> findByMemberRecord_MemberNumber(Long memberNumber);

	List<Grant> findByMemberRecord_MemberNumberAndActiveStatus(Long memberNumber, boolean status);

	Grant findByGrantIDGrantedMemberNumberAndGrantIDSymbolNumber(Long grantedMemberNumber, Long symbolNumber);

	List<Grant> findByGrantIDGrantedMemberNumber(Long grantedMemberNumber);

	// Grant findByGrantIDGrantedMemberNumber(Long memberNumber);
}
