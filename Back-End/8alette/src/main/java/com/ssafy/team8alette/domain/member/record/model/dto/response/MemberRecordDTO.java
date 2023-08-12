package com.ssafy.team8alette.domain.member.record.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRecordDTO {
	String memberNickName;
	String memberIntro;
	Long level;
	int maxExp;
	int presentExp;
	int remainingExp;

}
