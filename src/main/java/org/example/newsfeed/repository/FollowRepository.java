package org.example.newsfeed.repository;

import org.example.newsfeed.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
