package top.wangjun.service.impl;

import org.springframework.stereotype.Service;
import top.wangjun.model.User;
import top.wangjun.service.IUserService;

/**
 * @author zino
 * @date 2016-02-08 19:54
 */
@Service
public class UserServiceImpl implements IUserService {
	@Override
	public User admin() {
		return null;
	}
}
