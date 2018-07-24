package com.wbb.service.transaction.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wbb.bean.Cost;
import com.wbb.mapper.CostMapper;
import com.wbb.service.transaction.CostService;

@Service
public class CostServiceImpl implements CostService{
	
	@Resource
	private CostMapper costMapper;

	/*
	 *  �� spring�� TransactionDefinition�ӿ���һ���������������񴫲����ԣ�Ĭ��PROPAGATION_REQUIRED
		PROPAGATION_REQUIRED -- ֧�ֵ�ǰ���������ǰû�����񣬾��½�һ���������������ѡ�� 
		PROPAGATION_SUPPORTS -- ֧�ֵ�ǰ���������ǰû�����񣬾��Է�����ʽִ�С� 
		PROPAGATION_MANDATORY -- ֧�ֵ�ǰ���������ǰû�����񣬾��׳��쳣�� 
		PROPAGATION_REQUIRES_NEW -- �½����������ǰ�������񣬰ѵ�ǰ������� 
		PROPAGATION_NOT_SUPPORTED -- �Է�����ʽִ�в����������ǰ�������񣬾Ͱѵ�ǰ������� 
		PROPAGATION_NEVER -- �Է�����ʽִ�У������ǰ�����������׳��쳣�� 
		PROPAGATION_NESTED -- �����ǰ������������Ƕ��������ִ�С������ǰû������
		�������PROPAGATION_REQUIRED���ƵĲ�����
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insert(Cost cost,boolean b) throws Exception {
		System.out.println("insert:"+costMapper.insert(cost));
		if(!b){
			throw new Exception("�Զ������");
		}
	}

	@Override
	public int sum() {
		return costMapper.sum();
	}

	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public void insert2(Cost cost, boolean b) throws Exception {
		System.out.println("insert2:"+costMapper.insert(cost));
		try {
			insert(cost, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public void insert5(Cost cost, boolean b) throws Exception {
		System.out.println("insert5:"+costMapper.insert(cost));
		insert(cost, b);
	}

	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
	public void insert3(Cost cost,boolean b) throws Exception {
		System.out.println("insert3:"+costMapper.insert(cost));
		if(!b){
			throw new Exception("�Զ������");
		}
	}
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public void insert4(Cost cost, boolean b) throws Exception {
		System.out.println("insert4:"+costMapper.insert(cost));
		try {
			insert3(cost, b);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public void insert6(Cost cost, boolean b) throws Exception {
		System.out.println("insert4:"+costMapper.insert(cost));
		insert3(cost, b);	
	}
	@Transactional(rollbackFor=Exception.class)
	public void insert7(Cost c){
		costMapper.insert(c);
		int a = 1/0;
	}
}
