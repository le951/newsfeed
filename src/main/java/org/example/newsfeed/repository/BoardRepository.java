package org.example.newsfeed.repository;

import java.util.List;
import java.util.Optional;
import org.example.newsfeed.entity.Board;
import org.example.newsfeed.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;



public interface BoardRepository extends JpaRepository<Board, Long> {

  Optional<Board> findByUserIdAndId(Long userId, Long id);

  default Board findByUserIdAndIdOrElseThrow(Long userId, Long boardId){
    return findByUserIdAndId(userId,boardId)
        .orElseThrow(()->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Does not exist id = " + boardId
            )
        );
  }

  Page<Board> findAllByUserIdIn(List<Follow> followerIds, Pageable pageable);
}

