package org.example.newsfeed.dto.user;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

	private final String oldNickname;
	private final String newNickname;
	private final String oldPassword;
	private final String newPassword;

	public UpdateUserRequestDto(String oldNickname, String newNickname, String oldPassword, String newPassword) {
		this.oldNickname = oldNickname;
		this.newNickname = newNickname;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
}
