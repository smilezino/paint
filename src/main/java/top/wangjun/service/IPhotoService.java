package top.wangjun.service;

import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;
import top.wangjun.model.Photo;

import java.io.IOException;
import java.util.List;

/**
 * @author zino
 * @date 2016-02-08 19:54
 */
public interface IPhotoService {
    public Photo findById(Integer id);

    public int countByUserId(Integer userId);

    public List<Photo> findByUser(Integer userId);

    public Page<Photo> findPageByUser(Integer userId, Integer p, Integer ps);

    public List<Photo> findByAlbum(Integer albumId);

    public Page<Photo> findPageByAlbum(Integer albumId, Integer p, Integer ps);

    public Photo upload(Photo photo, int watermark, MultipartFile file) throws IOException;

    public void incrViewCount(Integer id);

    public void incrDownloadCount(Integer id);
}
