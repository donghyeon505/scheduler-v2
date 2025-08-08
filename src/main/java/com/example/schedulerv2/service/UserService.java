package com.example.schedulerv2.service;

import com.example.schedulerv2.dto.user.UserCreateRequest;
import com.example.schedulerv2.dto.user.UserCreateResponse;
import com.example.schedulerv2.dto.user.UserGetResponse;
import com.example.schedulerv2.dto.user.UserUpdateResponse;
import com.example.schedulerv2.entity.User;
import com.example.schedulerv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 생성
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

    // 유저 찾기
    @Transactional (readOnly = true)
    public UserGetResponse findById(Long id) {

        // ID 값 여부 체크
        checkId(id);

        // 유저 여부 체크
        User user = userRepository.findUserByIdOrElseThrow(id);

        // 유저 정보 반환
        return UserGetResponse.toDto(user);
    }

    // 유저 닉네임 수정
    @Transactional
    public UserUpdateResponse updateUser(Long id, String username, String password) {

        // ID 값 여부 체크
        checkId(id);

        // 유저 여부 체크
        User user = userRepository.findUserByIdOrElseThrow(id);

        // 비밀번호 체크
        checkPassword(password);

        // 비밀번호가 비교
        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // 유저 닉네임 null, 공백 체크
        if (username == null || username.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "닉네임은 필수 입력 값입니다.");
        }

        // 해당 유저 닉네임 수정
        user.setUsername(username);

        // DB에 즉시 반영
        userRepository.flush();

        // 수정된 유저 정보 반환
        return UserUpdateResponse.toDto(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long id, String password) {

        // ID 값 여부 체크
        checkId(id);

        // 유저 여부 체크
        User user = userRepository.findUserByIdOrElseThrow(id);

        // 비밀번호 체크
        checkPassword(password);

        // 비밀번호가 비교
        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // DB에 유저 정보 삭제
        userRepository.delete(user);
    }






    // ===== 헬퍼 메서드 =====

    // ID 값 여부 체크
    private void checkId(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID는 필수 입력값입니다");
        }
    }

    private void checkPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID는 필수 입력값입니다");
        }
    }
}
