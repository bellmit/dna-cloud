package com.bazl.dna.lims.core.service;

import com.bazl.dna.lims.core.model.po.AmPersonalInfo;
import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaUserInfoService {

    /**
     * 根据用户名查询用户信息
     * @param loginName
     * @return
     */
    public LoaUserInfo selectByUserNameAndAreaCode(String loginName, String areaCode);

    public LoaUserInfo selectByUserNameOnly(String loginName);

    void addLoaUserInfo(LoaUserInfo loaUserInfo);

    List<LoaUserInfo> queryloaUserInfoByPersonalId(String personalId);

    LoaUserInfo queryloaUserInfoByuserId(String userid);

    void deleteloaUserInfoByUserId(String userId);

    void updateloaUserInfoByUserId(AmPersonalInfo amPersonalInfo);

    void forbiddenLoaUserInfo(String personalId, String user_active_flase);

    void startusingLoaUserInfo(String personalId, String user_active_true);
}