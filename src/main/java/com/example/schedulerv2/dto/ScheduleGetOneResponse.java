package com.example.schedulerv2.dto;

import com.example.schedulerv2.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleGetOneResponse {

    private final Long id;

    private final String title;

    private final String content;

    private final String username;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public ScheduleGetOneResponse(
            Long id,
            String title,
            String content,
            String username,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ScheduleGetOneResponse toDto(Schedule schedule) {
        return new ScheduleGetOneResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getUsername(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
