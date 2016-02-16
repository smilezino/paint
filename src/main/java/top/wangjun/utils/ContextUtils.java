package top.wangjun.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * 上下文
 */
@Component
public class ContextUtils implements ApplicationContextAware, ServletContextAware{

	private static ApplicationContext applicationContext;

	private static ServletContext servletContext;

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static String getWebrootPath() {
		return servletContext.getRealPath(File.separator);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ContextUtils.applicationContext = applicationContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		ContextUtils.servletContext = servletContext;
	}
}
