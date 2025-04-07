package org.example.newsfeed.repository;

import org.example.newsfeed.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
