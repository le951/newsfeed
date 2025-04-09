package org.example.newsfeed.controller;

import org.example.newsfeed.dto.user.DeleteUserRequestDto;
import org.example.newsfeed.dto.user.FindUserRequestDto;
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

	@PostMapping("/signup")
	public ResponseEntity<SignUpResponseDto> createUser(@Valid @RequestBody SignUpRequestDto dto) {
		return new ResponseEntity<>(userService.signUp(dto), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequestDto dto) {
		String jwt = userService.login(dto.getEmail(), dto.getPassword());
		return new ResponseEntity<>(jwt, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<UserResponseDto> findUser(@Valid @RequestBody FindUserRequestDto dto) {
		if (dto.getNickname() != null) {
			return new ResponseEntity<>(userService.findByNickname(dto.getNickname()), HttpStatus.OK);
		} else if (dto.getEmail() != null) {
			return new ResponseEntity<>(userService.findByEmail(dto.getEmail()), HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PatchMapping("/password")
	public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequestDto dto, HttpServletRequest servletRequest) {
		Long userId = (Long) servletRequest.getAttribute("userId");
		if (userId == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		userService.updatePassword(userId, dto.getOldPassword(), dto.getNewPassword());
		return new ResponseEntity<>("비밀번호 변경완료", HttpStatus.OK);
	}

	@PatchMapping("/nickname")
	public ResponseEntity<String> updateNickname(@Valid @RequestBody UpdateNicknameRequestDto dto, HttpServletRequest servletRequest) {
		Long userId = (Long) servletRequest.getAttribute("userId");
		if (userId == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		userService.updateNickname(userId, dto.getNickname());
		return new ResponseEntity<>("닉네임 변경완료", HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<String> deleteUser(@RequestBody DeleteUserRequestDto dto, HttpServletRequest servletRequest) {
		Long userId = (Long) servletRequest.getAttribute("userId");
		if (userId == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		userService.delete(userId, dto.getPassword());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
