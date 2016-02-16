package top.wangjun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.wangjun.model.Photo;

@Controller
public class UserController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(Photo photo) {
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload() {
		return "upload";
	}

	@RequestMapping("login")
	public String login() {
		return "login";
	}
}
