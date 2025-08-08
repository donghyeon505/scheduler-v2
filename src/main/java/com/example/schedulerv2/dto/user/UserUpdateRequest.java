package com.example.schedulerv2.dto.user;

import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private final String username;

    private final String password;

    public UserUpdateRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
