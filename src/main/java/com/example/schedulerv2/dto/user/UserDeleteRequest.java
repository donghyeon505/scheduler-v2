package com.example.schedulerv2.dto.user;

import lombok.Getter;

@Getter
public class UserDeleteRequest {

    private final String password;

    public UserDeleteRequest(String password) {
        this.password = password;
    }
}
