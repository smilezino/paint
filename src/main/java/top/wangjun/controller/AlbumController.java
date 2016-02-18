package top.wangjun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.wangjun.core.AuthRequired;
import top.wangjun.core.CurrentUser;
import top.wangjun.model.Album;
import top.wangjun.model.User;
import top.wangjun.service.IAlbumService;

import javax.annotation.Resource;

/**
 *
 */
@Controller
public class AlbumController {

	@Resource
	private IAlbumService albumService;

	@AuthRequired
	@RequestMapping(value = "/album/add", method = RequestMethod.GET)
	public String add() {
		return "album/add";
	}

	@AuthRequired
	@RequestMapping(value = "/album/add", method = RequestMethod.POST)
	public String add(@CurrentUser User user, Album album, ModelMap modelMap) {
		album.setUser(user.getId());
		albumService.add(album);
		modelMap.put("album", album);
		return "album/add";
	}
}
