package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.QuickCompareResultMix;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.vo.QuickCompareResultMixVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 混合快速比对结果表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
@Repository
public interface QuickCompareResultMixMapper extends BaseMapper<QuickCompareResultMix> {

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
