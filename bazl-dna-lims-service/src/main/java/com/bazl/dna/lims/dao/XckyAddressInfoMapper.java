package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.XckyAddressInfo;
import java.util.List;

public interface XckyAddressInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(XckyAddressInfo record);

    XckyAddressInfo selectByPrimaryKey(String id);

    List<XckyAddressInfo> selectAll();

    int updateByPrimaryKey(XckyAddressInfo record);
    
    XckyAddressInfo selectByOrgId(String orgId);
    
    XckyAddressInfo selectDefault();
}