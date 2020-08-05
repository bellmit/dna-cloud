package com.bazl.dna.database.service.model.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@EqualsAndHashCode(callSuper = false)
public class MatchYstrDetailResultQuery extends AbstractQuery implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 案件主键id
     */
    private int id;

    private Integer groupId;

    /**
     *源案件名称
     */
    private String caseName;
    /**
     *比对案件名称
     */
    private String compareCaseName;

    /**
     *源检材名称
     */
    private String sampleName;
    /**
     *比对检材名称
     */
    private String compareSampleName;

    /**
     * 样本类型
     */
    private String sampleType;
    /**
     * 检验人名称
     */
    private String personName;

    /**
     * 比中状态
     */
    private Integer reviewFlag;
    
    private String evidenceFlag;

    /**
     * 比中时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDatetime;
}
