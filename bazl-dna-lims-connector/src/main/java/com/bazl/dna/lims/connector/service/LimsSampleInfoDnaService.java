package com.bazl.dna.lims.connector.service;

import java.util.List;

import com.bazl.dna.lims.connector.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.connector.model.vo.LimsSampleInfoDnaVo;

public interface LimsSampleInfoDnaService {

    List<LimsSampleInfoDna> selectByCaseId(String caseId);

    List<LimsSampleInfoDna> selectListByConsignmentId(String consignmentId);


    List<LimsSampleInfoDna> selectBySampleNo(String sampleNo);


    /**
     * 根据条件查询信息
     * @param limsSampleInfoDna
     * @return
     */
    public List<LimsSampleInfoDna> selectList(LimsSampleInfoDna limsSampleInfoDna);

    public List<LimsSampleInfoDnaVo> selectSampleInfos(String caseId);

    /**
     * 根据委托id
     * @param consignmentId
     * @return
     */
    public List<LimsSampleInfoDna> selectByConsignmentId(String consignmentId);

    /**
     * 根据检材id获取检材数据
     * @param sampleId
     * @return
     */
    List<LimsSampleInfoDna> selectBySampleId(String sampleId);

    /**
     * 判断检材是否入库
     * @param sampleId
     * @return
     */
    public boolean isStoredBySampleId(String sampleId);


}