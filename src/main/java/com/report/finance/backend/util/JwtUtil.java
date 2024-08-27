package com.report.finance.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.report.finance.backend.dto.AuthorizationInfo;
import com.report.finance.backend.dto.JwtInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@Deprecated
public class JwtUtil {

    public static final String CLAIM_KEY_AUTHORITIES = "authorities";
    @Value("${jwt.secret:#{null}}")
    private String jwtSecret;
    @Value("${jwt.expired:#{null}}")
    private Integer jwtExpired;
    @Value("${jwt.refresh.secret:#{null}}")
    private String jwtRefreshSecret;
    @Value("${jwt.refresh.expired:#{null}}")
    private Integer jwtRefreshExpired;
    @Value("${jwt.tokenType:#{null}}")
    private String jwtTokenType;

    @Autowired
    ObjectMapper objectMapper;

    public JwtInfo generateAccessToken(String username) {
        log.debug("JwtUtil.generateAccessToken...");


        AuthorizationInfo authorizationInfo = new AuthorizationInfo();
        List<String> listRole = new ArrayList<>(Arrays.asList("ADMIN"));
        authorizationInfo.setIsSuperAdmin(true);
        authorizationInfo.setRoleCds(listRole);
        String secret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + (jwtExpired));
        String token = 
                Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username)
                .claim(CLAIM_KEY_AUTHORITIES, authorizationInfo)
                .setIssuedAt(issuedAt).setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,secret.getBytes()).compact();

        return new JwtInfo(token, username, issuedAt, expiration, jwtExpired);
    }

    public JwtInfo generateRefreshToken(String username) {
        log.debug("JwtUtil.generateRefreshToken...");
        String secret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + (jwtRefreshExpired));
        String token = Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username)
                .setIssuedAt(issuedAt).setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();

        return new JwtInfo(token, username, issuedAt, expiration, jwtRefreshExpired);
    }

    public Claims parseAccessToken(String token) {
        String secret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }

    public Claims parseRefreshToken(String token) {
        return Jwts.parser().setSigningKey(jwtRefreshSecret.getBytes()).parseClaimsJws(token).getBody();
    }

    public String getAccessToken(String authorizationString) {
        if (authorizationString != null && authorizationString.startsWith(jwtTokenType)) {
            return authorizationString.replaceFirst(jwtTokenType + " ", org.apache.commons.lang3.StringUtils.EMPTY);
        }

        return null;
    }

    public String getAccessToken(HttpServletRequest request) {
        String authenticationString = request.getHeader("Authorization");

        return this.getAccessToken(authenticationString);
    }

    public String getTokenType() {
        return jwtTokenType;
    }

    @SuppressWarnings("rawtypes")
    public AuthorizationInfo getAuthorizationInfo(Claims claims) {
        Map map = (Map) claims.get(JwtUtil.CLAIM_KEY_AUTHORITIES);

        return objectMapper.convertValue(map, AuthorizationInfo.class);
    }
}