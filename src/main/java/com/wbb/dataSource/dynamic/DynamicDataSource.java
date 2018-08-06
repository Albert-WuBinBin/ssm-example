package com.wbb.dataSource.dynamic;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.alibaba.druid.pool.DruidDataSource;


public class  DynamicDataSource extends AbstractRoutingDataSource implements ApplicationContextAware{

	private ApplicationContext applicationContext ;

	/**
	 * 连接数据源前,调用该方法
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		//1.获取手动设置的数据源参数DataSourceBean
		DataSourceBean dataSourceBean = DataSourceContext.getDataSource();
		if(dataSourceBean == null) {
			return null;
		}
		try {
			//2.获取AbstractRoutingDataSource的targetDataSources属性,该属性存放数据源属性
			Map<Object, Object> targetSourceMap = getTargetSource();
			synchronized(this) {
				/*
				 * 3.判断targetDataSources中是否已经存在要设置的数据源bean
				 * 存在的话,则直接返回beanName
				 * 
				 */
				if(!targetSourceMap.keySet().contains(dataSourceBean.getBeanName())) {
					/*不存在，则进行以下几步
					3.1 先在spring容器中创建该数据源bean
					*/
					Object dataSource = createDataSource(dataSourceBean);
					//3.2 在创建后的bean,放入到targetDataSources Map中
					targetSourceMap.put(dataSourceBean.getBeanName(), dataSource);
					/*
					 * 3.3 通知spring有bean更新
					 * 主要更新AbstractRoutingDataSource的resolvedDefaultDataSource(Map)属性,
					 * 更新完以后,AbstractRoutingDataSource的determineTargetDataSource()中,才能找到数据源
					 * 代码如下：
					 * Object lookupKey = determineCurrentLookupKey();
					   DataSource dataSource = this.resolvedDataSources.get(lookupKey);
					 */
					super.afterPropertiesSet();
				}
			}
			for(Map.Entry<Object, Object> entry : targetSourceMap.entrySet()) {
				System.out.println(entry.getKey()+"-"+entry.getValue());
			}
			return dataSourceBean.getBeanName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object createDataSource(DataSourceBean dataSourceBean) throws IllegalAccessException {
		//1.获取
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory beanFactory =  (DefaultListableBeanFactory) context.getBeanFactory();
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
		Map<String, Object> propertyKeyValues = getPropertyKeyValues(DataSourceBean.class, dataSourceBean);
		for(Map.Entry<String,Object> entry : propertyKeyValues.entrySet()) {
			beanDefinitionBuilder.addPropertyValue(entry.getKey(), entry.getValue());
		}
		beanFactory.registerBeanDefinition(dataSourceBean.getBeanName(), beanDefinitionBuilder.getBeanDefinition());
		return context.getBean(dataSourceBean.getBeanName());
	
	}
    @SuppressWarnings("unused")
	private <T> Map<String, Object> getPropertyKeyValues(Class<T> clazz, Object object) throws IllegalAccessException {
       Field[] fields = clazz.getDeclaredFields();
       Map<String,Object> map = new HashMap<>();
       for (Field field : fields) {
    	   field.setAccessible(true);
    	   map.put(field.getName(), field.get(object));
       }
       map.remove("beanName");
       return map;
    }
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getTargetSource() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = AbstractRoutingDataSource.class.getDeclaredField("targetDataSources");
		field.setAccessible(true);
		return (Map<Object, Object>) field.get(this);
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.applicationContext = applicationContext;
	}
 
}
