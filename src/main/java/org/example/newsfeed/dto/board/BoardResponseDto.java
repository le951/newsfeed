package org.example.newsfeed.dto.board;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.entity.Board;

@Getter
@AllArgsConstructor
public class BoardResponseDto {

  private final Long id;

  private final String title;

  private final String contents;

  private final LocalDateTime createdAt;


  public static BoardResponseDto toDto(Board board){
    return new BoardResponseDto(board.getId(), board.getTitle(), board.getContents(), board.getCreatedAt());
  }

}
