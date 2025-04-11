package org.example.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.dto.like.LikeResponseDto;
import org.example.newsfeed.entity.Like;
import org.example.newsfeed.entity.LikeType;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.LikeRepository;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    public LikeResponseDto doLike(Long userId, Long targetId, LikeType targetLikeType){

        User user = userRepository.findByIdOrElseThrow(userId);
        //유저 id,타겟(게시글,댓글)id,타입으로 조회
        Optional<Like> optionalLike = likeRepository.findByUserIdAndTargetIdAndLikeTargetType(userId, targetId, targetLikeType);
        //해당 게시글에 이미 좋아요를 했다면 에러 출력
        if(optionalLike.isEmpty()){
            Like like = new Like(user,targetId,targetLikeType);
            likeRepository.save(like);
            return new LikeResponseDto(targetId,targetLikeType, "좋아요");
        } else throw new CustomException(ErrorCode.ALREADY_LIKED);
    }
    public LikeResponseDto unLike(Long userId, Long targetId, LikeType targetLikeType){

        User user = userRepository.findByIdOrElseThrow(userId);
        //유저 id,타겟(게시글,댓글)id,타입으로 조회
        Optional<Like> optionalLike = likeRepository.findByUserIdAndTargetIdAndLikeTargetType(userId, targetId, targetLikeType);
        //해당 게시글에 좋아요를 하지 않았다면 에러 출력
        if(optionalLike.isPresent()){
            likeRepository.delete(optionalLike.get());
            return new LikeResponseDto(targetId,targetLikeType, "좋아요 취소");
        }else throw new CustomException(ErrorCode.NOT_LIKED);
    }
}
