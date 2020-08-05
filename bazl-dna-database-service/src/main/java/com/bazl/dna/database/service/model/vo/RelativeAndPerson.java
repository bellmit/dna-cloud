package com.bazl.dna.database.service.model.vo;

import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RelativeAndPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    private CasePersonInfo casePersonInfo;
    private PersonRelativeInfo personRelativeInfo;



}
