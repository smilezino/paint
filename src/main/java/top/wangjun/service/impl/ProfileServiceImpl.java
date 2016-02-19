package top.wangjun.service.impl;

import org.springframework.stereotype.Service;
import top.wangjun.core.Constants;
import top.wangjun.dao.ProfileMapper;
import top.wangjun.model.Profile;
import top.wangjun.service.IProfileService;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class ProfileServiceImpl implements IProfileService {

	@Resource
	private ProfileMapper mapper;

	@Override
	public List<Profile> getAll() {
		return mapper.selectAll();
	}

	@Override
	public String watermarkText(Integer userId) {
		Profile record = new Profile();
		record.setUser(userId);
		record.setKey(Constants.SETTING_WATERMARK_TEXT);
		Profile profile = mapper.selectOne(record);
		return profile != null ? profile.getValue() : Constants.SETTING_WATERMARK_TEXT_DEFAULT;
	}

	@Override
	public boolean autoGenerateCover(Integer userId) {
		Profile record = new Profile();
		record.setUser(userId);
		record.setKey(Constants.SETTING_AUTO_GENERATE_COVER);
		Profile profile = mapper.selectOne(record);
		return profile == null || "true".equalsIgnoreCase(profile.getValue());
	}
}
