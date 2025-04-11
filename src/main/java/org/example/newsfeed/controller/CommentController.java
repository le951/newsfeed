package org.example.newsfeed.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.common.jwt.JwtUtil;
import org.example.newsfeed.dto.comment.CommentRequestDto;
import org.example.newsfeed.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성 기능
    @PostMapping("/newsfeeds/{newsfeedsId}/comments")
    public ResponseEntity<String> saveComment(
            @PathVariable Long newsfeedsId,
            @Valid @RequestBody CommentRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {

        Long userId = loginCheck(httpServletRequest);
        commentService.saveComment(newsfeedsId, userId, requestDto);

        return new ResponseEntity<>("댓글 작성 완료", HttpStatus.CREATED);
    }

    //댓글 수정 기능
    //Response 메세지는 임시 - 후에 논의 후 수정
    @PatchMapping("/comments/{commentsId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long commentsId,
            HttpServletRequest httpServletRequest,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {

        Long userId = loginCheck(httpServletRequest);
        commentService.updateComment(commentsId, userId, requestDto);

        return new ResponseEntity<>("댓글 수정 완료", HttpStatus.OK);
    }

    //댓글 삭제 기능
    @DeleteMapping("/comments/{commentsId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentsId,
            HttpServletRequest httpServletRequest
    ){
        Long userId = loginCheck(httpServletRequest);

        commentService.deleteComment(commentsId,userId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    private Long loginCheck(HttpServletRequest httpServletRequest){
        Long userId = (Long)httpServletRequest.getAttribute("userId");
        if(userId == null){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        return userId;
    }
}
