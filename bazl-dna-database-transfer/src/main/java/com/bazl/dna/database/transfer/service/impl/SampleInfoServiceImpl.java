package com.bazl.dna.database.transfer.service.impl;

import com.bazl.dna.database.transfer.mapper.SampleInfoMapper;
import com.bazl.dna.database.transfer.model.po.*;
import com.bazl.dna.database.transfer.service.SampleInfoService;
import com.bazl.dna.database.transfer.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/6/9.
 */
@Service
public class SampleInfoServiceImpl extends BaseService implements SampleInfoService {

    @Autowired
    SampleInfoMapper sampleInfoMapper;

    /**
     * 查询dna库中所有样本的入库类型
     */
    @Override
    public List<String> selectSampleTypeByCaseId(String caseId) {
        try {
            return sampleInfoMapper.selectSampleTypeByCaseId(caseId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectSampleTypeByCaseId error, param is " + caseId + ".", ex);
            return null;
        }
    }


    /***
     * 根据案件队列ID获取物证列表
     * @param queueId
     * @return
     */
    @Override
    public List<PhysicalEvidenceModel> selectPhysicalEvidenceByCaseQueueId(String queueId) {
        try {
            return sampleInfoMapper.selectPhysicalEvidenceByQueueId(queueId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectPhysicalEvidenceByCaseQueueId error, param is " + queueId + ".", ex);
            return null;
        }
    }


    /***
     * 根据案件队列ID获取样本列表
     * @param queueId
     * @return
     */
    @Override
    public List<SampleInfoModel> selectSampleInfoListByCaseQueueId(String queueId) {
        try {
            return sampleInfoMapper.selectSampleInfoByQueueId(queueId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectSampleInfoListByCaseQueueId error, param is " + queueId + ".", ex);
            return null;
        }
    }


    @Override
    public SampleInfoModel selectSampleInfoByBarcode(String barcode) {
        try {
            List<SampleInfoModel> sampleInfoModelList = sampleInfoMapper.selectSampleInfoByBarcode(barcode);
            SampleInfoModel sampleInfoModel = null;
            if (ListUtils.isNotEmptyList(sampleInfoModelList)) {
                sampleInfoModel = sampleInfoModelList.get(0);
            }
            return sampleInfoModel;
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectSampleInfoByBarcode error, param is " + barcode + ".", ex);
            return null;
        }
    }


    @Override
    public InstoreSampleInfo selectInstoreSampleInfoBySampleBarcode(String sampleBarcode){
        try {
            return sampleInfoMapper.selectInstoreSampleInfoBySampleBarcode(sampleBarcode.toUpperCase());
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectInstoreSampleInfoBySampleBarcode error, param is " + sampleBarcode + ".", ex);
            return null;
        }
    }

    @Override
    public String selectSingleRelation(String sampleId) {
        try {
            return sampleInfoMapper.selectSingleRelation(sampleId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectSingleRelation error, param is " + sampleId + ".", ex);
            return null;
        }
    }

    @Override
    public List<RelativeSampleInfo> selectRelativeSampleBySampleBarcode(String sampleBarcode){
        try {
            return sampleInfoMapper.selectRelativeSampleBySampleBarcode(sampleBarcode);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectRelativeSampleBySampleBarcode error, param is " + sampleBarcode + ".", ex);
            return null;
        }
    }

    @Override
    public List<RelativeSampleInfo> selectRelativeSampleByTargetPersonId(String id){
        try {
            return sampleInfoMapper.selectRelativeSampleByTargetPersonId(id);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectRelativeSampleByTargetPersonId error, param is " + id + ".", ex);
            return null;
        }
    }

    @Override
    public List<StrDnaLibView> selectStrDnaLibViewBySampleBarcode(String sampleBarcode) {
        try {
            return sampleInfoMapper.selectStrDnaLibViewBySampleBarcode(sampleBarcode);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectStrDnaLibViewBySampleBarcode error, param is " + sampleBarcode + ".", ex);
            return null;
        }
    }

    @Override
    public List<YstrDnaLibView> selectYstrDnaLibViewBySampleBarcode(String sampleBarcode) {
        try {
            return sampleInfoMapper.selectYstrDnaLibViewBySampleBarcode(sampleBarcode);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectYstrDnaLibViewBySampleBarcode error, param is " + sampleBarcode + ".", ex);
            return null;
        }
    }

    @Override
    public List<LimsMarkernames> selectStrMarkernames(String geneType) {
        try {
            return sampleInfoMapper.selectStrMarkernames(geneType);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectStrMarkernames error.", ex);
            return null;
        }
    }

    @Override
    public RelativeDefModel selectRelationByBarcode(String barcode) {
        try {
            return sampleInfoMapper.selectRelationByBarcode(barcode);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectRelationByBarcode error.", ex);
            return null;
        }
    }

    @Override
    public void updateSampleNoAndStateByBarcode(String nationSampleNo, int state, String barcode) {
        try {
            InstoreSampleInfo instoreSampleInfo = new InstoreSampleInfo();
            instoreSampleInfo.setSampleid(barcode);
            instoreSampleInfo.setSampleNo(nationSampleNo);
            instoreSampleInfo.setState(state);
            sampleInfoMapper.updateSampleNoAndStateByBarcode(instoreSampleInfo);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.updateSampleNoAndStateByBarcode error.", ex);
        }
    }


    @Override
    public List<YstrDnaLibView> selectYstrDnaLibViewList(int startCount, int endCount) {
        try {
            return sampleInfoMapper.selectYstrDnaLibViewList(startCount, endCount);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectYstrDnaLibViewList error.", ex);
            return null;
        }
    }

    @Override
    public List<SampleInfoModel> selectInstoreSampleList(int startCount, int endCount) {
        try {
            return sampleInfoMapper.selectInstoreSampleList(startCount, endCount);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectInstoreSampleList error.", ex);
            return null;
        }
    }

    @Override
    public List<PersonInfoModel> getMemberList (String caseId, String personType) {
        try {
            return sampleInfoMapper.getMemberList(caseId, personType);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.getMemberList error.", ex);
            return null;
        }
    }


    @Override
    public HttpSampleInfoModel getSampleinfo(String caseId) {
        return sampleInfoMapper.getSampleinfo(caseId);
    }

    @Override
    public void updateSampleNoAndStateByBarcode2(String nationSampleNo, int state, String barcode) {
        try {
            InstoreSampleInfo instoreSampleInfo = new InstoreSampleInfo();
            instoreSampleInfo.setSampleid(barcode);
            instoreSampleInfo.setSampleNo(nationSampleNo);
            instoreSampleInfo.setState(state);
            sampleInfoMapper.updateSampleNoAndStateByBarcode2(instoreSampleInfo);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.updateSampleNoAndStateByBarcode2 error.", ex);
        }
    }

    @Override
    public List<SampleGeneInfoModel> getSampleGeneListByQueueId(String queueId) {
        try {
            return sampleInfoMapper.getSampleGeneListByQueueId(queueId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.getSampleGeneListByQueueId error.", ex);
            return null;
        }
    }

    @Override
    public List<SampleGeneInfoModel> getCriminalSampleGeneListByQueueId(String queueId) {
        try {
            return sampleInfoMapper.getCriminalSampleGeneListByQueueId(queueId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.getCriminalSampleGeneListByQueueId error.", ex);
            return null;
        }
    }

    @Override
    public SampleInfoModel selectCriminalSampleInfoByPersonId(String personId) {
        try {
            List<SampleInfoModel> sampleInfoModelList = sampleInfoMapper.selectCriminalSampleInfoByPersonId(personId);
            SampleInfoModel sampleInfoModel = null;
            if (ListUtils.isNotEmptyList(sampleInfoModelList)) {
                sampleInfoModel = sampleInfoModelList.get(0);
            }
            return sampleInfoModel;
        }catch (Exception e) {
            logger.error("invoke SampleInfoService.selectCriminalSampleInfoByPersonId error.", e);
            return null;
        }
    }

    @Override
    public void updateCriminalSampleNo(String nationSampleNo, int nationSampleStateTrue, String sampleLabNo) {
        try {
            InstoreSampleInfo instoreSampleInfo = new InstoreSampleInfo();
            instoreSampleInfo.setSampleid(sampleLabNo);
            instoreSampleInfo.setSampleNo(nationSampleNo);
            instoreSampleInfo.setState(nationSampleStateTrue);
            sampleInfoMapper.updateCriminalSampleNo(instoreSampleInfo);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.updateCriminalSampleNo error.", ex);
        }
    }

    @Override
    public List<String> selectCriminalSampleTypeByPersonId(String personId) {
        try {
            return sampleInfoMapper.selectCriminalSampleTypeByPersonId(personId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectCriminalSampleTypeByPersonId error.", ex);
            return null;
        }
    }
}
