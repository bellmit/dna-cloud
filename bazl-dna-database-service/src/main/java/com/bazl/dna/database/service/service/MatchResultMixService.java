package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.MatchResultMix;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * dna混合比对结果信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultMixService extends IService<MatchResultMix> {
	
	/**
     * 获取 MatchResultSameGroupNo
     * @param sampleAId
     * @param sampleBId
     * @return
     */
    Integer getMatchResultGroupNo(Integer sampleAId, Integer sampleBId);
}
