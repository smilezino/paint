package top.wangjun.service.impl;

import org.springframework.stereotype.Service;
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
	public int add(Album album) {
		album.setCreateTime(new Date());
		album.setUpdateTime(new Date());
		album.setStatus((byte) Status.OPEN.getValue());
		return mapper.insertSelective(album);
	}

	@Override
	public List<Album> queryByUserId(Integer userId) {
		Album record = new Album();
		record.setUser(userId);
		return mapper.select(record);
	}
}
