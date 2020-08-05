package com.bazl.dna.database.service.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;

/**
 * <p>
 * 快速比对对队列表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
@Repository
public interface QuickCompareQueueService extends IService<QuickCompareQueue> {

    int deleteByPrimaryKey(Integer id);

    int insert(QuickCompareQueue record);

    int insertSelective(QuickCompareQueue record);

    QuickCompareQueue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuickCompareQueue record);

    int updateByPrimaryKey(QuickCompareQueue record);
    
    public List<QuickCompareQueue> findListByCompareMode(int compareMode, String compareStatus, int pageSize);
}
