package top.wangjun.utils;

import org.apache.commons.lang.StringUtils;
import top.wangjun.model.User;

/**
 *
 */
public class UserUtils {

	public static String encrypt(User user) {
		if(user == null) return null;
		return TripleDES.encrypt(user.getId() + "|" + user.getPwd(), ContextUtils.getProperty("3des.key"));
	}

	public static User decrypt(String token) {
		if(StringUtils.isBlank(token)) return null;
		String decryptString = TripleDES.decrypt(token, ContextUtils.getProperty("3des.key"));
		if(StringUtils.isBlank(decryptString)) return null;
		String[] parts = decryptString.split("|");
		User user = new User();
		user.setId(Integer.valueOf(parts[0]));
		user.setPwd(parts[3]);
		return user;
	}
}
