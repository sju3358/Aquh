package com.ssafy.team8alette.global.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
	// @Value("${cloud.aws.credentials.access-key}")
	// private String accessKey;
	// @Value("${cloud.aws.credentials.secret-key}")
	// private String secretKey;
	// @Value("${cloud.aws.region.static}")
	// private String region;
	//
	// @Bean
	// public AmazonS3Client amazonS3Client() {
	// 	BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
	// 	return (AmazonS3Client)AmazonS3ClientBuilder.standard()
	// 		.withRegion(region)
	// 		.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
	// 		.build();
	// }
}
