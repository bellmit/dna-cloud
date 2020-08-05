package com.bazl.dna.database.service.model.po;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author lizhihua on 2020-03-09.
 */
@Data
public class CompareParams implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 匹配下限
     */
    private int lowestSameLimit;

    /**
     * 容差上限
     */
    private int mostDiffLimit;

    /**
     * 比对目标实验室列表
     */
    private List<String> targetLabServerNo;

    /**
     * 比对目标数据类型列表
     */
    private List<String> targetDataType;

}
