package org.example.newsfeed.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // common
    INVALID_INPUT_VALUE(400, "Bad Request", "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed", "C002", "Method Not Allowed"),
    ENTITY_NOT_FOUND(400, "Bad Request", "C003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "Server Error", "C004", "Internal Server Error"),
    INVALID_TYPE_VALUE(400, "Bad Request", "C005", "Invalid Type Value"),

    // User
    EMAIL_DUPLICATION(400, "Bad Request", "U001", "Email is Duplicated"),
    NICKNAME_DUPLICATION(400, "Bad Request", "U002", "NickName is Duplicated"),
    USER_NOT_FOUND(404, "Not Found", "U003", "User Not Found"),
    EMPTY_EMAIL_OR_NICKNAME(400, "Bad Request", "U004", "Email or Nickname is Empty"),
    PASSWORD_NOT_MATCHED(400, "Bad Request", "U005", "Password do not match"),
    SAME_NICKNAME(400, "Bad Request", "U006", "Cannot update to the same nickname."),
    UNAUTHORIZED_USER(401, "Unauthorized", "U007", "Login is required."),

    // Follow
    ACTION_SELF_ACCOUNT(400, "Bad Request", "F001", "Can't Self Action"),
    ALREADY_FOLLOW(400, "Bad Request", "F002", "Already Followed User"),
    NOT_FOLLOW_USER(400, "Bad Request", "F003", "Not Follow User"),

    //board
    BOARD_NOT_FOUND(404, "Not Found", "B001", "Board Not Found"),

    //comment
    COMMENT_NOT_FOUND(404,"Not Found","CM001","Can Not Found Comment"),
    NOT_ALLOWED_USER(403,"Forbidden","CM002","Not Allowed User"),
    COMMENT_UPDATE_FORBIDDEN(403,"Forbidden","CM003","Only Comment Author Can Update Comment"),
    COMMENT_DELETE_FORBIDDEN(403,"Forbidden","CM003","Only Comment Author And NewsFeed Author Can Update Comment"),

    //like
    LIKETYPE_NOT_FOUND(404,"Not Found","L001","Not found Like Type"),
    ALREADY_LIKED(400, "Bad Request", "L002", "Already like it"),
    NOT_LIKED(400, "Bad Request", "L003", "Not Liked");


    private final int status;
    private final String error;
    private final String code;
    private final String message;

}
