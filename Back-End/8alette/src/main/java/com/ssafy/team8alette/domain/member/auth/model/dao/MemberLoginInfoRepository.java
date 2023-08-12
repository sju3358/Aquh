package com.ssafy.team8alette.domain.member.auth.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.member.auth.model.dto.MemberLoginInfo;

@Repository
public interface MemberLoginInfoRepository extends CrudRepository<MemberLoginInfo, String> {
}
