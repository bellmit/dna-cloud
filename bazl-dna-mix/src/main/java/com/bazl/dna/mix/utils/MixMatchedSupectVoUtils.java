package com.bazl.dna.mix.utils;

import com.bazl.dna.mix.model.vo.MixMatchedSupectVo;
import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;

/**
 * @Author: lzn
 * @Date: 2019/7/18 16:49
 * @Version: 1.0
 * 首页混合案件列表
 */
public class MixMatchedSupectVoUtils {


    /**
     * @param mixedSampleGeneVo //混合样本比中单一
     * @return
     */
    public static MixMatchedSupectVo mixMatchedSupectCommonVoS(MixedSampleGeneVo mixedSampleGeneVo) {
        MixMatchedSupectVo mixMatchedSupectVo = new MixMatchedSupectVo();

        mixMatchedSupectVo.setId(mixedSampleGeneVo.getCaseId());
        mixMatchedSupectVo.setCaseNo(mixedSampleGeneVo.getCaseNo());
        mixMatchedSupectVo.setCaseName(mixedSampleGeneVo.getCaseName());
        mixMatchedSupectVo.setSampleName(mixedSampleGeneVo.getSampleName());
        mixMatchedSupectVo.setSampleNo(mixedSampleGeneVo.getSampleNo());
        mixMatchedSupectVo.setMatchedSampleNo(mixedSampleGeneVo.getSampleNobizhong());
        mixMatchedSupectVo.setPersonName(mixedSampleGeneVo.getPersonName());
        mixMatchedSupectVo.setSampleGeneId(mixedSampleGeneVo.getMixedSampleGendId());
        mixMatchedSupectVo.setRatiogeneId(mixedSampleGeneVo.getSinglegeneId());
        mixMatchedSupectVo.setRatio(mixedSampleGeneVo.getRatio());
        mixMatchedSupectVo.setSplitDigit(mixedSampleGeneVo.getSplitDigit());
        mixMatchedSupectVo.setComparisonTime(mixedSampleGeneVo.getComparisonTime());

        return mixMatchedSupectVo;

    }

}
