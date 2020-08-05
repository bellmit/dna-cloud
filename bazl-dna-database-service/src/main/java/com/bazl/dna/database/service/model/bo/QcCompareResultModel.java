package com.bazl.dna.database.service.model.bo;

import com.bazl.dna.database.algorithm.result.StrSameCompareResult;
import com.bazl.dna.database.service.model.po.QcSampleInfo;

import java.io.Serializable;

/**
 * 质控比对结果表
 * Created by lizhihua on 2020-03-03.
 */
public class QcCompareResultModel implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * 待比对的质控样本信息
     */
    QcCompareSampleModel qcCompareSampleModel;


    /**
     * 质控样本信息
     */
    private QcSampleInfo qcSampleInfo;


    /**
     * 比中信息
     */
    StrSameCompareResult strSameCompareResult;

    public QcSampleInfo getQcSampleInfo() {
        return qcSampleInfo;
    }

    public void setQcSampleInfo(QcSampleInfo qcSampleInfo) {
        this.qcSampleInfo = qcSampleInfo;
    }

    public QcCompareSampleModel getQcCompareSampleModel() {
        return qcCompareSampleModel;
    }

    public void setQcCompareSampleModel(QcCompareSampleModel qcCompareSampleModel) {
        this.qcCompareSampleModel = qcCompareSampleModel;
    }

    public StrSameCompareResult getStrSameCompareResult() {
        return strSameCompareResult;
    }

    public void setStrSameCompareResult(StrSameCompareResult strSameCompareResult) {
        this.strSameCompareResult = strSameCompareResult;
    }
}
