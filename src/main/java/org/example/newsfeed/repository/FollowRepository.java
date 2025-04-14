package org.example.newsfeed.repository;

import org.example.newsfeed.entity.Follow;
import org.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // follow 테이블에 toUser / fromUser 으로 된 조합이 있는지 확인
    // => 팔로우 되어있는지 확인
    boolean existsByToUserAndFromUser(User toUser, User fromUser);

    // fromUser가 toUser에 해당하는 팔로우 반환
    Optional<Follow> findByToUserAndFromUser(User toUser, User fromUser);

    // 팔로워 수 확인
    int countByToUser(User toUser);

    // 팔로잉 수 확인
    int countByFromUser(User fromUser);

    // 회원 탈퇴시 회원 팔로우 관계 삭제 (내가 팔로우한 사람 + 나를 팔로우한 사람)
    void deleteAllByFromUserIdOrToUserId(Long fromUserId, Long toUserId);

}
