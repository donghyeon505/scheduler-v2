package com.example.schedulerv2.service;

import com.example.schedulerv2.dto.comment.*;
import com.example.schedulerv2.entity.Comment;
import com.example.schedulerv2.entity.Schedule;
import com.example.schedulerv2.entity.User;
import com.example.schedulerv2.repository.CommentRepository;
import com.example.schedulerv2.repository.ScheduleRepository;
import com.example.schedulerv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    @Transactional
    public CommentCreateResponse save(Long scheduleId, Long loginUserId, CommentCreateRequest commentRequest) {

        // 유저정보와 일정정보 가져오기
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        User user = userRepository.findUserByIdOrElseThrow(loginUserId);

        // 댓글 객체에 값 넣기
        Comment comment = new Comment(commentRequest.getComment());
        comment.assignUserAndSchedule(schedule, user);

        // DB에 댓글 저장
        Comment savedComment = commentRepository.save(comment);

        // 댓글 정보 반환
        return CommentCreateResponse.toDto(savedComment);
    }

    // 댓글 수정
    @Transactional
    public CommentUpdateResponse update(Long commentId, CommentUpdateRequest commentRequest, Long loginUserId) {

        // 해당 댓글 찾기
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        // 본인 댓글인지 확인
        checkComment(comment.getUser().getId(), loginUserId, "본인의 댓글만 수정할 수 있습니다.");

        // 댓글 정보 업데이트
        comment.update(commentRequest.getComment());

        // DB에 즉시 반영 (수정일을 바로 보기위함)
        commentRepository.flush();

        // 수정된 정보 반환
        return CommentUpdateResponse.toDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void delete(Long commentId, Long loginUserId) {

        // 해당 댓글 찾기
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        // 본인 댓글인지 확인
        checkComment(comment.getUser().getId(), loginUserId, "본인의 댓글만 삭제할 수 있습니다.");

        commentRepository.delete(comment);

    }







    // ===== 헬퍼 메서드 =====

    // 본인 게시물인지 체크
    private void checkComment(Long commentOwnerId, Long loginUserId, String message) {
        if (!commentOwnerId.equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
        }
    }
}
