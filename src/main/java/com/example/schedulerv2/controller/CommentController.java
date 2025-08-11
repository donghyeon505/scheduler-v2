package com.example.schedulerv2.controller;

import com.example.schedulerv2.dto.comment.CommentCreateRequest;
import com.example.schedulerv2.dto.comment.CommentCreateResponse;
import com.example.schedulerv2.dto.comment.CommentUpdateRequest;
import com.example.schedulerv2.dto.comment.CommentUpdateResponse;
import com.example.schedulerv2.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentCreateResponse> createComment(
            @PathVariable @NotNull(message = "일정 ID는 필수값입니다.")
            @Positive(message = "일정 ID는 1 이상의 값이어야 합니다.")
            Long scheduleId,
            @RequestBody @Valid CommentCreateRequest commentRequest,
            HttpServletRequest request
    ) {
        Long loginUserId = getLoginUserId(request);

        CommentCreateResponse response = commentService.save(scheduleId, loginUserId, commentRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateComment(
            @PathVariable @NotNull(message = "댓글 ID는 필수값입니다.")
            @Positive(message = "댓글 ID는 1 이상의 값이어야 합니다.")
            Long commentId,
            @RequestBody @Valid CommentUpdateRequest commentRequest,
            HttpServletRequest request
    ) {
        Long loginUserId = getLoginUserId(request);

        CommentUpdateResponse response = commentService.update(commentId, commentRequest, loginUserId);

        return ResponseEntity.ok(response);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(
            @PathVariable @NotNull(message = "댓글 ID는 필수값입니다.")
            @Positive(message = "댓글 ID는 1 이상의 값이어야 합니다.")
            Long commentId,
            HttpServletRequest request
    ) {
        Long loginUserId = getLoginUserId(request);

        commentService.delete(commentId, loginUserId);
    }








    // ===== 헬퍼 메서드 =====

    // session 에서 loginUserId 가져오기
    private Long getLoginUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (Long) session.getAttribute("LOGIN_USER");
    }
}
