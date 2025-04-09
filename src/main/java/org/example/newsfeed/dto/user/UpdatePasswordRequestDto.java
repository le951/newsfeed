package org.example.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$", message = "비밀번호는 8 ~ 16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다.")
	private final String oldPassword;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$", message = "비밀번호는 8 ~ 16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다.")
	private final String newPassword;

	public UpdatePasswordRequestDto(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
}
