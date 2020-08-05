package com.bazl.dna.util.query;

import java.io.Serializable;
import java.util.List;

/***
 * <p>Description: 查询条件设置</p>
 * @author lrm
 * @date 2018年10月8日
 */
public class QueryCriteriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/***
	 * 页码
	 */
	private Integer pageIndex = 1;

	/***
	 * 页大小
	 */
	private Integer pageSize = 15;
	
	/**
	 * 偏移量
	 */
	private int offset;

	/***
	 * 查询条件
	 */
	private List<AttributeBean> whereList;

	/***
	 * 排序条件
	 */
	private AttributeBean order;

	public QueryCriteriaBean() {
		super();
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<AttributeBean> getWhereList() {
		return whereList;
	}

	public void setWhereList(List<AttributeBean> whereList) {
		this.whereList = whereList;
	}

	public AttributeBean getOrder() {
		return order;
	}

	public void setOrder(AttributeBean order) {
		this.order = order;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		this.offset = (pageIndex-1) * pageSize;
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
}
