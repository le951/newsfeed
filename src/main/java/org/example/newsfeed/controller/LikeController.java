package org.example.newsfeed.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.entity.LikeType;
import org.example.newsfeed.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{targetType}/{targetId}/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;


    @PostMapping
    public ResponseEntity<String> toggleLike(
            HttpServletRequest httpServletRequest,
            @PathVariable Long targetId,
            @PathVariable String targetType
            ){
        Long userId = (Long)httpServletRequest.getAttribute("userId");
        //도메인 타입 String->Enum으로 변환
        LikeType likeType = LikeType.strLikeTypeToEnum(targetType);
        String likeMessage = likeService.toggleLike(userId,targetId,likeType);
        return new ResponseEntity<>(likeMessage, HttpStatus.OK);
    }
}
