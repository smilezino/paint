package top.wangjun.service;

import top.wangjun.model.Profile;

import java.util.List;

/**
 * 用户设置
 */
public interface IProfileService {
	public int add(Profile profile);
	public List<Profile> getAll();
	public String watermarkText(Integer userId);
	public boolean autoGenerateCover(Integer userId);
	public Profile findByKey(Integer userId, String key);
	public int update(Profile profile);
	public int update(Integer userId, String key, String value);
}
