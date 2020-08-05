package com.bazl.dna.database.service.model.vo;

import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 质控人员Vo
 * Created by lizhihua on 2020-04-12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AlleleFrequencyInfoVo extends AlleleFrequencyInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 位点个数
     */
    private Integer locusCount;

	/**
	 * 种群信息集合
	 */

	private List<AlleleFrequencyInfo>  alleleFrequencyInfoList;


}
