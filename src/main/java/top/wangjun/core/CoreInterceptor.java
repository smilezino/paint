package top.wangjun.core;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.wangjun.model.User;
import top.wangjun.service.IUserService;
import top.wangjun.utils.CookieUtils;
import top.wangjun.utils.UserUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class CoreInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private IUserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if(handler instanceof HandlerMethod) {
			User user = this.getUser(request);
			request.setAttribute(Constants.CURRENT_USER_KEY, user);
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			AuthRequired loginRequired = handlerMethod.getMethodAnnotation(AuthRequired.class);

			if(loginRequired != null && loginRequired.login() && user==null) {
				throw new UnloginException();
			}
		}


		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(modelAndView == null) return;

		if(!modelAndView.getViewName().contains("redirect")) {
			modelAndView.addObject("user", userService.admin());
			modelAndView.addObject("login", this.getUser(request) != null);
			modelAndView.addObject("today", new Date());
			modelAndView.addObject("currentUrl", request.getRequestURL().toString());
			modelAndView.addObject("queryString", request.getQueryString());
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	private User getUser(HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, Constants.USER_COOKIE_TOKEN);
		User cookieUser = UserUtils.decrypt(token);
		if(cookieUser == null) return null;
		return userService.login(cookieUser.getId(), cookieUser.getPwd());
	}
}
