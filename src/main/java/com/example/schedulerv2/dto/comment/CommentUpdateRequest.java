package com.example.schedulerv2.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    @NotBlank(message = "댓글은 필수 입력 항목입니다.")
    @Size(max = 100, message = "댓글은 100자 이내여야 합니다.")
    private final String comment;

    public CommentUpdateRequest(String comment) {
        this.comment = comment;
    }
}
