package org.example.newsfeed.controller;

import org.example.newsfeed.dto.user.DeleteUserRequestDto;
import org.example.newsfeed.dto.user.SignUpRequestDto;
import org.example.newsfeed.dto.user.SignUpResponseDto;
import org.example.newsfeed.dto.user.UserResponseDto;
import org.example.newsfeed.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@DeleteMapping("/{id}")
	private ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody DeleteUserRequestDto dto) {

		userService.delete(id, dto.getPassword());

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
