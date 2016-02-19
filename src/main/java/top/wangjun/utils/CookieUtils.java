package top.wangjun.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CookieUtils
 */
public class CookieUtils {
	/**
	 * 获取COOKIE
	 *
	 * @param name
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null)	return null;
		for (Cookie ck : cookies) {
			if (StringUtils.equalsIgnoreCase(name, ck.getName()))
				return ck;
		}
		return null;
	}

	/**
	 * 获取COOKIE
	 *
	 * @param name
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) return null;
		for (Cookie ck : cookies) {
			if (StringUtils.equalsIgnoreCase(name, ck.getName()))
				return ck.getValue();
		}
		return null;
	}

	/**
	 * 设置COOKIE
	 *
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void deleteCookie(HttpServletResponse response, String name) {
		setCookie(response, name, "", 0);
	}
}
