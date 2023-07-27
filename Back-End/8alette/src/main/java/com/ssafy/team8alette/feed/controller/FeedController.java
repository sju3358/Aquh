package com.ssafy.team8alette.feed.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.feed.model.dto.Feed;
import com.ssafy.team8alette.feed.model.service.FeedService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
public class FeedController {

	private final FeedService feedService;

	// 게시글 등록 response로 일단 파일 빼고 등록되게하자.
	// @PostMapping("/")
	// public ResponseEntity<?> createFeed(@RequestBody Feed feed) throws NullValueException {
	//
	// 	//피드 잘 넣어지면 200
	// 	feedService.registFeed(feed);
	// 	Map<String, Object> responseData = new HashMap<>();
	// 	responseData.put("message", "success");
	// 	responseData.put("status", 200);
	// 	return new ResponseEntity<>(responseData, HttpStatus.OK);
	// }

	// 게시글 등록 response로 일단 파일 빼고 등록되게하자.
	@PostMapping("/")
	public ResponseEntity<?> createFeed(@RequestBody Feed feed) {
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
			feedService.registFeed(feed);
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "success");
			responseData.put("status", 200);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
	}

	// 게시글 전체 조회
	@GetMapping("/list")
	public ResponseEntity<List<?>> findAllFeeds() {
		List<Feed> feedList = feedService.getFeeds();
		//일단 스켈레톤이니까 다시 수정
		return new ResponseEntity<>(feedList, HttpStatus.OK);
	}

	// 게시글 상세글 조회
	// @GetMapping("/{feed_number}")
	// public ResponseEntity<?> detailFeed(@PathVariable Long feed_number) {
	// 	Feed feed = feedService.getFeedById(feed_number);
	// 	return new ResponseEntity<>(feed, HttpStatus.OK);
	// }

	// 게시글 수정
	// @PutMapping("/")
	// public ResponseEntity<?> modifyFeed(@RequestBody Feed feed) {
	// 	feedService.modifyFeed(feed);
	// 	Map<String, Object> responseData = new HashMap<>();
	// 	responseData.put("message", "success");
	// 	responseData.put("status", 200);
	// 	return new ResponseEntity<>(responseData, HttpStatus.OK);
	// }

	// 게시글 삭제
	// @DeleteMapping("/{feed_number}")
	// public ResponseEntity<?> deleteFeed(@PathVariable Long feed_number) {
	//
	// 	feedService.deleteFeed(feed_number);
	// 	Map<String, Object> responseData = new HashMap<>();
	// 	responseData.put("message", "success");
	// 	responseData.put("status", 200);
	// 	return new ResponseEntity<>(responseData, HttpStatus.OK);
	// }
}
