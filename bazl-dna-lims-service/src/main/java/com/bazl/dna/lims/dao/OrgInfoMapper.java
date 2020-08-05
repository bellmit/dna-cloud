package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.OrgInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgInfoMapper {
    int insert(OrgInfo record);

    List<OrgInfo> selectAll();

    OrgInfo selectByPrimaryKey(String orgId);

    List<OrgInfo> selectDelegateByParentsId(@Param("parentId") String parentId);

    List<OrgInfo> selectAcceptOrgList();

    String getUseridByOrgid(String userId);
    
    /**
    查询分局（统计系统用）
     */
    List<OrgInfo> selectAllList();
}