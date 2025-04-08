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
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성 기능
    @PostMapping("/newsfeeds/{newsfeedsId}/comments")
    public ResponseEntity<String> saveComment(
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
        commentService.saveComment(newsfeedsId,1L,requsetDto);

        return new ResponseEntity<>("댓글 작성 완료",HttpStatus.CREATED);
    }
    //댓글 수정 기능
    //Response 메세지는 임시 - 후에 논의 후 수정
    @PatchMapping("/comments/{id}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long id,
    //       HttpServletRequest httpServletRequest,
            @Valid @RequestBody CommentRequestDto requestDto
    ){
    //        LoginResponseDto loginUser = (LoginResponseDto) httpServletRequest.getSession().getAttribute(Const.LOGIN_USER);
       commentService.updateComment(id,1L, requestDto);

       return new ResponseEntity<>("댓글 수정 완료", HttpStatus.OK);
    }

}