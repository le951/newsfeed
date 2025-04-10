package org.example.newsfeed.repository;

import java.util.List;
import java.util.Optional;
import org.example.newsfeed.entity.Like;
import org.example.newsfeed.entity.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {
    //로그인한 유저가 해당 게시글 혹은 댓글에 좋아요를 했는지 여부 체크
    Optional<Like>findByUserIdAndTargetIdAndLikeTargetType(Long userId, Long targetId, LikeType targetType);

    //해당 게시글 혹은 댓글의 좋아요 수 count
    Long countByTargetIdAndLikeTargetType(Long targetId, LikeType targetType);

    @Query("SELECT l.targetId, COUNT(l) FROM Like l WHERE l.targetId IN :targetIds AND l.likeTargetType = :targetType GROUP BY l.targetId")
    List<Object[]> countLikeByTargetIdInAndLikeTargetType(
        @Param("targetIds") List<Long> targetIds,
        @Param("targetType") LikeType targetType);

}
