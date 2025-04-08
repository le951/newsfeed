package org.example.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteUserRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    public DeleteUserRequestDto(String password) {
        this.password = password;
    }
}
