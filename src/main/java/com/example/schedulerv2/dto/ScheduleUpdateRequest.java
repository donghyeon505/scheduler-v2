package com.example.schedulerv2.dto;

import lombok.Getter;

@Getter
public class ScheduleUpdateRequest {

    private final String title;
    private final String content;


    public ScheduleUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
