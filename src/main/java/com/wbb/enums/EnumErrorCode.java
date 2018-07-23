package com.wbb.enums;

import org.springframework.http.HttpStatus;

public enum EnumErrorCode {

	OK(HttpStatus.OK,200,"OK"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,500,"·þÎñÆ÷Òì³£");
	
	private HttpStatus httpStatus;
	private Integer value;
	private String msg;
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public Integer getValue() {
		return value;
	}
	public String getMsg() {
		return msg;
	}
	private EnumErrorCode(HttpStatus httpStatus, Integer value, String msg) {
		this.httpStatus = httpStatus;
		this.value = value;
		this.msg = msg;
	}
	private EnumErrorCode() {
	}
}
