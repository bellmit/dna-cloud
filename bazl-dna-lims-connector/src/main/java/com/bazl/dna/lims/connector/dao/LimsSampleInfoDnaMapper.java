package com.bazl.dna.lims.connector.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.connector.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.connector.model.vo.LimsSampleInfoDnaVo;

@Repository
public interface LimsSampleInfoDnaMapper {

    void insertSampleInfoDna(LimsSampleInfoDna sampleInfoDna);

    void updateSampleInfoDna(LimsSampleInfoDna sampleInfoDna);


    void deleteBySampleId(String sampleId);

    LimsSampleInfoDna selectById(String sampleId);

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

    public LimsSampleInfoDna isStoredBySampleId(String sampleId);

}