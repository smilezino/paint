package top.wangjun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.wangjun.core.AuthRequired;
import top.wangjun.core.Constants;
import top.wangjun.core.CurrentUser;
import top.wangjun.image.ImageProcessor;
import top.wangjun.model.Album;
import top.wangjun.model.Photo;
import top.wangjun.model.User;
import top.wangjun.service.IAlbumService;
import top.wangjun.service.IPhotoService;
import top.wangjun.service.IUserService;
import top.wangjun.utils.CookieUtils;
import top.wangjun.utils.UserUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

	@Resource
	private IUserService userService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@RequestParam("identity") String identity,
						@RequestParam("password") String password,
						HttpServletResponse response,
						ModelMap modelMap) {
		User user = this.userService.login(identity, password);
		if(user != null) {
			CookieUtils.setCookie(response, Constants.USER_COOKIE_TOKEN, UserUtils.encrypt(user), 30 * 24 * 60 * 60);
			return "redirect:/";
		} else {
			modelMap.put("error", "用户名密码不正确");
			modelMap.put("identity", identity);
			return "login";
		}

	}
}
