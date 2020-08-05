package com.bazl.dna.mix.model.vo;

import java.io.Serializable;

/**
 * @author wanghaiyang
 * @date 2018/4/1.
 */
public class AbstractBaseVO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
    public int offset;
    public int rows;

    protected T entity;

    public AbstractBaseVO() {
        super();
    }

    public AbstractBaseVO(T entity) {
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


    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

}
