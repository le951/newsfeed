package org.example.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.dto.follow.FollowResponseDto;
import org.example.newsfeed.entity.Follow;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.FollowRepository;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public FollowResponseDto follow(Long toUserId, Long fromUserId) {

        User toUser = userRepository.findByIdOrElseThrow(toUserId);
        User fromUser = userRepository.findByIdOrElseThrow(fromUserId);

        if (followRepository.existsByToUserAndFromUser(toUser, fromUser)) {
            throw new CustomException(ErrorCode.ALREADY_FOLLOW);
        }

        Follow follow = new Follow(toUser, fromUser);
        followRepository.save(follow);

        return new FollowResponseDto(toUser.getId(), fromUser.getId(), "팔로우");
    }

    public void unfollow(Long toUserId, Long fromUserId) {

        User toUser = userRepository.findByIdOrElseThrow(toUserId);
        User fromUser = userRepository.findByIdOrElseThrow(fromUserId);

        Follow follow = followRepository.findByToUserAndFromUser(toUser, fromUser)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOLLOW_USER));

        followRepository.delete(follow);
    }

    public String checkFollowBack(Long toUserId, Long fromUserId) {

        User toUser = userRepository.findByIdOrElseThrow(toUserId);
        User fromUser = userRepository.findByIdOrElseThrow(fromUserId);

        int toUserCount = followRepository.countByToUser(toUser);
        int fromUserCount = followRepository.countByFromUser(fromUser);

        if (followRepository.existsByToUserAndFromUser(toUser, fromUser) && followRepository.existsByToUserAndFromUser(toUser, fromUser)) {
            return "맞팔입니다." + "팔로워 : " + toUserCount + "팔로잉 : " + fromUserCount;
        }

        return "맞팔아님" +"팔로워 : " + toUserCount + "팔로잉 : " + fromUserCount;
    }
}
