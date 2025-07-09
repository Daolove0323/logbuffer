package com.daol.logbuffer.member.infra;

import com.daol.logbuffer._common.exception.UnauthenticatedException;
import com.daol.logbuffer.member.auth.AuthMember;
import com.daol.logbuffer.member.auth.TokenResponse;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.member.domain.MemberId;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private long accessTokenExpiration;

    @Autowired
    private ObjectMapper objectMapper;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public AuthMember parseTokenToMember(TokenResponse tokenResponse) {
        JwtParser parser = Jwts.parser()
            .verifyWith(getSigningKey())
            .json(new JacksonDeserializer<>(objectMapper))
            .build();
        Claims claims = parser.parseSignedClaims(tokenResponse.accessToken()).getPayload();
        if (claims.getExpiration().before(new Date())) {
            throw new UnauthenticatedException("토큰이 만료되었습니다.");
        }
        String gradeString = claims.get("grade", String.class);
        Grade grade = Grade.valueOf(gradeString);
        return new AuthMember(new MemberId(claims.getSubject()), grade);
    }

    public TokenResponse generateToken(MemberId memberId, Grade grade) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("grade", grade.name());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);
        return new TokenResponse(
            Jwts.builder()
                .json(new JacksonSerializer<>(objectMapper))
                .claims(claims)
                .subject(memberId.getValue().toString())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact()
        );
    }
}