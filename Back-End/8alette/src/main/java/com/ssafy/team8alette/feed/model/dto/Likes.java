package com.ssafy.team8alette.feed.model.dto;

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
@Table(name = "likes")
@ToString
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_feed_number", nullable = false)
	private Long likeFeedNumber;

	// @ManyToOne(fetch = FetchType.LAZY)
	// private Feed feed;

	// @ManyToOne(fetch = FetchType.LAZY)
	// private Member member;
	// public Like(Feed feed, Member member){
	// 	this.feed = feed;
	// 	this.member = memeber;
	// }

}
