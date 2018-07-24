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
	public Object handlerException(Exception e){
		
		return  ResponseData.createErrorResponseData(
				EnumErrorCode.INTERNAL_SERVER_ERROR, null,e.getMessage());
	}
	
}
