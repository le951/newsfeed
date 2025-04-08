package org.example.newsfeed.dto.user;

import lombok.Getter;

@Getter
public class UserResponseDto {

	private final String nickname;
	private final String email;

	public UserResponseDto(String nickname, String email) {
		this.nickname = nickname;
		this.email = email;
	}
}
