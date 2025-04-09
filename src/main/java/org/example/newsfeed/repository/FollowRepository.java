package org.example.newsfeed.repository;

import org.example.newsfeed.entity.Follow;
import org.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerAndFollowing(User follower, User following);

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    int countByFollower(User follower);

    int countByFollowing(User following);


}
