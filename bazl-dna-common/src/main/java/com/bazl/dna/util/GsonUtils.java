package com.bazl.dna.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

public class GsonUtils {
	
	private GsonUtils() {
		
	}

	protected static final Gson gson = new GsonBuilder()
			.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat(DateUtil.DEFAULT_FORMAT)
			.registerTypeAdapter(Date.class, new DateTypeAdapter()).setDateFormat(DateUtil.FULL_DATE_FORMAT)
			.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).setDateFormat(DateUtil.DEFAULT_FORMAT)
			.create();
	
	/***
	 * 把对象转化成JSON
	 * 
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj) {
		return gson.toJson(obj);
	}

	/***
	 * JSON转对象类型
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToBean(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	/***
	 * JSON转对象类型
	 * 
	 * @param json
	 *            json字符串
	 * @param type
	 *            new TypeToken<List<T>>() {}
	 * @return
	 */
	public static <T> T jsonToBean(String json, TypeToken<T> type) {
		return gson.fromJson(json, type.getType());
	}

}
