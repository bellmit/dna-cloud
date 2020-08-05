package com.bazl.dna.mix.utils;

import com.bazl.dna.mix.model.vo.CaseMixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;
import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: lzn
 * @Date: 2019/7/12 13:55
 * @Version: 1.0
 */
public class InitializationData {

    /**
     * CaseMixedSampleGeneVo
     * 初始化查询条件数据
     * @param query
     * @return
     */
    public static CaseMixedSampleGeneVo caseMixedSampleGeneVoQuery(CaseMixedSampleGeneVo query) {

        //案件编号
        if (StringUtils.isBlank(query.getCaseNo())) {
            query.setCaseNo(null);
        } else {
            query.setCaseNo(query.getCaseNo().trim());
        }

        //案件名称
        if (StringUtils.isBlank(query.getCaseName())) {
            query.setCaseName(null);
        } else {
            query.setCaseName(query.getCaseName().trim());
        }

        //检材编号
        if (StringUtils.isBlank(query.getSampleNo())) {
            query.setSampleNo(null);
        } else {
            query.setSampleNo(query.getSampleNo().trim());
        }

        //检材名称
        if (StringUtils.isBlank(query.getSampleName())) {
            query.setSampleName(null);
        } else {
            query.setSampleName(query.getSampleName().trim());
        }

        return query;
    }
    /**
     * MixedSampleGeneVo
     * 初始化查询条件数据
     * @param query
     * @return
     */
    public static MixedSampleGeneVo mixedSampleGeneVoVoQuery(MixedSampleGeneVo query) {

        //案件编号
        if (StringUtils.isBlank(query.getCaseNo())) {
            query.setCaseNo(null);
        } else {
            query.setCaseNo(query.getCaseNo().trim());
        }

        //案件名称
        if (StringUtils.isBlank(query.getCaseName())) {
            query.setCaseName(null);
        } else {
            query.setCaseName(query.getCaseName().trim());
        }

        //检材编号
        if (StringUtils.isBlank(query.getSampleNo())) {
            query.setSampleNo(null);
        } else {
            query.setSampleNo(query.getSampleNo().trim());
        }

        //检材名称
        if (StringUtils.isBlank(query.getSampleName())) {
            query.setSampleName(null);
        } else {
            query.setSampleName(query.getSampleName().trim());
        }

        return query;
    }
    /**
     * 初始化查询条件数据
     * @param query
     * @return
     */
    public static SingleSampleGeneVo caseSingleSampleGeneVoQuery(SingleSampleGeneVo query) {
        //案件id
        if (StringUtils.isBlank(query.getCaseId())) {
            query.setCaseId(null);
        } else {
            query.setCaseId(query.getCaseId().trim());
        }

        //
        if (StringUtils.isBlank(query.getSampleFlag())) {
            query.setSampleFlag(null);
        } else {
            query.setSampleFlag(query.getSampleFlag().trim());
        }

        return query;
    }
}
