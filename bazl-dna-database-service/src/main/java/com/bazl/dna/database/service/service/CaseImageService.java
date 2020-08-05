package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.CaseImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 案件照片信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface CaseImageService extends IService<CaseImage> {

    /**
     * 根据案件id获取所有案件的现场照片
     * @param caseId
     * @return
     */
    public List<CaseImage> selectListByCaseId(Integer caseId);
}
