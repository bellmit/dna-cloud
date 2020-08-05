package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.database.service.model.po.CriminalSampleInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  @author lizhihua on 2020-04-12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CriminalSampleInfoVo extends CriminalSampleInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 检材类型名称
     */
    private String sampleTypeName;

    /**
     * 数据入库类型名称
     */
    private String instoreDataTypeName;

    /**
     * 基因信息列表
     */
    private List<DnaGeneInfoVo> dnaGeneInfoVoList;

    /**
     * STR信息
     */
    private DnaGeneInfoVo strDnaGeneInfo;

    /**
     * Y-STR信息
     */
    private DnaGeneInfoVo ystrDnaGeneInfo;

}
