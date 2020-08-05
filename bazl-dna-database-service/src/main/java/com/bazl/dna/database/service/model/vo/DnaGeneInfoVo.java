package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;

import com.bazl.dna.database.service.model.po.DnaGeneInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2020-04-12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DnaGeneInfoVo extends DnaGeneInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String geneTypeName;

    private String dnaPanelName;


}
