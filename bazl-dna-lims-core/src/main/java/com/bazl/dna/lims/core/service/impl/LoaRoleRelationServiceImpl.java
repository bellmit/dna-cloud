package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.dao.LoaRoleRelationMapper;
import com.bazl.dna.lims.core.model.po.LoaRoleRelation;
import com.bazl.dna.lims.core.service.LoaRoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/12/21.
 */
@Service
public class LoaRoleRelationServiceImpl implements LoaRoleRelationService{
    @Autowired
    private LoaRoleRelationMapper loaRoleRelationMapper;

    @Override
    public void addLoaRoleRelation(LoaRoleRelation loaRoleRelation) {
        loaRoleRelationMapper.addLoaRoleRelation(loaRoleRelation);
    }

    @Override
    public void deleteloaRoleRelationByUserId(String userId) {
        loaRoleRelationMapper.deleteloaRoleRelationByUserId(userId);
    }

    @Override
    public void updateloaRoleRelationByUserId(String roleId, String userId) {
        loaRoleRelationMapper.updateloaRoleRelationByUserId(roleId,userId);
    }

    /**
     * 通过用户Id获取关联的角色
     * @param userId
     * @return
     */
    @Override
    public List<LoaRoleRelation> listByUserId(String userId) {
        return loaRoleRelationMapper.listByUserId(userId);
    }
}
