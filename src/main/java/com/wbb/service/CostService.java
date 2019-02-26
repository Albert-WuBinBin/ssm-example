package com.wbb.service;

import com.wbb.bean.Cost;

public interface CostService {
	int sum();
	void insert(Cost cost,boolean b) throws Exception ;
	/**
	 * 
	 * @param cost
	 * @param first 
	 * @param second
	 * @throws Exception
	 */
	void insert2(Cost cost, boolean first,boolean second) throws Exception ;
	
}
