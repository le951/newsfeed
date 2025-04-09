package org.example.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateNicknameRequestDto {

	@NotBlank
	private final String nickname;

	public UpdateNicknameRequestDto(String nickname) {
		this.nickname = nickname;
	}
}
