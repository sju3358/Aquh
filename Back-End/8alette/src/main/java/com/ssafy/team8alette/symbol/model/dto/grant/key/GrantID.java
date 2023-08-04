package com.ssafy.team8alette.symbol.model.dto.grant.key;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class GrantID implements Serializable {

	private Long grantedMemberNumber;
	private Long symbolNumber;

	private GrantID(Long grantedMemberNumber, Long symbolNumber) {
		this.grantedMemberNumber = grantedMemberNumber;
		this.symbolNumber = symbolNumber;
	}
}
