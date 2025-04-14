package org.example.newsfeed.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentMessageResponseDto {

    private final Long id;

    private final String nickname;

    private final String comments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;

    private final String message;

    public static CommentMessageResponseDto toDto(Comment comment,String message) {
        return new CommentMessageResponseDto(
                comment.getId(),
                comment.getUser().getNickname(),
                comment.getComments(),
                comment.getCreatedAt(),
                message
        );
    }

}
