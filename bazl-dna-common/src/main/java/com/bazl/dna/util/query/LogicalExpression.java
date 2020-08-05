package com.bazl.dna.util.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.compress.utils.Lists;

/***
 * <p>Description: 逻辑条件表达式 用于复杂条件时使用，如但属性多对应值的OR查询等</p>
 * @author lrm
 * @date 2018年9月27日
 */
public class LogicalExpression implements Criterion {
	private Criterion[] criterion; // 逻辑表达式中包含的表达式
	private Operator operator; // 计算符

	public LogicalExpression(Criterion[] criterions, Operator operator) {
		this.criterion = criterions;
		this.operator = operator;
	}

	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = Lists.newArrayList();
		for (int i = 0; i < this.criterion.length; i++) {
			predicates.add(this.criterion[i].toPredicate(root, query, builder));
		}
		if (Operator.OR.equals(operator)) {
			return builder.or(predicates.toArray(new Predicate[predicates.size()]));
		}
		return null;
	}

}
