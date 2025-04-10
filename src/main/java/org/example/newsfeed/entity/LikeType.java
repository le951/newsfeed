package org.example.newsfeed.entity;

import lombok.Getter;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public enum LikeType {
    BOARD("newsfeeds"),
    COMMENT("comments");

    private final String targetLikeType;

    LikeType(String targetLikeType){
        this.targetLikeType = targetLikeType;
    }

    //도메인 타입 String->Enum으로 변환하는 메서드
    //enum에 같은 targetLikeType 값이 있을 경우에만 실행
    //관련 custom예외 후에 추가
    public static LikeType strLikeTypeToEnum(String strLikeType) {
        for (LikeType likeType : values()) {
            if (likeType.targetLikeType.equalsIgnoreCase(strLikeType)) {
                return likeType;
            }
        }
        throw new CustomException(ErrorCode.LIKETYPE_NOT_FOUND);
    }
}
