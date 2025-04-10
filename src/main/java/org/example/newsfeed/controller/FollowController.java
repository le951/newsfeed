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

    // 팔로우
    @PostMapping
    public ResponseEntity<FollowResponseDto> followUser(@Valid @RequestBody UserRequestDto requestDto, HttpServletRequest servletRequest) {

        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        // jwt 토큰에서 userId 정보를 가져옴
        Long userId = (Long) servletRequest.getAttribute("userId");

        // userId가 NULL 값이 아닌지 확인
        checkedLogin(userId);

        // 이메일과 닉네임으로 상대방(팔로우 받는 사람)의 유저 정보를 가져옴
        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        // 자기 자신을 팔로우 불가
        checkNotSelfAction(findUser.getId(), userId);

        // 팔로우
        FollowResponseDto follow = followService.follow(findUser.getId(), userId);

        return new ResponseEntity<>(follow, HttpStatus.OK);

    }

    // 언팔로우
    @DeleteMapping
    public ResponseEntity<String> unfollowUser(@Valid @RequestBody UserRequestDto requestDto, HttpServletRequest servletRequest) {

        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        // jwt 토큰에서 userId 정보를 가져옴
        Long userId = (Long) servletRequest.getAttribute("userId");

        // userId가 NULL 값이 아닌지 확인
        checkedLogin(userId);

        // 이메일과 닉네임으로 상대방(언팔로우 받는 사람)의 유저 정보를 가져옴
        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        // 본인 언팔 불가
        checkNotSelfAction(findUser.getId(), userId);

        followService.unfollow(findUser.getId(), userId);
        return new ResponseEntity<>("언팔로우", HttpStatus.OK);
    }

    // 일단 맞팔을 확인하고 팔로워와 팔로윙을 하는 사람의 수를 체크하는 기능..
    @GetMapping
    public ResponseEntity<String> followBack(@Valid @RequestBody UserRequestDto requestDto, HttpServletRequest servletRequest) {

        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        // jwt 토큰에서 userId 정보를 가져옴
        Long userId = (Long) servletRequest.getAttribute("userId");

        // userId가 NULL 값이 아닌지 확인
        checkedLogin(userId);

        // 이메일과 닉네임으로 상대방(언팔로우 받는 사람)의 유저 정보를 가져옴
        UserResponseDto findUser = checkEmailOrNickname(nickname, email);

        String isFollowBack = followService.checkFollowBack(findUser.getId(), userId);

        return new ResponseEntity<>(isFollowBack, HttpStatus.OK);
    }

    // 닉네임과 이메일 값중 존재하는 값으로 유저를 조회후 반환
    public UserResponseDto checkEmailOrNickname(String nickname, String email) {
        if (email != null) {
            return userService.findByEmail(email);
        } else if (nickname != null) {
            return userService.findByNickname(nickname);
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    // 본인 스스로에게 액션(팔로우, 언팔로우)하면 예외처리
    public void checkNotSelfAction(Long toUserId, Long fromUserId) {
        if (toUserId.equals(fromUserId)) {
            throw new CustomException(ErrorCode.ACTION_SELF_ACCOUNT);
        }
    }

    // 현재 userId값이 Null 값인지 확인후 Null 값이면 예외 처리
    public void checkedLogin(Long userId) {
        if (userId == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
