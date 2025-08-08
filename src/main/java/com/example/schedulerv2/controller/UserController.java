package com.example.schedulerv2.controller;

import com.example.schedulerv2.dto.user.*;
import com.example.schedulerv2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 유저 생성
    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {

        UserCreateResponse response = userService.create(userCreateRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 유저 찾기
    @GetMapping("/{userId}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable long userId) {

        UserGetResponse response = userService.findById(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 유저 닉네임 수정
    @PutMapping("/{userId}")
    public ResponseEntity<UserUpdateResponse> UpdateUser(
            @PathVariable long userId,
            @RequestBody UserUpdateRequest request
    ) {

        UserUpdateResponse response = userService.updateUser(userId, request.getUsername(), request.getPassword());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public void deleteUser(
            @PathVariable long userId,
            @RequestBody UserDeleteRequest request
    ) {
        userService.deleteUser(userId, request.getPassword());
    }
}
