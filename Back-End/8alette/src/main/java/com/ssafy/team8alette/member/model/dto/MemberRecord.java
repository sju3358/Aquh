package com.ssafy.team8alette.member.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "record")
public class MemberRecord extends Member {
	
	@Id
	@GeneratedValue
	private Long memberNumber;

}
