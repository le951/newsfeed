package org.example.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.entity.Like;
import org.example.newsfeed.entity.LikeType;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.LikeRepository;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    public String toggleLike(Long userId, Long targetId, LikeType targetLikeType){

        User user = userRepository.findByIdOrElseThrow(userId);
        //유저 id,타겟(게시글,댓글)id,타입으로 조회
        Optional<Like> optionalLike = likeRepository.findByUserIdAndTargetIdAndLikeTargetType(userId, targetId, targetLikeType);

        //이미 해당 게시글에 좋아요를 했을 경우 좋아요 취소
        if(optionalLike.isEmpty()){
            Like like = new Like(user,targetId,targetLikeType);
            likeRepository.save(like);
            return "좋아요";
        }else{
            likeRepository.delete(optionalLike.get());
            return "좋아요 취소";
        }
    }
}
