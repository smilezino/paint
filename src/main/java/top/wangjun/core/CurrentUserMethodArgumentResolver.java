package top.wangjun.core;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * controller方法中, 支持使用@CurrentUser注解获取当前账户信息
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
		return webRequest.getAttribute(currentUserAnnotation.value(), NativeWebRequest.SCOPE_REQUEST);
	}
}
