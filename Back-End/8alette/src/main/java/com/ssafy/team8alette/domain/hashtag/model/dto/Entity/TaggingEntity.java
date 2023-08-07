package com.ssafy.team8alette.domain.hashtag.model.dto.Entity;

import java.io.Serializable;
import java.util.Date;

import com.ssafy.team8alette.domain.hashtag.model.dto.Key.TaggingID;
import com.ssafy.team8alette.domain.webrtc.openvidu.dto.entity.RoomEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tagging")
public class TaggingEntity implements Serializable {

	@EmbeddedId
	private TaggingID taggingID;

	@MapsId("roomNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_number")
	private RoomEntity roomEntity;

	@MapsId("hashTagNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hashtag_number")
	private HashtagEntity hashtagEntity;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

}
