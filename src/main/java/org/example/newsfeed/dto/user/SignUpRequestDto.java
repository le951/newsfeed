package org.example.newsfeed.dto.user;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class SignUpRequestDto {

	private final String nickname;
	private final String email;
	private final String password;
	private final LocalDate birth;

	public SignUpRequestDto(String nickname, String email, String password, LocalDate birth) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.birth = birth;
	}
}
