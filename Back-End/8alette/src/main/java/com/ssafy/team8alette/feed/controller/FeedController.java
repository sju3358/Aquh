package com.ssafy.team8alette.feed.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.team8alette.feed.model.dto.feed.entity.Feed;
import com.ssafy.team8alette.feed.model.dto.feed.response.FeedResponseDTO;
import com.ssafy.team8alette.feed.model.dto.like.request.LikeRequestDTO;
import com.ssafy.team8alette.feed.model.service.FeedService;
import com.ssafy.team8alette.feed.model.service.LikeService;
import com.ssafy.team8alette.follow.model.dao.FollowRepository;
import com.ssafy.team8alette.member.model.dao.MemberRecordRepository;
import com.ssafy.team8alette.member.model.dto.Member;
import com.ssafy.team8alette.member.model.service.MemberService;
import com.ssafy.team8alette.symbol.model.dao.SymbolRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
public class FeedController {

	private final FeedService feedService;
	private final LikeService likeService;
	private final MemberService memberService;
	private final FollowRepository followRepository;
	private final SymbolRepository symbolRepository;
	private final MemberRecordRepository memberRecordRepository;
	//파일경로
	private static String projectPath = "C:\\pictures";

	//피드 등록 파
	// 일

	@PostMapping
	public ResponseEntity<?> createFeed(@RequestPart(value = "feed") Feed feed,
		@RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
		if (feed.getTitle() == null) {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "제목을 작성해주세요");
			responseData.put("status", 405);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		} else if (feed.getContent() == null) {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "내용을 작성해주세요");
			responseData.put("status", 406);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		} else {
			//피드 잘 넣어지면 200
			feedService.registFeed(feed, file);
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "success");
			responseData.put("status", 200);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<?>> findAllFeeds(
		@RequestParam(required = false, defaultValue = "createDate", value = "filter") String orderCriteria
	) {
		List<Feed> feedList = feedService.getFeeds(orderCriteria);
		List<FeedResponseDTO> dtoList = feedList.stream()
			.map(feedService::convertToDTO)
			.collect(Collectors.toList());
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}

	// 게시글 상세글 조회
	@GetMapping("/{feed_number}")
	public ResponseEntity<Map<String, Object>> detailFeed(@PathVariable Long feed_number) {
		Map<String, Object> responseData = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		Feed feed = feedService.getFeedById(feed_number);
		Member member = memberService.getMemberInfo(feed.getMember().getMemberNumber());
		//만약 저장했던 피드의 이미지가 존재한다면
		if (feed.getFeedImgTrans() != null) {
			File saveFile = new File(projectPath, feed.getFeedImgTrans());
			data.put("img", saveFile);
		}
		data.put("feedNumber", feed.getFeedNumber());
		data.put("feedCreatorNumber", feed.getMember().getMemberNumber());
		data.put("memberNickName", member.getMemberNickname());
		data.put("title", feed.getTitle());
		data.put("content", feed.getContent());
		data.put("feedLikeCnt", feed.getFeedLikeCnt());
		data.put("viewCnt", feed.getViewCnt());
		data.put("feedActive", feed.isFeedActive());
		data.put("feedImgOrigin", feed.getFeedImgOrigin());
		data.put("feedImgTrans", feed.getFeedImgTrans());
		data.put("createDate", feed.getCreateDate());
		data.put("deleteDate", feed.getDeleteDate());
		//아직 심볼부여는 빼고
		data.put("symbolNumber", 0);
		data.put("exp", memberRecordRepository.findMemberRecordByMemberNumber(feed.getMember().getMemberNumber())
			.getMemberExpCnt());
		data.put("followingCnt", followRepository.countByFollowingMemberNumber(feed.getMember()));

		responseData.put("message", "success");
		responseData.put("status", 200);
		responseData.put("data", data);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 게시글 수정
	@PutMapping
	public ResponseEntity<?> modifyFeed(@RequestPart Feed feed,
		@RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
		feedService.modifyFeed(feed, file);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 게시글 삭제
	@PutMapping("/{feed_number}")
	public ResponseEntity<?> deleteFeed(@PathVariable Long feed_number) {

		feedService.deleteFeed(feed_number);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 피드 좋아요
	@PostMapping("/like")
	public ResponseEntity<?> addLike(@RequestBody LikeRequestDTO likeRequestDTO) {
		boolean result;

		//수정
		result = likeService.addLike(likeRequestDTO.getFeedNumber(), likeRequestDTO.getMemberNumber());
		if (result) {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "좋아요 되었습니다.");
			responseData.put("status", 200);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		} else {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "좋아요 취소했습니다.");
			responseData.put("status", 200);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}

	}

}
