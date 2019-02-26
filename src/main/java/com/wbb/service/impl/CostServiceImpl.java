package com.wbb.service.impl;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wbb.bean.Cost;
import com.wbb.mapper.CostMapper;
import com.wbb.service.CostService;
import com.wbb.service.CostService2;

@Service
public class CostServiceImpl implements CostService {

	@Autowired
	private CostMapper costMapper;
	@Autowired
	private CostService2 costService2;
	/**
	 * Spring定义了7个以PROPAGATION_开头的常量表示它的传播属性。
	 * PROPAGATION_REQUIRED 支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择，也是Spring默认的事务的传播。
	 * PROPAGATION_SUPPORTS 支持当前事务，如果当前没有事务，就以非事务方式执行。
	 * PROPAGATION_MANDATORY 支持当前事务，如果当前没有事务，就抛出异常。 
	 * PROPAGATION_REQUIRES_NEW 新建事务，如果当前存在事务，把当前事务挂起。
	 * PROPAGATION_NOT_SUPPORTED 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
	 * PROPAGATION_NEVER以非事务方式执行，如果当前存在事务，则抛出异常。
	 * PROPAGATION_NESTED如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，
	 * 则进行与PROPAGATION_REQUIRED类似的操作。
	 */
	@Override
	public int sum() {
		return costMapper.sum();
	}

	@Override
	@Transactional(rollbackFor = Exception.class , propagation = Propagation.REQUIRED)
	public void insert(Cost cost, boolean b) throws Exception {
		System.out.println("CostService insert:"+costMapper.insertSelective(cost));
		if(!b){
			throw new Exception("CostService自定义错误");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class , propagation = Propagation.REQUIRED)
	public void insert2(Cost cost, boolean first, boolean second) throws Exception {
		System.out.println("CostService insert2:"+costMapper.insertSelective(cost));
		cost.setMoney(cost.getMoney()-50);
		costService2.insert(cost, second);
		if(!first){
			throw new Exception("CostService自定义错误");
		}
	}

}