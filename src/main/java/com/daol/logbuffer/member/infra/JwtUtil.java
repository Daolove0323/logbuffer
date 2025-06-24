package com.daol.logbuffer.member.infra;

import com.daol.logbuffer._common.exception.UnauthenticatedException;
import com.daol.logbuffer.member.auth.AuthMember;
import com.daol.logbuffer.member.auth.Token;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.member.domain.MemberId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private long accessTokenExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public AuthMember parseTokenToMember(Token token) {
        JwtParser parser = Jwts.parser()
            .verifyWith(getSigningKey())
            .build();
        Claims claims = parser.parseSignedClaims(token.accessToken()).getPayload();
        if (claims.getExpiration().before(new Date())) {
            throw new UnauthenticatedException("토큰이 만료되었습니다.");
        }
        return new AuthMember(new MemberId(claims.getSubject()), claims.get("grade", Grade.class));
    }

    public Token generateToken(MemberId memberId, Grade grade) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("grade", grade);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);
        return new Token(
            Jwts.builder()
                .claims(claims)
                .subject(memberId.getValue().toString())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact()
        );
    }
}