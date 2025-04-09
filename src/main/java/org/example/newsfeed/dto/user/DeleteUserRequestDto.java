package org.example.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class DeleteUserRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$", message = "비밀번호는 8 ~ 16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다.")
    private final String password;

    public DeleteUserRequestDto(String password) {
        this.password = password;
    }
}
