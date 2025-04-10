package org.example.newsfeed.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.dto.like.LikeRequestDto;
import org.example.newsfeed.entity.LikeType;
import org.example.newsfeed.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;


    @PostMapping
    public ResponseEntity<String> doLike(
            HttpServletRequest httpServletRequest,
            @RequestBody LikeRequestDto requestDto
            ){
        Long userId = (Long)httpServletRequest.getAttribute("userId");
        //도메인 타입 String->Enum으로 변환
        LikeType likeType = LikeType.strLikeTypeToEnum(requestDto.getTargetType());
        String likeMessage = likeService.doLike(userId, requestDto.getTargetId(), likeType);
        return new ResponseEntity<>(likeMessage, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> unLike(
            HttpServletRequest httpServletRequest,
            @RequestBody LikeRequestDto requestDto
            ){
        Long userId = (Long)httpServletRequest.getAttribute("userId");
        //도메인 타입 String->Enum으로 변환
        LikeType likeType = LikeType.strLikeTypeToEnum(requestDto.getTargetType());
        String likeMessage = likeService.unLike(userId, requestDto.getTargetId(), likeType);
        return new ResponseEntity<>(likeMessage, HttpStatus.OK);

    }
}
