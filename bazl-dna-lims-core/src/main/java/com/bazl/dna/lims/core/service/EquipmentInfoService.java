package com.bazl.dna.lims.core.service;

import com.bazl.dna.lims.core.model.PageInfo;
import com.bazl.dna.lims.core.model.po.EquipmentInfo;
import com.bazl.dna.lims.core.model.po.EquipmentNameInfo;
import com.bazl.dna.lims.core.model.vo.EquipmentInfoVo;
import com.bazl.dna.lims.core.model.vo.EquipmentNameInfoVo;
import org.springframework.data.domain.Page;

import java.util.List;

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
