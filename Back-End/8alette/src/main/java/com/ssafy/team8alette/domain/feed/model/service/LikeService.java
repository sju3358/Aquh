package com.ssafy.team8alette.domain.feed.model.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.feed.model.dao.FeedRepository;
import com.ssafy.team8alette.domain.feed.model.dao.LikeRepository;
import com.ssafy.team8alette.domain.feed.model.dto.entity.FeedEntity;
import com.ssafy.team8alette.domain.feed.model.dto.entity.LikeEntity;
import com.ssafy.team8alette.domain.feed.model.dto.key.LikeID;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.record.model.service.MemberRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final FeedRepository feedRepository;
	private final LikeRepository likeRepository;
	private final MemberRepository memberRepository;
	private final MemberRecordService memberRecordService;

	public boolean addLike(Long feedNumber, Long memberNumber) {

		FeedEntity feedEntity = feedRepository.findFeedByFeedNumber(feedNumber);
		Member member = memberRepository.findById(memberNumber).orElse(null);

		if (likeRepository.findByLikeIDLikeFeedNumberAndLikeIDLikeMemberNumber(feedNumber, memberNumber).isEmpty()) {
			LikeID likeID = new LikeID(feedNumber, memberNumber);
			LikeEntity likeEntityDTO = new LikeEntity();
			likeEntityDTO.setLikeID(likeID);
			likeEntityDTO.setCreateDate(new Date());

			//이렇게 setFeed로 연결
			likeEntityDTO.setFeedEntity(feedEntity);
			likeEntityDTO.setMember(member);

			int likeCnt = feedEntity.getFeedLikeCnt();
			int setLikeCnt = likeCnt + 1; // 또는 ++likeCnt;
			feedEntity.setFeedLikeCnt(setLikeCnt);
			feedRepository.save(feedEntity);
			likeRepository.save(likeEntityDTO);

			/* 기록 테이블 경험치 추가 */
			memberRecordService.updateMemberExp(member.getMemberNumber(), 10);
			memberRecordService.updateMemberExp(feedEntity.getMember().getMemberNumber(), 30);
			return true;
		}

		LikeID likeID = new LikeID(feedNumber, memberNumber);
		LikeEntity likeEntityDTO = new LikeEntity();
		likeEntityDTO.setLikeID(likeID);
		likeEntityDTO.setFeedEntity(feedEntity);
		int likeCnt = feedEntity.getFeedLikeCnt();
		int setLikeCnt = likeCnt - 1;
		feedEntity.setFeedLikeCnt(setLikeCnt);
		feedRepository.save(feedEntity);
		likeRepository.delete(likeEntityDTO);

		/* 기록 테이블 경험치 추가 */
		memberRecordService.updateMemberExp(member.getMemberNumber(), -10);
		return false;
	}

	// public boolean delete

}
