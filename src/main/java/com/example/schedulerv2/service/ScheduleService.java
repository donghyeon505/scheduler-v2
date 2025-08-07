package com.example.schedulerv2.service;

import com.example.schedulerv2.dto.*;
import com.example.schedulerv2.entity.Schedule;
import com.example.schedulerv2.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 일정 생성
    @Transactional
    public ScheduleCreateResponse save(ScheduleCreateRequest request) {

        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), request.getWriter());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleCreateResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getWriter(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    // 일정 전체 조회
    @Transactional (readOnly = true)
    public List<ScheduleGetAllResponse> findAll() {

        List<Schedule> findSchedule = scheduleRepository.findAll();

        List<ScheduleGetAllResponse> response = new ArrayList<>();

        for (Schedule schedule : findSchedule) {
            response.add(new ScheduleGetAllResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getWriter(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            ));
        }

        return response;
    }

    // 선택 일정 조회
    @Transactional (readOnly = true)
    public ScheduleGetOneResponse findById(Long id) {

        // Id 값 여부 체크
        checkId(id);

        // 선택 일정 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleGetOneResponse(
                findSchedule.getId(),
                findSchedule.getTitle(),
                findSchedule.getContent(),
                findSchedule.getWriter(),
                findSchedule.getCreatedAt(),
                findSchedule.getModifiedAt()
        );
    }

    // 일정 수정
    @Transactional
    public ScheduleUpdateResponse update(Long id, ScheduleUpdateRequest request) {

        // Id 값 여부 체크
        checkId(id);

        // 선택 일정 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목은 필수값 입니다");
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "내용은 필수값 입니다");
        }

        findSchedule.setTitle(request.getTitle());
        findSchedule.setContent(request.getContent());

        scheduleRepository.flush();

        return new ScheduleUpdateResponse(
                findSchedule.getId(),
                findSchedule.getTitle(),
                findSchedule.getContent(),
                findSchedule.getWriter(),
                findSchedule.getCreatedAt(),
                findSchedule.getModifiedAt()
        );
    }

    // 일정 삭제
    @Transactional
    public void delete(Long id) {

        // Id 값 여부 체크
        checkId(id);

        // 선택 일정 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 일정 삭제
        scheduleRepository.delete(findSchedule);
    }







    // ===== 헬퍼 메서드 =====

    // Id 값 여부 체크
    private void checkId(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID는 필수 입력값입니다");
        }
    }
}
