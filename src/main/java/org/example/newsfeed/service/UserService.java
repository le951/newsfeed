package org.example.newsfeed.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.example.newsfeed.common.config.PasswordEncoder;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.common.jwt.JwtUtil;
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
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public SignUpResponseDto signUp(SignUpRequestDto dto) {

		String encodePassword = passwordEncoder.encode(dto.getPassword());

		User user = new User(dto.getNickname(), dto.getEmail(), encodePassword, dto.getBirth());
		User savedUser = userRepository.save(user);
		return new SignUpResponseDto(savedUser.getNickname(), savedUser.getEmail());
	}

	public String login(String email, String password) {
		User findUser = userRepository.findByEmailOrElseThrow(email);
		if (!passwordEncoder.matches(password, findUser.getPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHED);
		}
		return jwtUtil.generateAccessToken(findUser.getId(), findUser.getNickname(), findUser.getEmail());
	}

	public UserResponseDto findByNickname(String nickname) {
		User findUser = userRepository.findByNicknameOrElseThrow(nickname);
		return new UserResponseDto(findUser.getId(), findUser.getNickname(), findUser.getEmail());
	}

	public UserResponseDto findByEmail(String email) {
		User findUser = userRepository.findByEmailOrElseThrow(email);
		return new UserResponseDto(findUser.getId(), findUser.getNickname(), findUser.getEmail());
	}

	@Transactional
	public void updatePassword(Long id, String oldPassword, String newPassword) {
		User findUser = userRepository.findByIdOrElseThrow(id);

		checkedPassword(findUser, oldPassword);

		// 새로운 비밀번호 암호화
		String encodeNewPassword = passwordEncoder.encode(newPassword);

		findUser.updatePassword(encodeNewPassword);
	}

	public void updateNickname(Long id, String nickname) {
		User findUser = userRepository.findByIdOrElseThrow(id);

		// 이름이 동일하면 종료
		if (findUser.getNickname().equals(nickname)) {
			throw new CustomException(ErrorCode.SAME_NICKNAME);
		}

		if (userRepository.findByNickname(nickname).isPresent()) {
			throw new CustomException(ErrorCode.NICKNAME_DUPLICATION);
		}

		findUser.updateNickname(nickname);
		userRepository.save(findUser);
	}

	public void delete(Long id, String password) {
		User findUser = userRepository.findByIdOrElseThrow(id);

		checkedPassword(findUser, password);

		DeletedUser deletedUser = new DeletedUser(findUser, LocalDateTime.now());
		deletedUserRepository.save(deletedUser);

		userRepository.delete(findUser);
	}

	// 비밀번호 검증
	public void checkedPassword(User user, String password) {

		String userPassword = user.getPassword();

		if (!passwordEncoder.matches(password, userPassword)) {
			throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHED);
		}
	}


}