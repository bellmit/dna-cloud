package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.MatchResultMixGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * dna混合比对结果分组信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultMixGroupService extends IService<MatchResultMixGroup> {

	/**
     * 获取sequence
     * @return
     */
    Integer getNextval();
    
}
