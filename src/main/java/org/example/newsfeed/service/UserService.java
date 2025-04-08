package org.example.newsfeed.service;

import jakarta.validation.constraints.NotBlank;
import org.example.newsfeed.dto.user.SignUpRequestDto;
import org.example.newsfeed.dto.user.SignUpResponseDto;
import org.example.newsfeed.dto.user.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    SignUpResponseDto signUp(SignUpRequestDto dto);
    UserResponseDto findByNickname(String nickname);
    UserResponseDto findByEmail(String email);

    void delete(Long id, @NotBlank(message = "비밀번호를 입력해주세요.") String password);
}