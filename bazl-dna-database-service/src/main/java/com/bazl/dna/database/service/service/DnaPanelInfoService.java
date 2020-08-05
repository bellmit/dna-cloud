package com.bazl.dna.database.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.DnaPanelInfo;
import com.bazl.dna.database.service.model.po.PanelInfoQuery;
import com.bazl.dna.database.service.model.qo.DnaPanelInfoQuery;

import java.util.List;

/**
 * <p>
 * 试剂盒信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface DnaPanelInfoService extends IService<DnaPanelInfo> {

    List<DnaPanelInfo> panelInfoList(DnaPanelInfo panelInfo);

    int insert(DnaPanelInfo panelInfo);

    List<PanelInfoQuery> panelInfoQuery(DnaPanelInfo dnaPanelInfo);
    //根据id查询
    DnaPanelInfo selectById(String dnaPanelId);
    //分页查询基因座信息
    List<DnaPanelInfo> dnaPanelInfoPaginationQuery(DnaPanelInfoQuery dnaPanelInfoQuery);
    //查询总计
    int panelPaginationCount(DnaPanelInfoQuery dnaPanelInfoQuery);
}
