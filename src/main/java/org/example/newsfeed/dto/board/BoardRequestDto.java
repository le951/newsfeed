package org.example.newsfeed.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardRequestDto {

  private final String title;

  private final String contents;


}

