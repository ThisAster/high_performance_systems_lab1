package com.example.clinic.doctor;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Configuration
@Profile("test")
public class ApplicationNoSecurity {

    @Value("${keys.private}")
    private String privateKey;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .requestMatchers(new AntPathRequestMatcher("/**"));
    }

    @Bean
    @Qualifier("test_token")
    public String generateToken() {
        return Jwts.builder()
                .claims(Map.of(
                        "id", 1,
                        "roles", List.of("ROLE_ADMIN"),
                        "enabled", true,
                        "username", "admin"
                ))
                .subject("admin")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5))
                .signWith(getSigningKey())
                .compact();
    }

    private PrivateKey getSigningKey() {
        try {

            String privateKeyBase64 = privateKey.replace("\n", "");
            byte[] keyBytes = Base64.getDecoder().decode(privateKeyBase64);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

            return keyFactory.generatePrivate(keySpec);

        } catch (Exception e) {
            throw new RuntimeException("Error while loading private key", e);
        }
    }
}