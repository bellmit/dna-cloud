package com.bazl.dna.database.service.model.po;


import lombok.Data;

import java.io.Serializable;

@Data
public class ToReportInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer toReportCaseCount;
    private Integer toReportMatterCount;
    private Integer toReportPersionCount;

}
