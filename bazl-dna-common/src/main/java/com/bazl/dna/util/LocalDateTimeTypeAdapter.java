package com.bazl.dna.util;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
	private final DateFormat format = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT);

	public JsonElement serialize(LocalDateTime src, Type arg1, JsonSerializationContext arg2) {
		String dateFormatAsString = format.format(new Date(DateUtil.localDateTimeToDate(src).getTime()));
		return new JsonPrimitive(dateFormatAsString);
	}

	public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The localDateTime should be a string value");
		}
		try {
			if (StringUtils.trimToNull(json.getAsString()) == null) {
				return null;
			} else {
				Date date = format.parse(json.getAsString());
				return DateUtil.dateToLocalDateTime(date);
			}
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
	}

}
