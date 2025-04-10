package org.example.newsfeed.service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.dto.board.BoardDetailResponseDto;
import org.example.newsfeed.dto.board.BoardListDto;
import org.example.newsfeed.dto.board.BoardRequestDto;
import org.example.newsfeed.dto.board.BoardResponseDto;
import org.example.newsfeed.dto.comment.CommentResponseDto;
import org.example.newsfeed.entity.Board;
import org.example.newsfeed.entity.Comment;
import org.example.newsfeed.entity.Follow;
import org.example.newsfeed.entity.LikeType;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.BoardRepository;
import org.example.newsfeed.repository.CommentRepository;
import org.example.newsfeed.repository.LikeRepository;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;
  private final LikeRepository likeRepository;

  // 게시물 생성
  public BoardResponseDto saveBoard(Long userId, BoardRequestDto requestDto) {

    // UserId로 User객체 찾아옴
    User findUser = userRepository.findByIdOrElseThrow(userId);

    // 요청 시 받은 내용으로 board 객체 생성
    Board board = new Board(requestDto.getTitle(), requestDto.getContents());

    // board와 user 연결
    board.setUser(findUser);

    // board객체 저장
    Board savedBoard = boardRepository.save(board);

    return new BoardResponseDto(
        savedBoard.getId(),
        savedBoard.getTitle(),
        savedBoard.getContents(),
        savedBoard.getCreatedAt()
    );
  }

  // 게시물 수정
  @Transactional
  public BoardResponseDto updateBoard(Long userId, Long newsfeedsId, BoardRequestDto requestDto) {

    // 로그인 유저의 해당 게시물 찾아오기
    Board findBoard = boardRepository.findByUserIdAndIdOrElseThrow(userId, newsfeedsId);

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

  // 게시물 단건 조회
  public BoardDetailResponseDto findBoardById(Long newsfeedsId) {

    // newsfeedsId와 일치하는 board 단건 조회
    Board findBoard = boardRepository.findByIdOrElseThrow(newsfeedsId);

    Long countBoardLike = likeRepository.countByTargetIdAndLikeTargetType(findBoard.getId(), changeType("newsfeeds"));

    List<Long> commentIdList = commentRepository.findAllByBoardId(findBoard.getId()).stream().map(comment -> comment.getId()).toList();

//    List<Object[]> commentLikeList = likeRepository.countLikesByTargetIdInAndLikeTargetType(commentIdList, changeType("comments"));

    Map<Long, Long> commentLikeMap = likeRepository.countLikeByTargetIdInAndLikeTargetType(commentIdList, changeType("comments"))
        .stream()
        .collect(Collectors.toMap(
            row -> (Long) row[0], // commentId
            row -> (Long) row[1]  // likeCount
        ));

    // board와 연결된 commentList 가져오기
    List<CommentResponseDto> commentList = findBoard
        .getCommentList()
        .stream()
        .map(comment -> {
          Long likeCount = commentLikeMap.getOrDefault(comment.getId(),0L);
          return CommentResponseDto.toDto(comment,likeCount);
        } )
        .toList();

    return new BoardDetailResponseDto(
        findBoard.getUser().getNickname(),
        findBoard.getTitle(),
        findBoard.getContents(),
        countBoardLike,
        findBoard.getCreatedAt(),
        commentList
    );

  }

  // 팔로잉 유저 게시물 조회
  @Transactional(readOnly = true)
  public Page<BoardListDto> findAllBoardsOfFollowingUsers(Pageable pageable, Long userId) {

    // 로그인 유저 객체 가져옴
    User findUser = userRepository.findByIdOrElseThrow(userId);

    // 로그인 유저의 팔로잉 리스트 가져오기
    List<Follow> findToUserList = findUser.getFromUserList();

    // 팔로잉 리스트의 아이디값 추출
    List<Long> toUserIdList = findToUserList
        .stream()
        .map(follow -> follow.getToUser().getId())
        .toList();

    // 아이디값에 해당하는 게시물 모두 가져옴 -> board 객체들 페이징됨
    Page<Board> boardPages = boardRepository.findAllByUserIdIn(toUserIdList,pageable);

    // map을 이용하여 board 타입에서 BoardListDto 타입으로 변환
    Page<BoardListDto> boardListDtos = boardPages
        .map(boardPage ->
            new BoardListDto(
                boardPage.getUser().getNickname(),
                boardPage.getTitle(),
                boardPage.getCreatedAt()
            )
        );

    return boardListDtos;

  }

  // 모든 유저 게시물 조회
  @Transactional(readOnly = true)
  public Page<BoardListDto> findAllBoardsOfAllUsers(Pageable pageable) {

    // 모든 유저의 board 가져옴 -> board 객체들 페이징됨
    Page<Board> boardPages = boardRepository.findAll(pageable);

    // map을 이용하여 board 타입에서 BoardListDto 타입으로 변환
    Page<BoardListDto> boardListDtos = boardPages
        .map(boardPage ->
            new BoardListDto(
                boardPage.getUser().getNickname(),
                boardPage.getTitle(),
                boardPage.getCreatedAt()
            )
        );

    return boardListDtos;

  }


  // 게시물 삭제
  @Transactional
  public void deleteBoard(Long userId, Long newsfeedsId) {

    // userId와 newsfeedsId에 일치하는 board 객체
    Board findBoard = boardRepository.findByUserIdAndIdOrElseThrow(userId, newsfeedsId);

    // board와 연결된 댓글 리스트 조회
    List<Comment> findComment = commentRepository.findAllByBoardId(findBoard.getId());

    // 둘이 외래키로 연결됨
    // 게시글 삭제할 때 댓글 다 삭제 해줘야함
    commentRepository.deleteAll(findComment);

    boardRepository.delete(findBoard);
  }

  public LikeType changeType(String type){
    return LikeType.strLikeTypeToEnum(type);
  }


}
