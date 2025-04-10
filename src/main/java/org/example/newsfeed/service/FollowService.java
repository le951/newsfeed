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

    // 팔로우
    public FollowResponseDto follow(Long toUserId, Long fromUserId) {

        // 팔로워와 팔로잉을 찾아옴
        User toUser = userRepository.findByIdOrElseThrow(toUserId);
        User fromUser = userRepository.findByIdOrElseThrow(fromUserId);

        // 이미 팔로우 된 유저인지 확인
        if (checkFollow(toUser, fromUser)) {
            throw new CustomException(ErrorCode.ALREADY_FOLLOW);
        }

        Follow follow = new Follow(toUser, fromUser);
        followRepository.save(follow);

        return new FollowResponseDto(toUser.getId(), fromUser.getId(), "팔로우");
    }

    // 언팔로우
    public void unfollow(Long toUserId, Long fromUserId) {

        // 팔로워와 팔로잉을 찾아옴
        User toUser = userRepository.findByIdOrElseThrow(toUserId);
        User fromUser = userRepository.findByIdOrElseThrow(fromUserId);

        // 팔로우 관계를 찾아서 반환
        Follow follow = followRepository.findByToUserAndFromUser(toUser, fromUser)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOLLOW_USER));

        followRepository.delete(follow);
    }

    // 맞팔.. 확인 기능 + 팔로워, 팔로잉 카운트
    public String checkFollowBack(Long toUserId, Long fromUserId) {

        // 팔로워와 팔로잉을 찾아옴
        User toUser = userRepository.findByIdOrElseThrow(toUserId);
        User fromUser = userRepository.findByIdOrElseThrow(fromUserId);

        // 팔로워, 팔로잉 카운트를 가져옴
        int toUserFollowerCount = followRepository.countByToUser(toUser);
        int toUserFollowingCount = followRepository.countByFromUser(toUser);

        if (checkFollow(toUser, fromUser) && checkFollow(fromUser, toUser)) {
            return "맞팔 입니다.   " + "팔로워 : " + toUserFollowerCount + "팔로잉 : " + toUserFollowingCount;
        }

        return "맞팔이 아닙니다.   " +"팔로워 : " + toUserFollowerCount + "팔로잉 : " + toUserFollowingCount;
    }

    // 팔로우인지 확인후 Boolean값 반환
    public boolean checkFollow(User toUser, User fromUser) {
        return followRepository.existsByToUserAndFromUser(toUser, fromUser);
    }
}
