package com.bazl.dna.database.nation.converter.service;


import java.util.List;

import com.bazl.dna.database.nation.converter.model.po.LocusInfo;

public interface LocusInfoService {

    public List<LocusInfo> selectByLocusType(String locusType);

    public List<String> selectNameByLocusType(String locusType);

}
	