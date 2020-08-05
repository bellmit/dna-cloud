package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;

import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConverterDataVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private ConsignmentInfo consignmentInfo;
	private CaseInfo caseInfo;
	private List<ConverterDataSampleAndPersonVo> samplePersonList;
	private List<RelativeAndPerson> relativeAndPersonList;//亲缘信息

	private List<PersonRelativeInfo> personRelativeInfoList;
}
