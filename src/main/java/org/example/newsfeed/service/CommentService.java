package org.example.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.dto.comment.CommentMessageResponseDto;
import org.example.newsfeed.dto.comment.CommentRequestDto;
import org.example.newsfeed.entity.Board;
import org.example.newsfeed.entity.Comment;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.BoardRepository;
import org.example.newsfeed.repository.CommentRepository;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    //댓글 작성 기능
    @Transactional
    public CommentMessageResponseDto saveComment(Long newsfeedsId, Long userId, CommentRequestDto requestDto) {

        //해당 게시글, 작성자 관련 데이터 호출
        User user = userRepository.findByIdOrElseThrow(userId);
        Board board = boardRepository.findByIdOrElseThrow(newsfeedsId);

        Comment comment = new Comment(user, board, requestDto.getComments());
        commentRepository.save(comment);
        return CommentMessageResponseDto.toDto(comment,"댓글 작성 완료");
    }

    //댓글 수정 기능
    @Transactional
    public CommentMessageResponseDto updateComment(Long commentId, Long loginUserId, CommentRequestDto requestDto) {
        //id값에 해당하는 댓글 데이터 가져오기
        Comment savedComment = commentRepository.findByIdOrElseThrow(commentId);

        //로그인한 유저와 댓글 작성자가 동일하지 않을 경우 에러 출력
        if (!loginUserId.equals(savedComment.getUser().getId())) {
            throw new CustomException(ErrorCode.COMMENT_UPDATE_FORBIDDEN);
        }

        savedComment.updateComment(requestDto.getComments());
        return CommentMessageResponseDto.toDto(savedComment,"댓글 수정 완료");
    }

    @Transactional
    public void deleteComment(Long commentId, Long loginUserId){
        //id값에 해당하는 댓글 데이터 가져오기
        Comment savedComment = commentRepository.findByIdOrElseThrow(commentId);

        //로그인한 유저와 댓글 작성자 혹은 게시글 작성자와 동일하지 않을 경우 에러 출력
        if (!loginUserId.equals(savedComment.getUser().getId()) &&
                !loginUserId.equals(savedComment.getBoard().getUser().getId())
        ) {
            throw new CustomException(ErrorCode.COMMENT_DELETE_FORBIDDEN);
        }
        commentRepository.deleteById(commentId);
    }
}