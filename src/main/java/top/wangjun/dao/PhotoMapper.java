package top.wangjun.dao;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.wangjun.model.Photo;

public interface PhotoMapper extends Mapper<Photo> {
	public void incrViewCount(@Param("id") Integer id);
	public void incrDownloadCount(@Param("id") Integer id);
}