package org.example.newsfeed.service;

import java.util.Optional;

import org.example.newsfeed.dto.user.SignUpRequestDto;
import org.example.newsfeed.dto.user.SignUpResponseDto;
import org.example.newsfeed.dto.user.UserResponseDto;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

	@Override
	public UserResponseDto findByNickname(String nickname) {
		Optional<User> optionalUser = userRepository.findByNickname(nickname);
		if(optionalUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, nickname + "은 존재하지 않는 회원입니다.");
		}
		User findUser = optionalUser.get();
		return new UserResponseDto(findUser.getNickname(), findUser.getEmail());
	}

	@Override
	public UserResponseDto findByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if(optionalUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, email + "은 존재하지 않는 회원입니다.");
		}
		User findUser = optionalUser.get();
		return new UserResponseDto(findUser.getNickname(), findUser.getEmail());
	}
}
