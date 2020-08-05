/**
 * 
 */
package com.bazl.dna.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.CloneFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.Maps;

/**
 * 功能：数据工具类
 * 
 * @author liutao
 * 
 * 
 */
public class DataUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);
	
	private DataUtils() {
		
	}

	public static List<Map<String, Object>> getBeanAnnotationCol(Class<?> clazz) {
		try {
			Field field;
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
			}
			List<Map<String,Object>> list = Lists.newArrayList();
			for (int i = 0; i < fields.length; i++) {
				field = clazz.getDeclaredField(fields[i].getName());
				Column column = field.getAnnotation(Column.class);
				Method[] methods = Column.class.getDeclaredMethods();
				Map<String,Object> map = Maps.newHashMap();
				map.put(Field.class.getSimpleName().toLowerCase(), field.getName());
				if (column != null) {
					for(Method method : methods){
						map.put(method.getName(), method.invoke(column));
					}
				}
				JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
				Method[] joinMethods = JoinColumn.class.getDeclaredMethods();
				if(joinColumn != null){
					for(Method method : joinMethods){
						map.put(method.getName(), method.invoke(joinColumn));
					}
				}
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return Lists.newArrayList();
	}

	/**
	 * 获得文件的扩展名
	 * 
	 * @param fileName
	 * @return String
	 */
	public static String getExtension(String fileName) {
		if (fileName != null) {
			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(i + 1).toLowerCase();
			}
		}
		return "";
	}
	
	/**
	 * 新建目录
	 */
	public static void createFolder(String folderPath) {
		try {
			File myFilePath = new File(folderPath);
			FileUtils.forceMkdir(new File(myFilePath.getParent()));
		} catch (Exception e) {
			LOGGER.error("Error createFolder:", e);
		}
	}
	
	/**
	 * 获得文件的名称
	 * @param f
	 * @return String
	 */
	public static String getFileName(String f) {
		if (f != null) {
			String fileName = f.replace('\\', '/');
			if (fileName.lastIndexOf('/') > 0) {
				fileName = fileName.substring(fileName.lastIndexOf('/') + 1,
						fileName.length());
			}
			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(0, i);
			}
		}
		return "";
	}
	
	/**
	 * 对象转成字符串
	 * @param obj
	 * @return String
	 */
	public static String getString(Object obj) {
		String str = StringUtils.EMPTY;
		if (obj != null) {
			if (obj instanceof String) {
				str = (String) obj;
			} else {
				str = obj.toString();
			}
		}
		return str;
	}
	
	/**
	 * 去除下划线 返回首字母大写字符串
	 * @param name 名称
	 * @return String
	 */
	public static String builderName(String name) {
		if (StringUtils.trimToNull(name) == null) {
			return StringUtils.EMPTY;
		}
		StringBuilder temp = new StringBuilder();
		String[] str = name.split("_");
		if (str.length == 1) {
			temp.append(str[0].substring(0, 1).toUpperCase() + str[0].substring(1));
		} else {
			for (String s : str) {
				temp.append(s.substring(0, 1).toUpperCase());
				temp.append(s.substring(1).toLowerCase());
			}
		}
		return temp.toString();
	}
	
	/**
	 * 去除下划线替换其他字符 返回首字母大写字符串
	 * @param name 名称
	 * @return String
	 */
	public static String builderName(String name, String splitName) {
		if (StringUtils.trimToNull(name) == null) {
			return StringUtils.EMPTY;
		}
		
		String split = null;
		if (splitName == null) {
			split = " ";
		} else {
			split = splitName;
		}
		StringBuilder temp = new StringBuilder();
		String[] str = name.split("_");
		String result = null;
		if (str.length == 1) {
			temp.append(str[0].substring(0, 1).toUpperCase() + str[0].substring(1));
			result = temp.toString();
		} else {
			for (String s : str) {
				temp.append(s.substring(0, 1).toUpperCase());
				temp.append(s.substring(1).toLowerCase());
				temp.append(split);
			}
			result = temp.substring(0, temp.length() - 1);
		}
		return result;
	}
	
	/**
	 * 去除下划线 返回首字母小写字符串
	 * @param name 名称
	 * @return String
	 */
	public static String coderName(String name) {
		String result = builderName(name);
		result = result.substring(0, 1).toLowerCase() + result.substring(1);
		return result;
	}
	
	/**
	 * 去除下划线 返回首字母小写字符串
	 * @param name 名称
	 * @return String
	 */
	public static String coderName(String name, String splitName) {
		if (StringUtils.trimToNull(name) == null) {
			return StringUtils.EMPTY;
		}
		String split = null;
		if (splitName == null) {
			split = " ";
		} else {
			split = splitName;
		}
		StringBuilder temp = new StringBuilder();
		String[] str = name.split("_");
		String result = null;
		if (str.length == 1) {
			temp.append(str[0].substring(0, 1).toLowerCase() + str[0].substring(1));
			result = temp.toString();
		} else {
			for (String s : str) {
				temp.append(s.substring(0, 1).toLowerCase());
				temp.append(s.substring(1).toLowerCase());
				temp.append(split);
			}
			result = temp.substring(0, temp.length() - 1);
		}
		return result;
	}
	
	/**
   	 * 克隆
   	 * @param obj
   	 * @return Object
   	 */
   	public static Object clone(Object obj){
   		try (
   			//将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
   			ByteArrayOutputStream bos = new ByteArrayOutputStream();
   			ObjectOutputStream oos = new ObjectOutputStream(bos);
   			//将流序列化成对象
   			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
   			ObjectInputStream ois = new ObjectInputStream(bis)) {
   			
   			oos.writeObject(obj);
   			return ois.readObject();
   		} catch (Exception e) {
   			throw new CloneFailedException(e);
   		}
   	} 
   	
   	/**
	 * 获取本机ip
	 * @return String
	 */
	public static String getLocalhost() {
		String result = "";
		try {
			InetAddress inet = InetAddress.getLocalHost();
			result = inet.getHostAddress();

			Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			Enumeration<InetAddress> addrs;
			while (networks.hasMoreElements()) {
				addrs = networks.nextElement().getInetAddresses();
				while (addrs.hasMoreElements()) {
					ip = addrs.nextElement();
					if (ip instanceof Inet4Address && ip.isSiteLocalAddress()
							&& !ip.getHostAddress().equals(result)) {
						return ip.getHostAddress();
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error getLocalhost:", e);
		}
		return result;
	}

	private static long getIp2long(String ip) {
		ip = ip.trim();
		String[] ips = ip.split("\\.");
		long ip2long = 0L;
		for (int i = 0; i < 4; ++i) {
			ip2long = ip2long << 8 | Integer.parseInt(ips[i]);
		}
		return ip2long;
	}
	
	public static boolean ipExistsInRange(String ip, String ipSection) {
		ipSection = ipSection.trim();
		ip = ip.trim();
		int idx = ipSection.indexOf('-');
		String beginIP = ipSection.substring(0, idx);
		String endIP = ipSection.substring(idx + 1);
		return getIp2long(beginIP) <= getIp2long(ip) && getIp2long(ip) <= getIp2long(endIP);
	}
   	
   	/**
   	 * 下载文件
   	 * @param file
   	 * @param fileName
   	 * @return
   	 */
   	public static ResponseEntity<byte[]> download(File file, String fileName) {
   		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(file.length());
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
			headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "utf-8")); 
			return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
   		} catch (IOException e) {
   			LOGGER.error(e.getMessage());
   		}
   		return null;
   	}
}