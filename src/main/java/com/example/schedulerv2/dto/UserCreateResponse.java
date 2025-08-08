package com.example.schedulerv2.dto;

import com.example.schedulerv2.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCreateResponse {

    private final Long id;

    private final String username;

    private final String email;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public UserCreateResponse(
            Long id,
            String username,
            String email,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserCreateResponse toDto(User user) {
        return new UserCreateResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
