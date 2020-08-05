package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.MatchResultYstr;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * ystr样本比中结果信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultYstrService extends IService<MatchResultYstr> {

	/**
     * 获取 MatchResultSameGroupNo
     * @param sampleAId
     * @param sampleBId
     * @return
     */
    public Integer getMatchResultGroupNo(Integer sampleAId, Integer sampleBId);
    
    public void updateMatchResultYstr(MatchResultYstr entity);
}
