package com.report.finance.backend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.report.finance.backend.dto.Response;
import com.report.finance.backend.dto.UserInfo;
import com.report.finance.backend.dto.UserRequest;
import com.report.finance.backend.service.UserService;
import com.report.finance.backend.util.ResponseUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController extends BaseController{

    @Autowired
	private ResponseUtil responseUtil;

    private final UserService userService;
    
    @PostMapping("adduser")
	public Response<UserInfo> adduser(@RequestBody @Valid UserRequest user, Authentication authentication) {
		log.debug("Login with user [{}]", user);

		UserInfo userInfo = userService.creatUserInfo(user, this.getLoginUsername(authentication));

		return responseUtil.generateResponseSuccess(userInfo);
	}
}
