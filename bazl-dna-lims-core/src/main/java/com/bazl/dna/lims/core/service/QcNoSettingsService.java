package com.bazl.dna.lims.core.service;

import com.bazl.dna.lims.core.model.po.QcNoSettings;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liuchang on 2020/7/6.
 */
@Repository
public interface QcNoSettingsService {

    /**
     * 根据实验室编码查询信息
     */
     public List<QcNoSettings> selectByLab(String labServerNo);

}
