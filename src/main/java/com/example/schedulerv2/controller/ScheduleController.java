package com.example.schedulerv2.controller;

import com.example.schedulerv2.dto.schedule.*;
import com.example.schedulerv2.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
            @RequestBody @Valid ScheduleCreateRequest scheduleRequest,
            HttpServletRequest request
    ) {

        // session 에서 loginUserId 가져오기
        Long loginUserId = getLoginUserId(request);

        ScheduleCreateResponse response = scheduleService.save(scheduleRequest, loginUserId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleGetAllResponse>> findAll() {

        List<ScheduleGetAllResponse> response = scheduleService.findAll();

        return ResponseEntity.ok(response);
    }

    // 선택 일정 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleGetWithCommentResponse> findById(
            @PathVariable @NotNull(message = "일정 ID는 필수값입니다.")
            @Positive(message = "일정 ID는 1 이상의 값이어야 합니다.")
            Long scheduleId
    ) {

        ScheduleGetWithCommentResponse response = scheduleService.findById(scheduleId);

        return ResponseEntity.ok(response);
    }

    // 일정 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> update(
            @PathVariable @NotNull(message = "일정 ID는 필수값입니다.")
            @Positive(message = "일정 ID는 1 이상의 값이어야 합니다.")
            Long scheduleId,
            @RequestBody @Valid ScheduleUpdateRequest scheduleRequest,
            HttpServletRequest request
    ) {

        // session 에서 loginUserId 가져오기
        Long loginUserId = getLoginUserId(request);

        ScheduleUpdateResponse response = scheduleService.update(scheduleId, scheduleRequest, loginUserId);

        return ResponseEntity.ok(response);
    }

    // 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public void delete(
            @PathVariable @NotNull(message = "일정 ID는 필수값입니다.")
            @Positive(message = "일정 ID는 1 이상의 값이어야 합니다.")
            Long scheduleId,
            HttpServletRequest request
    ) {

        // session 에서 loginUserId 가져오기
        Long loginUserId = getLoginUserId(request);

        scheduleService.delete(scheduleId, loginUserId);
    }






    // ===== 헬퍼 메서드 =====

    // session 에서 loginUserId 가져오기
    private Long getLoginUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (Long) session.getAttribute("LOGIN_USER");
    }
}
