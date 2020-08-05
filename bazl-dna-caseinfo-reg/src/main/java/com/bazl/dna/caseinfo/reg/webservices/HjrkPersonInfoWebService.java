package com.bazl.dna.caseinfo.reg.webservices;

import java.util.List;

import javax.jws.WebService;

import com.bazl.dna.lims.model.po.HjrkPersonInfo;

/**
 * Created by wangliu on 2018/6/12.
 */
@WebService
public interface HjrkPersonInfoWebService {

    /**
     * 根据身份证号获取人员信息(毕节)
     * @return
     */
    public List<HjrkPersonInfo> selectListByPid(String pid);

    /**
     * 根据身份证号获取人员信息(北京)
     * @return
     */
    public List<HjrkPersonInfo> selectListByPid2(String pid);

}
