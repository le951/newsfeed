package org.example.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.dto.comment.CommentRequestDto;
import org.example.newsfeed.dto.comment.CommentResponseDto;
import org.example.newsfeed.entity.Board;
import org.example.newsfeed.entity.Comment;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.BoardRepository;
import org.example.newsfeed.repository.CommentRepository;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    //댓글 작성 기능
    @Transactional
    public void saveComment(Long newsfeedsId, Long userId, CommentRequestDto requestDto){

            //해당 게시글, 작성자 관련 데이터 호출
            //관련 예외 추가되면 수정할 예정
            User user = userRepository.findByIdOrElseThrow(userId);
            Board board = boardRepository.findById(newsfeedsId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));
            ;

            Comment comment = new Comment(user, board, requestDto.getComments());
            commentRepository.save(comment);

    }
        //댓글 수정 기능
        @Transactional
        public void updateComment(Long commentId,Long loginUserId,CommentRequestDto requestDto) {
            //id값에 해당하는 댓글 데이터 가져오기
            Comment savedComment = commentRepository.findByIdOrElseThrow(commentId);

            //로그인한 유저와 댓글 작성자가 동일하지 않을 경우 에러 출력
            if(!loginUserId.equals(savedComment.getUser().getId())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"작성자만 댓글을 수정할 수 있습니다.");
            }

            savedComment.updateComment(requestDto.getComments());
        }
    }