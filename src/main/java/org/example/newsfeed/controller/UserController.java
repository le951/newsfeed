package org.example.newsfeed.controller;

import org.example.newsfeed.dto.user.SignUpRequestDto;
import org.example.newsfeed.dto.user.SignUpResponseDto;
import org.example.newsfeed.dto.user.UpdateUserRequestDto;
import org.example.newsfeed.dto.user.UserResponseDto;
import org.example.newsfeed.dto.user.UpdatePasswordRequestDto;
import org.example.newsfeed.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	private ResponseEntity<SignUpResponseDto> createUser(@RequestBody SignUpRequestDto dto) {
		return new ResponseEntity<>(userService.signUp(dto), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<UserResponseDto> findUser(
		@RequestParam(required = false) String nickname, // required = false -> 해당 파라미터가 없어도 에가 발생하지 않음
		@RequestParam(required = false) String email
	) {
		if (nickname != null) {
			return new ResponseEntity<>(userService.findByNickname(nickname), HttpStatus.OK);
		} else if (email != null) {
			return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordRequestDto dto) {
		userService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
