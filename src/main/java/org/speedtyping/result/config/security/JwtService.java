package org.speedtyping.result.config.security;

import io.jsonwebtoken.Claims;
import org.speedtyping.result.dto.UserDto;

import java.util.function.Function;

public interface JwtService {
    UserDto extractUser(String jwtToken);

    <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver);

    boolean isTokenValid(String jwtToken);
}