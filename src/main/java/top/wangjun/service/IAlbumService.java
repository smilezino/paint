package top.wangjun.service;

import top.wangjun.model.Album;

import java.util.List;

/**
 * @author zino
 * @date 2016-02-08 19:53
 */
public interface IAlbumService {
	public int add(Album album);
	public List<Album> queryByUserId(Integer userId);
}
