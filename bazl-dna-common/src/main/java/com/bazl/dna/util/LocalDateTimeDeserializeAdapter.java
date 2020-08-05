package com.bazl.dna.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateTimeDeserializeAdapter extends JsonDeserializer<LocalDateTime> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTimeDeserializeAdapter.class);
	
	@Override
	public LocalDateTime deserialize(JsonParser json, DeserializationContext arg1)
			throws IOException {
		try {
			if (StringUtils.trimToNull(json.getValueAsString()) == null) {
				return null;
			} else {
				Date date = DateUtil.stringToDate(json.getValueAsString(), DateUtil.FULL_TIME_FORMAT);
				return DateUtil.dateToLocalDateTime(date);
			}
		} catch (Exception e) {
			LOGGER.error("Error deserialize:", e);
		}
		return null;
	}

}
