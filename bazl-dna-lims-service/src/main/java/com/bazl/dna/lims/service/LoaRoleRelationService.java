package com.bazl.dna.lims.service;

import com.bazl.dna.lims.model.po.LoaRoleRelation;

import java.util.List;

/**
 * Created by Administrator on 2018/12/21.
 */
public interface LoaRoleRelationService {
    void addLoaRoleRelation(LoaRoleRelation loaRoleRelation);

    void deleteloaRoleRelationByUserId(String userId);

    void updateloaRoleRelationByUserId(String roleId, String userId);

    /**
     * 通过用户id获取角色信息
     * @param userId
     * @return
     */
    List<LoaRoleRelation> listByUserId(String userId);
}
