package com.example.schedulerv2.service;

import com.example.schedulerv2.dto.user.*;
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
    public User login(String email, String password) {

        // 필수 값 여부 체크
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일은 필수 값입니다.");
        }

        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수 값입니다.");
        }

        // 해당 이메일의 유저가 있는지 체크
        User user = userRepository.findUserByEmailOrElseThrow(email);

        // 해당 유저의 비밀번호 일치여부
        if (!user.getPassword().equals(password)) {
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
    public UserUpdateResponse updateUser(String username, String password, Long loginUserId) {

        // 유저 여부 체크
        User user = userRepository.findUserByIdOrElseThrow(loginUserId);

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
        user.update(username);

        // DB에 즉시 반영
        userRepository.flush();

        // 수정된 유저 정보 반환
        return UserUpdateResponse.toDto(user);
    }

    // 유저 탈퇴
    @Transactional
    public void deleteUser(String password, Long loginUserId) {

        // 유저 정보 가져오기
        User user = userRepository.findUserByIdOrElseThrow(loginUserId);

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

    // 비밀번호 체크
    private void checkPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력값입니다");
        }
    }
}
