package org.example.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class FindUserRequestDto {

	private final String nickname;

	@Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$", message = "이메일 형식이 아닙니다.")
	private final String email;

	public FindUserRequestDto(String nickname, String email) {
		this.nickname = nickname;
		this.email = email;
	}
}
