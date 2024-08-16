package com.report.finance.backend.util;

import org.springframework.stereotype.Component;
import com.report.finance.backend.dto.Response;
import com.report.finance.backend.dto.ResponseStatus;

import java.util.Date;

@Component
public class ResponseUtil {

	public <T> Response<T> generateResponseSuccess(String messageCode, T data, String... varValues) {
		Response<T> result = new Response<>(ResponseStatus.SUCCESS, new Date());
		result.setMessageCode(messageCode);
		result.setMessage(result.getMessage());
		result.setData(data);

		return result;
	}

	public <T> Response<T> generateResponseSuccess(T data) {

		return this.generateResponseSuccess("COMMNINF00002", data);
	}

	public <T> Response<T> generateResponseError(String messageCode, T data, String... varValues) {

		Response<T> result = new Response<>(ResponseStatus.ERROR, new Date());
		result.setMessageCode(messageCode);
		result.setMessage(result.getMessageCode());
		result.setData(null);
		return result;

	}

	public Response<Object> generateResponseSuccess() {
		return this.generateResponseSuccess(null);
	}

	public Response<Object> generateResponseError(Exception ex) {
		Response<Object> result = new Response<>(ResponseStatus.ERROR, new Date());
		result.setMessageCode("COMMNERR00002");
		result.setMessage(result.getMessage());

		return result;
	}

	public Response<Object> generateResponseError(String messageCode, String... varValues) {
		Response<Object> result = new Response<>(ResponseStatus.ERROR, new Date());
		result.setMessageCode(messageCode);
		result.setMessage(result.getMessageCode());

		return result;
	}
}