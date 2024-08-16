package com.report.finance.backend.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.report.finance.backend.dto.LoginResponse;
import com.report.finance.backend.dto.Response;
import com.report.finance.backend.dto.User;
import com.report.finance.backend.service.UserService;
import com.report.finance.backend.util.ResponseUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController extends BaseController{

    @Autowired
	private ResponseUtil responseUtil;

    private final UserService userService;
    
    @PostMapping("login")
	public Response<LoginResponse> login(@RequestBody @Valid User user) {
		log.debug("Login with user [{}]", user);

		LoginResponse loginResponse = userService.login(user);

		return responseUtil.generateResponseSuccess(loginResponse);
	}

  




}
