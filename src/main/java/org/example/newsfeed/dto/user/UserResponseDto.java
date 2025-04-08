package org.example.newsfeed.dto.user;

import lombok.Getter;

@Getter
public class UserResponseDto {

	private final Long id;
	private final String nickname;
	private final String email;

	public UserResponseDto(Long id, String nickname, String email) {
		this.id = id;
		this.nickname = nickname;
		this.email = email;
	}
}
