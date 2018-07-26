package com.wbb.web.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wbb.bean.Logs;
import com.wbb.dataSource.MultiDataSource;
import com.wbb.enums.EnumErrorCode;
import com.wbb.service.LogsService;
import com.wbb.web.action.ResponseData;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Resource
	private LogsService logsService;

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object handlerException(Exception e){
		MultiDataSource.setDataSourceKey("dataSource1");
		Date date = new Date();
		Logs log = new Logs();
		log.setHappenTime(date);
		log.setMessage(e.toString());
		logsService.addLogs(log);
		MultiDataSource.toDefault();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("errorTime", sdf.format(date));
		return  ResponseData.createErrorResponseData(
				EnumErrorCode.INTERNAL_SERVER_ERROR, jsonObject,e.toString());
	}
	
}
