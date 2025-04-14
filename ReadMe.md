![header](https://capsule-render.vercel.app/api?type=waving&color=auto&height=250&section=header&text=NewsFeed%20Project&fontSize=80)

## 개요
- Srping Boot, Spring Data JPA와 MySQL을 사용한 뉴스 피드 제작

## 개발 환경
언어 : ![Static Badge](https://img.shields.io/badge/Java-red?style=flat-square)  

JDK : ![Static Badge](https://img.shields.io/badge/JDK-17-yellow?style=flat-square)  

프레임워크 : ![Static Badge](https://img.shields.io/badge/SpringBoot-%23FFFF00?logo=springboot)  

DB : ![Static Badge](https://img.shields.io/badge/MySql-%23FFFFFF?style=flat&logo=mysql)  

ORM : ![Static Badge](https://img.shields.io/badge/JPA-FFA500?style=flat)  

## 🔠 목차

1. [API 명세서](#-api-명세서)
2. [ERD](#-erd)
3. [기능 요약](#-기능-요약)
4. [디렉토리 구조](#-디렉토리-구조)

# API 명세서
![API2.png](img/API2.png)

# ERD
![erd.png](img/erd.png)

# 기능 요약
- 유저 CRUD
- 뉴스피드 CRUD
- 댓글 CRUD
- 팔로우 CRUD
- 좋아요 CRUD
- JWT 를 이용한 로그인

# 디렉토리 구조
```
/newsfeed
├── common
│       ├── config
│       │       └── PasswordEncoder
│       ├── exception
│       │       ├── CustomException
│       │       ├── ErrorCode
│       │       ├── ErrorResponse
│       │       ├── GlobalExceptionHandler
│       │       └── UserNotFoundException
│       ├── filter
│       │       └── JwtAuthFilter
│       └── jwt
│             ├── JwtProperties
│             └── JwtUtil
├── controller
│       ├── BoardController
│       ├── CommentController
│       ├── FollowController
│       ├── LikeController
│       └── UserController
├── dto
│     ├── board
│     │     ├── BoardDetailResponseDto
│     │     ├── BoardListDto
│     │     ├── BoardRequestDto
│     │     └── BoardResponseDto
│     ├── comment
│     │       ├── CommentRequestDto
│     │       └── CommentResponseDto
│     ├── follow
│     │       └── FollowResponseDto
│     ├── like
│     │     └── LikeRequestDto
│     └── user
│           ├── DeleteUserRequestDto
│           ├── LoginRequestDto
│           ├── LikeRequestDto
│           ├── SignUpResponseDto
│           ├── UpdateNicknameRequestDto
│           ├── UpdatePasswordRequestDto
│           ├── UserRequestDto
│           └── UserResponseDto
├── entity
│     ├── BaseEntity
│     ├── Board
│     ├── Comment
│     ├── DeletedUser
│     ├── Follow
│     ├── Like
│     ├── LikeType
│     └── User
├── repository
│       ├── BoardRepository
│       ├── CommentRepository
│       ├── DeletedUserRepository
│       ├── FollowRepository
│       ├── LikeRepository
│       └── UserRepository
├── service
│       ├── BoardService
│       ├── CommentService
│       ├── FollowService
│       ├── LikeService
│       └── UserService
└── NewsfeedApplication

```