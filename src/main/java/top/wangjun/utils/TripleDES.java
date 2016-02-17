package top.wangjun.utils;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 3DES加密算法
 */
public class TripleDES {

	private static final String DEFAULT_KEY_STRING = "3des_key_string";
	private static final String MCRYPT_TRIPLEDES = "DESede";
	private static final String TRANSFORMATION = "DESede/CBC/PKCS5Padding";

	private static Logger logger = LoggerFactory.getLogger(TripleDES.class);

	public static String getKey(String key) {
		String md5Key = DigestUtils.md5Hex(key);
		return md5Key.substring(0, 24);
	}

	public static Cipher getCipher(String key, int mode) {
		try {
			byte[] keyByte = getKey(key).getBytes();
			IvParameterSpec iv = new IvParameterSpec("00000000".getBytes());
			SecretKey secretKey = new SecretKeySpec(keyByte, MCRYPT_TRIPLEDES);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(mode, secretKey, iv);

			return cipher;
		} catch (Exception e) {
			logger.error("3des encrypt error. key: [{}]", key);
		}
		return null;
	}

	public static String encrypt(String text, String key) {
		try {
			Cipher cipher = getCipher(key, Cipher.ENCRYPT_MODE);
			return byte2hex(cipher.doFinal(text.getBytes()));
		} catch (Exception e) {
			logger.error("3des encrypt error. text: [{}], key: [{}]", text, key);
		}
		return null;
	}

	public static String encrypt(String text) {
		return encrypt(text, DEFAULT_KEY_STRING);
	}

	public static String decrypt(String encryptText, String key) {
		try {
			Cipher cipher = getCipher(key, Cipher.DECRYPT_MODE);
			return new String(cipher.doFinal(hex2byte(encryptText.getBytes())));
		} catch (Exception e) {
			logger.error("3des decrypt error. encryptText: [{}], key: [{}]", encryptText, key);
		}
		return null;
	}

	public static String decrypt(String encryptText) {
		return decrypt(encryptText, DEFAULT_KEY_STRING);
	}

	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString();
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException();
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

}
