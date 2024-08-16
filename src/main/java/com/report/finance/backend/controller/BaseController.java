package com.report.finance.backend.controller;

import org.springframework.security.core.Authentication;

import com.report.finance.backend.dto.UserInfo;

public class BaseController {
    protected UserInfo getLoginUserInfo(Authentication authentication) {
        return (UserInfo) authentication.getPrincipal();
    }

    protected String getLoginUsername(Authentication authentication) {

        return this.getLoginUserInfo(authentication).getUsername();
    }

}
