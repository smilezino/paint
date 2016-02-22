package top.wangjun.controller;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import top.wangjun.model.Photo;
import top.wangjun.model.User;
import top.wangjun.service.IPhotoService;
import top.wangjun.service.IUserService;

import javax.annotation.Resource;

/**
 *
 */
@Controller
public class CommonController {

    @Resource
    private IUserService userService;

    @Resource
    private IPhotoService photoService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping({"/", "index"})
    public String index(@RequestParam(value = "p", defaultValue = "1") Integer p,
                        @RequestParam(value = "ps", defaultValue = "10") Integer ps,
                        ModelMap modelMap) {

        User admin = userService.admin();

        Page<Photo> photos = photoService.findPageByUser(admin.getId(), p, ps);

        modelMap.put("photos", photos);
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
