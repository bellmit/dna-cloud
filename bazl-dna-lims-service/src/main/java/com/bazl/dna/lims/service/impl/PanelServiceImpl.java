package com.bazl.dna.lims.service.impl;

import com.bazl.dna.lims.dao.PanelMapper;
import com.bazl.dna.lims.model.po.Panel;
import com.bazl.dna.lims.service.PanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/3/11.
 */
@Service
public class PanelServiceImpl extends BaseService implements PanelService {

    @Autowired
    PanelMapper panelMapper;

    @Override
    public int deleteByPrimaryKey(String panelid) {
        return panelMapper.deleteByPrimaryKey(panelid);
    }

    @Override
    public int insert(Panel record) {
        return panelMapper.insert(record);
    }

    @Override
    public Panel selectByPrimaryKey(String panelid) {
        return panelMapper.selectByPrimaryKey(panelid);
    }

    @Override
    public List<Panel> selectAll() {
        return panelMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Panel record) {
        return panelMapper.updateByPrimaryKey(record);
    }

}
