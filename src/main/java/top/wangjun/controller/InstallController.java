package top.wangjun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.wangjun.enums.UserType;
import top.wangjun.model.User;
import top.wangjun.service.IUserService;

import javax.annotation.Resource;

/**
 * @author zino
 * @date 2016-02-21 19:46
 */
@Controller
public class InstallController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/install", method = RequestMethod.GET)
    public String install() {
        User admin = userService.admin();

        return admin == null ? "install" : "redirect:/";
    }

    @RequestMapping(value = "/install", method = RequestMethod.POST)
    public String install(User user) {
        User admin = userService.admin();
        if(admin == null) {
            user.setRole( (byte) UserType.ADMIN.getRole());
            userService.add(user);
        }
        return "redirect:/";
    }
}
