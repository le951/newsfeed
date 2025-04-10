package org.example.newsfeed.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowResponseDto {

    // 팔로우 받는 사람
    private final Long toUserId;

    // 팔로우 요청을 하는 사람
    private final Long fromUserId;

    private final String message;
}
