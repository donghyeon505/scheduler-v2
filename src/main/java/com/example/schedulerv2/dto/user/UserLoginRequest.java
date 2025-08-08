package com.example.schedulerv2.dto.user;

import lombok.Getter;

@Getter
public class UserLoginRequest {

    private final String email;

    private final String password;

    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
