package org.example.newsfeed.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.jwt.JwtUtil;
import org.example.newsfeed.dto.board.BoardDetailResponseDto;
import org.example.newsfeed.dto.board.BoardListDto;
import org.example.newsfeed.dto.board.BoardPagingDto;
import org.example.newsfeed.dto.board.BoardRequestDto;
import org.example.newsfeed.dto.board.BoardResponseDto;
import org.example.newsfeed.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;
  private final JwtUtil jwtUtil;

  // token 사용해서 로그인한 경우
  // 게시물 생성 로직
  // 필터에서 토큰 검증이 있다는 가정하에 토큰 검증 진행 x
  @PostMapping
  public ResponseEntity<BoardResponseDto> saveBoard(
      @RequestBody BoardRequestDto requestDto,
      HttpServletRequest request
  ){

    Long userId = findUserIdFromToken(request);
    BoardResponseDto boardResponseDto = boardService.saveBoard(userId, requestDto);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  // 게시물 수정
  @PatchMapping("/{boardId}")
  public ResponseEntity<BoardResponseDto> updateBoard(
      @PathVariable Long boardId,
      @RequestBody BoardRequestDto requestDto,
      HttpServletRequest request
  ){

    Long userId = findUserIdFromToken(request);

    BoardResponseDto boardResponseDto =
        boardService.updateBoard(
            userId,
            boardId,
            requestDto
        );

    return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
  }


  // 게시물 단건 조회
  @GetMapping("/{boardId}")
  public ResponseEntity<BoardDetailResponseDto> findBoardById(
      @PathVariable Long boardId
  ){

    BoardDetailResponseDto findBoard = boardService.findBoardById(boardId);

    return new ResponseEntity<>(findBoard,HttpStatus.OK);

  }

  // 게시물 전체 조회 -> 팔로우 한사람들만(구독중인 채널 가져오기 같은 느낌)
  @GetMapping("/following")
  public Page<BoardListDto> findAllBoardsOfFollowingUsers(
      @RequestParam int pageNumber,
      HttpServletRequest request
  ){

    Long userId = findUserIdFromToken(request);

    BoardPagingDto boardPagingDto = new BoardPagingDto();

    boardPagingDto.setPage(pageNumber);

    return boardService.findAllBoardsOfFollowingUsers(boardPagingDto, userId);

  }

  // 게시물 전체 조회 -> 모든 게시물중에서
  @GetMapping("/allusers")
  public Page<BoardListDto> findAllBoardsOfAllUsers(
      @RequestParam int pageNumber
  ){

    BoardPagingDto boardPagingDto = new BoardPagingDto();

    boardPagingDto.setPage(pageNumber);

    return boardService.findAllBoardsOfAllUsers(boardPagingDto);

  }

  // 게시물 삭제
  @DeleteMapping("/{boardId}")
  public ResponseEntity<Void> deleteBoard(
      HttpServletRequest request,
      @PathVariable Long boardId
  ){

    Long userId = findUserIdFromToken(request);

    boardService.deleteBoard(userId,boardId);

    return new ResponseEntity<>(HttpStatus.OK);
  }


  public Long findUserIdFromToken(HttpServletRequest request){
    Number userIdAttr = (Number) request.getAttribute("userId");
    Long userId = userIdAttr.longValue();

    if (userId == null) {
      throw new RuntimeException("로그인이 필요합니다.");
    }
    return userId;
  }

}
