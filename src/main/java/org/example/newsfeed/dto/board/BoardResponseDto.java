package org.example.newsfeed.dto.board;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.newsfeed.entity.Board;

@Getter
public class BoardResponseDto {

  private final Long id;

  private final String title;

  private final String contents;

  private final LocalDateTime createdAt;


  public BoardResponseDto(Long id, String title, String contents, LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.contents = contents;
    this.createdAt = createdAt;
  }

  public static BoardResponseDto toDto(Board board){
    return new BoardResponseDto(board.getId(), board.getTitle(), board.getContents(), board.getCreatedAt());
  }

}