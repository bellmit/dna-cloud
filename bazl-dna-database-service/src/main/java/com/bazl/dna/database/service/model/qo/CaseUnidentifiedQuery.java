package com.bazl.dna.database.service.model.qo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bazl.dna.util.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CaseUnidentifiedQuery extends AbstractQuery implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String caseName;
	private String caseAcceptNo;
	private Integer caseId;
	private String consignmentNo;
	private String consignOrgCode;
	private String consignOrgName;
	private String caseProperty;
	private List<String> clientOrgList;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date consignmentRegDatetime;
	
    private String consignmentRegStartDatetime;
    private String consignmentRegEndDatetime;
	
	private String consignPersonName;
	private String consignPerson1Name;
	private String consignPerson2Name;

}
