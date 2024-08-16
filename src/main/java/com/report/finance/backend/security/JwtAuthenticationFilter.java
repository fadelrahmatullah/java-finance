package com.report.finance.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.report.finance.backend.dto.AuthorizationInfo;
import com.report.finance.backend.dto.UserInfo;
import com.report.finance.backend.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Component
@Deprecated
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;

    // @Autowired
    // private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        log.debug("JwtAuthenticationFilter.java.doFilterInternal [start]...");

        try {
            String token = jwtUtil.getAccessToken(request);

            log.debug("token [{}]", token);

            if (token != null) {
                Claims claims = jwtUtil.parseAccessToken(token);
                AuthorizationInfo authorizationInfo = (AuthorizationInfo) jwtUtil.getAuthorizationInfo(claims);

                if (authorizationInfo != null) {

                    if (authorizationInfo.getIsSuperAdmin() || !authorizationInfo.getIsSuperAdmin()) {
                        setupSpringAuthentication(claims);
                    }else{
                        SecurityContextHolder.clearContext();
                    }
                    
                    
                }else{
                    SecurityContextHolder.clearContext();
                }

                
            } else {
                SecurityContextHolder.clearContext();
            }
            
        } catch (ExpiredJwtException e) {
            log.error("Got Exception ExpiredJwtException...", e);

            SecurityContextHolder.clearContext();
        } catch (UnsupportedJwtException e) {
            log.error("Got Exception UnsupportedJwtException...", e);

            SecurityContextHolder.clearContext();
        } catch (MalformedJwtException e) {
            log.error("Got Exception MalformedJwtException...", e);

            SecurityContextHolder.clearContext();

        } catch (SignatureException e) {
            log.error("Got SignatureException Exception...", e);
            SecurityContextHolder.clearContext();
            
        } catch (JwtException e) {
            log.error("Got JwtException Exception...", e);
            SecurityContextHolder.clearContext();

        } catch (Exception e) {
            log.error("Got Exception Exception...", e);

            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);

        log.debug("JwtAuthenticationFilter.java.doFilterInternal [end]...");
    }

    /**
     * Authentication method in Spring flow
     *
     * @param claims
     */
    private void setupSpringAuthentication(Claims claims) {
//        List<String> authorities = (List<String>) claims.get(JwtUtil.CLAIM_KEY_AUTHORITIES);
        AuthorizationInfo authorizationInfo = (AuthorizationInfo) jwtUtil.getAuthorizationInfo(claims);
        
        UserInfo userInfo = new UserInfo(claims.getSubject());

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userInfo, null,
                authorizationInfo.getRoleCds().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                
        SecurityContextHolder.getContext().setAuthentication(auth);
    }


}
