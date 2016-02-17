package top.wangjun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.wangjun.core.Constants;
import top.wangjun.model.Photo;
import top.wangjun.model.User;
import top.wangjun.service.IUserService;
import top.wangjun.utils.CookieUtils;
import top.wangjun.utils.UserUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Resource
	private IUserService userService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(Photo photo) {
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload() {
		return "upload";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@RequestParam("identity") String identity,
						@RequestParam("password") String password,
						HttpServletRequest request,
						HttpServletResponse response,
						ModelMap modelMap) {
		User user = this.userService.login(identity, password);
		if(user != null) {
			CookieUtils.setCookie(request, response, Constants.USER_COOKIE_TOKEN, UserUtils.encrypt(user), 30 * 24 * 60 * 60);
			return "index";
		} else {
			modelMap.put("error", "loginError");
			return "login";
		}

	}
}
