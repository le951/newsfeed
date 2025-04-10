package org.example.newsfeed.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowResponseDto {

    private final Long toUserId;

    private final Long fromUserId;

    private final String message;

}
