package com.example.schedulerv2.controller;

import com.example.schedulerv2.dto.schedule.*;
import com.example.schedulerv2.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<ScheduleCreateResponse> createSchedule(
            @RequestBody ScheduleCreateRequest scheduleRequest,
            HttpServletRequest request
    ) {

        // session 에서 loginUserId 가져오기
        HttpSession session = request.getSession(false);
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");

        ScheduleCreateResponse response = scheduleService.save(
                scheduleRequest,
                loginUserId
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleGetAllResponse>> findAll() {

        List<ScheduleGetAllResponse> response = scheduleService.findAll();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 선택 일정 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleGetOneResponse> findById(@PathVariable Long scheduleId) {

        ScheduleGetOneResponse response = scheduleService.findById(scheduleId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 일정 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> update(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleUpdateRequest scheduleRequest,
            HttpServletRequest request
    ) {

        // session 에서 loginUserId 가져오기
        HttpSession session = request.getSession(false);
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");

        ScheduleUpdateResponse response = scheduleService.update(
                scheduleId,
                scheduleRequest.getTitle(),
                scheduleRequest.getContent(),
                loginUserId
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public void delete(
            @PathVariable Long scheduleId,
            HttpServletRequest request
    ) {

        // session 에서 loginUserId 가져오기
        HttpSession session = request.getSession(false);
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");

        scheduleService.delete(scheduleId, loginUserId);
    }

}
