package org.example.newsfeed.controller;

import lombok.RequiredArgsConstructor;
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
                                                        @RequestParam(required = false) String email) {

        // 로그인한 유저 정보 가져오는 거 추가해야함.

        Long tempId = 1L;

        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        // 자기 자신을 팔로우 불가
        checkNotSelfAction(findUser.getId(), tempId);

        FollowResponseDto follow = followService.follow(findUser.getId(), tempId);

        return new ResponseEntity<>(follow, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<String> unfollowUser(@RequestParam(required = false) String nickname,
                                               @RequestParam(required = false) String email) {

        // 로그인한 유저 정보 가져오는 거 추가해야함.

        Long tempId = 1L;

        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        // 본인 언팔 불가
        checkNotSelfAction(findUser.getId(), tempId);

        followService.unfollow(findUser.getId(), tempId);
        return new ResponseEntity<>("언팔로우", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> followBack(@RequestParam(required = false) String nickname,
                                             @RequestParam(required = false) String email) {

        Long tempId = 1L;

        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        String isFollowBack = followService.checkFollowBack(findUser.getId(), tempId);

        return new ResponseEntity<>(isFollowBack, HttpStatus.OK);
    }

    public UserResponseDto checkEmailOrNickname(String nickname, String email) {

        UserResponseDto findUser;

        if (nickname != null) {
            findUser = userService.findByNickname(nickname);
        } else if (email != null) {
            findUser = userService.findByEmail(email);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return findUser;
    }

    public void checkNotSelfAction(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
