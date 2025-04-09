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

  Optional<Board> findByUserIdAndId(Long userId, Long id);

  default Board findByUserIdAndIdOrElseThrow(Long userId, Long boardId){
    return findByUserIdAndId(userId,boardId)
        .orElseThrow(()->
            new CustomException(ErrorCode.BOARD_NOT_FOUND)
        );
  }

  default Board findByIdOrElseThrow(Long boardId){
    return findById(boardId)
        .orElseThrow(()->
            new CustomException(ErrorCode.BOARD_NOT_FOUND)
        );

  }

  Page<Board> findAllByUserIdIn(List<Long> followerIds, Pageable pageable);
}

