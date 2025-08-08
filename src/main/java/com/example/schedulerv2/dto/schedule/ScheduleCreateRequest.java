package com.example.schedulerv2.dto.schedule;

import lombok.Getter;

@Getter
public class ScheduleCreateRequest {

    private final String title;

    private final String content;

    private final Long userId;

    public ScheduleCreateRequest(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
