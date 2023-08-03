package com.ssafy.team8alette.feed.model.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.feed.model.dao.FeedRepository;
import com.ssafy.team8alette.feed.model.dao.LikeRepository;
import com.ssafy.team8alette.feed.model.dto.Feed.Entity.Feed;
import com.ssafy.team8alette.feed.model.dto.Like.Entity.Like;
import com.ssafy.team8alette.feed.model.dto.Like.Key.LikeID;
import com.ssafy.team8alette.member.model.dao.MemberRepository;
import com.ssafy.team8alette.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final FeedRepository feedRepository;
	private final LikeRepository likeRepository;
	private final MemberRepository memberRepository;

	public boolean addLike(Long feedNumber, Long memberNumber) {

		Feed feed = feedRepository.findFeedByFeedNumber(feedNumber);
		Member member = memberRepository.findById(memberNumber).orElse(null);

		if (likeRepository.findByLikeIDLikeFeedNumberAndLikeIDLikeMemberNumber(feedNumber, memberNumber).isEmpty()) {
			LikeID likeID = new LikeID(feedNumber, memberNumber);
			Like likeDTO = new Like();
			likeDTO.setLikeID(likeID);
			likeDTO.setCreateDate(new Date());

			//이렇게 setFeed로 연결
			likeDTO.setFeed(feed);
			likeDTO.setMember(member);

			int likeCnt = feed.getFeedLikeCnt();
			int setLikeCnt = likeCnt + 1; // 또는 ++likeCnt;
			feed.setFeedLikeCnt(setLikeCnt);
			feedRepository.save(feed);
			likeRepository.save(likeDTO);
			return true;
		}

		LikeID likeID = new LikeID(feedNumber, memberNumber);
		Like likeDTO = new Like();
		likeDTO.setLikeID(likeID);
		likeDTO.setFeed(feed);
		int likeCnt = feed.getFeedLikeCnt();
		int setLikeCnt = likeCnt - 1;
		feed.setFeedLikeCnt(setLikeCnt);
		feedRepository.save(feed);
		likeRepository.delete(likeDTO);
		return false;
	}

	// public boolean delete

}
