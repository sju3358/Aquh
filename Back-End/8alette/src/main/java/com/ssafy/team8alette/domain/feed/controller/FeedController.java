package com.ssafy.team8alette.domain.feed.controller;

import java.sql.SQLException;
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

import com.ssafy.team8alette.domain.feed.model.dto.FeedDto;
import com.ssafy.team8alette.domain.feed.model.dto.request.LikeRequestDTO;
import com.ssafy.team8alette.domain.feed.model.dto.response.FeedResponseDTO;
import com.ssafy.team8alette.domain.feed.model.service.FeedService;
import com.ssafy.team8alette.domain.feed.model.service.LikeService;
import com.ssafy.team8alette.domain.member.alarm.model.service.AlarmService;
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
	private final AlarmService alarmService;

	@LoginRequired
	@PostMapping
	public ResponseEntity<?> createFeed(@RequestPart(value = "feed") FeedDto feedDto,
		@RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
		Map<String, Object> responseData = new HashMap<>();

		if (feedDto.getTitle() == null) {
			responseData.put("message", "제목을 작성해주세요");
			responseData.put("status", 405);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
		if (feedDto.getContent() == null) {
			responseData.put("message", "내용을 작성해주세요");
			responseData.put("status", 406);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
		feedService.registFeed(feedDto, file);

		responseData.put("message", "success");
		responseData.put("status", 200);
		return new ResponseEntity<>(responseData, HttpStatus.OK);

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
		FeedResponseDTO feedDto = feedService.getFeedById(feed_number);
		Member member = memberService.getMemberInfo(feedDto.getFeedCreatorNumber());
		//만약 저장했던 피드의 이미지가 존재한다면
		if (feedDto.getFeedImgTrans() != null) {
			data.put("img_name", feedDto.getFeedImgOrigin());
			data.put("img_url", feedDto.getFeedImgTrans());
		}
		data.put("feedNumber", feedDto.getFeedNumber());
		data.put("feedCreatorNumber", feedDto.getFeedCreatorNumber());
		data.put("memberNickName", member.getMemberNickname());
		data.put("title", feedDto.getTitle());
		data.put("content", feedDto.getContent());
		data.put("feedLikeCnt", feedDto.getFeedLikeCnt());
		data.put("viewCnt", feedDto.getViewCnt());
		data.put("feedActive", feedDto.isFeedActive());
		data.put("feedImgOrigin", feedDto.getFeedImgOrigin());
		data.put("feedImgTrans", feedDto.getFeedImgTrans());
		data.put("createDate", feedDto.getCreateDate());
		data.put("deleteDate", feedDto.getDeleteDate());

		//아직 심볼부여는 빼고
		List<GrantResponseDTO> list = symbolGrantService.getGrantList(feedDto.getFeedCreatorNumber());
		data.put("symbolNumber", list);
		int exp = followRepository.countByFollowingMemberNumber(member);
		data.put("level", convertExpToLevel(exp));
		data.put("followingCnt", followRepository.countByFollowingMemberNumber(member));
		responseData.put("message", "success");
		responseData.put("data", data);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PutMapping
	public ResponseEntity<?> modifyFeed(@RequestPart(value = "feed") FeedDto feedDto,
		@RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
		feedService.modifyFeed(feedDto, file);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	//
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

		Member receivedLikeMember = feedService.getFeedCreatorNumber(likeRequestDTO.getFeedNumber());

		boolean result;

		result = likeService.addLike(likeRequestDTO.getFeedNumber(), likeRequestDTO.getMemberNumber());

		if (result) {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "피드 좋아요 되었습니다.");
			responseData.put("status", 200);

			if (likeRequestDTO.getMemberNumber() != receivedLikeMember.getMemberNumber()) {
				Member requestMember = memberService.getMemberInfo(likeRequestDTO.getMemberNumber());
				alarmService.requestAlarm(receivedLikeMember, "likes",
					requestMember.getMemberNickname() + "님이 피드 좋아요를 눌렀습니다.",
					0);
			}

			return new ResponseEntity<>(responseData, HttpStatus.OK);
		} else {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "피드 좋아요를 취소했습니다.");
			responseData.put("status", 200);

			if (likeRequestDTO.getMemberNumber() != receivedLikeMember.getMemberNumber()) {
				Member requestMember = memberService.getMemberInfo(likeRequestDTO.getMemberNumber());
				alarmService.requestAlarm(receivedLikeMember, "likes",
					requestMember.getMemberNickname() + "님이 피드 좋아요를 취소했습니다.",
					0);
			}
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
	}

	@LoginRequired
	@GetMapping("/like/{feedNumber}")
	public ResponseEntity<?> addLike(@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@PathVariable Long feedNumber) throws SQLException, ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);

		boolean isFeedLike = likeService.findTrueOrFalse(feedNumber, memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("isClick", isFeedLike);
		responseData.put("status", 200);
		return new ResponseEntity<>(responseData, HttpStatus.OK);

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
