package com.example.schedulerv2.dto.user;

import lombok.Getter;

@Getter
public class UserCreateRequest {

    private final String username;

    private final String email;

    private final String password;

    public UserCreateRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
