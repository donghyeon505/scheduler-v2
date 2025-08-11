package com.example.schedulerv2.service;

import com.example.schedulerv2.dto.user.*;
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
public class UserService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    // 회원가입
    @Transactional
    public UserCreateResponse create(UserCreateRequest userCreateRequest) {

        // 유저 객체 만들어서 정보 저장
        User user = new User(
                userCreateRequest.getUsername(),
                userCreateRequest.getEmail(),
                userCreateRequest.getPassword()
        );

        // DB에 유저 정보 저장
        User savedUser = userRepository.save(user);

        // 유저 정보 반환
        return UserCreateResponse.toDto(savedUser);
    }

    // 로그인
    @Transactional (readOnly = true)
    public User login(UserLoginRequest request) {

        // 해당 이메일의 유저가 있는지 체크
        User user = userRepository.findUserByEmailOrElseThrow(request.getEmail());

        // 해당 유저의 비밀번호 일치여부
        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이나 비밀번호가 일치하지 않습니다.");
        }

        // 반환
        return user;
    }

    // 본인 계정 정보 보기
    @Transactional
    public UserGetInfoResponse getInfo(Long loginUserId) {

        User user = userRepository.findUserByIdOrElseThrow(loginUserId);

        return UserGetInfoResponse.toDto(user);
    }

    // 닉네임 수정
    @Transactional
    public UserUpdateResponse updateUser(UserUpdateRequest request, Long loginUserId) {

        // 유저 여부 체크
        User user = userRepository.findUserByIdOrElseThrow(loginUserId);

        // 비밀번호가 비교
        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // 해당 유저 닉네임 수정
        user.update(request.getUsername());

        // DB에 즉시 반영 (수정일을 바로 보기위함)
        userRepository.flush();

        // 수정된 유저 정보 반환
        return UserUpdateResponse.toDto(user);
    }

    // 유저 탈퇴
    @Transactional
    public void deleteUser(Long loginUserId) {

        // 유저 정보 가져오기
        User user = userRepository.findUserByIdOrElseThrow(loginUserId);

        // 해당 유저의 일정 가져오기
        List<Schedule> schedule = scheduleRepository.findAllByUserId(loginUserId);

        // 해당 유저의 일정 전부 삭제
        scheduleRepository.deleteAll(schedule);

        // DB에 유저 정보 삭제
        userRepository.delete(user);
    }
}
