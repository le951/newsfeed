package org.example.newsfeed.test.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.example.newsfeed.common.jwt.JwtProperties;
import org.example.newsfeed.common.config.PasswordEncoder;
import org.example.newsfeed.common.jwt.JwtUtil;
import org.example.newsfeed.entity.User;
import org.example.newsfeed.repository.UserRepository;
import org.example.newsfeed.test.service.AuthTestService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Profile("dev")
@RestController
@RequestMapping("/test-auth")
public class AuthTestController {

    private final AuthTestService authTestService;

    private final UserRepository userRepository;

    private final JwtProperties jwtProperties;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder = new PasswordEncoder();
    AuthTestController(AuthTestService authTestService, UserRepository userRepository, JwtProperties jwtProperties, JwtUtil jwtUtil){
        this.authTestService = authTestService;
        this.userRepository = userRepository;
        this.jwtProperties = jwtProperties;
        this.jwtUtil = jwtUtil;
    }



    @Profile("dev")
    @Transactional
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(
            @RequestBody Map<String, Object> request
            ){

        String nickname = (String) request.get("nickname");
        String password = (String) request.get("password");
        String email = (String) request.get("email");
        LocalDate birth;
        try {
            birth = LocalDate.parse((String)request.get("birth"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Map.of("message", e.getMessage())
            );
        }

        if(nickname.isBlank() || password.isBlank() || email.isBlank())
            return ResponseEntity.badRequest().body("Some Body is Blank.");

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(nickname, email, encodedPassword, birth);

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Profile("dev")
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody Map<String, Object> request
    ){

        String password = (String) request.get("password");
        String email = (String) request.get("email");

        if(password.isBlank() || email.isBlank())
            return ResponseEntity.badRequest().body("Some Body is Blank.");

        User user = userRepository.findByEmail(email);

        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found by email");

        if(!passwordEncoder.matches(password, user.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password not same");

        // Login 성공 시

        String token = jwtUtil.generateAccessToken(user.getId(), user.getNickname(), user.getEmail());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @Profile("dev")
    @Transactional
    @GetMapping("/JwtValidationQuery")
    public ResponseEntity<?> jwtValidation(
            @RequestParam String token
    ){

        return ResponseEntity.status(HttpStatus.OK).body(jwtUtil.validateAccessToken(token));
    }

    @Profile("dev")
    @Transactional
    @GetMapping("/JwtValidationHeader")
    public ResponseEntity<?> jwtValidation(
            HttpServletRequest request
    ){
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token Not Found");

        String token = authHeader.substring(7);

        return ResponseEntity.status(HttpStatus.OK).body(jwtUtil.validateAccessToken(token));
    }



    @Profile("dev")
    @Transactional
    @GetMapping("/JwtProperties")
    public ResponseEntity<?> jwtProperties(){

        return ResponseEntity.status(HttpStatus.OK).body(jwtProperties.getSecretKey());
    }

    @Profile("dev")
    @Transactional
    @GetMapping("/JwtFilterTest")
    public ResponseEntity<?> jwtFilterTest(
            HttpServletRequest request
    ){

        if(request.getAttribute("userId")==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token NotFound. (expired, validation error, not entered)");
        }

        StringBuilder st = new StringBuilder();

        st.append(request.getAttribute("userId")).append("\n")
                .append(request.getAttribute("email")).append("\n")
                .append(request.getAttribute("nickname"));


        return ResponseEntity.status(HttpStatus.OK).body(st);
    }


}
