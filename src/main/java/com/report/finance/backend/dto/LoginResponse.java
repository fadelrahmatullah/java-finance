package com.report.finance.backend.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LoginResponse {
	private String tokenType;
	private String accessToken;
	@JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss")
	private Date accessTokenExpDate;
	private Integer accessTokenAge;
	// private String refreshToken;
	// @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss")
	// private Date refreshTokenExpDate;
	// private Integer refreshTokenAge;

}
