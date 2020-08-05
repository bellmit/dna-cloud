package com.bazl.dna.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin4jUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Pinyin4jUtils.class);
	
	public enum Type {  
		UPPERCASE,			  //全部大写  
		LOWERCASE,			  //全部小写  
	}
	
	/**
	 * 将汉字转换为全拼(大写)
	 * @param src 转换内容
	 * @return String 转换后的定符串
	 */
	public static String toPinYinUppercase(String src){
		return toPinYin(src,Type.UPPERCASE);
	}
	
	/**
	 * 将汉字转换为全拼(小写)
	 * @param src 转换内容
	 * @return String 转换后的定符串
	 */
	public static String toPinYinLowercase(String src){
		return toPinYin(src,Type.LOWERCASE);
	}
	
	/**
	 * 提取每个汉字的首字母(大写)
	 * 
	 * @param src 转换内容
	 * @return String 首字母大小字符串
	 */
	public static String toPinYinUppercaseInitials(String src) {
		return toPinYinInitials(src,Type.UPPERCASE);
	}
	
	/**
	 * 提取每个汉字的首字母(小写)
	 * 
	 * @param src 转换内容
	 * @return String 首字母大小字符串
	 */
	public static String toPinYinLowercaseInitials(String src) {
		return toPinYinInitials(src,Type.LOWERCASE);
	}

	/***
	 * 获取拼间首字母
	 * @param src 转换内容
	 * @param type 大小写类型,默认大写
	 * @return
	 */
	public static String toPinYinInitials(String src,Type type) {
		StringBuilder convert = new StringBuilder();
		for (int j = 0; j < src.length(); j++) {
			char word = src.charAt(j);
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert.append(pinyinArray[0].charAt(0));
			} else {
				convert.append(word);
			}
		}
		if(type == Type.LOWERCASE) { 
			return convert.toString().toLowerCase();
		} else{  
			return convert.toString().toUpperCase();
		}
	}
	
	/**
	 * 将汉字转换为全拼
	 * @param src 转换内容
	 * @param type 大小写类型,默认大写
	 * @return String 转换后的定符串
	 */
	public static String toPinYin(String src,Type type) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder t4 = new StringBuilder();
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					t4.append(t2[0]);// 取出该汉字全拼的第一种读音并连接到字符串t4后
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					t4.append(Character.toString(t1[i]));
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			LOGGER.error("Error toPinYin:", e);
		}
		if(type == Type.LOWERCASE) { 
			return t4.toString().toLowerCase();
		} else{  
			return t4.toString().toUpperCase();
		}
	}

	/**
	 * 将字符串转换成ASCII码
	 * 
	 * @param cnStr
	 * @return String
	 */
	public static String getCnASCII(String cnStr) {
		StringBuilder strBuf = new StringBuilder();
		// 将字符串转换成字节序列
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// 将每个字符转换成ASCII码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

}
