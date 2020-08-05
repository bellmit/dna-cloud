package com.bazl.dna.mix.service;

import java.util.List;
import java.util.Map;

import com.bazl.dna.mix.model.po.AutoanalysisMix;
import com.bazl.dna.mix.model.po.CaseInfo;
import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:14
 * @Version: 1.0
 */
public interface MatchResultMixSingleService {

    /**
     * 自动分析混合样本基因分型和单一样本分型进行比对
     * @param mixedSampleGeneList
     * @param singleSampleGeneList
     * @param mixSameCount
     * @return
     */
    public Map<String, Object> autoanalysisMixedAndSingleCompare(List<MixedSampleGeneVo> mixedSampleGeneList, List<SingleSampleGeneVo> singleSampleGeneList, int mixSameCount,CaseInfo caseInfo);

    public List<AutoanalysisMix> autoanalysisMixedAndSingle(List<MixedSampleGeneVo> mixedSampleGeneList, List<SingleSampleGeneVo> singleSampleGeneList, int mixSameCount, CaseInfo caseInfo);


}
