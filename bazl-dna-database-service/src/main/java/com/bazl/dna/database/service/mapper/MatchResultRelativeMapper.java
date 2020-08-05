package com.bazl.dna.database.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.CompareResult;
import com.bazl.dna.database.service.model.po.MatchResultRelative;
import com.bazl.dna.database.service.model.qo.MatchRelativeDetailQuery;
import com.bazl.dna.database.service.model.qo.MatchRelativeDetailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchRelativeResultQuery;

/**
 * <p>
 * 亲缘比对结果表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultRelativeMapper extends BaseMapper<MatchResultRelative> {

    List<CompareResult> queryRelativeCompareResult(CompareResult compareResult);

    CompareResult queryRelativeCompareinfo(String id);

    int relativeResultCount(MatchRelativeResultQuery query);

    List<MatchRelativeResultQuery> relativeResultInfo(MatchRelativeResultQuery query);

    int relativeDatailCount(MatchRelativeDetailQuery query);

    List<MatchRelativeDetailResultQuery> relativeDatailInfo(MatchRelativeDetailQuery query);
}
