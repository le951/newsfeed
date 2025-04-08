package org.example.newsfeed.service;

import org.example.newsfeed.dto.user.SignUpRequestDto;
import org.example.newsfeed.dto.user.SignUpResponseDto;
import org.example.newsfeed.dto.user.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	SignUpResponseDto signUp(SignUpRequestDto dto);
	UserResponseDto findByNickname(String name);
	UserResponseDto findByEmail(String email);
}
