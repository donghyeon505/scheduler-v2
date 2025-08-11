package com.example.schedulerv2.dto.schedule;

import com.example.schedulerv2.dto.comment.CommentGetResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleGetWithCommentResponse {

    private final ScheduleGetOneResponse schedule;
    private final List<CommentGetResponse> comments;

    public ScheduleGetWithCommentResponse(ScheduleGetOneResponse schedule, List<CommentGetResponse> comments) {
        this.schedule = schedule;
        this.comments = comments;
    }
}
