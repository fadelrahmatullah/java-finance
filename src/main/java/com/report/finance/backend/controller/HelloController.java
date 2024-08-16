package com.report.finance.backend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.report.finance.backend.dto.Response;
import com.report.finance.backend.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController extends BaseController {
    
    @Autowired
    private ResponseUtil responseUtil;

    @GetMapping("hello-world")
    public Response<String> helloWorld(Principal principal, Authentication authentication) {
        
        log.debug("Hello World !!!");

        log.debug("principal.getName() = {}", principal.getName());
        log.debug("authentication.getPrincipal() = {}", authentication.getPrincipal());

        return responseUtil.generateResponseSuccess("Hello World !!!");
    }

    @GetMapping("generate-error")
    public Response<String> helloWorld(@RequestParam String clazz) throws Exception {
        log.debug("Throw execption with type [{}]", clazz);

        if ("Exception".equals(clazz)) {
            throw new Exception("On Purpose throw Exception");
        } else if ("RuntimeException".equals(clazz)) {
            throw new RuntimeException("On Purpose throw RuntimeException");
        }

        return responseUtil.generateResponseSuccess("Unknown clazz");
    }

}
