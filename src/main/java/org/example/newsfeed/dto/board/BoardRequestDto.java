package org.example.newsfeed.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardRequestDto {

  @NotBlank(message = "제목은 비울 수 없습니다.")
  @Size(max = 50, message = "제목은 50자를 초과할 수 없습니다.")
  private final String title;

  @NotBlank(message = "내용은 비울 수 없습니다.")
  @Size(max = 1000, message = "댓글은 1000자를 초과할 수 없습니다.")
  private final String contents;


}

