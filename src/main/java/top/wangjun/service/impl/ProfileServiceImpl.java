package top.wangjun.service.impl;

import org.springframework.stereotype.Service;
import top.wangjun.core.Constants;
import top.wangjun.dao.ProfileMapper;
import top.wangjun.enums.Status;
import top.wangjun.model.Profile;
import top.wangjun.service.IProfileService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class ProfileServiceImpl implements IProfileService {

    @Resource
    private ProfileMapper mapper;

    @Override
    public int add(Profile profile) {
        return mapper.insert(profile);
    }

    @Override
    public List<Profile> getAll() {
        return mapper.selectAll();
    }

    @Override
    public String watermarkText(Integer userId) {
        Profile profile = this.findByKey(userId, Constants.SETTING_WATERMARK_TEXT);
        if (profile == null) {
            profile = new Profile();
            profile.setUser(userId);
            profile.setKey(Constants.SETTING_WATERMARK_TEXT);
            profile.setValue(Constants.SETTING_WATERMARK_TEXT_DEFAULT);
            profile.setStatus((byte) Status.OPEN.getValue());
            profile.setCreateTime(new Date());
            profile.setUpdateTime(new Date());

            this.add(profile);
            return Constants.SETTING_WATERMARK_TEXT_DEFAULT;
        }
        return profile.getValue();
    }

    @Override
    public boolean autoGenerateCover(Integer userId) {
        Profile profile = this.findByKey(userId, Constants.SETTING_AUTO_GENERATE_COVER);
        if (profile == null) {
            profile = new Profile();
            profile.setUser(userId);
            profile.setKey(Constants.SETTING_AUTO_GENERATE_COVER);
            profile.setValue("true");
            profile.setStatus((byte) Status.OPEN.getValue());
            profile.setCreateTime(new Date());
            profile.setUpdateTime(new Date());
            this.add(profile);
            return true;
        }
        return "true".equalsIgnoreCase(profile.getValue());
    }

    @Override
    public Profile findByKey(Integer userId, String key) {
        Profile record = new Profile();
        record.setUser(userId);
        record.setKey(key);
        return mapper.selectOne(record);
    }

    @Override
    public int update(Profile profile) {
        return mapper.updateByPrimaryKeySelective(profile);
    }

    @Override
    public int update(Integer userId, String key, String value) {
        return mapper.update(userId, key, value);
    }
}
