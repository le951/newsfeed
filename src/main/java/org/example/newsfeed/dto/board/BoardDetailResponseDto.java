package org.example.newsfeed.dto.board;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.dto.comment.CommentResponseDto;

// 게시물 단건 조회 responseDto
@Getter
@AllArgsConstructor
public class BoardDetailResponseDto {

  // 게시물 작성자 닉네임
  private final String userNickname;

  // 게시물 제목
  private final String boardTitle;

  // 게시물 내용
  private final String BoardContents;

  // 게시물 생성일
  private final LocalDateTime BoardCreatedAt;

  // 댓글 리스트
  private final List<CommentResponseDto> commentList;

//  private final String commenterNickname;
//
//  private  final String comments;
//
//  private final LocalDateTime commentCreatedAt;

}
