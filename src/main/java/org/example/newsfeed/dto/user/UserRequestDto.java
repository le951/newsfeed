package org.example.newsfeed.dto.user;

import lombok.Getter;

@Getter
public class UserRequestDto {

	private final Long id;

	public UserRequestDto(Long id) {
		this.id = id;
	}
}
