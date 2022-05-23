package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.constant.MemberConst.*;

import com.ssafy.happyhouse5.dto.comment.CommentRegistDto;
import com.ssafy.happyhouse5.dto.comment.CommentUpdateDto;
import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.service.MemberService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequestMapping("/api/members/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Response> createComment(
        @SessionAttribute(MEMBER_SESSION) Long memberId,
        @RequestBody CommentRegistDto commentRegistDto) {

        Long commentId = memberService.createComment(
            memberId, commentRegistDto);

        return ResponseEntity.created(URI.create("/api/members/comments"))
            .body(Response.success(commentId));
    }

    @PutMapping
    public ResponseEntity<Response> updateComment(
        @SessionAttribute(MEMBER_SESSION) Long memberId,
        @RequestBody CommentUpdateDto commentUpdateDto) {

        Long updateCommentId = memberService.updateComment(
            memberId, commentUpdateDto);

        return ResponseEntity.ok(Response.success(updateCommentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteComment(
        @SessionAttribute(MEMBER_SESSION) Long memberId,
        @PathVariable("id") Long commentId) {

        Long deletedCommentId = memberService.deleteComment(memberId, commentId);

        return ResponseEntity.ok(Response.success(deletedCommentId));
    }
}
