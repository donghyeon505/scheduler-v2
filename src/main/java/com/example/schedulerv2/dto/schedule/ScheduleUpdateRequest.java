package com.example.schedulerv2.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleUpdateRequest {

    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    @Size(max = 20, message = "제목은 20자 이내여야 합니다.")
    private final String title;

    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    @Size(max = 200, message = "내용은 200자 이내여야 합니다.")
    private final String content;


    public ScheduleUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
