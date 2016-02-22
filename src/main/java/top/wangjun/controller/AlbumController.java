package top.wangjun.controller;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.wangjun.core.AuthRequired;
import top.wangjun.core.CurrentUser;
import top.wangjun.model.Album;
import top.wangjun.model.Photo;
import top.wangjun.model.User;
import top.wangjun.service.IAlbumService;
import top.wangjun.service.IPhotoService;
import top.wangjun.service.IUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Controller
public class AlbumController {

    @Resource
    private IAlbumService albumService;

    @Resource
    private IPhotoService photoService;

    @Resource
    private IUserService userService;

    @RequestMapping("/album")
    public String album(@RequestParam(value = "p", defaultValue = "1") Integer p,
                        @RequestParam(value = "ps", defaultValue = "10") Integer ps,
                        ModelMap modelMap) {
        User user = userService.admin();
        Page<Album> albums = albumService.queryPageByUserId(user.getId(), p, ps);
        modelMap.put("albums", albums);
        return "album/index";
    }

    @RequestMapping("/album/{id}")
    public String album(@PathVariable("id") Integer id,
                        @RequestParam(value = "p", defaultValue = "1") Integer p,
                        @RequestParam(value = "ps", defaultValue = "10") Integer ps,
                        ModelMap modelMap) {
        Album album = albumService.findById(id);

        if (album == null) {
            return "redirect:/404";
        }

        Page<Photo> photos = photoService.findPageByAlbum(album.getId(), p, ps);

        modelMap.put("album", album);
        modelMap.put("photos", photos);

        return "album/album";
    }

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
