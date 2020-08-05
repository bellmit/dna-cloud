package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.dao.QcSettingsMapper;
import com.bazl.dna.lims.core.model.po.QcNoSettings;
import com.bazl.dna.lims.core.service.QcNoSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liuchang on 2020/7/6.
 */
@Service
public class QcNoSettingsServiceImpl  extends BaseService implements QcNoSettingsService {

    @Autowired
    QcSettingsMapper  qcSettingsMapper;

    @Override
    public List<QcNoSettings> selectByLab(String labServerNo) {
        try {
            return qcSettingsMapper.selectByLab(labServerNo);
        }catch (Exception ex){
            logger.error("invoke QcNoSettingsService.selectByLab error!"+ex.getMessage());
        }
        return null;
    }
}
