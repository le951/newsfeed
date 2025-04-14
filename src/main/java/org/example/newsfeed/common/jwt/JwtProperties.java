package org.example.newsfeed.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
// application.properties에서 jwt prefix 인 값을 변수로 읽어오는 Spring 기능.
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    private Expiration expiration;

    JwtProperties(){
        this.expiration = new Expiration();
    }

    @Getter
    @Setter
    public static class Expiration{
        private Long access;
        private Long refresh;
    }

}
