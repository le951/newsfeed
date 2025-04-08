package org.example.newsfeed.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowResponseDto {

    private final Long follower_id;

    private final Long following_id;

    private final String message;

}
