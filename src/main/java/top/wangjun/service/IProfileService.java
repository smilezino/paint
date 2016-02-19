package top.wangjun.service;

import top.wangjun.model.Profile;

import java.util.List;

/**
 * 用户设置
 */
public interface IProfileService {
	public List<Profile> getAll();
	public String watermarkText(Integer userId);
	public boolean autoGenerateCover(Integer userId);
}
