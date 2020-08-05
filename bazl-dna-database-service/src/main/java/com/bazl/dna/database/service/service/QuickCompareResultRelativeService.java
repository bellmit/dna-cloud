package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.QuickCompareResultRelative;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.vo.QuickCompareResultRelativeVo;

import java.util.List;

/**
 * <p>
 * 亲缘快速比对结果表（含亲缘三联体、亲缘单亲比对） 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
public interface QuickCompareResultRelativeService extends IService<QuickCompareResultRelative> {

    //通过比对id查询比对结果
    List<QuickCompareResultRelativeVo> selectByQuickCompareQueueId(Integer quickCompareQueueId);
    //条数
    int selectByQueueIdCount(Integer quickCompareQueueId);
}
