package org.example.newsfeed.dto.board;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.dto.comment.CommentResponseDto;

@Getter
@AllArgsConstructor
public class BoardDetailResponseDto {

  private final String userNickname;

  private final String boardTitle;

  private final String BoardContents;

  private final LocalDateTime BoardCreatedAt;

  private final List<CommentResponseDto> commentList;

//  private final String commenterNickname;
//
//  private  final String comments;
//
//  private final LocalDateTime commentCreatedAt;

}
