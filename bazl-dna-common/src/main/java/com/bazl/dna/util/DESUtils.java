package com.bazl.dna.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * DES加密算法工具类
 * 
 * @author
 *
 */
public class DESUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DESUtils.class);

	private static Key key;
	private static final String KEY_STR = "tuspark";// 密钥
	private static final String ALGORITHM = "AES";// 加密类型
	private static Key secretKey;
	private static Map<String, Key> keyMap = Maps.newHashMap();

	static {
		try {
			key = getKey(KEY_STR);
		} catch (Exception e) {
			LOGGER.error("Error:", e);
		}
	}
	
	private DESUtils() {
		
	}

	public static SecretKey getKey(String strKey) {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());
			generator.init(128, secureRandom);
			return generator.generateKey();
		} catch (Exception e) {
			LOGGER.error("初始化密钥出现异常 ", e);
		}
		return null;
	}

	/**
	 * 对str进行DES加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
		try {
			byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] doFinal = cipher.doFinal(bytes);
			return Base64.getEncoder().encodeToString(doFinal);
		} catch (Exception e) {
			LOGGER.error("getEncryptString error:", e);
		}
		return null;
	}

	/**
	 * 对str进行DES解密
	 * 
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str) {
		try {
			byte[] bytes = Base64.getDecoder().decode(str);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] doFinal = cipher.doFinal(bytes);
			return new String(doFinal, StandardCharsets.UTF_8);
		} catch (Exception e) {
			LOGGER.error("getDecryptString error:", e);
		}
		return null;
	}

	public static Key getSecretKey(String saltkey) {
		if (keyMap.get(saltkey) != null) {
			secretKey = keyMap.get(saltkey);
		} else {
			secretKey = getKey(saltkey);
		}
		return secretKey;
	}

	/**
	 * 对str进行DES加密
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public static String getEncryptString(String str, String key) {
		try {
			byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));
			byte[] doFinal = cipher.doFinal(bytes);
			return Base64.getEncoder().encodeToString(doFinal);
		} catch (Exception e) {
			LOGGER.error("getEncryptString error:", e);
		}
		return null;
	}

	/**
	 * 对str进行DES解密
	 * 
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str, String key) {
		try {
			byte[] bytes = Base64.getDecoder().decode(str);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
			byte[] doFinal = cipher.doFinal(bytes);
			return new String(doFinal, StandardCharsets.UTF_8);
		} catch (Exception e) {
			LOGGER.error("getDecryptString error:", e);
		}
		return null;
	}
	
}
