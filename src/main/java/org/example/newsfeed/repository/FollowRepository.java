package org.example.newsfeed.repository;

import org.example.newsfeed.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowRepository extends JpaRepository<Follow, Long> {
}
