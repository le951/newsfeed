package org.example.newsfeed.dto.user;

import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

	private final String oldPassword;
	private final String newPassword;

	public UpdatePasswordRequestDto(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
}
