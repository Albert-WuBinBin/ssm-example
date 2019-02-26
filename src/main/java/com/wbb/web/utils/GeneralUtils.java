package com.wbb.web.utils;

import com.spreada.utils.chinese.ZHConverter;

/**
 * 通用工具类
 * @author wbb
 */
public class GeneralUtils {

	/**
	 * 繁体转简体
	 * 
	 * @param name
	 * @return
	 */
	public static String toSimplified(String name) {
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
		String simplified = converter.convert(name);
		return simplified;
	}
	/**
	 * 简单转繁体
	 * 
	 * @param name
	 * @return
	 */
	public static String toTraditional(String name) {
		String traditional = ZHConverter.convert(name, ZHConverter.TRADITIONAL);
		return traditional;
	}
}
