package com.ssafy.team8alette.symbol.model.dto.grant.entity;

import java.io.Serializable;
import java.util.Date;

import com.ssafy.team8alette.member.model.dto.MemberRecord;

import com.ssafy.team8alette.symbol.model.dto.grant.key.GrantID;
import com.ssafy.team8alette.symbol.model.dto.symbol.Symbol;

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
public class Grant implements Serializable {

	@EmbeddedId
	private GrantID grantID;

	@MapsId("grantedMemberNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "granted_member_number", nullable = false)
	private MemberRecord memberRecord;

	@MapsId("symbolNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "symbol_number", nullable = false)
	private Symbol symbol;

	@Column(name = "create_dttm", nullable = false)
	private Date date;

}
