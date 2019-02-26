package com.wbb.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wbb.bean.Cost;
import com.wbb.mapper.CostMapper;
import com.wbb.service.CostService;
import com.wbb.service.CostService2;

@Service
public class CostServiceImpl2 implements CostService2{
	
	@Resource
	private CostMapper costMapper;
	@Resource
	private CostService costService;
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
	public void insert(Cost cost,boolean b) throws Exception {
		System.out.println("CostService2 insert:"+costMapper.insert(cost));
		if(!b){
			throw new Exception("CostService2自定义错误");
		}
	}
}