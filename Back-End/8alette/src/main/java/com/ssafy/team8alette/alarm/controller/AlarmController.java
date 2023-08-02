package com.ssafy.team8alette.alarm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.alarm.model.service.AlarmService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/alarm")
@RestController
@RequiredArgsConstructor
public class AlarmController {

	private final AlarmService alarmService;

	@GetMapping("/list/{memberNumber}")
	public ResponseEntity<Map<String, Object>> alarmListRequest(@PathVariable Long memberNumber) {

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);
		responseData.put("data", alarmService.getAlarmList(memberNumber));

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PutMapping("/{alarmIndex}")
	public ResponseEntity<Map<String, Object>> readAlarmRequest(@PathVariable Long alarmIndex) {

		alarmService.readAlarm(alarmIndex);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
