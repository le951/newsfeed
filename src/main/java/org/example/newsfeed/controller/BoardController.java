package org.example.newsfeed.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.dto.board.BoardDetailResponseDto;
import org.example.newsfeed.dto.board.BoardListDto;
import org.example.newsfeed.dto.board.BoardRequestDto;
import org.example.newsfeed.dto.board.BoardResponseDto;
import org.example.newsfeed.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/newsfeeds")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  // token 사용해서 로그인한 경우
  // 게시물 생성 로직
  @PostMapping
  public ResponseEntity<BoardResponseDto> saveBoard(
      HttpServletRequest request,
      @RequestBody BoardRequestDto requestDto
  ){

    Long userId = findUserIdFromToken(request);

    BoardResponseDto boardResponseDto =
        boardService.saveBoard(
            userId,
            requestDto
        );

    return new ResponseEntity<>(boardResponseDto,HttpStatus.CREATED);
  }

  // 게시물 수정
  @PatchMapping("/{newsfeedsId}")
  public ResponseEntity<BoardResponseDto> updateBoard(
      HttpServletRequest request,
      @PathVariable Long newsfeedsId,
      @Valid @RequestBody BoardRequestDto requestDto
  ){

    Long userId = findUserIdFromToken(request);

    BoardResponseDto boardResponseDto =
        boardService.updateBoard(
            userId,
            newsfeedsId,
            requestDto
        );

    return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
  }


  // 게시물 단건 조회
  @GetMapping("/{newsfeedsId}")
  public ResponseEntity<BoardDetailResponseDto> findBoardById(
      @PathVariable Long newsfeedsId
  ){

    BoardDetailResponseDto findBoard = boardService.findBoardById(newsfeedsId);

    return new ResponseEntity<>(findBoard,HttpStatus.OK);

  }

  // 게시물 전체 조회 -> 팔로우 한사람들만(구독중인 채널 가져오기 같은 느낌)
  @GetMapping("/following")
  public ResponseEntity<Page<BoardListDto>> findAllBoardsOfFollowingUsers(
      HttpServletRequest request,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
  ){

    Long userId = findUserIdFromToken(request);

    Page<BoardListDto> boardPages =
        boardService.findAllBoardsOfFollowingUsers(
            pageable,
            userId
        );

    return new ResponseEntity<>(boardPages, HttpStatus.OK);

  }


  // 게시물 전체 조회 -> 모든 게시물중에서
  @GetMapping("/allusers")
  public ResponseEntity<Page<BoardListDto>> findAllBoardsOfAllUsers(
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
  ){

    Page<BoardListDto> boardPages = boardService.findAllBoardsOfAllUsers(pageable);

    return new ResponseEntity<>(boardPages, HttpStatus.OK);

  }

  // 게시물 삭제
  @DeleteMapping("/{newsfeedsId}")
  public ResponseEntity<Void> deleteBoard(
      HttpServletRequest request,
      @PathVariable Long newsfeedsId
  ){

    Long userId = findUserIdFromToken(request);

    boardService.deleteBoard(userId,newsfeedsId);

    return new ResponseEntity<>(HttpStatus.OK);
  }


  // 토큰에서 userId 값 가져오기
  public Long findUserIdFromToken(HttpServletRequest request){

    Long userId = (Long) request.getAttribute("userId");

    // 유저 아이디가 비어 있다면 USER_NOT_FOUND 에러 발생
    if (userId == null) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }
    return userId;
  }

}
