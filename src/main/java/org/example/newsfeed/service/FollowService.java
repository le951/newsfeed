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

    public FollowResponseDto follow(Long followerId, Long FollowingId) {

        User follower = userRepository.findByIdOrElseThrow(followerId);
        User following = userRepository.findByIdOrElseThrow(FollowingId);

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new CustomException(ErrorCode.ALREADY_FOLLOW);
        }

        Follow follow = new Follow(follower, following);
        followRepository.save(follow);

        return new FollowResponseDto(follower.getId(), following.getId(), "팔로우");
    }

    public void unfollow(Long followerId, Long followingId) {

        User follower = userRepository.findByIdOrElseThrow(followerId);
        User following = userRepository.findByIdOrElseThrow(followingId);

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOLLOW_USER));

        followRepository.delete(follow);
    }

    public String checkFollowBack(Long followerId, Long followingId) {

        User follower = userRepository.findByIdOrElseThrow(followerId);
        User following = userRepository.findByIdOrElseThrow(followingId);

        int followerCount = followRepository.countByFollower(follower);
        int followingCount = followRepository.countByFollowing(following);

        if (followRepository.existsByFollowerAndFollowing(follower, following) && followRepository.existsByFollowerAndFollowing(following, follower)) {
            return "맞팔입니다." + "팔로워 : " + followerCount + "팔로잉 : " + followingCount;
        }

        return "맞팔아님" +"팔로워 : " + followerCount + "팔로잉 : " + followingCount;
    }
}
