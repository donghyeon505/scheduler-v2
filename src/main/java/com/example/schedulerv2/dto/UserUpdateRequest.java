package com.example.schedulerv2.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private final String username;

    public UserUpdateRequest(String username) {
        this.username = username;
    }
}
