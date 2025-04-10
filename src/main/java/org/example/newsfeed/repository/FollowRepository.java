package org.example.newsfeed.repository;

import org.example.newsfeed.entity.Follow;
import org.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByToUserAndFromUser(User toUser, User fromUser);

    Optional<Follow> findByToUserAndFromUser(User toUser, User fromUser);

    int countByToUser(User toUser);

    int countByFromUser(User fromUser);

}
