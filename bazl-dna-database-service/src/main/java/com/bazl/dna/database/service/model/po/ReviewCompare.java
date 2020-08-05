package com.bazl.dna.database.service.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewCompare implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 物证比中人员
     */
    private Integer matterComparePersonCount;//物证比中人员数目
    private Integer regionCompare;//本区比中
    private Integer spanningArea;//跨区比中

    /**
     * 物证比中物证
     */
    private Integer matterComparematterCount;//物证比中物证
    private Integer matterSameRegionCount;//本区比中
    private Integer matterSpanningAreaCount;//跨区比中

}
