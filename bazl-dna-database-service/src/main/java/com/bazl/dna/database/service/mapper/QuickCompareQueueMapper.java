package com.bazl.dna.database.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;

/**
 * <p>
 * 快速比对对队列表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
public interface QuickCompareQueueMapper extends BaseMapper<QuickCompareQueue> {

    int deleteByPrimaryKey(Integer id);

    int insert(QuickCompareQueue record);

    int insertSelective(QuickCompareQueue record);

    QuickCompareQueue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuickCompareQueue record);

    int updateByPrimaryKey(QuickCompareQueue record);
    
    public List<QuickCompareQueue> findListByCompareMode(@Param("compareMode") int compareMode,
			@Param("compareStatus") String compareStatus, @Param("pageSize") int pageSize);
}
