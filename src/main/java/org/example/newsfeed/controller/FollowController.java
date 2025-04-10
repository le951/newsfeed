package org.example.newsfeed.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.dto.follow.FollowResponseDto;
import org.example.newsfeed.dto.user.UserRequestDto;
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
    public ResponseEntity<FollowResponseDto> followUser(@Valid @RequestBody UserRequestDto requestDto, HttpServletRequest servletRequest) {

        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

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
    public ResponseEntity<String> unfollowUser(@Valid @RequestBody UserRequestDto requestDto, HttpServletRequest servletRequest) {

        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        Long userId = (Long) servletRequest.getAttribute("userId");

        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        // 본인 언팔 불가
        checkNotSelfAction(findUser.getId(), userId);

        followService.unfollow(findUser.getId(), userId);
        return new ResponseEntity<>("언팔로우", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> followBack(@Valid @RequestBody UserRequestDto requestDto, HttpServletRequest servletRequest) {

        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        Long userId = (Long) servletRequest.getAttribute("userId");

        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        String isFollowBack = followService.checkFollowBack(findUser.getId(), userId);

        return new ResponseEntity<>(isFollowBack, HttpStatus.OK);
    }

    public UserResponseDto checkEmailOrNickname(String nickname, String email) {
        if (email != null) {
            return userService.findByEmail(email);
        } else if (nickname != null) {
            return userService.findByNickname(nickname);
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public void checkNotSelfAction(Long toUserId, Long fromUserId) {
        if (toUserId.equals(fromUserId)) {
            throw new CustomException(ErrorCode.ACTION_SELF_ACCOUNT);
        }
    }
}
