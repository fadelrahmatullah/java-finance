package com.report.finance.backend.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.report.finance.backend.dto.Response;
import com.report.finance.backend.dto.ResponseStatus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Response<Object> resp = new Response<>(ResponseStatus.ERROR, new Date());
        resp.setMessageCode("COMMNERR00005");
        resp.setMessage("Invalid token");

        String body = null;
        try {
            body = objectMapper.writeValueAsString(resp);
        } catch (JsonProcessingException ex2) {
            body = ex2.getMessage();

            log.error("Generate response got JsonProcessingException...", ex2);
        }

        response.getWriter().write(body);
    }
}