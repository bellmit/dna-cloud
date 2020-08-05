package com.bazl.dna.database.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.database.service.model.po.CompareResult;
import com.bazl.dna.database.service.model.po.MatchResultRelative;
import com.bazl.dna.database.service.model.qo.MatchRelativeDetailQuery;
import com.bazl.dna.database.service.model.qo.MatchRelativeDetailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchRelativeResultQuery;

/**
 * <p>
 * 亲缘比对结果表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultRelativeService extends IService<MatchResultRelative> {

    int relativeResultCount(MatchRelativeResultQuery query);

    List<MatchRelativeResultQuery> relativeResultInfo(MatchRelativeResultQuery query);

    int relativeDatailCount(MatchRelativeDetailQuery query);

    List<MatchRelativeDetailResultQuery> relativeDatailInfo(MatchRelativeDetailQuery query);
    
    List<CompareResult> queryRelativeCompareResult(PageInfo pageInfo, CompareResult compareResult);

    CompareResult queryRelativeCompareinfo(String id);
    
    public void updateMatchResultRelative(MatchResultRelative entity);
}
