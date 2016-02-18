package top.wangjun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
public class CommonController {

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping({"/", "index"})
	public String index() {
		return "index";
	}

	@RequestMapping("/404")
	public String error404() {
		return "404";
	}

	@RequestMapping("/500")
	public String error500() {
		return "500";
	}
}
