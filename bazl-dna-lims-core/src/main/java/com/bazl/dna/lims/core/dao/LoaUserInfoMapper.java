package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AmPersonalInfo;
import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaUserInfoMapper {

    public List<LoaUserInfo> selectAll();

    /**
     * 根据用户名查询用户信息
     * @param loginName
     * @return
     */
    public LoaUserInfo selectByUserNameAndAreaCode(@Param("loginName") String loginName, @Param("areaCode") String areaCode);
    public List<LoaUserInfo> selectByUserNameOnly(@Param("loginName") String loginName);

    void addLoaUserInfo(LoaUserInfo loaUserInfo);

    List<LoaUserInfo> queryloaUserInfoByPersonalId(String personalId);

    LoaUserInfo queryloaUserInfoByuserId(String userid);

    void deleteloaUserInfoByUserId(String userId);

    void updateloaUserInfoByUserId(AmPersonalInfo amPersonalInfo);

    void forbiddenLoaUserInfo(@Param("personalId") String personalId, @Param("useractiveflase") String user_active_flase);

    void startusingLoaUserInfo(@Param("personalId")String personalId,  @Param("useractiveflase") String user_active_true);

}