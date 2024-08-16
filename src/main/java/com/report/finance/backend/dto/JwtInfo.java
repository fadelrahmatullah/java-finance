package com.report.finance.backend.dto;

import java.util.Date;

import lombok.Data;
import lombok.NonNull;

@Data
public class JwtInfo {
	@NonNull
	private String token;
	@NonNull
	private String subject;
	@NonNull
	private Date issuedAt;
	@NonNull
	private Date expiration;
	@NonNull
	private Integer age;
}
