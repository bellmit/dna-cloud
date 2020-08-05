package com.bazl.dna.database.service.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StrCompare extends Model<StrCompare> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sampleNo;
    private String sampleName;
    private String instoreDataType;//比对条件
    private String labServerInfo;
    private String lowestSameLimit;//匹配下限
    private String mostDiffLimit;//容差
    private String geneInfo;//基因信息
    private String populationName;//种群名称

}
