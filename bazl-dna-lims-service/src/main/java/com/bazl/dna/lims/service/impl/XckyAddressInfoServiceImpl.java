package com.bazl.dna.lims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.dao.XckyAddressInfoMapper;
import com.bazl.dna.lims.model.po.XckyAddressInfo;
import com.bazl.dna.lims.service.XckyAddressInfoService;

/**
 * Created by Administrator on 2019/1/27.
 */
@Service
public class XckyAddressInfoServiceImpl extends BaseService implements XckyAddressInfoService {

    @Autowired
    XckyAddressInfoMapper xckyAddressInfoMapper;


    /**
     * 根据单位id获取现勘地址信息
     * @param orgId
     * @return
     */
    public XckyAddressInfo selectByOrgId(String orgId) {
        try {
            return xckyAddressInfoMapper.selectByOrgId(orgId);
        } catch (Exception ex) {
            logger.error("根据单位id获取现勘地址信息错误！", ex);
            return null;
        }
    }

    /**
     * 获取默认的现勘地址信息
     * @return
     */
    public XckyAddressInfo selectDefault(){
        try {
            return xckyAddressInfoMapper.selectDefault();
        } catch (Exception ex) {
            logger.error("根据单位id获取现勘地址信息错误！", ex);
            return null;
        }
    }

}
