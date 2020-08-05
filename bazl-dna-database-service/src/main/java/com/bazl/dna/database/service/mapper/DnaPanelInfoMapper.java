package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.DnaPanelInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.qo.DnaPanelInfoQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 试剂盒信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface DnaPanelInfoMapper extends BaseMapper<DnaPanelInfo> {

    List<DnaPanelInfo> panelInfoQueryList(DnaPanelInfo dnaPanelInfo);

    /**
     * 分页查询试剂盒信息
     * @param dnaPanelInfoQuery
     * @return
     */
    List<DnaPanelInfo> panelPaginationQuery(DnaPanelInfoQuery dnaPanelInfoQuery);

    /**
     * 分页查询总计试剂盒信息
     * @param dnaPanelInfoQuery
     * @return
     */
    int panelPaginationCount(DnaPanelInfoQuery dnaPanelInfoQuery);


}
