package org.example.newsfeed.dto.board;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardListDto {

  private final String nickname;

  private final String title;

  private final LocalDateTime createdAt;

}
