package top.wangjun.controller;

import com.github.pagehelper.Page;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import top.wangjun.core.CurrentUser;
import top.wangjun.enums.Status;
import top.wangjun.image.ImageProcessor;
import top.wangjun.model.Photo;
import top.wangjun.model.User;
import top.wangjun.service.IPhotoService;
import top.wangjun.service.IUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 *
 */
@Controller
public class CommonController {

	@Resource
	private IUserService userService;

	@Resource
	private IPhotoService photoService;

	@Resource
	private ImageProcessor imageProcessor;

	/**
	 * 首页
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

	@RequestMapping("/download")
	public String download(@CurrentUser User user, @RequestParam("item") Integer id, HttpServletResponse response) throws IOException {
		Photo photo = photoService.findById(id);

		if(photo == null || (Status.CLOSE.getValue() == photo.getStatus() && user == null)) {
			return "redirect:404";
		}

		File file = imageProcessor.readImage(photo.getOrigin());

		String mimeType= URLConnection.guessContentTypeFromName(file.getName());
		if(mimeType == null){
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(photo.getFilename(), "utf-8") + "\"");
		response.setContentLength((int)file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());

		return null;
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
