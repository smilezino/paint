package top.wangjun.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.wangjun.dao.PhotoMapper;
import top.wangjun.image.Image;
import top.wangjun.image.ImageProcessor;
import top.wangjun.model.Photo;
import top.wangjun.service.IPhotoService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author zino
 * @date 2016-02-08 20:29
 */
@Service
public class PhotoServiceImpl implements IPhotoService {

	@Resource
	private ImageProcessor imageProcessor;

	@Resource
	private PhotoMapper mapper;

	@Override
	public Photo upload(Photo photo, int watermark, MultipartFile file) throws IOException {

		String md5Name = DigestUtils.md5Hex(file.getBytes()) + "." + FilenameUtils.getExtension(file.getOriginalFilename());

		String originPath = imageProcessor.getFilePath(ImageProcessor.PREFIX_ORIGIN, md5Name);
		photo.setOrigin(originPath);

		String normalPath = imageProcessor.getFilePath(ImageProcessor.PREFIX_NORMAL, md5Name);
		photo.setFilepath(normalPath);

		String thumbPath = imageProcessor.getFilePath(ImageProcessor.PREFIX_THUMB, md5Name);
		photo.setThumb(thumbPath);

		File originFile = imageProcessor.saveOriginFile(file, originPath);
		Image image = new Image(originFile);

		int width = image.getWidth() > ImageProcessor.NORMAL_IMAGE_WIDTH ? image.getWidth() : ImageProcessor.NORMAL_IMAGE_WIDTH;
		int height = imageProcessor.calculateNormalHeight(width, image.getHeight());

		photo.setWidth(width);
		photo.setHeight(height);

		//TODO: 异步处理
		imageProcessor.generateNormalImage(originFile, normalPath, watermark);
		imageProcessor.generateThumbImage(originFile, thumbPath);


		photo.setCreateTime(new Date());
		photo.setUpdateTime(new Date());
		mapper.insertSelective(photo);
		return photo;
	}
}
