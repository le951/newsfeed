package org.example.newsfeed.service;

import org.example.newsfeed.dto.user.SignUpRequestDto;
import org.example.newsfeed.dto.user.SignUpResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	SignUpResponseDto signup(SignUpRequestDto dto);
}
