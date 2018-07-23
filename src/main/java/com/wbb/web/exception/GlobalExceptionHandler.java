package com.wbb.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wbb.enums.EnumErrorCode;
import com.wbb.web.action.ResponseData;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	Object handlerException(Exception e){
		System.out.println(e.getMessage());
		ResponseData<Object> data = ResponseData.createErrorResponseData(EnumErrorCode.INTERNAL_SERVER_ERROR, null,e.getMessage());
		return data;
	}
	
}
