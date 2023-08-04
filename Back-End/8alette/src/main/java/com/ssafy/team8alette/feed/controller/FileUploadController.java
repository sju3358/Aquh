package com.ssafy.team8alette.feed.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileUploadController {

	// private final AmazonS3Client amazonS3Client;
	//
	// @Value("${spring.data.couchbase.bucket-name}")
	// private String bucket;
	//
	// @PostMapping
	// public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
	// 	try {
	// 		String fileName = file.getOriginalFilename();
	// 		String fileUrl = "https://" + bucket + "/test" + fileName;
	// 		ObjectMetadata metadata = new ObjectMetadata();
	// 		metadata.setContentType(file.getContentType());
	// 		metadata.setContentLength(file.getSize());
	// 		amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
	// 		return ResponseEntity.ok(fileUrl);
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// 	}
	// }
}
