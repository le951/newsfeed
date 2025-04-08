package org.example.newsfeed.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.dto.follow.FollowResponseDto;
import org.example.newsfeed.dto.user.UserResponseDto;
import org.example.newsfeed.service.FollowService;
import org.example.newsfeed.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        UserResponseDto findUser;

        if (nickname != null) {
            findUser = userService.findByNickname(nickname);
        } else if (email != null) {
            findUser = userService.findByEmail(email);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 자기 자신을 팔로우 불가
        if (findUser.getId().equals(tempId)) {
            throw new IllegalStateException("이미 팔로우한 사용자");
        }

        FollowResponseDto follow = followService.follow(findUser.getId(), tempId);

        return new ResponseEntity<>(follow, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<String> unfollowUser(@RequestParam(required = false) String nickname,
                                               @RequestParam(required = false) String email) {

        // 로그인한 유저 정보 가져오는 거 추가해야함.

        Long tempId = 1L;

        UserResponseDto findUser;

        if (nickname != null) {
            findUser = userService.findByNickname(nickname);
        } else if (email != null) {
            findUser = userService.findByEmail(email);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (findUser.getId().equals(tempId)) {
            throw new IllegalStateException("본인 언팔로우 금지");
        }

        followService.unfollow(findUser.getId(), tempId);
        return new ResponseEntity<>("언팔로우", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> followBack(@RequestParam(required = false) String nickname,
                                             @RequestParam(required = false) String email) {

        Long tempId = 1L;

        UserResponseDto findUser;

        if (nickname != null) {
            findUser = userService.findByNickname(nickname);
        } else if (email != null) {
            findUser = userService.findByEmail(email);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String isFollowBack = followService.checkFollowBack(findUser.getId(), tempId);

        return new ResponseEntity<>(isFollowBack, HttpStatus.OK);
    }
}
