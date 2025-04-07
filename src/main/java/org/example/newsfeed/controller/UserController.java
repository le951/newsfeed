package org.example.newsfeed.controller;

import org.example.newsfeed.dto.user.SignUpRequestDto;
import org.example.newsfeed.dto.user.SignUpResponseDto;
import org.example.newsfeed.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
