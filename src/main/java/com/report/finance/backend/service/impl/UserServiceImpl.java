package com.report.finance.backend.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.report.finance.backend.dto.BusinessException;
import com.report.finance.backend.dto.JwtInfo;
import com.report.finance.backend.dto.LoginResponse;
import com.report.finance.backend.dto.User;
import com.report.finance.backend.dto.UserInfo;
import com.report.finance.backend.dto.UserRequest;
import com.report.finance.backend.dto.ValidationException;
import com.report.finance.backend.entity.UserInfoEntity;
import com.report.finance.backend.repository.UserInfoRepository;
import com.report.finance.backend.service.UserService;
import com.report.finance.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class UserServiceImpl implements UserService{

	private final AuthenticationManager authenticationManager;

	private final JwtUtil jwtUtil;

    private final UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(User user) {
        return this.generLoginResponseLogin(user);
    }

    @Override
    public UserInfo creatUserInfo(UserRequest userRequest, String username) {
        
        UserInfoEntity entity = userInfoRepository.getUserByUserName(userRequest.getUserName());

        if (entity != null) {
            throw new ValidationException("ERROR0001", userRequest.getUserName()+" Already Exist");
        }

        entity = new UserInfoEntity();
        BeanUtils.copyProperties(userRequest, entity);
		String finalPass = passwordEncoder.encode(userRequest.getPassword());

        entity.setUsername(userRequest.getUserName());
        entity.setPassword(finalPass);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());

        userInfoRepository.save(entity);
        return new UserInfo(entity.getUsername());

        
    }

    private LoginResponse generLoginResponseLogin(User user){

        Authentication authenticate;
		try {
			authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new BusinessException("COMMNERR00008", "BadCredentialsException : "+ex.getMessage());
		}

        JwtInfo accessTokenInfo = jwtUtil.generateAccessToken(user.getUsername());
        LoginResponse response = new LoginResponse();
		response.setTokenType(jwtUtil.getTokenType());

		response.setAccessToken(accessTokenInfo.getToken());
		response.setAccessTokenExpDate(accessTokenInfo.getExpiration());
		response.setAccessTokenAge(accessTokenInfo.getAge());

        return response;
    }

    @Override
    public UserInfoEntity getUserInfo(String username) {
        UserInfoEntity entity = userInfoRepository.getUserByUserName(username);

        if (entity == null) {
            throw new ValidationException("ERROR0001", username.toUpperCase()+" Not Found");
        }

        return entity;

    }
    
}
