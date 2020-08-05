package com.bazl.dna.util;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateTimeSerializeAdapter extends JsonSerializer<LocalDateTime> {

	@Override
	public void serialize(LocalDateTime arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
		String dateFormatAsString = DateUtil.dateToString(DateUtil.localDateTimeToDate(arg0), DateUtil.FULL_DATE_FORMAT);
		arg1.writeString(dateFormatAsString);
	}

}
