package org.example.newsfeed.controller;

import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.dto.user.DeleteUserRequestDto;
import org.example.newsfeed.dto.user.UserRequestDto;
import org.example.newsfeed.dto.user.LoginRequestDto;
import org.example.newsfeed.dto.user.SignUpRequestDto;
import org.example.newsfeed.dto.user.SignUpResponseDto;
import org.example.newsfeed.dto.user.UpdateNicknameRequestDto;
import org.example.newsfeed.dto.user.UserResponseDto;
import org.example.newsfeed.dto.user.UpdatePasswordRequestDto;
import org.example.newsfeed.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<SignUpResponseDto> createUser(@Valid @RequestBody SignUpRequestDto dto) {
		return new ResponseEntity<>(userService.signUp(dto), HttpStatus.CREATED);
	}

	// 로그인 (JWT 토큰 반환)
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequestDto dto) {
		String jwt = userService.login(dto.getEmail(), dto.getPassword());
		return new ResponseEntity<>(jwt, HttpStatus.OK);
	}

	// 사용자 조회 (이메일 또는 닉네임으로 검색)
	@GetMapping
	public ResponseEntity<UserResponseDto> findUser(@Valid @RequestBody UserRequestDto dto) {
		if (dto.getEmail() != null) {
			return new ResponseEntity<>(userService.findByEmail(dto.getEmail()), HttpStatus.OK);
		} else if (dto.getNickname() != null) {
			return new ResponseEntity<>(userService.findByNickname(dto.getNickname()), HttpStatus.OK);
		} else {
			throw new CustomException(ErrorCode.EMPTY_EMAIL_OR_NICKNAME);
		}
	}

	// 비밀번호 변경 (JWT 인증 필요)
	@PatchMapping("/password")
	public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequestDto dto, HttpServletRequest servletRequest) {
		Long userId = (Long) servletRequest.getAttribute("userId");
		checkedLogin(userId);
		userService.updatePassword(userId, dto.getOldPassword(), dto.getNewPassword());
		return new ResponseEntity<>("비밀번호 변경완료", HttpStatus.OK);
	}

	// 닉네임 변경 (JWT 인증 필요)
	@PatchMapping("/nickname")
	public ResponseEntity<String> updateNickname(@Valid @RequestBody UpdateNicknameRequestDto dto, HttpServletRequest servletRequest) {
		Long userId = (Long) servletRequest.getAttribute("userId");
		checkedLogin(userId);
		userService.updateNickname(userId, dto.getNickname());
		return new ResponseEntity<>("닉네임 변경완료", HttpStatus.OK);
	}

	// 회원 탈퇴 (JWT 인증 필요)
	@DeleteMapping
	public ResponseEntity<String> deleteUser(@RequestBody DeleteUserRequestDto dto, HttpServletRequest servletRequest) {
		Long userId = (Long) servletRequest.getAttribute("userId");
		checkedLogin(userId);
		userService.delete(userId, dto.getPassword());
		return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
	}

	// 유저 존재 검증
	public void checkedLogin(Long userId) {
		if (userId == null) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
	}
}
