package com.bazl.dna.common;

import java.io.Serializable;
import java.util.List;

public class PageInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int curPage = 1;// 当前页码
	private long pageCount;// 总页数
	private int evePageRecordCnt = 15;// 每页记录数
	private int page = 1;// 跳转页码
	private int prePage;
	private long nextPage;
	private long allRecordCount;
	private int star;
	private List<?> dataList;
	
	public PageInfo() {
		
	}
	
	public PageInfo(int curPage, long allRecordCount) {
		this.curPage = curPage;
		this.pageCount = (allRecordCount-1) / evePageRecordCnt + 1;
		this.allRecordCount = allRecordCount;
		
		getStar();
		this.prePage = this.curPage - 1;
		if (this.prePage <= 0)
			this.prePage = this.curPage;
		this.nextPage = this.curPage + 1;
		if (this.nextPage > this.pageCount)
			this.nextPage = this.pageCount;
	}
	
	public PageInfo(int curPage, long allRecordCount, long pageCount) {
		this.curPage = curPage;
		this.allRecordCount = allRecordCount;
		this.pageCount = pageCount;
		
		getStar();
		this.prePage = this.curPage - 1;
		if (this.prePage <= 0)
			this.prePage = this.curPage;
		this.nextPage = this.curPage + 1;
		if (this.nextPage > this.pageCount)
			this.nextPage = this.pageCount;
	}

	public int getEvePageRecordCnt() {
		return evePageRecordCnt;
	}

	public void setEvePageRecordCnt(int evePageRecordCnt) {
		this.evePageRecordCnt = evePageRecordCnt;
	}

	public long getAllRecordCount() {
		return allRecordCount;
	}

	public void setAllRecordCount(long allRecordCount) {
		this.pageCount = (allRecordCount - 1) / evePageRecordCnt + 1;
		this.allRecordCount = allRecordCount;
	}

	public long getNextPage() {
		return nextPage;
	}

	public void setNextPage(long nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public int getStar() {
		this.star = (this.curPage - 1) * this.evePageRecordCnt;
		return star;
	}
	
	public void setStar(int star) {
		this.star = star;
	}
	
	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

	public PageInfo updatePageInfo(long allRecordCount) {
        this.setAllRecordCount((int)allRecordCount);
        if(allRecordCount%evePageRecordCnt==0){
            this.setPageCount((int)allRecordCount/evePageRecordCnt);
        }else{
            this.setPageCount((int)allRecordCount/evePageRecordCnt+1);
        }

        this.setCurPage(page);
        if(this.curPage==0||this.curPage==1){
            this.setPrePage(1);
        }else{
            this.setPrePage(this.curPage-1);
        }
        if(this.curPage==pageCount){
            this.setNextPage(this.getCurPage());
        }else{
            this.setNextPage(this.getCurPage()+1);
        }
        if(pageCount==0){
            pageCount=1;
        }
        return this;
    }
}
