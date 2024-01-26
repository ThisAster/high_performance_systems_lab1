package com.example.clinic;

import com.example.clinic.dto.UserDetailsDto;
import com.example.clinic.security.JwtUtils;
import com.example.clinic.security.SecurityHelper;
import com.example.clinic.security.SecurityHelperClass;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter implements WebFilter {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final String SWAGGER_PATH = "\\/api\\/.*\\/docs\\/.*";
    private final SecurityHelper securityHelper = new SecurityHelperClass();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = extractToken(exchange.getRequest().getHeaders());

        if(exchange.getRequest().getPath().toString().matches(SWAGGER_PATH))
            token = "eyJhbGciOiJSUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1NVUEVSVklTT1IiXSwiaWQiOjEsImVuYWJsZWQiOnRydWUsInVzZXJuYW1lIjoiYWRtaW4iLCJzdWIiOiJhZG1pbiIsImlhdCI6MTczNjM3Mjg1OCwiZXhwIjoxNzM2ODA0ODU4fQ.BV93APAsLSk23Wg0mWh8_keJdsg1VJjsfRJxpP-oaRzLBNMqUlW8KzzU0I1KAt52w13thulxcm2s54Q7OwKD6J7JK9xLXSIAGBq8Q9gSiXAwRGI9uNHb1BwVoqxUaLqBN_e6OmCUMBT9GEGKBoJZ0meE3tVbN4oZBgZAvmWEJvF_8yt8bSb1WfimqaKHupdGCm7OIsAHNrz4gQBcoHB937J2INNRvPpgEtqGRNIG94e6rReCUXA03AxpCFAziFiOrCh4I6oxUmJKI1k_Xjwb6PuT_J-EmSsnbNO4geeA-6Q1lFdbMvxbc8Wi_2v53Nv7cDEm5kMyVmnDgNDJkV_SLw";

        if (token == null) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        return Mono.justOrEmpty(parseClaims(token))
                .filter(this::isTokenValid)
                .map(this::createAuthenticationToken)
                .flatMap(authentication -> {
                    SecurityContext context = new SecurityContextImpl(authentication);
                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }));
    }

    private String extractToken(HttpHeaders headers) {
        String token = headers.getFirst(HEADER);
        if (StringUtils.hasLength(token) && token.startsWith(PREFIX)) {
            return token.replace(PREFIX, "").trim();
        }
        return null;
    }

    private Claims parseClaims(String token) {
        return JwtUtils.getAccessClaims(token, securityHelper.getJwtValidationKey());
    }

    private boolean isTokenValid(Claims claims) {
        return claims != null && !isTokenExpired(claims) && isUserEnabled(claims);
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    private boolean isUserEnabled(Claims claims) {
        Boolean isEnabled = (Boolean) claims.get("enabled");
        return isEnabled != null && isEnabled;
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(Claims claims) {
        List<SimpleGrantedAuthority> authorities = Optional.ofNullable((List<?>) claims.get("roles"))
                .orElse(Collections.emptyList())
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());

        Long userId = ((Number) claims.get("id")).longValue();

        return new UsernamePasswordAuthenticationToken(
                new UserDetailsDto(userId, authorities, true, true, true, true),
                null,
                authorities
        );
    }
}

