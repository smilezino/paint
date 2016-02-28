package top.wangjun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.wangjun.core.AuthRequired;
import top.wangjun.core.CurrentUser;
import top.wangjun.enums.Status;
import top.wangjun.image.ImageTool;
import top.wangjun.model.Album;
import top.wangjun.model.Photo;
import top.wangjun.model.User;
import top.wangjun.service.IAlbumService;
import top.wangjun.service.IPhotoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 *
 */

@Controller
public class PhotoController {

    @Resource
    private IPhotoService photoService;

    @Resource
    private IAlbumService albumService;

    @Resource
    private ImageTool imageTool;

    @RequestMapping("/item/{id}")
    public String item(@PathVariable("id") Integer id, ModelMap modelMap) {
        Photo photo = photoService.findById(id);
        if (photo == null) {
            return "redirect:/404";
        }

        Album album = albumService.findById(photo.getAlbum());

        photoService.incrViewCount(id);

        modelMap.put("album", album);
        modelMap.put("photo", photo);
        return "photo/item";
    }

    @AuthRequired
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@CurrentUser User user, Photo photo, Integer watermark, @RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {

        if (file.isEmpty()) {
            modelMap.put("error", "请选择文件");
            return "photo/upload";
        }

        String filename = file.getOriginalFilename();
        if (!imageTool.isValid(filename)) {
            modelMap.put("error", "文件格式不正确");
            return "photo/upload";
        }

        if(photo.getAlbum() == null || photo.getAlbum() == 0) {
            modelMap.put("error", "请先新建图册集");
            return "photo/upload";
        }

        //TODO: album 所有者校验
        photo.setUser(user.getId());
        photo = photoService.upload(photo, watermark, file);
        modelMap.put("photoId", photo.getId());
        return "photo/upload";
    }

    @AuthRequired
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload(@CurrentUser User user, ModelMap modelMap) {
        List<Album> albums = albumService.queryByUserId(user.getId());
        modelMap.put("albums", albums);
        return "photo/upload";
    }

    @RequestMapping("/download")
    public String download(@CurrentUser User user, @RequestParam("item") Integer id, HttpServletResponse response) throws IOException {
        Photo photo = photoService.findById(id);

        if (photo == null || (Status.CLOSE.getValue() == photo.getStatus() && user == null)) {
            return "404";
        }

        photoService.incrDownloadCount(id);

        File file = imageTool.readImage(photo.getOrigin());

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(photo.getFilename(), "utf-8") + "\"");
        response.setContentLength((int) file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());

        return null;
    }
}
