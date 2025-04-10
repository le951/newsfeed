package org.example.newsfeed.repository;

import java.util.List;
import java.util.Optional;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BoardRepository extends JpaRepository<Board, Long> {

  // UserId와 BoardId가 일치하는 게시물을 optional로 감싸서 반환
  Optional<Board> findByUserIdAndId(Long userId, Long boardId);

  // null 값이면 customException 발생 null이 아니면 board 객체 반환
  default Board findByUserIdAndIdOrElseThrow(Long userId, Long boardId){
    return findByUserIdAndId(userId,boardId)
        .orElseThrow(()->
            new CustomException(ErrorCode.BOARD_NOT_FOUND)
        );
  }

  // boardId에 일치하는 Board가 null 값이면 customException 발생 null이 아니면 board 객체 반환
  default Board findByIdOrElseThrow(Long boardId){
    return findById(boardId)
        .orElseThrow(()->
            new CustomException(ErrorCode.BOARD_NOT_FOUND)
        );

  }

  // 리스트 안에 있는 모든 값과 일치하는 board
  Page<Board> findAllByUserIdIn(List<Long> followerIds, Pageable pageable);
}

