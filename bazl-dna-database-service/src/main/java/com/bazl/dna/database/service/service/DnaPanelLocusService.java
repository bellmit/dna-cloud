package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.bo.PanelInfoModel;
import com.bazl.dna.database.service.model.po.DnaPanelLocus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 试剂盒基因座关系表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface DnaPanelLocusService extends IService<DnaPanelLocus> {
    //查询试剂盒对应基因座信息
    List<DnaPanelLocus> listByPanelId(Integer dnaPanelId);
    //保存本地试剂盒，基因座信息
    public boolean savePanelLoucsInfo(PanelInfoModel panelInfoModel) throws Exception;

    public int isSelectId(DnaPanelLocus dnaPanelLocus);

}
