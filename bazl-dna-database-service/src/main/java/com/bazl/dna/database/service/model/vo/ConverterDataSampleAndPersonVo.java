package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.CriminalPersonInfo;
import com.bazl.dna.database.service.model.po.CriminalSampleInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConverterDataSampleAndPersonVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private DnaSampleInfo dnaSampleInfo;
	private CasePersonInfo casePersonInfo;

	private CriminalPersonInfo criminalPersonInfo;
	private CriminalSampleInfo criminalSampleInfo;
	
	private List<DnaStrGeneInfo> dnaStrGeneInfoList;
	private List<DnaYstrGeneInfo> dnaYstrGeneInfoList;
	
}
