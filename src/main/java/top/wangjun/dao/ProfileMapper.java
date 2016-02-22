package top.wangjun.dao;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.wangjun.model.Profile;

public interface ProfileMapper extends Mapper<Profile> {
    public int update(@Param("userId") Integer userId, @Param("key") String key, @Param("value") String value);
}