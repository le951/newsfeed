package org.example.newsfeed.repository;

import org.example.newsfeed.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like>findByUserIdAndBoardId(Long userId, Long boardId);

    Long countByBoardId(Long boardId);
}
