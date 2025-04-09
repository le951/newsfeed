package org.example.newsfeed.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{boardId}/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<String> doLike(
            HttpServletRequest httpServletRequest,
            @PathVariable Long boardId
    ){
        Long userId = (Long)httpServletRequest.getAttribute("userId");
        String likeMessage = likeService.toggleLike(userId,boardId);
        return new ResponseEntity<>(likeMessage, HttpStatus.OK);
    }
}
