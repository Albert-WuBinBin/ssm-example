package com.wbb.web.action;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbb.service.CostService;

@RestController
@RequestMapping("/test")
public class TestAction {

	@Resource
	private CostService costService;
}
