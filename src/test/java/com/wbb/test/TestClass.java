package com.wbb.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wbb.bean.Cost;
import com.wbb.service.CostService;
import com.wbb.service.CostService2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring.xml",
		"classpath:spring-mvc.xml",
		"classpath:spring-mybatis.xml",
		})
public class TestClass {

	@Resource
	CostService costService;
	@Resource
	CostService2 costService2;
	@Test
	public void test1(){
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert(cost, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
	@Test
	public void test2(){
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert2(cost, false, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
}