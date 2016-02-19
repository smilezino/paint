package top.wangjun.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.wangjun.dao.PhotoMapper;
import top.wangjun.image.Image;
import top.wangjun.image.ImageTool;
import top.wangjun.image.WatermarkPosition;
import top.wangjun.model.Photo;
import top.wangjun.service.IAlbumService;
import top.wangjun.service.IPhotoService;
import top.wangjun.service.IProfileService;

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
	private ImageTool imageTool;

	@Resource
	private PhotoMapper mapper;

	@Resource
	private IProfileService profileService;

	@Resource
	private IAlbumService albumService;

	@Override
	public Photo findById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int countByUserId(Integer userId) {
		Photo record = new Photo();
		record.setUser(userId);
		return mapper.selectCount(record);
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
	@Transactional
	public Photo upload(Photo photo, int watermark, MultipartFile file) throws IOException {

		photo.setFilename(file.getOriginalFilename());
		if(StringUtils.isBlank(photo.getTitle())) {
			photo.setTitle(FilenameUtils.getBaseName(photo.getFilename()));
		}

		String md5Name = DigestUtils.md5Hex(file.getBytes()) + "." + FilenameUtils.getExtension(file.getOriginalFilename());

		String originPath = imageTool.getFilePath(ImageTool.PREFIX_ORIGIN, md5Name);
		photo.setOrigin(originPath);

		String normalPath = imageTool.getFilePath(ImageTool.PREFIX_NORMAL, md5Name);
		photo.setFilepath(normalPath);

		String thumbPath = imageTool.getFilePath(ImageTool.PREFIX_THUMB, md5Name);
		photo.setThumb(thumbPath);

		File originFile = imageTool.saveOriginFile(file, originPath);
		Image image = new Image(originFile);

		int width = image.getWidth() > ImageTool.NORMAL_IMAGE_WIDTH ? ImageTool.NORMAL_IMAGE_WIDTH : image.getWidth();
		int height = imageTool.calculateNormalHeight(image.getWidth(), image.getHeight());

		photo.setWidth(width);
		photo.setHeight(height);


		WatermarkPosition position = WatermarkPosition.getPosition(watermark);
		String watermarkText = null;
		if(position!= null) {
			watermarkText = profileService.watermarkText(photo.getUser());
		}
		//TODO: 异步处理
		imageTool.generateNormalImage(originFile, normalPath, position, watermarkText);
		imageTool.generateThumbImage(originFile, thumbPath);

		//自动生成封面
		if(profileService.autoGenerateCover(photo.getUser())) {
			List<Photo> photos = this.findPageByAlbum(photo.getAlbum(), 1, 2);
			String cover = imageTool.generateAlbumCover(thumbPath, photo.getAlbum(), photos);
			albumService.updateCover(photo.getAlbum(), cover);
		}

		photo.setCreateTime(new Date());
		photo.setUpdateTime(new Date());
		mapper.insertSelective(photo);
		return photo;
	}

	@Override
	public void incrViewCount(Integer id) {
		mapper.incrViewCount(id);
	}

	@Override
	public void incrDownloadCount(Integer id) {
		mapper.incrDownloadCount(id);
	}
}
