package org.example.newsfeed.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    //좋아요id, 작성자 닉네임, 댓글내용, 생성일, 좋아요 수
    private final Long id;

    private final String nickname;

    private final String comments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;

    private final Long likeCount;

    public static CommentResponseDto toDto(Comment comment,Long likeCount) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getNickname(),
                comment.getComments(),
                comment.getCreatedAt(),
                likeCount
        );
    }
}