package org.example.newsfeed.dto.user;

import lombok.Getter;

@Getter
public class UpdateNicknameRequestDto {

	private final String nickname;

	public UpdateNicknameRequestDto(String nickname) {
		this.nickname = nickname;
	}
}
