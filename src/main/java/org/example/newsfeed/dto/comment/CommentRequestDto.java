package org.example.newsfeed.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용은 비울 수 없습니다.")
    @Size(max = 500, message = "댓글은 500자를 초과할 수 없습니다.")
    private final String comments;
}
