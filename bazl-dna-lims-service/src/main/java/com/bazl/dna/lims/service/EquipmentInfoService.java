package com.bazl.dna.lims.service;

import java.util.List;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.po.EquipmentInfo;
import com.bazl.dna.lims.model.vo.EquipmentInfoVo;

/**
 * @author wanghaiyang
 * @date 2019/3/11.
 */
public interface EquipmentInfoService {

    public List<EquipmentInfoVo> selectEquipmentInfoList(EquipmentInfoVo equipmentInfoVo, PageInfo pageInfo);

    int selectEquipmentInfoCount(EquipmentInfoVo equipmentInfoVo);

    public EquipmentInfo selectByPrimaryKey(String id);

    public void update(EquipmentInfo equipmentInfo);

    public void insert(EquipmentInfo equipmentInfo);

    public List<EquipmentInfo> selectAll();

    public List<EquipmentInfoVo> selectEquipmentScrapInfoList(EquipmentInfoVo equipmentInfoVo, PageInfo pageInfo);

    int selectEquipmentScrapInfoCount(EquipmentInfoVo equipmentInfoVo);

    public EquipmentInfoVo selectById(String id);
}
