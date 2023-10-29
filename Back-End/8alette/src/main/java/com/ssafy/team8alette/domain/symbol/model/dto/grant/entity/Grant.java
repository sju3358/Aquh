package com.ssafy.team8alette.domain.symbol.model.dto.grant.entity;

import java.io.Serializable;

import com.ssafy.team8alette.domain.member.record.model.dto.entity.MemberRecord;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.key.GrantID;
import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "symbol_grant")
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

	@Column(name = "is_symbol_active")
	private boolean activeStatus;

}
