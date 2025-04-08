package org.example.newsfeed.test.controller;


import jakarta.servlet.http.HttpSession;
import org.example.newsfeed.common.config.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder = new PasswordEncoder();
    AuthTestController(AuthTestService authTestService, UserRepository userRepository){
        this.authTestService = authTestService;
        this.userRepository = userRepository;
    }



    @Profile("dev")
    @Transactional
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(
            @RequestBody Map<String, Object> request,
            HttpSession session
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
    public ResponseEntity<?> login(
            @RequestBody Map<String, Object> request,
            HttpSession session
    ){

        String password = (String) request.get("password");
        String email = (String) request.get("email");

        if(password.isBlank() || email.isBlank())
            return ResponseEntity.badRequest().body("Some Body is Blank.");

        User user = userRepository.findByEmail(email);

        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found by email");

        // Login 성공 가정.
        // Service 에서 (Long id) 만 반환하여 Session 에 등록.
        session.setAttribute("userId", user.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
