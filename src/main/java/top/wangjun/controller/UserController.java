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
import top.wangjun.image.ImageTool;
import top.wangjun.model.User;
import top.wangjun.service.IProfileService;
import top.wangjun.service.IUserService;
import top.wangjun.utils.CookieUtils;
import top.wangjun.utils.UserUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

	@Resource
	private IUserService userService;

	@Resource
	private IProfileService profileService;

	@Resource
	private ImageTool imageTool;

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

	@AuthRequired
	@RequestMapping(value = "setting", method = RequestMethod.GET)
	public String setting(@CurrentUser User user, ModelMap modelMap) {
		modelMap.put("userinfo", user);

		boolean autoGenerateCover = profileService.autoGenerateCover(user.getId());
		String watermarkText = profileService.watermarkText(user.getId());

		modelMap.put("autoGenerateCover", autoGenerateCover);
		modelMap.put("watermarkText", watermarkText);
		return "setting/index";
	}

	@AuthRequired
	@RequestMapping(value = "setting", method = RequestMethod.POST, params = "type=user")
	public String settingUserInfo(@CurrentUser User user, User userinfo, MultipartFile file, ModelMap modelMap) throws IOException {

		if(userinfo == null) {
			userinfo = new User();
		}

		userinfo.setId(user.getId());

		if(!file.isEmpty() && imageTool.isValid(file.getOriginalFilename())) {
			String avator = imageTool.saveAvator(file, user.getId());
			userinfo.setAvator(avator);
		}

		userService.update(userinfo);

		boolean autoGenerateCover = profileService.autoGenerateCover(user.getId());
		String watermarkText = profileService.watermarkText(user.getId());

		modelMap.put("autoGenerateCover", autoGenerateCover);
		modelMap.put("watermarkText", watermarkText);

		modelMap.put("success", "更新成功");
		modelMap.put("userinfo", userinfo);

		modelMap.put("type", "user");
		return "setting/index";
	}

	@AuthRequired
	@RequestMapping(value = "setting", method = RequestMethod.POST, params = "type=sys")
	public String settingSys(@CurrentUser User user, Boolean autoGenerateCover, String watermarkText, ModelMap modelMap) {
		modelMap.put("userinfo", user);
		modelMap.put("type", "sys");

		String autoGenerateCoverText = autoGenerateCover ? "true" : "false";

		profileService.update(user.getId(), Constants.SETTING_AUTO_GENERATE_COVER, autoGenerateCoverText);
		profileService.update(user.getId(), Constants.SETTING_WATERMARK_TEXT, watermarkText);


		modelMap.put("autoGenerateCover", autoGenerateCover);
		modelMap.put("watermarkText", watermarkText);

		modelMap.put("success", "更新成功");
		return "setting/index";
	}
}
