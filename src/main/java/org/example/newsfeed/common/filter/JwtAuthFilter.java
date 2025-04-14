package org.example.newsfeed.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed.common.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


// 고려사항
// 만료, AccessToken, RefreshToken, 인증 성공, 인증 실패

// 모든 경로에 대해 Auth 진행.
// 인증 정보를 해석하여 request에 저장
// 필요한 경우 Controller / Service 에서 400번대 Response 처리
// request.getAttribute("userInfo") == null
//  => 로그인 되어있지 않음.
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    JwtAuthFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");


        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        Map<String, Object> claims = jwtUtil.validateAccessToken(token);

        if(claims.isEmpty())
            filterChain.doFilter(request, response);

        Object tempId = claims.get("userId");
        Long userId;

        if(tempId instanceof Integer){
            userId = ((Integer) tempId).longValue();
        } else if(tempId instanceof Long){
            userId = ((Long) tempId);
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        request.setAttribute("userId", userId);
        request.setAttribute("nickname", claims.get("nickname"));
        request.setAttribute("email", claims.get("email"));


        filterChain.doFilter(request, response);

//        request.setAttribute("userInfo");
    }
}
