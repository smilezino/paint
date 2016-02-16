package top.wangjun.image;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 图片处理工具类
 */
@Component
public class ImageUtils implements ApplicationContextAware, InitializingBean {

	@Value("#{servletContext.getRealPath('')}")
	private String servletContextPath;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println(servletContextPath);
	}
}
