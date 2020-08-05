package com.bazl.dna.lims.core.service;

import com.bazl.dna.lims.core.model.po.LabExtractKit;
import com.bazl.dna.lims.core.model.po.LabPcrInfo;
import com.bazl.dna.lims.core.model.po.Panel;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/3/11.
 */
public interface PanelService {

    /**
     * 根据主键id删除信息
     * @param panelid
     * @return
     */
    public int deleteByPrimaryKey(String panelid);

    /**
     * 插入信息
     * @param record
     * @return
     */
    public int insert(Panel record);

    /**
     * 根据主键id查询信息
     * @param panelid
     * @return
     */
    public Panel selectByPrimaryKey(String panelid);

    /**
     * 查询所有信息
     * @return
     */
    public List<Panel> selectAll();

    /**
     * 更新信息
     * @param record
     * @return
     */
    public int updateByPrimaryKey(Panel record);
}
