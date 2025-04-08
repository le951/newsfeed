package org.example.newsfeed.service;


import lombok.RequiredArgsConstructor;
import org.example.newsfeed.dto.board.BoardRequestDto;
import org.example.newsfeed.dto.board.BoardResponseDto;
import org.example.newsfeed.entity.Board;
import org.example.newsfeed.repository.BoardRepository;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;

  // session버전 게시물 생성
  public BoardResponseDto saveBoard(Long userId, BoardRequestDto requestDto){
   User findUser = userRepository.findByIdOrElseThrow(userId);
   Board board = new Board(requestDto.getTitle(), requestDto.getContents());
   board.setUser(findUser);
   Board savedBoard = boardRepository.save(board);

   return new BoardResponseDto(
       board.getId(),
       board.getTitle(),
       board.getContents(),
       board.getCreatedAt()
   );
  }

  // 게시물 수정
  @Transactional
  public BoardResponseDto updateBoard(Long userId, Long boardId, BoardRequestDto requestDto){
    // 로그인 유저의 해당 게시물 찾아오기
    Board findBoard = boardRepository.findByUserIdAndIdOrElseThrow(userId, boardId);

    // 업데이트
    findBoard.updateBoard(
        requestDto.getTitle(),
        requestDto.getContents()
    );

    return new BoardResponseDto(
        findBoard.getId(),
        findBoard.getTitle(),
        findBoard.getContents(),
        findBoard.getCreatedAt()
    );

  }

//  // token ver 게시물 생성
//  public BoardResponseDto saveBoard(String token, BoardRequestDto requestDto){
//    User findUser = userRepository.findByIdOrElseThrow(token.());
//    Board board = new Board(requestDto.getTitle(), requestDto.getContents());
//    board.setUser(findUser);
//    Board savedBoard = boardRepository.save(board);
//
//    return new BoardResponseDto(
//        board.getId(),
//        board.getTitle(),
//        board.getContents(),
//        board.getCreatedAt()
//    );
//
//  }
//
//  public Long getUserIdFromToken(String token){
//    // 구현 필요
//  }




}
