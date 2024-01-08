package org.speedtyping.result.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.speedtyping.result.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${access-token-secret-key}")
    private String SECRET_KEY;

    @Override
    public UserDto extractUser(String jwtToken) {
        Claims claims = extractAllClaims(jwtToken);
        String ID = claims.get("ID", String.class);
        String nickname = claims.get("nickname", String.class);
        return new UserDto(
                ID, nickname
        );
    }

    @Override
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    @Override
    public boolean isTokenValid(String jwtToken) {
        return !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(
                        SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
