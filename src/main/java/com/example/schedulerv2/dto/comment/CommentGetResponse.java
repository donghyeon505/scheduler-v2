package com.example.schedulerv2.dto.comment;

import com.example.schedulerv2.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentGetResponse {

    private final Long id;
    private final String comment;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public CommentGetResponse(
            Long id,
            String comment,
            String username,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.comment = comment;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentGetResponse toDto(Comment comment) {
        return new CommentGetResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUser().getUsername(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
