package com.bazl.dna.lims.connector.model.vo;

import java.io.Serializable;

public class AbstractBaseVo<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    protected int offset;
    protected int rows;

    protected String orderByClause;

    protected T entity;

    public AbstractBaseVo() {
        super();
    }

    public AbstractBaseVo(T entity) {
        super();
        this.entity = entity;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
