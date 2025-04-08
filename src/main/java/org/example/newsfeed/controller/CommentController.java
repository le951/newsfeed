package org.example.newsfeed.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.dto.comment.CommentRequestDto;
import org.example.newsfeed.dto.comment.CommentResponseDto;
import org.example.newsfeed.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsfeeds/{newsfeedsId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성 기능
    @PostMapping
    public ResponseEntity<Void> saveComment(
            @PathVariable Long newsfeedsId,
//            HttpServletRequest httpServletRequest,
            @Valid @RequestBody CommentRequestDto requsetDto
    ){

        //현재 userId 는 테스트용도 - 후에 수정할 예정
        /*
        테스트 당시 board 기능이 구현되지 않아 console에서 하단 쿼리문 실행 후 테스트함

        INSERT INTO boards(title, contents,user_id,created_at)
        VALUES('타이틀','내용',1,'2025-04-08');

        localhost:8080/newsfeeds/1/comments

        {
        "comments" : "댓글3"
        }

         */
        CommentResponseDto responseDto = commentService.saveComment(newsfeedsId,1L,requsetDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}