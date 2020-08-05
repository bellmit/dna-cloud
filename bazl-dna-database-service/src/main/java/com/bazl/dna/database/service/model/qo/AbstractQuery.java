package com.bazl.dna.database.service.model.qo;

import lombok.EqualsAndHashCode;

/**
 * 查询类
 * Created by lizhihua on 2020-04-12.
 */
@EqualsAndHashCode
public abstract class AbstractQuery {

    /**
     * 当前页码
     */
    protected int pageIndex = 1;

    /**
     * 每页显示条数
     */
    protected int rows = 15;

    /**
     * 偏移量（(pageIndex-1) * rows）
     */
    protected int offset;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        this.offset = (pageIndex-1) * rows;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
