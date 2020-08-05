package com.bazl.dna.lims.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.po.AmPersonalInfo;
import com.bazl.dna.lims.model.vo.AmPersonalInfoVo;

/**
 * Created by Administrator on 2018/12/21.
 */
@Repository
public interface AmPersonalInfoService {
    List<AmPersonalInfo> queryAmPersonalInfoLIst(String orgId);

    List<AmPersonalInfoVo> queryAmPersonalInfoVoList(String orgId);

    void addAmPersonalInfo(AmPersonalInfo amPersonalInfo);

    void deleteAmPersonalInfo(AmPersonalInfo amPersonalInfo);

    void updateAmPersonalInfo(AmPersonalInfo amPersonalInfo);

    AmPersonalInfo queryAmPersonalInfo(String personalId);

    void updateAmPersonalInfoById(AmPersonalInfo amPersonalInfo);

    AmPersonalInfo selectByPersonalId(String personalId);

    List<AmPersonalInfo> queryAmPersonalInfoListOrgId(String orgId);
    
    /**
     * 分页查询信息
     * @param query
     * @param pageInfo
     * @return
     */
    public List<AmPersonalInfoVo> selectVOList(AmPersonalInfoVo query, PageInfo pageInfo);

    /**
     * 根据条件查询总数
     * @param query
     * @return
     */
    public int selectVOCnt(AmPersonalInfoVo query);
}
