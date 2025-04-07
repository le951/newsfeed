package org.example.newsfeed.dto.user;

import lombok.Getter;

@Getter
public class SignUpResponseDto {

	private final String nickname;
	private final String email;

	public SignUpResponseDto(String nickname, String email) {
		this.nickname = nickname;
		this.email = email;
	}
}
