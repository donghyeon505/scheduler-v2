package com.example.schedulerv2.dto.schedule;

import com.example.schedulerv2.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleUpdateResponse {

    private final Long id;

    private final String title;

    private final String content;

    private final String username;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public ScheduleUpdateResponse(
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

    public static ScheduleUpdateResponse toDto(Schedule schedule) {
        return new ScheduleUpdateResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getUsername(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
