package org.example.newsfeed.service;


import lombok.RequiredArgsConstructor;
import org.example.newsfeed.dto.board.BoardRequestDto;
import org.example.newsfeed.dto.board.BoardResponseDto;
import org.example.newsfeed.entity.Board;
import org.example.newsfeed.repository.BoardRepository;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;

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
