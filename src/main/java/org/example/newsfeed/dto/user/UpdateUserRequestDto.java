package org.example.newsfeed.dto.user;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

	private final String nickname;
	private final String oldPassword;
	private final String newPassword;

	public UpdateUserRequestDto(String nickname, String oldPassword, String newPassword) {
		this.nickname = nickname;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
}
