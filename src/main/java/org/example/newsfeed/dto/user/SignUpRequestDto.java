package org.example.newsfeed.dto.user;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

	private final String nickname;
	@NotBlank
	@Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$", message = "이메일 형식이 아닙니다.")
	private final String email;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$", message = "비밀번호는 8 ~ 16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다.")
	private final String password;
	@Past
	private final LocalDate birth;

	public SignUpRequestDto(String nickname, String email, String password, LocalDate birth) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.birth = birth;
	}
}
