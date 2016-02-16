package top.wangjun.core;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if(ex instanceof UnloginException) {
			return new ModelAndView("redirect:/login");
		}
		return super.doResolveException(request, response, handler, ex);
	}
}
