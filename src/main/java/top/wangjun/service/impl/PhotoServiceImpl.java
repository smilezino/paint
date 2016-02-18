package top.wangjun.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import top.wangjun.dao.PhotoMapper;
import top.wangjun.image.Image;
import top.wangjun.image.ImageProcessor;
import top.wangjun.model.Photo;
import top.wangjun.service.IPhotoService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
	public Photo findById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Photo> findByUser(Integer userId) {
		Photo record = new Photo();
		record.setUser(userId);
		return mapper.select(record);
	}

	@Override
	public Page<Photo> findPageByUser(Integer userId, Integer p, Integer ps) {
		PageHelper.startPage(p, ps, "id desc");
		List<Photo> list = this.findByUser(userId);
		return (Page<Photo>) list;
	}

	@Override
	public List<Photo> findByAlbum(Integer albumId) {
		Photo record = new Photo();
		record.setAlbum(albumId);
		return mapper.select(record);
	}

	@Override
	public Page<Photo> findPageByAlbum(Integer albumId, Integer p, Integer ps) {
		PageHelper.startPage(p, ps, "id desc");
		List<Photo> list = this.findByAlbum(albumId);
		return (Page<Photo>) list;
	}

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
