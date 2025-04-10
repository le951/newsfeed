package org.example.newsfeed.dto.board;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 게시물 생성 후 반환하는 responseDTO
@Getter
@AllArgsConstructor
public class BoardResponseDto {

  // 게시물 아이디
  private final Long id;

  // 게시물 제목
  private final String title;

  // 게시물 내용
  private final String contents;

  // 게시물 생성일
  private final LocalDateTime createdAt;
}
