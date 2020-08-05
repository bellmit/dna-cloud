package com.bazl.dna.database.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;

/**
 * <p>
 * 自动比对队列表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
public interface AutoCompareQueueMapper extends BaseMapper<AutoCompareQueue> {

	public List<AutoCompareQueue> findListByCompareMode(@Param("compareMode") int compareMode,
			@Param("compareStatus") String compareStatus, @Param("pageSize") int pageSize);
}
