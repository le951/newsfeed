package org.example.newsfeed.dto.like;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeRequestDto {

    private final Long targetId;
    private final String targetType;
}
