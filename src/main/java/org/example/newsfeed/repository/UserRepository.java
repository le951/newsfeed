package org.example.newsfeed.repository;

import java.util.Optional;

import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

public interface UserRepository extends JpaRepository<User, Long> {

	// 닉네임으로 사용자 검색 (Optional로 반환)
	Optional<User> findByNickname(String nickname);

	// 이메일로 사용자 검색 (Optional로 반환)
	Optional<User> findByEmail(String email);

	// 이메일로 사용자 검색, 없으면 예외 발생
	default User findByEmailOrElseThrow(String email) {
		return findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	// 닉네임으로 사용자 검색, 없으면 예외 발생
	default User findByNicknameOrElseThrow(String nickname) {
		return findByNickname(nickname).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	// ID로 사용자 검색, 없으면 예외 발생
	default User findByIdOrElseThrow(Long id) {
		return findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}
}
