package org.example.newsfeed.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.dto.board.BoardListDto;
import org.example.newsfeed.dto.board.BoardPagingDto;
import org.example.newsfeed.dto.board.BoardRequestDto;
import org.example.newsfeed.dto.board.BoardResponseDto;
import org.example.newsfeed.entity.Board;
import org.example.newsfeed.entity.Comment;
import org.example.newsfeed.entity.Follow;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.BoardRepository;
import org.example.newsfeed.repository.CommentRepository;
import org.example.newsfeed.repository.FollowRepository;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final FollowRepository followRepository;
  private final CommentRepository commentRepository;

  // session버전 게시물 생성
  public BoardResponseDto saveBoard(Long userId, BoardRequestDto requestDto) {
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
  public BoardResponseDto updateBoard(Long userId, Long boardId, BoardRequestDto requestDto) {
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

  // 팔로잉 유저 게시물 조회
  @Transactional(readOnly = true)
  public Page<BoardListDto> findAllBoardsOfFollowingUsers(BoardPagingDto boardPagingDto, Long userId) {

    Sort sort = Sort.by(Sort.Direction.fromString(boardPagingDto.getSort()), "createdAt");

    Pageable pageable = PageRequest.of(boardPagingDto.getPage(), boardPagingDto.getSize(), sort);

    User findUser = userRepository.findByIdOrElseThrow(userId);

    List<Follow> findFollowerIds = findUser.getFollowerId();

    Page<Board> boardPages = boardRepository.findAllByUserIdIn(findFollowerIds,pageable);

    Page<BoardListDto> boardListDtos = boardPages.map(
        boardPage -> new BoardListDto(boardPage.getUser().getNickname(), boardPage.getTitle(),
            boardPage.getCreatedAt()));

    return boardListDtos;

  }

  // 모든 유저 게시물 조회
  @Transactional(readOnly = true)
  public Page<BoardListDto> findAllBoardsOfAllUsers(BoardPagingDto boardPagingDto) {

    Sort sort = Sort.by(Sort.Direction.fromString(boardPagingDto.getSort()), "createdAt");
    Pageable pageable = PageRequest.of(boardPagingDto.getPage(), boardPagingDto.getSize(), sort);

    Page<Board> boardPages = boardRepository.findAll(pageable);

    Page<BoardListDto> boardListDtos = boardPages.map(
        boardPage -> new BoardListDto(boardPage.getUser().getNickname(), boardPage.getTitle(),
            boardPage.getCreatedAt()));

    return boardListDtos;

  }


  // 게시물 삭제
  @Transactional
  public void deleteBoard(Long userId, Long boardId) {
    Board findBoard = boardRepository.findByUserIdAndIdOrElseThrow(userId, boardId);

    List<Comment> findComment = commentRepository.findAllByBoardId(findBoard.getId());

    // 둘이 외래키로 연결됨
    // 게시글 삭제할 때 댓글 다 삭제 해줘야함
    commentRepository.deleteAll(findComment);

    boardRepository.delete(findBoard);
  }

}
