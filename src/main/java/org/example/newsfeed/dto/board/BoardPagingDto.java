package org.example.newsfeed.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardPagingDto {
  private int page;
  private int size = 10;
  private String sort = "DESC";

}
