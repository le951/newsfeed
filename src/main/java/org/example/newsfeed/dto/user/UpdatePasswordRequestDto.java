package org.example.newsfeed.dto.user;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

	private final String oldPassword;
	private final String newPassword;

		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
}
