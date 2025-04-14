package org.example.newsfeed.repository;

import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    List<Comment> findAllByBoardId(Long boardId);

	// 회원 탈퇴 시 회원의 게시글 전체 삭제
	void deleteAllByUserId(Long	userId);
}
