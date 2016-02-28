package top.wangjun.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import top.wangjun.core.Constants;
import top.wangjun.dao.AlbumMapper;
import top.wangjun.enums.Status;
import top.wangjun.model.Album;
import top.wangjun.service.IAlbumService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zino
 * @date 2016-02-08 20:30
 */

@Service
public class AlbumServiceImpl implements IAlbumService {

    @Resource
    private AlbumMapper mapper;

    @Override
    public Album findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int add(Album album) {
        album.setCover(Constants.DEFALUT_ALBUM_COVER);
        album.setCreateTime(new Date());
        album.setUpdateTime(new Date());
        album.setStatus((byte) Status.OPEN.getValue());
        return mapper.insertSelective(album);
    }

    @Override
    public int countByUserId(Integer userId) {
        Album record = new Album();
        record.setUser(userId);
        return mapper.selectCount(record);
    }

    @Override
    public List<Album> queryByUserId(Integer userId) {
        Album record = new Album();
        record.setUser(userId);
        return mapper.select(record);
    }

    @Override
    public Page<Album> queryPageByUserId(Integer userId, Integer p, Integer ps) {
        PageHelper.startPage(p, ps, "id desc");
        List<Album> list = this.queryByUserId(userId);
        return (Page<Album>) list;
    }

    @Override
    public int updateCover(Integer albumId, String cover) {
        Album record = new Album();
        record.setId(albumId);
        record.setCover(cover);
        return mapper.updateByPrimaryKeySelective(record);
    }
}
