package org.example.newsfeed.repository;

import org.example.newsfeed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 댓글입니다."));
    }

    List<Comment> findAllByBoardId(Long boardId);

	// 회원 탈퇴 시 회원의 게시글 전체 삭제
	void deleteAllByUserId(Long	userId);
}
