package top.wangjun.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.wangjun.dao.UserMapper;
import top.wangjun.enums.Status;
import top.wangjun.enums.UserType;
import top.wangjun.model.User;
import top.wangjun.service.IUserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zino
 * @date 2016-02-08 19:54
 */
@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private UserMapper mapper;

	@Override
	public User add(User user) {
		user.setStatus((byte) Status.OPEN.getValue());
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setPwd(DigestUtils.md5Hex(user.getPwd()));
		mapper.insertSelective(user);
		return user;
	}

	@Override
	public User login(Integer id, String password) {
		User user = this.getBetween(id, null, null);
		return user!=null && user.getPwd().equals(password) ? user : null;
	}

	@Override
	public User login(String usernameOrEmail, String password) {
		User user = this.getBetween(null, usernameOrEmail, usernameOrEmail);
		return user!=null && user.getPwd().equals(DigestUtils.md5Hex(password)) ? user : null;
	}

	@Override
	public User admin() {
		User record = new User();
		record.setRole((byte) UserType.ADMIN.getRole());
		return mapper.selectOne(record);
	}

	@Override
	public User getBetween(Integer id, String username, String email) {
		List<User> list = this.getListBetween(id, username, email);
		return list != null && !list.isEmpty() ? list.get(0) : null;
	}

	@Override
	public List<User> getListBetween(Integer id, String username, String email) {
		Example example = new Example(User.class);

		if(id != null) {
			example.or().andEqualTo("id", id);
		}

		if(StringUtils.isNotBlank(username)) {
			example.or().andEqualTo("username", username);
		}

		if(StringUtils.isNotBlank(email)) {
			example.or().andEqualTo("email", email);
		}

		return mapper.selectByExample(example);
	}

	@Override
	public int update(User user) {
		return mapper.updateByPrimaryKeySelective(user);
	}
}
