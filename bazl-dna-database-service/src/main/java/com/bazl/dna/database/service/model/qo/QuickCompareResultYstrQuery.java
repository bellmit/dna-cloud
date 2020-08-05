package com.bazl.dna.database.service.model.qo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.bazl.dna.util.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuickCompareResultYstrQuery extends AbstractQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer quickCompareQueueId;
	private Integer matchedGeneId;
	private Integer matchedLocusCount;
	private Integer diffLocusCount;
	private String matchedGeneDetails;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08")
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private Date matchedDatetime;

	private String matchedStartDatetime;
	private String matchedEndDatetime;

	private String labServerName;
	private String labServerNo;
	private String personType;
	private String personTypeName;
	private String matchSampleNo;
	private String matchSampleName;
	private String matchCaseNo;
	private String matchCaseName;
	private String evidenceFlag;
	private Map<String, Object> geneMap;
	private Integer sampleId;

}
