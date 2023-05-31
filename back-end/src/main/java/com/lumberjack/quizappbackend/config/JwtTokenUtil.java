package com.lumberjack.quizappbackend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 192 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    public DecodedJWT getDecodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token);
    }

    public boolean validateAndVerifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            // Token verification failed, return false
            return false;
        }
    }
    public Date getExpirationDateFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getExpiresAt();
    }
    private Map<String, Claim> getAllClaimsFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getClaims();
    }

    private Boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        decodedJWT.getExpiresAt();
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public String generateToken(HttpServletRequest request, User user) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .withIssuer(request.getRequestURI())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        return accessToken;
    }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        String username = decodedJWT.getSubject();
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}