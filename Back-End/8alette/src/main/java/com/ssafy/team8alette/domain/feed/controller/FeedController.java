package com.ssafy.team8alette.domain.feed.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.team8alette.domain.feed.model.dto.entity.FeedEntity;
import com.ssafy.team8alette.domain.feed.model.dto.request.LikeRequestDTO;
import com.ssafy.team8alette.domain.feed.model.dto.response.FeedResponseDTO;
import com.ssafy.team8alette.domain.feed.model.service.FeedService;
import com.ssafy.team8alette.domain.feed.model.service.LikeService;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberService;
import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import com.ssafy.team8alette.domain.member.follow.model.dao.FollowRepository;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.response.GrantResponseDTO;
import com.ssafy.team8alette.domain.symbol.model.service.SymbolGrantService;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
public class FeedController {

	private final FeedService feedService;
	private final LikeService likeService;
	private final MemberService memberService;
	private final SymbolGrantService symbolGrantService;
	private final FollowRepository followRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@LoginRequired
	@PostMapping
	public ResponseEntity<?> createFeed(@RequestPart(value = "feed") FeedEntity feedEntity,
		@RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
		if (feedEntity.getTitle() == null) {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "제목을 작성해주세요");
			responseData.put("status", 405);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		} else if (feedEntity.getContent() == null) {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "내용을 작성해주세요");
			responseData.put("status", 406);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		} else {
			feedService.registFeed(feedEntity, file);
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "success");
			responseData.put("status", 200);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
	}

	@LoginRequired
	@GetMapping("/list")
	public ResponseEntity<List<?>> findAllFeeds(
		@RequestParam(required = false, defaultValue = "createDate", value = "filter") String orderCriteria
	) {
		List<FeedResponseDTO> dtoList = feedService.getFeeds(orderCriteria);
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}

	@LoginRequired
	@GetMapping("/{feed_number}")
	public ResponseEntity<Map<String, Object>> detailFeed(@PathVariable Long feed_number) {
		Map<String, Object> responseData = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		FeedEntity feedEntity = feedService.getFeedById(feed_number);
		Member member = memberService.getMemberInfo(feedEntity.getMember().getMemberNumber());
		//만약 저장했던 피드의 이미지가 존재한다면
		if (feedEntity.getFeedImgTrans() != null) {
			data.put("img_name", feedEntity.getFeedImgOrigin());
			data.put("img_url",
				"https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/" + feedEntity.getFeedImgTrans());
		}
		data.put("feedNumber", feedEntity.getFeedNumber());
		data.put("feedCreatorNumber", feedEntity.getMember().getMemberNumber());
		data.put("memberNickName", member.getMemberNickname());
		data.put("title", feedEntity.getTitle());
		data.put("content", feedEntity.getContent());
		data.put("feedLikeCnt", feedEntity.getFeedLikeCnt());
		data.put("viewCnt", feedEntity.getViewCnt());
		data.put("feedActive", feedEntity.isFeedActive());
		data.put("feedImgOrigin", feedEntity.getFeedImgOrigin());
		data.put("feedImgTrans", feedEntity.getFeedImgTrans());
		data.put("createDate", feedEntity.getCreateDate());
		data.put("deleteDate", feedEntity.getDeleteDate());

		//아직 심볼부여는 빼고
		List<GrantResponseDTO> list = symbolGrantService.getGrantList(feedEntity.getMember().getMemberNumber());
		data.put("symbolNumber", list);
		int exp = followRepository.countByFollowingMemberNumber(feedEntity.getMember());
		data.put("level", convertExpToLevel(exp));
		data.put("followingCnt", followRepository.countByFollowingMemberNumber(feedEntity.getMember()));
		responseData.put("message", "success");
		responseData.put("data", data);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PutMapping
	public ResponseEntity<?> modifyFeed(@RequestPart FeedEntity feedEntity,
		@RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
		feedService.modifyFeed(feedEntity, file);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 게시글 삭제
	@LoginRequired
	@PutMapping("/{feed_number}")
	public ResponseEntity<?> deleteFeed(@PathVariable Long feed_number) {

		feedService.deleteFeed(feed_number);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 피드 좋아요
	@LoginRequired
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

	@LoginRequired
	@GetMapping
	public ResponseEntity<Map<String, Object>> getMemberFeedList(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken) throws ParseException {
		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);
		List<FeedResponseDTO> dtoList = feedService.getFeedsByMemberNumber(memberNumber);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);
		responseData.put("feedList", dtoList);
		//마이페이지 자신꺼 볼때 호출
		symbolGrantService.putSymbolGrant(memberNumber);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	private int convertExpToLevel(int exp) {
		int level = 1;
		if (exp >= 1000 && exp < 2500) {
			level = 2;
		} else if (exp >= 2500 && exp < 4500) {
			level = 3;
		} else if (exp >= 4500 && exp < 7000) {
			level = 4;
		} else if (exp >= 7000 && exp < 10000) {
			level = 5;
		} else if (exp >= 10000) {
			level = 6;
		}
		return level;
	}

}
