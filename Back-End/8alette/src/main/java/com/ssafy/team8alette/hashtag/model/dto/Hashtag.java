package com.ssafy.team8alette.hashtag.model.dto;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hashtag")
@ToString
public class Hashtag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hashtag_number", nullable = false)
	private Long hashtagNumber;

	@Column(name = "hashtag_name", nullable = false)
	private String hashtagName;

	@Column(name = "create_dt", nullable = false)
	private Date createDate;

	@Column(name = "delete_dt")
	private Date deleteDate;

}
