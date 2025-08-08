package com.example.schedulerv2.controller;

import com.example.schedulerv2.dto.user.*;
import com.example.schedulerv2.entity.User;
import com.example.schedulerv2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateRequest request) {

        UserCreateResponse response = userService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> Login(
            @RequestBody UserLoginRequest userRequest,
            HttpServletRequest request
    ) {
        // 유저 정보 찾기
        User user = userService.login(userRequest.getEmail(), userRequest.getPassword());

        // session 쿠키 발급 및 메모리에 세션 저장
        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_USER", user.getId());

        // 출력
        return new ResponseEntity<>(user.getUsername() + "님 반갑습니다.", HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        // 세션이 존재하는 경우 해당 세션 데이터를 삭제
        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
    }

    // 본인 계정 정보 보기
    @GetMapping
    public ResponseEntity<UserGetInfoResponse> getUserInfo(HttpServletRequest request) {

        // session 에서 loginUserId 가져오기
        HttpSession session = request.getSession(false);
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");

        // 정보 찾기
        UserGetInfoResponse response = userService.getInfo(loginUserId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 유저 닉네임 변경
    @PutMapping("/me")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @RequestBody UserUpdateRequest userRequest,
            HttpServletRequest request
    ) {

        // session 에서 loginUserId 가져오기
        HttpSession session = request.getSession(false);
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");

        // 닉네임 변경
        UserUpdateResponse response = userService.updateUser(
                userRequest.getUsername(),
                userRequest.getPassword(),
                loginUserId
        );

        // 수정된 정보 반환
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 유저 탈퇴
    @DeleteMapping("/me")
    public void deleteUser(
            @RequestBody UserDeleteRequest userRequest,
            HttpServletRequest request
    ) {

        // session 에서 loginUserId 가져오기
        HttpSession session = request.getSession(false);
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");

        // 유저 탈퇴
        userService.deleteUser(userRequest.getPassword(), loginUserId);
    }
}
