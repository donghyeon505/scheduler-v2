package com.example.schedulerv2.dto;

import lombok.Getter;

@Getter
public class ScheduleCreateRequest {

    private final String title;

    private final String content;

    private final String writer;

    public ScheduleCreateRequest(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
