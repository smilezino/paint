package top.wangjun.service;

import com.github.pagehelper.Page;
import top.wangjun.model.Album;

import java.io.IOException;
import java.util.List;

/**
 * @author zino
 * @date 2016-02-08 19:53
 */
public interface IAlbumService {
    public Album findById(Integer id);

    public int add(Album album);

    public int countByUserId(Integer userId);

    public List<Album> queryByUserId(Integer userId);

    public Page<Album> queryPageByUserId(Integer userId, Integer p, Integer ps);

    public int updateCover(Integer albumId, String cover);
}
