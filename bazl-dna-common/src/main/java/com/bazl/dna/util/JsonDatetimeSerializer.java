package com.bazl.dna.util;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Created by Administrator on 2017/1/7.
 */
public class JsonDatetimeSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date arg0, JsonGenerator arg1, SerializerProvider arg2)
            throws IOException {
    	String dateFormatAsString = DateUtil.dateToString(arg0, DateUtil.FULL_DATE_FORMAT);
        arg1.writeString(dateFormatAsString);
    }
}
