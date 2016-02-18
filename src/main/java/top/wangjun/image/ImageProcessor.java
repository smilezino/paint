package top.wangjun.image;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import top.wangjun.service.IConfigService;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 图片处理工具类
 */
@Component
public class ImageProcessor implements InitializingBean {

	public static final String DATE_FORMAT = "yyyy";

	public static final String PREFIX_ORIGIN = "origin";
	public static final String PREFIX_NORMAL = "uploads";
	public static final String PREFIX_THUMB = "uploads/thumb";

	public static final int NORMAL_IMAGE_WIDTH = 680;
	public static final int THUMB_IMAGE_WIDTH = 340;
	public static final int THUMB_IMAGE_HEIGHT = 272;
	public static final double IMAGE_WIDTH_HEIGHT_SCALE = 1.25;

	private List<String> imageTypes;

	@Value("#{servletContext.getRealPath('/WEB-INF')}")
	private String servletContextPath;

	@Resource
	private IConfigService configService;

	public boolean isValid(String filename) {
		String extension = null;
		if(filename.length() > 0 && filename.lastIndexOf(".") > -1) {
			extension = filename.substring(filename.lastIndexOf(".") + 1);
		}
		return extension != null && this.imageTypes.contains(extension.toLowerCase());
	}

	public File saveOriginFile(MultipartFile file, String filename) throws IOException {
		File originFile = new File(servletContextPath + filename);
		if(!originFile.getParentFile().exists()) {
			originFile.getParentFile().mkdirs();
		}
		IOUtils.copy(file.getInputStream(), new FileOutputStream(originFile));
		return originFile;
	}

	public void generateNormalImage(File file, String filename, int watermark) throws IOException {
		Image image = new Image(file);
		if(image.getWidth() > NORMAL_IMAGE_WIDTH) {
			int height = this.calculateNormalHeight(image.getWidth(), image.getHeight());
			image.resize(NORMAL_IMAGE_WIDTH, height);
		}

		WatermarkPosition position = WatermarkPosition.getPosition(watermark);

		if(position != null) {
			image.watermark(configService.getWatermarkText(), position);
		}

		File normalFile = new File(servletContextPath + filename);
		image.write(normalFile);

	}

	public void generateThumbImage(File file, String filename) throws IOException {
		Image originImage = new Image(file);

		boolean isNeedCrop = originImage.getWidth() > THUMB_IMAGE_WIDTH || originImage.getHeight() > THUMB_IMAGE_HEIGHT;

		if(isNeedCrop) {
			double scale = 1.00 * originImage.getWidth() / originImage.getHeight();

			//按 THUMB_IMAGE_HEIGHT 缩放后裁剪
			if(scale > IMAGE_WIDTH_HEIGHT_SCALE) {
				int width = THUMB_IMAGE_HEIGHT * originImage.getWidth() / originImage.getHeight();
				int x = (width - THUMB_IMAGE_WIDTH) / 2;
				int y = 0;
				originImage.resize(width, THUMB_IMAGE_HEIGHT).crop(x, y, THUMB_IMAGE_WIDTH, THUMB_IMAGE_HEIGHT);
			}
			//按 THUMB_IMAGE_HEIGHT 缩放后裁剪
			if(scale < IMAGE_WIDTH_HEIGHT_SCALE) {
				int height = THUMB_IMAGE_WIDTH * originImage.getHeight() / originImage.getWidth();
				int x = 0;
				int y = (height - THUMB_IMAGE_HEIGHT) / 2;
				originImage.resize(THUMB_IMAGE_WIDTH, height).crop(x, y, THUMB_IMAGE_WIDTH, THUMB_IMAGE_HEIGHT);
			}
			//比例相同直接缩放
			if(scale == IMAGE_WIDTH_HEIGHT_SCALE) {
				originImage.resize(THUMB_IMAGE_WIDTH, THUMB_IMAGE_HEIGHT);
			}
		}

		originImage.write(servletContextPath + filename);
	}

	public int calculateNormalHeight(int width, int height) {
		return width < NORMAL_IMAGE_WIDTH ? height : (int) Math.round(height / IMAGE_WIDTH_HEIGHT_SCALE);
	}

	public String getFilePath(String prefix, String filename) {
		return File.separator + prefix + File.separator
				+ DateFormatUtils.format(new Date(), DATE_FORMAT) + File.separator + filename;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		imageTypes = new ArrayList<>();
		imageTypes.add("png");
		imageTypes.add("jpg");
		imageTypes.add("jpge");
	}
}
