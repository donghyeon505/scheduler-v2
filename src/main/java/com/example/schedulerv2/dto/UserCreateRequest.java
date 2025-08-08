package com.example.schedulerv2.dto;

import lombok.Getter;

@Getter
public class UserCreateRequest {

    private final String username;

    private final String email;

    public UserCreateRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
