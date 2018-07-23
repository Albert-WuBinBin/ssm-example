package com.wbb.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;

import com.wbb.bean.Cost;
import com.wbb.service.transaction.CostService;
import com.wbb.service.transaction.CostService2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring-mvc.xml",
		"classpath:spring-mybatis.xml"
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
		System.out.println("--------------------------");
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert(cost, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
	/**
	 * ͬһ��service,���ⶼ��Propagation.REQUIRED,��㱻trycatch��������쳣,���ⶼ�ύ
	 */
	@Test
	public void test2(){
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert2(cost, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
		System.out.println("--------------------------");
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert2(cost, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
	/**
	 * ͬһ��service,���ⶼ��Propagation.REQUIRED,��㲻��trycatch��������쳣,���ⶼ���ύ
	 */
	@Test
	public void test3(){
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert5(cost, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
		System.out.println("--------------------------");
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert5(cost, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
	/**
	 * ͬһ��service����Propagation.REQUIRED��Propagation.REQUIRES_NEW����㱻trycatch��������쳣,���ⶼ�ύ
	 */
	@Test
	public void test4(){
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert4(cost, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
		System.out.println("--------------------------");
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert4(cost, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
	/**
	 * 	
	 * ͬһ��service����Propagation.REQUIRED��Propagation.REQUIRES_NEW����㲻��trycatch��������쳣,���ⶼ���ύ
	 */
	@Test
	public void test5(){
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert6(cost, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
		System.out.println("--------------------------");
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService.insert6(cost, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
	/**
	 * ����service,����Propagation.REQUIRED��Propagation.REQUIRES_NEW,��㱻trycatch��������쳣,�ﲻ�ύ,���ύ
	 */
	@Test
	public void test6(){
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService2.insert(cost, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
		System.out.println("--------------------------");
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService2.insert(cost, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
	/**
	 * ����service,����Propagation.REQUIRED��Propagation.REQUIRES_NEW,��㲻��trycatch��������쳣,���ⶼ���ύ
	 */
	@Test
	public void test8(){
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService2.insert3(cost, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
		System.out.println("--------------------------");
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService2.insert3(cost, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
	/**
	 * ����service,������Propagation.REQUIRED,��㱻trycatch��������쳣,���ⶼ���ύ
	 */
	@Test
	public void test7(){
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService2.insert2(cost, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
		System.out.println("--------------------------");
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService2.insert2(cost, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
	/**
	 * 	/**
	 * ����service,������Propagation.REQUIRED,��㲻��trycatch��������쳣,���ⶼ���ύ
	 */
	@Test
	public void test9(){
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService2.insert4(cost, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
		System.out.println("--------------------------");
		System.out.println("before insert:"+costService.sum());
		try {
			Cost cost = new Cost();
			cost.setMoney(100);
			costService2.insert4(cost, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after insert:"+costService.sum());
	}
}
