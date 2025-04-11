package org.example.newsfeed.dto.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.entity.LikeType;

@Getter
@AllArgsConstructor
public class LikeResponseDto {
    private final Long targetId;
    private final LikeType targetType;
    private final String message;
}
