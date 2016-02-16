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

	@RequestMapping("album")
	public String album() {
		return "album";
	}

	@RequestMapping("item")
	public String item() {
		return "item";
	}
}
