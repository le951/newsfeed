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
	Optional<User> findByNickname(String nickname);
	Optional<User> findByEmail(String email);

	default User findByEmailOrElseThrow(String email) {
		return findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	default User findByNicknameOrElseThrow(String nickname) {
		return findByNickname(nickname).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	default User findByIdOrElseThrow(Long id) {
		return findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}
}
