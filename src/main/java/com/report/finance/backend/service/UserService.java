package com.report.finance.backend.service;

import com.report.finance.backend.dto.LoginResponse;
import com.report.finance.backend.dto.User;
import com.report.finance.backend.dto.UserInfo;
import com.report.finance.backend.dto.UserRequest;

public interface UserService {
    
    LoginResponse login(User user);
    UserInfo creatUserInfo(UserRequest userRequest, String username);

}
