package com.ssafy.team8alette.domain.auth.alarm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.auth.alarm.method.AlarmListMapper;
import com.ssafy.team8alette.domain.auth.alarm.model.dto.Alarm;
import com.ssafy.team8alette.domain.auth.alarm.model.dto.AlarmListResponseDTO;
import com.ssafy.team8alette.domain.auth.alarm.model.service.AlarmService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarm")
public class AlarmController {

	private final AlarmService alarmService;

	@GetMapping("/list/{memberNumber}")
	public ResponseEntity<Map<String, Object>> alarmListRequest(@PathVariable Long memberNumber) {

		Map<String, Object> responseData = new HashMap<>();
		List<Alarm> alarmList = alarmService.getAlarmList(memberNumber);
		List<AlarmListResponseDTO> dtoList = alarmList.stream()
			.map(AlarmListMapper::convertToDTO)
			.collect(Collectors.toList());

		responseData.put("alarmList", dtoList);
		responseData.put("message", "success");
		responseData.put("status", 200);

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
