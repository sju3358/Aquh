package com.ssafy.team8alette.domain.feed.controller;

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

import com.ssafy.team8alette.domain.feed.model.dto.entity.FeedEntity;
import com.ssafy.team8alette.domain.feed.model.dto.request.LikeRequestDTO;
import com.ssafy.team8alette.domain.feed.model.dto.response.FeedResponseDTO;
import com.ssafy.team8alette.domain.feed.model.service.FeedService;
import com.ssafy.team8alette.domain.feed.model.service.LikeService;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberService;
import com.ssafy.team8alette.domain.member.follow.model.dao.FollowRepository;
import com.ssafy.team8alette.domain.member.record.model.dao.MemberRecordRepository;
import com.ssafy.team8alette.domain.symbol.model.dao.SymbolRepository;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.response.GrantResponseDTO;
import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;
import com.ssafy.team8alette.domain.symbol.model.service.SymbolGrantService;

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
	private final SymbolRepository symbolRepository;
	private final MemberRecordRepository memberRecordRepository;
	//파일경로
	private static String projectPath = "C:\\pictures";

	//피드 등록 파
	// 일

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
			//피드 잘 넣어지면 200
			feedService.registFeed(feedEntity, file);
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
		List<FeedEntity> feedEntityList = feedService.getFeeds(orderCriteria);
		List<FeedResponseDTO> dtoList = feedEntityList.stream()
			.map(feedService::convertToDTO)
			.collect(Collectors.toList());
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}

	// 게시글 상세글 조회
	@GetMapping("/{feed_number}")
	public ResponseEntity<Map<String, Object>> detailFeed(@PathVariable Long feed_number) {
		Map<String, Object> responseData = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		FeedEntity feedEntity = feedService.getFeedById(feed_number);
		Member member = memberService.getMemberInfo(feedEntity.getMember().getMemberNumber());
		//만약 저장했던 피드의 이미지가 존재한다면
		if (feedEntity.getFeedImgTrans() != null) {
			// File saveFile = new File(projectPath, feedEntity.getFeedImgTrans());
			// data.put("img", saveFile);
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
		// data.put("exp", memberRecordRepository.findMemberRecordByMemberNumber(feedEntity.getMember().getMemberNumber())
		// 	.getMemberExpCnt());
		int exp = followRepository.countByFollowingMemberNumber(feedEntity.getMember());
		String imgLink = "";
		Long share = (long)(exp / 1000);

		if (share == 0) {
			Symbol symbol = symbolRepository.findSymbolBySymbolNumber(share + 1);
			imgLink = "https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/" + symbol.getSymbolImgName();
			data.put("characterGrade", imgLink);
		} else if (share == 1) {
			Symbol symbol = symbolRepository.findSymbolBySymbolNumber(share + 2);
			imgLink = "https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/" + symbol.getSymbolImgName();
			data.put("characterGrade", imgLink);
		} else if (share == 2) {
			Symbol symbol = symbolRepository.findSymbolBySymbolNumber(share + 3);
			imgLink = "https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/" + symbol.getSymbolImgName();
			data.put("characterGrade", imgLink);
		} else if (share == 4) {
			Symbol symbol = symbolRepository.findSymbolBySymbolNumber(share + 4);
			imgLink = "https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/" + symbol.getSymbolImgName();
			data.put("characterGrade", imgLink);
		} else if (share == 7) {
			Symbol symbol = symbolRepository.findSymbolBySymbolNumber(share + 5);
			imgLink = "https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/" + symbol.getSymbolImgName();
			data.put("characterGrade", imgLink);
		} else {
			Symbol symbol = symbolRepository.findSymbolBySymbolNumber(share + 6);
			imgLink = "https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/" + symbol.getSymbolImgName();
			data.put("characterGrade", imgLink);
		}
		data.put("followingCnt", followRepository.countByFollowingMemberNumber(feedEntity.getMember()));
		responseData.put("message", "success");
		responseData.put("status", 200);
		responseData.put("data", data);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 게시글 수정
	@PutMapping
	public ResponseEntity<?> modifyFeed(@RequestPart FeedEntity feedEntity,
		@RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
		feedService.modifyFeed(feedEntity, file);
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

	@GetMapping
	public ResponseEntity<Map<String, Object>> getMemberFeedList(@RequestBody Map<String, String> param) {
		Long memberNumber = Long.parseLong(param.get("memberNumber"));
		List<FeedEntity> list = feedService.getFeedsByMemberNumber(memberNumber);
		List<FeedResponseDTO> dtoList = list.stream()
			.map(feedService::convertToDTO)
			.collect(Collectors.toList());
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "좋아요 되었습니다.");
		responseData.put("status", 200);
		responseData.put("feedList", dtoList);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
