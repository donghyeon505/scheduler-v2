package com.example.schedulerv2.service;

import com.example.schedulerv2.dto.schedule.*;
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
    public ScheduleCreateResponse save(ScheduleCreateRequest request, Long loginUserId) {

        // ID 값으로 user 찾기
        User user = userRepository.findUserByIdOrElseThrow(loginUserId);

        // 일정 객체에 값 넣기
        Schedule schedule = new Schedule(request.getTitle(), request.getContent());
        schedule.assignUser(user);

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

        // 선택 일정 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 일정 반환
        return ScheduleGetOneResponse.toDto(findSchedule);
    }

    // 일정 수정
    @Transactional
    public ScheduleUpdateResponse update(Long id, ScheduleUpdateRequest request, Long loginUserId) {

        // 선택 일정 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 본인 게시물인지 확인
        checkSchedule(findSchedule.getUser().getId(), loginUserId, "본인의 일정만 수정할 수 있습니다.");

        // 일정 정보 업데이트
        findSchedule.update(request.getTitle(), request.getContent());

        // DB에 즉시 반영 (수정일을 바로 보기위함)
        scheduleRepository.flush();

        // 수정된 일정 반환
        return ScheduleUpdateResponse.toDto(findSchedule);
    }

    // 일정 삭제
    @Transactional
    public void delete(Long id, Long loginUserId) {

        // 선택 일정 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 본인 게시물인지 확인
        checkSchedule(findSchedule.getUser().getId(), loginUserId, "본인의 일정만 삭제할 수 있습니다.");

        // 일정 삭제
        scheduleRepository.delete(findSchedule);
    }







    // ===== 헬퍼 메서드 =====

    // 본인 게시물인지 체크
    private void checkSchedule(Long scheduleOwnerId, Long loginUserId, String message) {
        if (!scheduleOwnerId.equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
        }
    }
}
