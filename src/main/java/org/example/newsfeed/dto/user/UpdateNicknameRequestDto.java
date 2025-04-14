package org.example.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateNicknameRequestDto {

	@NotBlank
	@Size(min = 2, max = 20)
	private final String nickname;

	public UpdateNicknameRequestDto(String nickname) {
		this.nickname = nickname;
	}
}
