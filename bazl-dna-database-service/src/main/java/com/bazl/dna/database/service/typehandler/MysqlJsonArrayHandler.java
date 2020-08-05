package com.bazl.dna.database.service.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by lizhihua on 2020-03-08.
 */
@MappedTypes(JSONArray.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MysqlJsonArrayHandler extends BaseTypeHandler<JSONArray> {

        @Override
        public void setNonNullParameter(PreparedStatement preparedStatement, int i, JSONArray jsonArray, JdbcType jdbcType) throws SQLException {
            String jsonValStr = jsonArray.toJSONString();
            preparedStatement.setString(i, jsonValStr);
        }

        @Override
        public JSONArray getNullableResult(ResultSet resultSet, String s) throws SQLException {
            String sqlJson = resultSet.getString(s);
            if (null != sqlJson){
                return JSONObject.parseArray(sqlJson);
            }
            return null;
        }

        @Override
        public JSONArray getNullableResult(ResultSet resultSet, int i) throws SQLException {
            String sqlJson = resultSet.getString(i);
            if (null != sqlJson){
                return JSONObject.parseArray(sqlJson);
            }
            return null;
        }

        @Override
        public JSONArray getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
            String sqlJson = callableStatement.getString(i);
            if (null != sqlJson){
                return JSONObject.parseArray(sqlJson);
            }
            return null;
        }
    }
