package org.example.newsfeed.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.dto.board.BoardRequestDto;
import org.example.newsfeed.dto.board.BoardResponseDto;
import org.example.newsfeed.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  // session 사용해서 로그인한 경우
  // 게시물 생성 로직
  @PostMapping
  public ResponseEntity<BoardResponseDto> saveBoard(
      @RequestBody BoardRequestDto requestDto,
      @SessionAttribute(name = Const.LOGIN_USER, required = false) LoginResposneDto loginUser
  ){

    BoardResponseDto boardResponseDto = boardService.saveBoard(loginUser.getId(), requestDto);

    return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
  }

  // 게시물 수정
  @PatchMapping("/{boardId}")
  public ResponseEntity<BoardResponseDto> updateBoard(
      @PathVariable Long boardId,
      @RequestBody BoardRequestDto requestDto,
      @SessionAttribute(name = Const.LOGIN_USER, required = false) LoginResposneDto loginUser
  ){

    BoardResponseDto boardResponseDto =
        boardService.updateBoard(
            loginUser.getId(),
            boardId,
            requestDto
        );

    return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
  }


//  // token 사용해서 로그인한 경우
//  // 게시물 생성 로직
//  // 필터에서 토큰 검증이 있다는 가정하에 토큰 검증 진행 x
//  @PostMapping
//  public ResponseEntity<BoardResponseDto> saveBoard(
//      @RequestBody BoardRequestDto requestDto,
//      @RequestHeader(value = "Authorization", required = false) String bearerToken
//  ){
//
//    // token 앞단에 Bearer 부분 없애기
//    String token = bearerToken.substring(7);
//
//    BoardResponseDto boardResponseDto = boardService.saveBoard(token, requestDto);
//
//    return new ResponseEntity<>(HttpStatus.CREATED);
//  }
}
