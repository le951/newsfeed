package org.example.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.entity.Board;
import org.example.newsfeed.entity.Like;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.BoardRepository;
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
    private final BoardRepository boardRepository;

    public String toggleLike(Long userId,Long boardId){
        User user = userRepository.findByIdOrElseThrow(userId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));

        Optional<Like> optionalLike = likeRepository.findByUserIdAndBoardId(userId, boardId);

        //이미 해당 게시글에 좋아요를 했을 경우 좋아요 취소
        if(optionalLike.isEmpty()){
            Like like = new Like(user,board);
            likeRepository.save(like);
            return "좋아요";
        }else{
            likeRepository.delete(optionalLike.get());
            return "좋아요 취소";
        }


    }
}
