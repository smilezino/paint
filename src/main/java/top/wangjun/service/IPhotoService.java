package top.wangjun.service;

import org.springframework.web.multipart.MultipartFile;
import top.wangjun.model.Photo;

import java.io.IOException;

/**
 * @author zino
 * @date 2016-02-08 19:54
 */
public interface IPhotoService {
	public Photo upload(Photo photo, int watermark, MultipartFile file) throws IOException;
}
