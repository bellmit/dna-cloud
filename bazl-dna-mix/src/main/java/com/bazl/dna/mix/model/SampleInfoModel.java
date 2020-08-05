package com.bazl.dna.mix.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SampleInfoModel {

    private String caseId;

    private List<String> geneIdList;
}
