package top.wangjun.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 上下文
 */
@Component
public class ContextUtils implements ApplicationContextAware, InitializingBean {

	private static ApplicationContext applicationContext;

	private static Properties properties;

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static String getProperty(String key) {
		return (String) properties.get(key);
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ContextUtils.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		properties = (Properties) ContextUtils.applicationContext.getBean("properties");
	}
}
