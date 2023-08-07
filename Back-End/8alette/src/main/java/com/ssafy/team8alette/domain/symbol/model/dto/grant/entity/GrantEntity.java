package com.ssafy.team8alette.domain.symbol.model.dto.grant.entity;

import java.io.Serializable;
import java.util.Date;

import com.ssafy.team8alette.domain.member.record.model.dto.MemberRecordEntity;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.key.GrantID;
import com.ssafy.team8alette.domain.symbol.model.dto.symbol.SymbolEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class GrantEntity implements Serializable {

	@EmbeddedId
	private GrantID grantID;

	@MapsId("grantedMemberNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "granted_member_number", nullable = false)
	private MemberRecordEntity memberRecordEntity;

	@MapsId("symbolNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "symbol_number", nullable = false)
	private SymbolEntity symbolEntity;

	@Column(name = "create_dttm", nullable = false)
	private Date date;

}
