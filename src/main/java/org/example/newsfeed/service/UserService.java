package org.example.newsfeed.service;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.example.newsfeed.dto.user.SignUpRequestDto;
import org.example.newsfeed.dto.user.SignUpResponseDto;
import org.example.newsfeed.dto.user.UserResponseDto;
import org.example.newsfeed.entity.DeletedUser;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.DeletedUserRepository;
import org.example.newsfeed.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final DeletedUserRepository deletedUserRepository;

	public SignUpResponseDto signUp(SignUpRequestDto dto) {
		User user = new User(dto.getNickname(), dto.getEmail(), dto.getPassword(), dto.getBirth());
		User savedUser = userRepository.save(user);
		return new SignUpResponseDto(savedUser.getNickname(), savedUser.getEmail());
	}

	public UserResponseDto findByNickname(String nickname) {
		Optional<User> optionalUser = userRepository.findByNickname(nickname);
		if(optionalUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, nickname + "은 존재하지 않는 회원입니다.");
		}
		User findUser = optionalUser.get();
		return new UserResponseDto(findUser.getNickname(), findUser.getEmail());
	}

	public UserResponseDto findByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if(optionalUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, email + "은 존재하지 않는 회원입니다.");
		}
		User findUser = optionalUser.get();
		return new UserResponseDto(findUser.getNickname(), findUser.getEmail());
	}

	@Transactional
	public void updatePassword(Long id, String oldPassword, String newPassword) {
		User findUser = userRepository.findByIdOrElseThrow(id);
		if(!findUser.getPassword().equals(oldPassword)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
		}
		findUser.updatePassword(newPassword);
	}

	public void delete(Long id, String password) {
		User findUser = userRepository.findByIdOrElseThrow(id);

		String userPassword = findUser.getPassword();

		if (!userPassword.equals(password)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호");
		}

		DeletedUser deletedUser = new DeletedUser(findUser, LocalDateTime.now());
		deletedUserRepository.save(deletedUser);

		userRepository.delete(findUser);
	}
}