package com.example.schedulerv2.service;

import com.example.schedulerv2.dto.*;
import com.example.schedulerv2.entity.Schedule;
import com.example.schedulerv2.entity.User;
import com.example.schedulerv2.repository.ScheduleRepository;
import com.example.schedulerv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 일정 생성
    @Transactional
    public ScheduleCreateResponse save(ScheduleCreateRequest request) {

        // ID 값으로 user 찾기
        User user = userRepository.findUserByIdOrElseThrow(request.getUserId());

        // 일정 객체에 값 넣기
        Schedule schedule = new Schedule(request.getTitle(), request.getContent());
        schedule.setUser(user);

        // 일정 저장
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // 일정 반환
        return ScheduleCreateResponse.toDto(savedSchedule);
    }

    // 일정 전체 조회
    @Transactional (readOnly = true)
    public List<ScheduleGetAllResponse> findAll() {

        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleGetAllResponse::toDto)
                .toList();
    }

    // 선택 일정 조회
    @Transactional (readOnly = true)
    public ScheduleGetOneResponse findById(Long id) {

        // ID 값 여부 체크
        checkId(id);

        // 선택 일정 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 일정 반환
        return ScheduleGetOneResponse.toDto(findSchedule);
    }

    // 일정 수정
    @Transactional
    public ScheduleUpdateResponse update(Long id, String title, String content) {

        // ID 값 여부 체크
        checkId(id);

        // 선택 일정 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 제목 null, 공백 체크
        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목은 필수값 입니다");
        }

        // 내용 null, 공백 체크
        if (content == null || content.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "내용은 필수값 입니다");
        }

        // 일정 정보 업데이트
        findSchedule.setTitle(title);
        findSchedule.setContent(content);

        // DB에 즉시 반영
        scheduleRepository.flush();

        // 수정된 일정 반환
        return ScheduleUpdateResponse.toDto(findSchedule);
    }

    // 일정 삭제
    @Transactional
    public void delete(Long id) {

        // ID 값 여부 체크
        checkId(id);

        // 선택 일정 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 일정 삭제
        scheduleRepository.delete(findSchedule);
    }







    // ===== 헬퍼 메서드 =====

    // ID 값 여부 체크
    private void checkId(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID는 필수 입력값입니다");
        }
    }
}
