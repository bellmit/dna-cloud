package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.AmPersonalInfo;
import com.bazl.dna.lims.model.vo.AmPersonalInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/12/21.
 */
@Repository
public interface AmPersonalInfoMapper {
    List<AmPersonalInfo> queryAmPersonalInfoLIst(@Param("orgId")String orgId);

    List<AmPersonalInfoVo> queryAmPersonalInfoVoList(@Param("orgId")String orgId);

    void addAmPersonalInfo(AmPersonalInfo amPersonalInfo);

    void deleteAmPersonalInfo(AmPersonalInfo amPersonalInfo);

   void updateAmPersonalInfo(AmPersonalInfo amPersonalInfo);

    AmPersonalInfo queryAmPersonalInfo(String personalId);

    void updateAmPersonalInfoById(AmPersonalInfo amPersonalInfo);

    AmPersonalInfo selectByPersonalId(String personalId);

    List<AmPersonalInfo> queryAmPersonalInfoListOrgId(@Param("orgId")String orgId);
    
    /**
     * 分页查询信息
     * @param query
     * @return
     */
    public List<AmPersonalInfoVo> selectVOList(AmPersonalInfoVo query);

    /**
     * 根据条件查询总数
     * @param query
     * @return
     */
    public int selectVOCnt(AmPersonalInfoVo query);
}
