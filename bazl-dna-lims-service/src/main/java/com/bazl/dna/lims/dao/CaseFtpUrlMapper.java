package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.CaseFtpUrl;
import java.util.List;

public interface CaseFtpUrlMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseFtpUrl record);

    CaseFtpUrl selectByPrimaryKey(String id);

    List<CaseFtpUrl> selectAll();

    int updateByPrimaryKey(CaseFtpUrl record);

    List<CaseFtpUrl> findCaseIdByTyye(CaseFtpUrl caseFtpUrlEneity);
}