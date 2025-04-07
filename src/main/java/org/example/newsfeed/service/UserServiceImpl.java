package org.example.newsfeed.service;

import org.example.newsfeed.dto.user.SignUpRequestDto;
import org.example.newsfeed.dto.user.SignUpResponseDto;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public SignUpResponseDto signUp(SignUpRequestDto dto) {
		User user = new User(dto.getNickname(), dto.getEmail(), dto.getPassword(), dto.getBirth());
		User savedUser = userRepository.save(user);
		return new SignUpResponseDto(savedUser.getNickname(), savedUser.getEmail());
	}

}
