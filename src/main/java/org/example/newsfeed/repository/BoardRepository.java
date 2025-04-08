package org.example.newsfeed.repository;

import java.util.Optional;
import org.example.newsfeed.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@Repository
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
}
