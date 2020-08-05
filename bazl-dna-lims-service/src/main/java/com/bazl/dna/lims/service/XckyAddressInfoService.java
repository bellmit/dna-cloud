package com.bazl.dna.lims.service;

import com.bazl.dna.lims.model.po.XckyAddressInfo;

/**
 * Created by Administrator on 2019/1/27.
 */
public interface XckyAddressInfoService {

    XckyAddressInfo selectByOrgId(String orgId);

    XckyAddressInfo selectDefault();

}
