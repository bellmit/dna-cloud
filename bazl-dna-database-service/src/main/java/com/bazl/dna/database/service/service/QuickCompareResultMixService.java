package com.bazl.dna.database.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.QuickCompareResultMix;
import com.bazl.dna.database.service.model.vo.QuickCompareResultMixVo;

/**
 * <p>
 * 混合快速比对结果表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
public interface QuickCompareResultMixService extends IService<QuickCompareResultMix> {

    int deleteByPrimaryKey(Integer id);

    int insert(QuickCompareResultMix record);

    int insertSelective(QuickCompareResultMix record);

    QuickCompareResultMix selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuickCompareResultMix record);

    int updateByPrimaryKey(QuickCompareResultMix record);

    //通过比对id查询比对结果
    List<QuickCompareResultMixVo> selectByQuickCompareQueueId(Integer quickCompareQueueId);
    //条数
    int selectByQueueIdCount(Integer quickCompareQueueId);
}
