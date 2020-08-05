package com.bazl.dna.util;

import java.util.Arrays;
import java.util.List;

import com.bazl.dna.util.query.AttributeBean;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;
import com.bazl.dna.util.query.Restrictions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;


/***
 * <p>Description: 查询工具类 </p>
 * @author lrm
 * @date 2018年9月27日
 */
public class QueryUtils {

	public static final String PARAM_ORDER_ASC = "ASC";
	public static final String PARAM_ORDER_DESC = "DESC";

	/***
	 * EQ, NE, LIKE, GT, LT, GTE, LTE
	 */
	public static final String PARAM_QUERY_EQ = "EQ";
	public static final String PARAM_QUERY_LIKE = "LIKE";
	public static final String PARAM_QUERY_NE = "NE";
	public static final String PARAM_QUERY_GT = "GT";
	public static final String PARAM_QUERY_LT = "LT";
	public static final String PARAM_QUERY_GTE = "GTE";
	public static final String PARAM_QUERY_LTE = "LTE";
	public static final String PARAM_QUERY_IN = "IN";
	public static final String PARAM_QUERY_AND = " and ";

	private QueryUtils() {
		
	}
	
	/***
	 * 构造查询条件
	 * 
	 * @param data
	 * @return
	 */
	public static <T> Criteria<T> buildCriteria(QueryCriteriaBean data) {
		Criteria<T> criteria = new Criteria<>();
		if (data!=null && data.getWhereList() != null && !data.getWhereList().isEmpty()) {
			List<AttributeBean> whereList = data.getWhereList();
			for (AttributeBean attr : whereList) {
				String key = attr.getKey();
				String opt = StringUtils.isNotEmpty(attr.getOpt())?attr.getOpt().toUpperCase():PARAM_QUERY_EQ;
				String value = attr.getVal();
				if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value))
					continue;
				if (PARAM_QUERY_LIKE.equals(opt)) {
					criteria.add(Restrictions.like(key, value));
				} else if (PARAM_QUERY_NE.equals(opt)) {
					criteria.add(Restrictions.ne(key, value));
				} else if (PARAM_QUERY_GT.equals(opt)) {
					criteria.add(Restrictions.gt(key, value));
				} else if (PARAM_QUERY_LT.equals(opt)) {
					criteria.add(Restrictions.lt(key, value));
				} else if (PARAM_QUERY_LTE.equals(opt)) {
					criteria.add(Restrictions.lte(key, value));
				} else if (PARAM_QUERY_GTE.equals(opt)) {
					criteria.add(Restrictions.gte(key, value));
				} else if (PARAM_QUERY_IN.equals(opt)) {
					criteria.add(Restrictions.in(key, Arrays.asList(value.split(",")), true));
				} else {
					criteria.add(Restrictions.eq(key, value));
				}
			}
		}
		return criteria;
	}

	/**
	 * 构造分页与排序内容
	 * @param data
	 * @return
	 */
	public static PageRequest buildPageRequest(QueryCriteriaBean data) {
		Sort sort = null;
		Integer pageNum = 1;
		Integer pageSize = Integer.MAX_VALUE;
		if(data!=null){
			AttributeBean order = data.getOrder();
			if (order != null && StringUtils.isNotEmpty(order.getKey())) {
				if(PARAM_ORDER_DESC.equalsIgnoreCase(order.getVal())){
					sort = Sort.by(Direction.DESC, order.getKey().split(","));
				}else{
					sort = Sort.by(Direction.ASC, order.getKey().split(","));
				}
			}
			pageNum = data.getPageIndex()==null?pageNum:data.getPageIndex();
			pageSize = data.getPageSize()==null?pageSize:data.getPageSize();
		}
		if(sort == null){
			return PageRequest.of(pageNum - 1, pageSize);
		}else{
			return PageRequest.of(pageNum - 1, pageSize, sort);
		}
	}
	
	
	/***
	 * 构造查询条件字符串
	 * @param whereList
	 * @return
	 */
	public static String buildWhereCondtion(List<AttributeBean> whereList) {
		StringBuilder sb = new StringBuilder();
		for (AttributeBean attr : whereList) {
			String key = attr.getKey();
			String value = attr.getVal();
			String opt = attr.getOpt();
			if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value))
				continue;
			if (PARAM_QUERY_LIKE.equals(opt)) {
				sb.append(PARAM_QUERY_AND+key+" like '%"+value+"%'");
			} else if (PARAM_QUERY_NE.equals(opt)) {
				sb.append(PARAM_QUERY_AND+key+" != '"+value+"'");
			} else if (PARAM_QUERY_GT.equals(opt)) {
				sb.append(PARAM_QUERY_AND+key+" > '"+value+"'");
			} else if (PARAM_QUERY_LT.equals(opt)) {
				sb.append(PARAM_QUERY_AND+key+" < '"+value+"'");
			} else if (PARAM_QUERY_LTE.equals(opt)) {
				sb.append(PARAM_QUERY_AND+key+" <= '"+value+"'");
			} else if (PARAM_QUERY_GTE.equals(opt)) {
				sb.append(PARAM_QUERY_AND+key+" >= '"+value+"'");
			} else if (PARAM_QUERY_IN.equals(opt)) {
				String inValStr = getInCondtion(Arrays.asList(value.split(",")));
				sb.append(PARAM_QUERY_AND+key+" in ("+inValStr+")");
			} else {
				sb.append(PARAM_QUERY_AND+key+" = '"+value+"'");
			}
		}
		return sb.toString();
	}
	
	/***
	 * 把list<String>转换成in查询str
	 * 
	 * @param list ['val1','val2','val3']
	 * @return  'val1','val2','val3'
	 */
	public static String getInCondtion(List<String> list) {
		String result = null;
		if (list != null && !list.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (String str : list) {
				sb.append("'" + str + "',");
			}
			result = sb.toString();
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}
