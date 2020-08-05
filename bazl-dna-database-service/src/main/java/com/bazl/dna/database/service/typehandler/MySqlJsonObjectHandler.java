package com.bazl.dna.database.service.typehandler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lizhihua on 2020-03-08.
 */
@MappedTypes(JSONObject.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MySqlJsonObjectHandler extends BaseTypeHandler<JSONObject> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JSONObject jsonObject, JdbcType jdbcType) throws SQLException {
        String jsonValStr = jsonObject.toJSONString();
        preparedStatement.setString(i, jsonValStr);
    }

    @Override
    public JSONObject getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String sqlJson = resultSet.getString(s);
        if (null != sqlJson){
            //JSONObject obj = new JSONObject(true);
            return JSONObject.parseObject(sqlJson, Feature.OrderedField);
        }
        return null;
    }

    @Override
    public JSONObject getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String sqlJson = resultSet.getString(i);
        if (null != sqlJson){
            //return JSONObject.parseObject(sqlJson);
            return JSONObject.parseObject(sqlJson, Feature.OrderedField);
        }
        return null;
    }

    @Override
    public JSONObject getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String sqlJson = callableStatement.getString(i);
        if (null != sqlJson){
            //return JSONObject.parseObject(sqlJson);
            return JSONObject.parseObject(sqlJson, Feature.OrderedField);
        }
        return null;
    }
}
