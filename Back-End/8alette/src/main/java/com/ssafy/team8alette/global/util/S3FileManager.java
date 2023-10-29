package com.ssafy.team8alette.global.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.team8alette.global.exception.NullValueException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3FileManager {

	@Value("${spring.data.couchbase.bucket-name}/thumbnail_img")
	private String thumbnail_bucket;

	@Value("${spring.data.couchbase.bucket-name}/feed_img")
	private String feedImage_bucket;

	private final AmazonS3Client amazonS3Client;
	private final ResourceLoader resourceLoader;

	/**
	 * Save imgfile in S3/feed
	 * @param imgUrl
	 * @return
	 * string[0] : origin FileName (exception : ""),
	 * string[1] : TransFileName (exception : "")
	 * @throws IOException
	 */
	public String[] saveThumbnailImage(String imgUrl) {

		String[] fileNames = new String[2];
		try {

			File file = downloadImg(imgUrl);

			String fileName = getRandomFileName(file.getName());

			amazonS3Client.putObject(thumbnail_bucket, fileName, file);

			file.delete();

			fileNames[0] = fileNames[1] = fileName;

		} catch (NullValueException exception) {
			fileNames = new String[] {"", ""};
		} catch (IOException e) {
			e.printStackTrace();
			fileNames = new String[] {"", ""};
		} catch (Exception e) {
			e.printStackTrace();
			fileNames = new String[] {"", ""};
		} finally {
			return fileNames;
		}
	}

	/**
	 * Save imgfile in S3/feed
	 * @param file
	 * @return
	 * string[0] : origin FileName (exception : ""),
	 * string[1] : TransFileName (exception : "")
	 * @throws IOException
	 */
	public String[] saveFeedImage(MultipartFile file) throws IOException {

		String[] fileNames = new String[2];

		try {

			if (file == null || file.getOriginalFilename().equals("empty") == true)
				return new String[2];

			String fileName = getRandomFileName(file.getName());

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getSize());
			amazonS3Client.putObject(feedImage_bucket, fileName, file.getInputStream(), metadata);

			fileNames[0] = file.getOriginalFilename();
			fileNames[1] = fileName;

		} catch (NullValueException e) {
			fileNames = new String[] {"", ""};
		} catch (IOException e) {
			e.printStackTrace();
			fileNames = new String[] {"", ""};
		} catch (Exception e) {
			e.printStackTrace();
			fileNames = new String[] {"", ""};
		} finally {
			return fileNames;
		}
	}

	private File downloadImg(String imgUrl) throws IOException {

		if (imgUrl == null || imgUrl.isEmpty() == true)
			throw new NullValueException();

		URL url = null;
		InputStream in = null;
		FileOutputStream fos = null;
		int data = -1;
		File file = null;

		StringTokenizer st = new StringTokenizer(imgUrl, "/");
		String fileName = "";
		while (st.hasMoreTokens())
			fileName = st.nextToken();
		st = new StringTokenizer(fileName, "?");
		fileName = st.nextToken();
		try {
			url = new URL(imgUrl);

			file = new File(fileName);

			in = url.openStream();

			fos = new FileOutputStream(file); //저장경로

			while ((data = in.read()) != -1) {
				fos.write(data);
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			in.close();
			fos.close();
			return file;
		}
	}

	private String getRandomFileName(String fileName) {

		Random generator = new java.util.Random();
		generator.setSeed(System.currentTimeMillis());
		String randomNumber = String.format("%06d", generator.nextInt(1000000) % 1000000);

		return convertDateToString(LocalDateTime.now()) + '_' + randomNumber;
	}

	private String convertDateToString(LocalDateTime nowDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return nowDate.format(formatter);
	}
}
