package com.bazl.dna.database.service.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class KinshipCompare extends Model<KinshipCompare> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sampleNo;
    private String sampleName;
    private String instoreDataType;
    private String labServerInfo;
    private String lowestSameLimit;
    private String mostDiffLimit;
    private String populationName;
    private String fatherGeneInfo;
    private String motherGeneInfo;
    private String childGeneInfo;

}
