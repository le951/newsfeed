package org.example.newsfeed.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.dto.follow.FollowResponseDto;
import org.example.newsfeed.dto.user.UserResponseDto;
import org.example.newsfeed.service.FollowService;
import org.example.newsfeed.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

    private final UserService userService;
    private final FollowService followService;

    @PostMapping
    public ResponseEntity<FollowResponseDto> followUser(@RequestParam(required = false) String nickname,
                                                        @RequestParam(required = false) String email,
                                                        HttpServletRequest servletRequest) {

        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        // 자기 자신을 팔로우 불가
        checkNotSelfAction(findUser.getId(), userId);

        FollowResponseDto follow = followService.follow(findUser.getId(), userId);

        return new ResponseEntity<>(follow, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<String> unfollowUser(@RequestParam(required = false) String nickname,
                                               @RequestParam(required = false) String email,
                                               HttpServletRequest servletRequest) {

        Long userId = (Long) servletRequest.getAttribute("userId");

        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        // 본인 언팔 불가
        checkNotSelfAction(findUser.getId(), userId);

        followService.unfollow(findUser.getId(), userId);
        return new ResponseEntity<>("언팔로우", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> followBack(@RequestParam(required = false) String nickname,
                                             @RequestParam(required = false) String email,
                                             HttpServletRequest servletRequest) {

        Long userId = (Long) servletRequest.getAttribute("userId");

        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        String isFollowBack = followService.checkFollowBack(findUser.getId(), userId);

        return new ResponseEntity<>(isFollowBack, HttpStatus.OK);
    }

    public UserResponseDto checkEmailOrNickname(String nickname, String email) {

        UserResponseDto findUser;

        if (nickname != null) {
            findUser = userService.findByNickname(nickname);
        } else if (email != null) {
            findUser = userService.findByEmail(email);
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return findUser;
    }

    public void checkNotSelfAction(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new CustomException(ErrorCode.ACTION_SELF_ACCOUNT);
        }
    }
}
