package top.wangjun.image;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import top.wangjun.model.Photo;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 图片处理工具类
 */
@Component
public class ImageTool implements InitializingBean {

    public static final String DATE_FORMAT = "yyyy";

    public static final String PREFIX_ORIGIN = "origin";
    public static final String PREFIX_NORMAL = "uploads";
    public static final String PREFIX_THUMB = "uploads/thumb";
    public static final String PREFIX_COVER = "uploads/cover";
    public static final String PREFIX_AVATOR = "uploads/avator";

    public static final int NORMAL_IMAGE_WIDTH = 680;
    public static final int THUMB_IMAGE_WIDTH = 340;
    public static final int THUMB_IMAGE_HEIGHT = 272;
    public static final double IMAGE_WIDTH_HEIGHT_SCALE = 1.25;

    public static final int AVATOR_SIZE = 100;

    private List<String> imageTypes;

    @Value("#{servletContext.getRealPath('')}")
    private String servletContextPath;


    public boolean isValid(String filename) {
        String extension = FilenameUtils.getExtension(filename);
        return extension != null && this.imageTypes.contains(extension.toLowerCase());
    }

    public File saveOriginFile(MultipartFile file, String filepath) throws IOException {
        File originFile = new File(servletContextPath + filepath);
        if (!originFile.getParentFile().exists()) {
            originFile.getParentFile().mkdirs();
        }
        IOUtils.copy(file.getInputStream(), new FileOutputStream(originFile));
        return originFile;
    }

    public void generateNormalImage(File file, String filepath, WatermarkPosition position, String watermarkText) throws IOException {
        Image image = new Image(file);
        if (image.getWidth() > NORMAL_IMAGE_WIDTH) {
            int height = this.calculateNormalHeight(image.getWidth(), image.getHeight());
            image.resize(NORMAL_IMAGE_WIDTH, height);
        }

        if (position != null) {
            image.watermark(watermarkText, position);
        }

        File normalFile = new File(servletContextPath + filepath);
        image.write(normalFile);

    }

    public void generateThumbImage(File file, String filepath) throws IOException {
        Image originImage = new Image(file);

        boolean isNeedCrop = originImage.getWidth() > THUMB_IMAGE_WIDTH || originImage.getHeight() > THUMB_IMAGE_HEIGHT;

        if (isNeedCrop) {
            double scale = 1.00 * originImage.getWidth() / originImage.getHeight();

            //按 THUMB_IMAGE_HEIGHT 缩放后裁剪
            if (scale > IMAGE_WIDTH_HEIGHT_SCALE) {
                int width = THUMB_IMAGE_HEIGHT * originImage.getWidth() / originImage.getHeight();
                int x = (width - THUMB_IMAGE_WIDTH) / 2;
                int y = 0;
                originImage.resize(width, THUMB_IMAGE_HEIGHT).crop(x, y, THUMB_IMAGE_WIDTH, THUMB_IMAGE_HEIGHT);
            }
            //按 THUMB_IMAGE_HEIGHT 缩放后裁剪
            if (scale < IMAGE_WIDTH_HEIGHT_SCALE) {
                int height = THUMB_IMAGE_WIDTH * originImage.getHeight() / originImage.getWidth();
                int x = 0;
                int y = (height - THUMB_IMAGE_HEIGHT) / 2;
                originImage.resize(THUMB_IMAGE_WIDTH, height).crop(x, y, THUMB_IMAGE_WIDTH, THUMB_IMAGE_HEIGHT);
            }
            //比例相同直接缩放
            if (scale == IMAGE_WIDTH_HEIGHT_SCALE) {
                originImage.resize(THUMB_IMAGE_WIDTH, THUMB_IMAGE_HEIGHT);
            }
        }

        originImage.write(servletContextPath + filepath);
    }

    public String generateAlbumCover(String filepath, Integer albumId, List<Photo> photos) throws IOException {

        Image image = new Image(servletContextPath + filepath);

        String name = albumId + "." + image.getImageExtension();
        String filename = this.getFilePath(PREFIX_COVER, name);

        image.createMergeArea(THUMB_IMAGE_WIDTH, THUMB_IMAGE_HEIGHT);

        int size = photos.size();
        for (int i = 0; i < size; i++) {
            Image merge = new Image(servletContextPath + photos.get(i).getThumb());
            int x = (i + 1) * 10;
            int y = (size - i - 1) * 10;
            int width = THUMB_IMAGE_WIDTH - (2 * x);
            int height = 10;
            image.merge(merge, x, y, width, height);
            image.fillRect(Color.WHITE, 0, y, x, height);
            image.fillRect(Color.WHITE, width + x, y, THUMB_IMAGE_WIDTH - width - x, height);
            image.fillRect(Color.WHITE, 0, (size - i) * 10 - 1, THUMB_IMAGE_WIDTH, 1);
        }

        image.write(servletContextPath + filename);
        return filename;
    }

    public String saveAvator(MultipartFile file, Integer userId) throws IOException {
        String filename = this.getFilePath(PREFIX_AVATOR, userId + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        String tmpname = this.getFilePath(PREFIX_AVATOR, userId + "_tmp." + FilenameUtils.getExtension(file.getOriginalFilename()));
        File tmp = new File(servletContextPath + tmpname);
        if (!tmp.getParentFile().exists()) {
            tmp.getParentFile().mkdirs();
        }
        IOUtils.copy(file.getInputStream(), new FileOutputStream(tmp));

        Image image = new Image(tmp);
        image.resize(AVATOR_SIZE, AVATOR_SIZE).write(servletContextPath + filename);

        tmp.delete();

        return filename;
    }

    public File readImage(String filepath) {
        File file = new File(servletContextPath + filepath);
        return file.exists() ? file : null;
    }

    public int calculateNormalHeight(int width, int height) {
        return width < NORMAL_IMAGE_WIDTH ? height : NORMAL_IMAGE_WIDTH * height / width;
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
        imageTypes.add("jpeg");
    }

}
