package com.bazl.dna.util.query;

import java.util.Collection;

import org.springframework.util.StringUtils;

import com.bazl.dna.util.query.Criterion.Operator;


/***
 * <p>Description: 条件构造器-用于创建条件表达式</p>
 * @author lrm
 * @date 2018年7月27日
 */
public class Restrictions {
	
	private Restrictions() {
		
	}
	
	/**
	 * 等于
	 * 
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public static SimpleExpression eq(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression(fieldName, value, Operator.EQ);
	}

	/**
	 * 不等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression ne(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression(fieldName, value, Operator.NE);
	}

	/**
	 * 模糊匹配
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression like(String fieldName, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression(fieldName, value, Operator.LIKE);
	}

	/**
	 * 大于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression gt(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression(fieldName, value, Operator.GT);
	}

	/**
	 * 小于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression lt(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression(fieldName, value, Operator.LT);
	}

	/**
	 * 小于等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression lte(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression(fieldName, value, Operator.LTE);
	}

	/**
	 * 大于等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression gte(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression(fieldName, value, Operator.GTE);
	}

	/**
	 * 并且
	 * 
	 * @param criterions
	 * @return
	 */
	public static LogicalExpression and(Criterion... criterions) {
		return new LogicalExpression(criterions, Operator.AND);
	}

	/**
	 * 或者
	 * 
	 * @param criterions
	 * @return
	 */
	public static LogicalExpression or(Criterion... criterions) {
		return new LogicalExpression(criterions, Operator.OR);
	}

	/**
	 * 包含于
	 * 
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public static LogicalExpression in(String fieldName, Collection<?> value, boolean ignoreNull) {
		if (ignoreNull && (value == null || value.isEmpty())) {
			return null;
		}
		SimpleExpression[] ses = new SimpleExpression[value.size()];
		int i = 0;
		for (Object obj : value) {
			ses[i] = new SimpleExpression(fieldName, obj, Operator.EQ);
			i++;
		}
		return new LogicalExpression(ses, Operator.OR);
	}
}
