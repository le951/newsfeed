package org.example.newsfeed.dto.board;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


// 게시물 전체 조회에 사용되는 responseDto
@Getter
@AllArgsConstructor
public class BoardListDto {

  // 게시글 작성자 닉네임
  private final String nickname;

  // 게시물 제목
  private final String title;

  // 게시글 생성일
  private final LocalDateTime createdAt;

}
