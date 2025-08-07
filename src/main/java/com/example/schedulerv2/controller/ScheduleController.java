package com.example.schedulerv2.controller;

import com.example.schedulerv2.dto.*;
import com.example.schedulerv2.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleCreateResponse> createSchedule(@RequestBody ScheduleCreateRequest request) {

        ScheduleCreateResponse response = scheduleService.save(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleGetAllResponse>> findAll() {

        List<ScheduleGetAllResponse> response = scheduleService.findAll();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 선택 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleGetOneResponse> findById(@PathVariable Long id) {

        ScheduleGetOneResponse response = scheduleService.findById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleUpdateResponse> update(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequest request
    ) {

        ScheduleUpdateResponse response = scheduleService.update(id, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        scheduleService.delete(id);
    }

}
