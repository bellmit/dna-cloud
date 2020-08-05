package com.bazl.dna.database.service.typehandler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.bazl.dna.exception.DnaRuntimeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JsonTypeHandler
 * 
 * @author zhaoyong
 * 
 */
public class ObjectJsonTypeHandler<T> extends BaseTypeHandler<T> {

	private static ObjectMapper objectMapper;
	private Class<T> type;
	static {
		objectMapper = new ObjectMapper();
	}

	public ObjectJsonTypeHandler(Class<T> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}

	private T parse(String json) {
		try {
			if (json == null || json.length() == 0) {
				return null;
			}
			return objectMapper.readValue(json, type);
		} catch (IOException e) {
			throw new DnaRuntimeException();
		}
	}

	private String toJsonString(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new DnaRuntimeException();
		}
	}

	@Override
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return (T) parse(rs.getString(columnName));
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return (T) parse(rs.getString(columnIndex));
	}

	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return (T) parse(cs.getString(columnIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int columnIndex, T parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(columnIndex, toJsonString(parameter));
	}
}
