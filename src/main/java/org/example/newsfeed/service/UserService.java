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
import org.example.newsfeed.repository.BoardRepository;
import org.example.newsfeed.repository.CommentRepository;
import org.example.newsfeed.repository.DeletedUserRepository;
import org.example.newsfeed.repository.FollowRepository;
import org.example.newsfeed.repository.LikeRepository;
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
	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;
	private final LikeRepository likeRepository;
	private final DeletedUserRepository deletedUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final FollowRepository followRepository;

	// 회원가입
	public SignUpResponseDto signUp(SignUpRequestDto dto) {

		// 비밀번호 암호화
		String encodePassword = passwordEncoder.encode(dto.getPassword());

		// User 엔티티 생성 및 저장
		User user = new User(dto.getNickname(), dto.getEmail(), encodePassword, dto.getBirth());
		User savedUser = userRepository.save(user);
		return new SignUpResponseDto(savedUser.getNickname(), savedUser.getEmail());
	}

	// 로그인: 이메일/비밀번호 확인 후 JWT 토큰 발급
	public String login(String email, String password) {

		User findUser = userRepository.findByEmailOrElseThrow(email);

		// 비밀번호 일치 확인
		if (!passwordEncoder.matches(password, findUser.getPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHED);
		}

		// JWT 토큰 생성 및 반환
		return jwtUtil.generateAccessToken(findUser.getId(), findUser.getNickname(), findUser.getEmail());
	}

	// 닉네임으로 사용자 조회
	public UserResponseDto findByNickname(String nickname) {
		User findUser = userRepository.findByNicknameOrElseThrow(nickname);
		return new UserResponseDto(findUser.getId(), findUser.getNickname(), findUser.getEmail());
	}

	// 이메일로 사용자 조회
	public UserResponseDto findByEmail(String email) {
		User findUser = userRepository.findByEmailOrElseThrow(email);
		return new UserResponseDto(findUser.getId(), findUser.getNickname(), findUser.getEmail());
	}

	// 비밀번호 수정
	@Transactional
	public void updatePassword(Long id, String oldPassword, String newPassword) {
		User findUser = userRepository.findByIdOrElseThrow(id);

		// 기존 비밀번호 검증
		checkedPassword(findUser, oldPassword);

		// 새 비밀번호 암호화 및 업데이트
		String encodeNewPassword = passwordEncoder.encode(newPassword);
		findUser.updatePassword(encodeNewPassword);
	}

	// 닉네임 수정
	public void updateNickname(Long id, String nickname) {
		User findUser = userRepository.findByIdOrElseThrow(id);

		// 동일한 닉네임이면 오류 반환
		if (findUser.getNickname().equals(nickname)) {
			throw new CustomException(ErrorCode.SAME_NICKNAME);
		}

		// 닉네임 중복 여부 확인
		if (userRepository.findByNickname(nickname).isPresent()) {
			throw new CustomException(ErrorCode.NICKNAME_DUPLICATION);
		}

		// 닉네임 업데이트 후 저장
		findUser.updateNickname(nickname);
		userRepository.save(findUser);
	}

	// 회원 탈퇴
	@Transactional
	public void delete(Long id, String password) {

		User findUser = userRepository.findByIdOrElseThrow(id);

		// 비밀번호 검증
		checkedPassword(findUser, password);

		// 탈퇴 사용자 정보 탈퇴테이블에 백업
		DeletedUser deletedUser = new DeletedUser(findUser, LocalDateTime.now());
		deletedUserRepository.save(deletedUser);

		// 좋아요 삭제
		likeRepository.deleteAllByUserId(id);
		// 팔로우 관계 삭제 (내가 팔로우한 사람 + 나를 팔로우한 사람)
		followRepository.deleteAllByFromUserIdOrToUserId(id, id);
		// 게시글 삭제
		boardRepository.deleteAllByUserId(id);
		// 댓글 삭제
		commentRepository.deleteAllByUserId(id);
		// 회원 삭제
		userRepository.delete(findUser);
	}

	// 비밀번호 검증
	public void checkedPassword(User user, String password) {

		String userPassword = user.getPassword();

		// 입력된 비밀번호와 저장된 해시값 비교
		if (!passwordEncoder.matches(password, userPassword)) {
			throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHED);
		}
	}


}