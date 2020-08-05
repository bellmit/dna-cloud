package com.bazl.dna.lims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.dao.FugitivesInfoMapper;
import com.bazl.dna.lims.dao.FugitivesMiddleTableMapper;
import com.bazl.dna.lims.dao.LimsCaseInfoMapper;
import com.bazl.dna.lims.dao.LimsConsignmentInfoMapper;
import com.bazl.dna.lims.dao.LimsPerosnRelationMapper;
import com.bazl.dna.lims.dao.LimsPersonInfoMapper;
import com.bazl.dna.lims.dao.LimsSampleInfoDnaMapper;
import com.bazl.dna.lims.dao.OrgInfoMapper;
import com.bazl.dna.lims.dao.PersonDetailMapper;
import com.bazl.dna.lims.model.bo.DelegateDataModel;
import com.bazl.dna.lims.model.po.FugitivesInfo;
import com.bazl.dna.lims.model.po.FugitivesMiddleTable;
import com.bazl.dna.lims.model.po.LimsCaseInfo;
import com.bazl.dna.lims.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.model.po.LimsPerosnRelation;
import com.bazl.dna.lims.model.po.LimsPersonInfo;
import com.bazl.dna.lims.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.po.PersonDetail;
import com.bazl.dna.lims.model.po.QueueSample;
import com.bazl.dna.lims.service.LimsConsignmentInfoService;
import com.bazl.dna.lims.service.QueueSampleService;
import com.bazl.dna.lims.service.SeqNoGenerateService;
import com.bazl.dna.lims.utils.DateUtils;
import com.bazl.dna.lims.utils.ImgUtils;
import com.bazl.dna.lims.utils.ListUtils;
import com.bazl.dna.lims.utils.UplodFtpUtils;


/**
 * Created by hj on 2018/12/20.
 */
@Service
public class LimsConsignmentInfoServiceImpl extends BaseService implements LimsConsignmentInfoService {

    @Autowired
    LimsConsignmentInfoMapper limsConsignatioInfoMapper;

    @Autowired
    LimsCaseInfoMapper limsCaseInfoMapper;

    @Autowired
    LimsPersonInfoMapper limsPersonInfoMapper;

    @Autowired
    LimsSampleInfoDnaMapper limsSampleInfoDnaMapper;

    @Autowired
    PersonDetailMapper personDetailMapper;

    @Autowired
    LimsPerosnRelationMapper limsPerosnRelationMapper;

    @Autowired
    SeqNoGenerateService seqNoGenerateService;
    
    @Autowired
    OrgInfoMapper orgInfoMapper;
    @Autowired
    QueueSampleService queueSampleService;
    @Autowired
    FugitivesInfoMapper fugitivesInfoMapper;
    @Autowired
    FugitivesMiddleTableMapper fugitivesMiddleTableMapper;
    @Autowired
    LimsConsignmentInfoMapper limsConsignmentInfoMapper;

    @Value("${personImg}")
    private String personImg;
    @Value("${sampleImg}")
    private String sampleImg;
    @Value("${ftpIp}")
    private String ftpIp;
    @Value("${ftpPort}")
    private String ftpPort;
    @Value("${ftpPersonImg}")
    private String ftpPersonImg;
    @Value("${ftpSampleImg}")
    private String ftpSampleImg;
    @Value("${ftpUser}")
    private String ftpUser;
    @Value("${ftpPassword}")
    private String ftpPassword;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, String> submitDelegate(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIdWzs, String sampleIds, String evaluationCenterId) {
        Map<String, String> result = new HashMap<>();
        try {

            if (ListUtils.isNull(sampleIdWzs)) {
                String sampleIdWz = sampleIdWzs.substring(1);
                String[] sampleIdWzsArr = sampleIdWz.split(",");
                for (String string : sampleIdWzsArr) {
                    LimsSampleInfoDna limsSampleInfoDna = limsSampleInfoDnaMapper.selectById(string);
                    limsSampleInfoDna.setSampleId(string);
                    limsSampleInfoDna.setDeleteFlag("1");
                    limsSampleInfoDna.setUpdateDatetime(new Date());
                    limsSampleInfoDna.setUpdatePerson(operateUser.getLoginName());
                    limsSampleInfoDnaMapper.updateSampleInfoDna(limsSampleInfoDna);
                }
            }

            if (ListUtils.isNull(sampleIds)) {
                String personId = sampleIds.substring(1);
                String[] sampleIdsArr = personId.split(",");
                for (String string : sampleIdsArr) {
                    LimsSampleInfoDna limsSampleInfoDna = limsSampleInfoDnaMapper.selectById(string);
                    limsSampleInfoDna.setSampleId(string);
                    limsSampleInfoDna.setDeleteFlag("1");
                    limsSampleInfoDna.setUpdateDatetime(new Date());
                    limsSampleInfoDna.setUpdatePerson(operateUser.getLoginName());
                    limsSampleInfoDnaMapper.updateSampleInfoDna(limsSampleInfoDna);
                }
            }

            if (ListUtils.isNull(personIds)) {
                String personId = personIds.substring(1);
                String[] personIdsArr = personId.split(",");
                for (String string : personIdsArr) {
                    LimsPersonInfo limsPersonInfo = limsPersonInfoMapper.selectPersonInfoById(string);
                    if(null != limsPersonInfo){
                        LimsPerosnRelation limsPerosnRelation = limsPerosnRelationMapper.selectBySourcePersonId(limsPersonInfo.getPersonId());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfo.getPersonId());
                        limsPerosnRelation.setDeleteFlag("1");
                        limsPerosnRelation.setUpdateDatetime(new Date());
                        limsPerosnRelation.setUpdatePerson(operateUser.getLoginName());
                        limsPerosnRelationMapper.updatePerosnRelation(limsPerosnRelation);
                        PersonDetail personDetail = personDetailMapper.selectByDetailId(limsPersonInfo.getPersonDetailId());
                        personDetail.setPersonDetailId(limsPersonInfo.getPersonDetailId());
                        personDetail.setDeleteFlag("1");
                        personDetail.setUpdatePerson(operateUser.getLoginName());
                        personDetail.setUpdateDatetime(new Date());
                        personDetailMapper.updatePersonDetail1(personDetail);
                        limsPersonInfo.setPersonId(string);
                        limsPersonInfo.setDeleteFlag("1");
                        limsPersonInfo.setUpdateDatetime(new Date());
                        limsPersonInfo.setUpdatePerson(operateUser.getLoginName());
                        limsPersonInfoMapper.updatePersonInfo(limsPersonInfo);
                    }
                }
            }

            //添加案件信息
            LimsCaseInfo caseInfoDna = delegateDataModel.getCaseInfoDna();
            if (StringUtils.isBlank(caseInfoDna.getCaseId())) {
                String caseId = UUID.randomUUID().toString();
                caseInfoDna.setCaseId(caseId);
                caseInfoDna.setCaseStatus("01");
                caseInfoDna.setCreateDatetime(new Date());
                caseInfoDna.setCreatePerson(operateUser.getLoginName());
                caseInfoDna.setDeleteFlag("0");
                caseInfoDna.setHasAppendFlag("0");
                caseInfoDna.setEntrustStatus("0");
                limsCaseInfoMapper.insertCaseInfo(caseInfoDna);
            } else {
                LimsCaseInfo limsCaseInfo = limsCaseInfoMapper.selectByCaseId(caseInfoDna.getCaseId());
                limsCaseInfo.setCaseId(caseInfoDna.getCaseId());
                limsCaseInfo.setCaseStatus("01");
                limsCaseInfo.setMajorNo("DNA鉴定");
                limsCaseInfo.setMajorType("01");
                limsCaseInfo.setCaseName(caseInfoDna.getCaseName());
                limsCaseInfo.setCaseLocation(caseInfoDna.getCaseLocation());
                limsCaseInfo.setCaseDatetime(caseInfoDna.getCaseDatetime());
                limsCaseInfo.setCaseType(caseInfoDna.getCaseType());
                limsCaseInfo.setCaseProperty(caseInfoDna.getCaseProperty());
                limsCaseInfo.setCaseLevel(caseInfoDna.getCaseLevel());
                limsCaseInfo.setCaseBrief(caseInfoDna.getCaseBrief());
                limsCaseInfo.setCaseRemark(caseInfoDna.getCaseRemark());
                limsCaseInfo.setUpdateDatetime(new Date());
                limsCaseInfo.setUpdatePerson(operateUser.getLoginName());
                limsCaseInfo.setCaseXkNo(caseInfoDna.getCaseXkNo());
                limsCaseInfoMapper.updateCaseInfoDna(limsCaseInfo);
            }

            //添加委托人信息
            LimsConsignmentInfo consignatioInfo = delegateDataModel.getConsignatioInfo();
            if (StringUtils.isBlank(consignatioInfo.getConsignmentId())) {
                String consignmentId = UUID.randomUUID().toString();
                consignatioInfo.setConsignmentId(consignmentId);
                consignatioInfo.setCaseId(caseInfoDna.getCaseId());
                // consignatioInfo.setConsignmentNo(seqNoGenerateService.getNextNoVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(consignatioInfo.getDelegateOrgCode()),evaluationCenterId));
                if (consignatioInfo.getConsignmentNo().equals("委托书编号自动生成") ) {
                    //获取并生成流水号
                    String code = seqNoGenerateService.getNextVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(evaluationCenterId), operateUser.getOrgId());
                    updateConsignationSerialNumber(evaluationCenterId,operateUser.getOrgId());

                    if(StringUtils.isNotBlank(operateUser.getOrgId())){
                        LimsConsignmentInfo limsConsignatioInfo = limsConsignatioInfoMapper.selectByConsignmentNo(code,operateUser.getOrgId());
                        while(limsConsignatioInfo != null){
//                            String strCode = code.substring(code.length()-4);
//                            int a = Integer.parseInt(strCode);
//                            String strA = String.valueOf(a+1);
//                            String repaleStrA = null;
//                            if(strA.length() <= 3){
//                                repaleStrA = "0"+strA;
//                            }
//
//                            StringBuffer sb = new StringBuffer(code);
//                            sb.replace(6, 10, repaleStrA);
                            code = seqNoGenerateService.getNextVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(evaluationCenterId), operateUser.getOrgId());
                            updateConsignationSerialNumber(evaluationCenterId,operateUser.getOrgId());

                            limsConsignatioInfo = limsConsignatioInfoMapper.selectByConsignmentNo(code,operateUser.getOrgId());
                        }
                        consignatioInfo.setConsignmentNo(code);
                    }
//                    consignatioInfo.setConsignmentNo(code);
                } else if(consignatioInfo.getConsignmentNo().equals("")) {
                    consignatioInfo.setConsignmentNo(null);
                }else{
                    consignatioInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());
                }

                consignatioInfo.setDelegateDatetime(new Date());
                int reidentifyCount = 0;
                consignatioInfo.setReidentifyCount((short) reidentifyCount);
                consignatioInfo.setCreateDatetime(new Date());
                consignatioInfo.setCreatePerson(operateUser.getLoginName());
                consignatioInfo.setDeleteFlag("0");
                consignatioInfo.setAppendFlag("0");
                consignatioInfo.setStatus("01");
                //鉴定中心orgId，设置入LimsConsignmentInfo表中
                consignatioInfo.setAcceptOrgId(evaluationCenterId);
                //委托类型（0：案件委托；1：在逃人员委托）
                consignatioInfo.setConsignmentType(Constants.CONSIGNMENT_TYPE_0);

                /**
                 * 添加现场委托案件时插入一条数据到数据到队列中
                 */
                QueueSample queueSample = new QueueSample();
                String id = UUID.randomUUID().toString();
                queueSample.setId(id);
                queueSample.setForeignId(caseInfoDna.getCaseId());
                queueSample.setCreateDatetime(new Date());// new Date()为获取当前系统时间
                queueSample.setQueueType("20");
                queueSample.setStatus(0);
                queueSample.setServerNo(evaluationCenterId);


                if(StringUtils.isNotEmpty(caseInfoDna.getConsignationXkNo() )) {
                    try {
                        queueSampleService.insert(queueSample);
                    } catch (Exception ex) {
                        logger.info("插入队列信息失败：" + ex);
                    }
                }


                try {
                    limsConsignatioInfoMapper.insertConsignatioInfo(consignatioInfo);
                } catch (Exception ex) {
                    logger.info("添加委托信息失败：" + ex);
                }

            } else {
                LimsConsignmentInfo limsConsignmentInfo = limsConsignatioInfoMapper.selectByConsignmentId(consignatioInfo.getConsignmentId());
                limsConsignmentInfo.setConsignmentId(consignatioInfo.getConsignmentId());
                limsConsignmentInfo.setCaseId(caseInfoDna.getCaseId());
                //limsConsignmentInfo.setDelegateDatetime(new Date());
                limsConsignmentInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());//修改委托书编号
                int reidentifyCount = 0;
                limsConsignmentInfo.setReidentifyCount((short) reidentifyCount);
                limsConsignmentInfo.setUpdatePerson(operateUser.getLoginName());
                limsConsignmentInfo.setUpdateDatetime(new Date());
                limsConsignmentInfo.setDelegateOrgCode(consignatioInfo.getDelegateOrgCode());
                limsConsignmentInfo.setDelegateOrgName(consignatioInfo.getDelegateOrgName());
                limsConsignmentInfo.setDelegator1Id(consignatioInfo.getDelegator1Id());
                limsConsignmentInfo.setDelegator1Name(consignatioInfo.getDelegator1Name());
                limsConsignmentInfo.setDelegator1Position(consignatioInfo.getDelegator1Position());
                limsConsignmentInfo.setDelegator1PaperworkType(consignatioInfo.getDelegator1PaperworkType());
                limsConsignmentInfo.setDelegator1PaperworkNo(consignatioInfo.getDelegator1PaperworkNo());
                limsConsignmentInfo.setDelegator1PhoneNumber(consignatioInfo.getDelegator1PhoneNumber());
                limsConsignmentInfo.setDelegator2Id(consignatioInfo.getDelegator2Id());
                limsConsignmentInfo.setDelegator2Name(consignatioInfo.getDelegator2Name());
                limsConsignmentInfo.setDelegator2Position(consignatioInfo.getDelegator2Position());
                limsConsignmentInfo.setDelegator2PaperworkType(consignatioInfo.getDelegator2PaperworkType());
                limsConsignmentInfo.setDelegator2PaperworkNo(consignatioInfo.getDelegator2PaperworkNo());
                limsConsignmentInfo.setDelegator2PhoneNumber(consignatioInfo.getDelegator2PhoneNumber());
                limsConsignmentInfo.setIdentifyRequirement(consignatioInfo.getIdentifyRequirement());
                limsConsignmentInfo.setAreaOrgCode(consignatioInfo.getAreaOrgCode());
                //鉴定中心orgId，设置入LimsConsignmentInfo表中
                limsConsignmentInfo.setAcceptOrgId(evaluationCenterId);
                //委托类型（0：案件委托；1：在逃人员委托）
                limsConsignmentInfo.setConsignmentType(Constants.CONSIGNMENT_TYPE_0);
                limsConsignatioInfoMapper.updateConsignatioInfo(limsConsignmentInfo);
            }

            //添加被鉴定人信息
            List<LimsPersonInfo> limsPersonInfoList = delegateDataModel.getLimsPersonInfoList();
            if (null != limsPersonInfoList && limsPersonInfoList.size() > 0) {
                for (LimsPersonInfo limsPersonInfo : limsPersonInfoList) {

                    //对人员照片进行上传
                    if (StringUtils.isNotBlank(limsPersonInfo.getPersonFrontPicture())) {
                        String personFrontPicturePath = ImgUtils.generateImage(limsPersonInfo.getPersonFrontPicture(), personImg);
                        String personFrontPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpPersonImg, personFrontPicturePath);
                        limsPersonInfo.setPersonFrontPicturePath(personFrontPicturePathFtp == null ? "" : personFrontPicturePathFtp);
                    }

                    PersonDetail personDetail = new PersonDetail();
                    LimsPersonInfo limsPersonInfo1 = new LimsPersonInfo();

                    LimsPersonInfo limsPersonInfo3 = limsPersonInfoMapper.selectPersonInfoById(limsPersonInfo.getPersonId());

                    if(StringUtils.isBlank(limsPersonInfo.getPersonId()) || limsPersonInfo3 == null){
                        //人员详细表
                        personDetail.setPersonDetailId(UUID.randomUUID().toString());
                        personDetail.setPersonName(limsPersonInfo.getPersonName());
                        personDetail.setPersonType(limsPersonInfo.getPersonType());
                        personDetail.setPersonGender(limsPersonInfo.getPersonGender());
                        personDetail.setPerosnAge(limsPersonInfo.getPerosnAge());
                        personDetail.setPersonHeight(limsPersonInfo.getPersonHeight());
                        personDetail.setPersonWeight(limsPersonInfo.getPersonWeight());
                        personDetail.setIdCardFlag(limsPersonInfo.getIdCardFlag());
                        personDetail.setPersonIdCard(limsPersonInfo.getPersonIdCard());
                        personDetail.setNoIdCardDesc(limsPersonInfo.getNoIdCardDesc());
                        personDetail.setPersonCurrentAddress(limsPersonInfo.getPersonCurrentAddress());
                        personDetail.setCreateDatetime(new Date());
                        personDetail.setCreatePerson(operateUser.getLoginName());
                        personDetail.setDeleteFlag("0");
                        if (StringUtils.isNotBlank(limsPersonInfo.getPersonFrontPicture())) {
                            personDetail.setPersonFrontPicture(limsPersonInfo.getPersonFrontPicture());
                            personDetail.setPersonFrontPicturePath(limsPersonInfo.getPersonFrontPicturePath());
                        }
                        limsPersonInfoMapper.insertPersonDetail(personDetail);

                        //被鉴定人信息
                        String personId = UUID.randomUUID().toString();
                        limsPersonInfo1.setPersonId(personId);
                        limsPersonInfo1.setCaseId(caseInfoDna.getCaseId());
                        limsPersonInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsPersonInfo1.setPersonName(limsPersonInfo.getPersonName());
                        limsPersonInfo1.setPersonType(limsPersonInfo.getPersonType());
                        limsPersonInfo1.setCreateDatetime(new Date());
                        limsPersonInfo1.setCreatePerson(operateUser.getLoginName());
                        limsPersonInfo1.setDeleteFlag("0");
                        limsPersonInfo1.setPersonDetailId(personDetail.getPersonDetailId());
                        limsPersonInfoMapper.insertPersonInfo(limsPersonInfo1);

                        //判断亲缘关系是否为空
                        LimsPerosnRelation limsPerosnRelation = new LimsPerosnRelation();
                        limsPerosnRelation.setRelationId(UUID.randomUUID().toString());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfo1.getPersonId());
                        limsPerosnRelation.setRelationType(limsPersonInfo.getRelationType());
                        limsPerosnRelation.setCreateDatetime(new Date());
                        limsPerosnRelation.setCreatePerson(operateUser.getLoginName());
                        limsPerosnRelation.setDeleteFlag("0");
                        limsPerosnRelationMapper.insertPersonRelation(limsPerosnRelation);

                    } else {
                        PersonDetail personDetail1 = personDetailMapper.selectByDetailId(limsPersonInfo3.getPersonDetailId());
                        //修改人员详情表
                        personDetail1.setPersonDetailId(limsPersonInfo3.getPersonDetailId());
                        personDetail1.setPersonName(limsPersonInfo.getPersonName());
                        personDetail1.setPersonType(limsPersonInfo.getPersonType());
                        personDetail1.setPersonGender(limsPersonInfo.getPersonGender());
                        personDetail1.setPerosnAge(limsPersonInfo.getPerosnAge());
                        personDetail1.setPersonHeight(limsPersonInfo.getPersonHeight());
                        personDetail1.setPersonWeight(limsPersonInfo.getPersonWeight());
                        personDetail1.setIdCardFlag(limsPersonInfo.getIdCardFlag());
                        personDetail1.setPersonIdCard(limsPersonInfo.getPersonIdCard());
                        personDetail1.setNoIdCardDesc(limsPersonInfo.getNoIdCardDesc());
                        personDetail1.setPersonCurrentAddress(limsPersonInfo.getPersonCurrentAddress());
                        personDetail1.setUpdateDatetime(new Date());
                        personDetail1.setUpdatePerson(operateUser.getLoginName());
                        if (StringUtils.isNotBlank(limsPersonInfo.getPersonFrontPicture())) {
                            personDetail1.setPersonFrontPicture(limsPersonInfo.getPersonFrontPicture());
                            personDetail1.setPersonFrontPicturePath(limsPersonInfo.getPersonFrontPicturePath());
                        }
                        personDetailMapper.updatePersonDetail1(personDetail1);

                        //修改人员表
                        LimsPersonInfo limsPersonInfo2 = limsPersonInfoMapper.selectPersonInfoById(limsPersonInfo.getPersonId());
                        limsPersonInfo2.setCaseId(caseInfoDna.getCaseId());
                        limsPersonInfo2.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsPersonInfo2.setPersonName(limsPersonInfo.getPersonName());
                        limsPersonInfo2.setPersonType(limsPersonInfo.getPersonType());
                        limsPersonInfo2.setUpdateDatetime(new Date());
                        limsPersonInfo2.setUpdatePerson(operateUser.getLoginName());
                        limsPersonInfo2.setPersonId(limsPersonInfo.getPersonId());
                        limsPersonInfoMapper.updatePersonInfo(limsPersonInfo2);

                        //修改人员关系
                        LimsPerosnRelation limsPerosnRelation = limsPerosnRelationMapper.selectBySourcePersonId(limsPersonInfo.getPersonId());
                        limsPerosnRelation.setRelationId(limsPerosnRelation.getRelationId());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfo.getPersonId());
                        limsPerosnRelation.setRelationType(limsPersonInfo.getRelationType());
                        limsPerosnRelation.setUpdateDatetime(new Date());
                        limsPerosnRelation.setUpdatePerson(operateUser.getLoginName());
                        limsPerosnRelationMapper.updatePerosnRelation(limsPerosnRelation);
                    }

                    //样本信息
                    List<LimsSampleInfoDna> limsSampleInfoDnaList = limsPersonInfo.getSampleInfoDnaList();
                    if (null != limsSampleInfoDnaList && limsSampleInfoDnaList.size() > 0) {
                        for (LimsSampleInfoDna limsSampleInfoDna : limsSampleInfoDnaList) {

                            //对人员样本照片进行上传
                            if (StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())) {
                                String sampleDnaPicturePath = ImgUtils.generateImage(limsSampleInfoDna.getSampleDnaPicture(), personImg);
                                String sampleDnaPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpSampleImg, sampleDnaPicturePath);
                                limsSampleInfoDna.setSampleDnaPicturePath(sampleDnaPicturePathFtp == null ? "" : sampleDnaPicturePathFtp);
                            }

                            if (StringUtils.isBlank(limsSampleInfoDna.getSampleId()) && StringUtils.isNotBlank(limsSampleInfoDna.getSampleType())) {
                                LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
                                String sampleId = UUID.randomUUID().toString();
                                sampleInfoDna.setSampleId(sampleId);
                                sampleInfoDna.setConsignmentId(consignatioInfo.getConsignmentId());
                                sampleInfoDna.setCaseId(caseInfoDna.getCaseId());
                                sampleInfoDna.setSampleName(limsSampleInfoDna.getSampleName());
                                sampleInfoDna.setSampleType(limsSampleInfoDna.getSampleType());
                                sampleInfoDna.setSampleDesc(limsSampleInfoDna.getSampleDesc());
                                sampleInfoDna.setSamplePacking(limsSampleInfoDna.getSamplePacking());
                                sampleInfoDna.setExtractDatetime(limsSampleInfoDna.getExtractDatetime());
                                sampleInfoDna.setExtractMethod(limsSampleInfoDna.getExtractMethod());

                                sampleInfoDna.setExtractPerson(operateUser.getLoginName());
                                sampleInfoDna.setSampleFlag("1");
                                if(StringUtils.isBlank(limsPersonInfo.getPersonId()) || limsPersonInfo3 == null){
                                    sampleInfoDna.setLinkId(limsPersonInfo1.getPersonId());
                                }else{
                                    sampleInfoDna.setLinkId(limsPersonInfo3.getPersonId());
                                }
                                sampleInfoDna.setSamplePurpose(limsSampleInfoDna.getSamplePurpose());
                                sampleInfoDna.setSampleStatus("01");
                                sampleInfoDna.setInstoredFlag("0");
                                sampleInfoDna.setCreateDatetime(new Date());
                                sampleInfoDna.setCreatePerson(operateUser.getLoginName());
                                sampleInfoDna.setDeleteFlag("0");
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())) {
                                    sampleInfoDna.setSampleDnaPicture(limsSampleInfoDna.getSampleDnaPicture());
                                    sampleInfoDna.setSampleDnaPicturePath(limsSampleInfoDna.getSampleDnaPicturePath());
                                }
                                //是否中心提取
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreTakenStats())){
                                    sampleInfoDna.setCoreTakenStats(limsSampleInfoDna.getCoreTakenStats());
                                }
                                //是否事主样本信息
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreVictimStats())){
                                    sampleInfoDna.setCoreVictimStats(limsSampleInfoDna.getCoreVictimStats());
                                }
                                sampleInfoDna.setSampleCarrier(limsSampleInfoDna.getSampleCarrier());
                                limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
                            } else if (StringUtils.isNotBlank(limsSampleInfoDna.getSampleId()) && StringUtils.isNotBlank(limsSampleInfoDna.getSampleType())) {
                                LimsSampleInfoDna sampleInfoDna2 = limsSampleInfoDnaMapper.selectSampleInfoDnaById(limsSampleInfoDna.getSampleId());
                                sampleInfoDna2.setConsignmentId(consignatioInfo.getConsignmentId());
                                sampleInfoDna2.setCaseId(caseInfoDna.getCaseId());
                                sampleInfoDna2.setSampleName(limsSampleInfoDna.getSampleName());
                                sampleInfoDna2.setSampleType(limsSampleInfoDna.getSampleType());
                                sampleInfoDna2.setSampleDesc(limsSampleInfoDna.getSampleDesc());
                                sampleInfoDna2.setSamplePacking(limsSampleInfoDna.getSamplePacking());
                                sampleInfoDna2.setExtractDatetime(limsSampleInfoDna.getExtractDatetime());
                                sampleInfoDna2.setExtractMethod(limsSampleInfoDna.getExtractMethod());
                                sampleInfoDna2.setExtractPerson(operateUser.getLoginName());
                                sampleInfoDna2.setSampleFlag("1");
                                sampleInfoDna2.setSamplePurpose(limsSampleInfoDna.getSamplePurpose());
                                sampleInfoDna2.setSampleStatus("01");
                                sampleInfoDna2.setInstoredFlag("0");
                                sampleInfoDna2.setUpdateDatetime(new Date());
                                sampleInfoDna2.setUpdatePerson(operateUser.getLoginName());
                                //是否为中心提取非空判断
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreTakenStats())){
                                    sampleInfoDna2.setCoreTakenStats(limsSampleInfoDna.getCoreTakenStats());
                                }
                                //是否为事主样本信息非空判断
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreVictimStats())){
                                    sampleInfoDna2.setCoreVictimStats(limsSampleInfoDna.getCoreVictimStats());
                                }
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())) {
                                    sampleInfoDna2.setSampleDnaPicture(limsSampleInfoDna.getSampleDnaPicture());
                                    sampleInfoDna2.setSampleDnaPicturePath(limsSampleInfoDna.getSampleDnaPicturePath());
                                }
                                sampleInfoDna2.setSampleCarrier(limsSampleInfoDna.getSampleCarrier());
                                limsSampleInfoDnaMapper.updateSampleInfoDna(sampleInfoDna2);
                            }
                        }
                    }
                }
            }

            //添加检材信息
            List<LimsSampleInfoDna> sampleInfoDnaList = delegateDataModel.getSampleInfoDnaList();
            if (null != sampleInfoDnaList && sampleInfoDnaList.size() > 0) {
                for (LimsSampleInfoDna sampleInfoDna : sampleInfoDnaList) {

                    //对物证检材照片进行上传
                    if (StringUtils.isNotBlank(sampleInfoDna.getSampleMaterialPicture())) {
                        String sampleMaterialPicturePath = ImgUtils.generateImage(sampleInfoDna.getSampleMaterialPicture(), personImg);
                        String sampleMaterialPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpSampleImg, sampleMaterialPicturePath);
                        sampleInfoDna.setSampleMaterialPicturePath(sampleMaterialPicturePathFtp == null ? "" : sampleMaterialPicturePathFtp);
                    }

                    if (StringUtils.isBlank(sampleInfoDna.getSampleIdwz())) {
                        sampleInfoDna.setSampleId(UUID.randomUUID().toString());
                        sampleInfoDna.setConsignmentId(consignatioInfo.getConsignmentId());
                        sampleInfoDna.setCaseId(caseInfoDna.getCaseId());
                        sampleInfoDna.setEvidenceNo(sampleInfoDna.getEvidenceNo());
                        sampleInfoDna.setEvidenceSerialNo(sampleInfoDna.getEvidenceSerialNo());
                        sampleInfoDna.setSampleName(sampleInfoDna.getSampleName());
                        sampleInfoDna.setSampleType(sampleInfoDna.getSampleType());
                        sampleInfoDna.setSampleDesc(sampleInfoDna.getSampleDesc());
                        sampleInfoDna.setSamplePacking(sampleInfoDna.getSamplePacking());
                        sampleInfoDna.setExtractDatetime(sampleInfoDna.getExtractDatetime());
                        sampleInfoDna.setExtractMethod(sampleInfoDna.getExtractMethod());
                        sampleInfoDna.setExtractPerson(operateUser.getLoginName());
                        sampleInfoDna.setSampleFlag("0");
                        //物证id不知道
                        sampleInfoDna.setSamplePurpose(sampleInfoDna.getSamplePurpose());
                        sampleInfoDna.setSampleStatus("01");
                        sampleInfoDna.setInstoredFlag("0");
                        sampleInfoDna.setCreateDatetime(new Date());
                        sampleInfoDna.setCreatePerson(operateUser.getLoginName());
                        sampleInfoDna.setDeleteFlag("0");
                        sampleInfoDna.setSampleCarrier(sampleInfoDna.getSampleCarrier());
                        //物证检材照片
                        sampleInfoDna.setSampleMaterialPicture(sampleInfoDna.getSampleMaterialPicture());
                        //物证检材照片路径
                        sampleInfoDna.setSampleMaterialPicturePath(sampleInfoDna.getSampleMaterialPicturePath());
                        //是否中心提取
                        if (StringUtils.isNotBlank(sampleInfoDna.getCoreTakenStats())){
                            sampleInfoDna.setCoreTakenStats(sampleInfoDna.getCoreTakenStats());
                        }
                        limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
                    } else {
                        LimsSampleInfoDna sampleInfoDna3 = limsSampleInfoDnaMapper.selectSampleInfoDnaById(sampleInfoDna.getSampleIdwz());
                        sampleInfoDna3.setConsignmentId(consignatioInfo.getConsignmentId());
                        sampleInfoDna3.setCaseId(caseInfoDna.getCaseId());
                        sampleInfoDna3.setEvidenceNo(sampleInfoDna.getEvidenceNo());
                        sampleInfoDna3.setEvidenceSerialNo(sampleInfoDna.getEvidenceSerialNo());
                        sampleInfoDna3.setSampleName(sampleInfoDna.getSampleName());
                        sampleInfoDna3.setSampleType(sampleInfoDna.getSampleType());
                        sampleInfoDna3.setSampleDesc(sampleInfoDna.getSampleDesc());
                        sampleInfoDna3.setSamplePacking(sampleInfoDna.getSamplePacking());
                        sampleInfoDna3.setExtractDatetime(sampleInfoDna.getExtractDatetime());
                        sampleInfoDna3.setExtractMethod(sampleInfoDna.getExtractMethod());
                        sampleInfoDna3.setExtractPerson(operateUser.getLoginName());
                        sampleInfoDna3.setSampleFlag("0");
                        sampleInfoDna3.setSamplePurpose(sampleInfoDna.getSamplePurpose());
                        sampleInfoDna3.setSampleStatus("01");
                        sampleInfoDna3.setInstoredFlag("0");
                        sampleInfoDna3.setUpdateDatetime(new Date());
                        sampleInfoDna3.setUpdatePerson(operateUser.getLoginName());
                        sampleInfoDna3.setSampleCarrier(sampleInfoDna.getSampleCarrier());
                        //物证检材照片
                        sampleInfoDna3.setSampleMaterialPicture(sampleInfoDna.getSampleMaterialPicture());
                        //物证检材照片路径
                        sampleInfoDna3.setSampleMaterialPicturePath(sampleInfoDna.getSampleMaterialPicturePath());
                        //是否中心提取
                        if (StringUtils.isNotBlank(sampleInfoDna.getCoreTakenStats())){
                            sampleInfoDna3.setCoreTakenStats(sampleInfoDna.getCoreTakenStats());
                        }
                        limsSampleInfoDnaMapper.updateSampleInfoDna(sampleInfoDna3);
                    }
                }
            }
            result.put("caseId", caseInfoDna.getCaseId());
            result.put("consignmentId", consignatioInfo.getConsignmentId());
        } catch (Exception ex) {
            logger.info("委托登记报错：" + ex);
        }

        return result;
    }
    
    private void updateConsignationSerialNumber(String evaluationCenterId, String orgId){
        try {
            seqNoGenerateService.updateTypeVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(evaluationCenterId), orgId);
        } catch (Exception ex) {
            logger.info("更新流水号失败：" + ex);
        }
    }

    /**
     * 删除查询与补送
     * @param consignmentId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delCaseAndBring(String consignmentId, String caseId, LoaUserInfo operateUser) {
        try {
            LimsConsignmentInfo consignationInfo = new LimsConsignmentInfo();
            consignationInfo.setConsignmentId(consignmentId);
            consignationInfo.setDeleteFlag("1");
            consignationInfo.setDeleteDatetime(new Date());
            consignationInfo.setDeletePerson(operateUser.getLoginName());
            limsConsignatioInfoMapper.deleteConsignationInfo(consignationInfo);
            LimsCaseInfo caseInfoDna = new LimsCaseInfo();
            caseInfoDna.setCaseId(caseId);
            caseInfoDna.setDeleteFlag("1");
            caseInfoDna.setDeleteDatetime(new Date());
            caseInfoDna.setDeletePerson(operateUser.getLoginName());
            limsCaseInfoMapper.deleteCaseInfo(caseInfoDna);
            LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
            limsPersonInfo.setDeleteFlag("1");
            limsPersonInfo.setDeleteDatetime(new Date());
            limsPersonInfo.setDeletePerson(operateUser.getLoginName());
            limsPersonInfo.setCaseId(caseId);
            limsPersonInfo.setConsignmentId(consignmentId);
            //根据委托id和案件id查询人员表
            List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoMapper.selectByCaseIdAndConsignmentId(limsPersonInfo);
            if(null != limsPersonInfoList && limsPersonInfoList.size() > 0){
                for(LimsPersonInfo limsPersonInfo1:limsPersonInfoList){
                    LimsPerosnRelation limsPerosnRelation = new LimsPerosnRelation();
                    limsPerosnRelation.setDeleteFlag("1");
                    limsPerosnRelation.setDeleteDatetime(new Date());
                    limsPerosnRelation.setDeletePerson(operateUser.getLoginName());
                    limsPerosnRelation.setSourcePersonId(limsPersonInfo1.getPersonId());
                    //根据人员id删除人员关系表
                    limsPerosnRelationMapper.deleteBySourcePersonId(limsPerosnRelation);
                }
            }
            //删除人员表
            limsPersonInfoMapper.deleteByCaseIdAndConsignmentId(limsPersonInfo);

            LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
            sampleInfoDna.setDeleteFlag("1");
            sampleInfoDna.setDeleteDatetime(new Date());
            sampleInfoDna.setDeletePerson(operateUser.getLoginName());
            sampleInfoDna.setCaseId(caseId);
            sampleInfoDna.setConsignmentId(consignmentId);
            //根据委托id和案件id删除样本表信息
            limsSampleInfoDnaMapper.deleteByCaseIdAndConsignmentId(sampleInfoDna);

       }catch (Exception ex){
            logger.info("删除失败"+ex);
        }

    }

    /**
     * 根据委托id查询委托信息
     * @param consignmentId
     * @return
     */
    @Override
    public LimsConsignmentInfo selectByConsignmentId(String consignmentId) {
        LimsConsignmentInfo consignatioInfo = limsConsignatioInfoMapper.selectByConsignmentId(consignmentId);
        return consignatioInfo;
    }


    /**
     * 非案件委托登记
     * @param delegateDataModel
     * @param operateUser
     * @param personIds
     * @param sampleIds
     * @param evaluationCenterId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String,String> submitNonDelegate(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIds, String evaluationCenterId, String sampleIdWzs){
        Map<String, String> result = new HashMap<>();
        try {
        	
        	if (ListUtils.isNull(sampleIdWzs)) {
                String sampleIdWz = sampleIdWzs.substring(1);
                String[] sampleIdWzsArr = sampleIdWz.split(",");
                for (String string : sampleIdWzsArr) {
                    LimsSampleInfoDna limsSampleInfoDna = limsSampleInfoDnaMapper.selectById(string);
                    limsSampleInfoDna.setSampleId(string);
                    limsSampleInfoDna.setDeleteFlag("1");
                    limsSampleInfoDna.setUpdateDatetime(new Date());
                    limsSampleInfoDna.setUpdatePerson(operateUser.getLoginName());
                    limsSampleInfoDnaMapper.updateSampleInfoDna(limsSampleInfoDna);
                }
            }

            if(ListUtils.isNull(sampleIds)){
                String personId = sampleIds.substring(1);
                String[] sampleIdsArr = personId.split(",");
                for(String string:sampleIdsArr){
                    LimsSampleInfoDna limsSampleInfoDna = limsSampleInfoDnaMapper.selectById(string);
                    limsSampleInfoDna.setSampleId(string);
                    limsSampleInfoDna.setDeleteFlag("1");
                    limsSampleInfoDna.setUpdateDatetime(new Date());
                    limsSampleInfoDna.setUpdatePerson(operateUser.getLoginName());
                    limsSampleInfoDnaMapper.updateSampleInfoDna(limsSampleInfoDna);
                }
            }

            if(ListUtils.isNull(personIds)){
                String personId = personIds.substring(1);
                String[] personIdsArr = personId.split(",");
                for(String string:personIdsArr){
                    LimsPersonInfo limsPersonInfo = limsPersonInfoMapper.selectPersonInfoById(string);
                    LimsPerosnRelation limsPerosnRelation = limsPerosnRelationMapper.selectBySourcePersonId(limsPersonInfo.getPersonId());
                    limsPerosnRelation.setSourcePersonId(limsPersonInfo.getPersonId());
                    limsPerosnRelation.setDeleteFlag("1");
                    limsPerosnRelation.setUpdateDatetime(new Date());
                    limsPerosnRelation.setUpdatePerson(operateUser.getLoginName());
                    limsPerosnRelationMapper.updatePerosnRelation(limsPerosnRelation);
                    PersonDetail personDetail = personDetailMapper.selectByDetailId(limsPersonInfo.getPersonDetailId());
                    personDetail.setPersonDetailId(limsPersonInfo.getPersonDetailId());
                    personDetail.setDeleteFlag("1");
                    personDetail.setUpdatePerson(operateUser.getLoginName());
                    personDetail.setUpdateDatetime(new Date());
                    personDetailMapper.updatePersonDetail1(personDetail);
                    limsPersonInfo.setPersonId(string);
                    limsPersonInfo.setDeleteFlag("1");
                    limsPersonInfo.setUpdateDatetime(new Date());
                    limsPersonInfo.setUpdatePerson(operateUser.getLoginName());
                    limsPersonInfoMapper.updatePersonInfo(limsPersonInfo);
                }
            }

            //添加案件信息
            LimsCaseInfo caseInfoDna = delegateDataModel.getCaseInfoDna();
            if(StringUtils.isBlank(caseInfoDna.getCaseId())){
                String caseId = UUID.randomUUID().toString();
                caseInfoDna.setCaseId(caseId);
                caseInfoDna.setCaseStatus("01");
                caseInfoDna.setCreateDatetime(new Date());
                caseInfoDna.setCreatePerson(operateUser.getLoginName());
                caseInfoDna.setDeleteFlag("0");
                caseInfoDna.setHasAppendFlag("0");
                limsCaseInfoMapper.insertCaseInfo(caseInfoDna);
            }else{
                LimsCaseInfo limsCaseInfo = limsCaseInfoMapper.selectByCaseId(caseInfoDna.getCaseId());
                limsCaseInfo.setCaseId(caseInfoDna.getCaseId());
                limsCaseInfo.setCaseStatus("01");
                limsCaseInfo.setMajorNo("DNA鉴定");
                limsCaseInfo.setMajorType("01");
                limsCaseInfo.setCaseName(caseInfoDna.getCaseName());
                limsCaseInfo.setCaseLocation(caseInfoDna.getCaseLocation());
                limsCaseInfo.setCaseDatetime(caseInfoDna.getCaseDatetime());
                limsCaseInfo.setCaseType(caseInfoDna.getCaseType());
                limsCaseInfo.setCaseProperty(caseInfoDna.getCaseProperty());
                limsCaseInfo.setCaseLevel(caseInfoDna.getCaseLevel());
                limsCaseInfo.setCaseBrief(caseInfoDna.getCaseBrief());
                limsCaseInfo.setCaseRemark(caseInfoDna.getCaseRemark());
                limsCaseInfo.setUpdateDatetime(new Date());
                limsCaseInfo.setUpdatePerson(operateUser.getLoginName());
                limsCaseInfoMapper.updateCaseInfoDna(limsCaseInfo);
            }

            //添加委托人信息
            LimsConsignmentInfo consignatioInfo = delegateDataModel.getConsignatioInfo();
            if(StringUtils.isBlank(consignatioInfo.getConsignmentId())){
                String consignmentId = UUID.randomUUID().toString();
                consignatioInfo.setConsignmentId(consignmentId);
                consignatioInfo.setCaseId(caseInfoDna.getCaseId());
//                consignatioInfo.setConsignmentNo(seqNoGenerateService.getNextNoVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(consignatioInfo.getDelegateOrgCode()),evaluationCenterId));
                if (consignatioInfo.getConsignmentNo().equals("委托书编号自动生成") ) {
                    //获取并生成流水号
                    String code = seqNoGenerateService.getNextVals(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(consignatioInfo.getDelegateOrgCode()),evaluationCenterId);
                    consignatioInfo.setConsignmentNo(code);
                    try {
                        seqNoGenerateService.updateTypeVals(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(evaluationCenterId), operateUser.getOrgId());
                    } catch (Exception ex) {
                        logger.info("更新流水号失败：" + ex);
                    }
                } else if(consignatioInfo.getConsignmentNo().equals("")) {
                    consignatioInfo.setConsignmentNo(null);
                }else{
                    consignatioInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());
                }
                consignatioInfo.setDelegateDatetime(new Date());
                int reidentifyCount = 0;
                consignatioInfo.setReidentifyCount((short)reidentifyCount);
                consignatioInfo.setCreateDatetime(new Date());
                consignatioInfo.setCreatePerson(operateUser.getLoginName());
                consignatioInfo.setDeleteFlag("0");
                consignatioInfo.setAppendFlag("0");
                consignatioInfo.setStatus("01");
                //鉴定中心orgId，设置入LimsConsignmentInfo表中
                consignatioInfo.setAcceptOrgId(evaluationCenterId);
                try {
                    limsConsignatioInfoMapper.insertConsignatioInfo(consignatioInfo);
                } catch (Exception ex) {
                    logger.info("保存委托信息失败：" + ex);
                }
            }else{
                LimsConsignmentInfo limsConsignmentInfo = limsConsignatioInfoMapper.selectByConsignmentId(consignatioInfo.getConsignmentId());
                limsConsignmentInfo.setConsignmentId(consignatioInfo.getConsignmentId());
                limsConsignmentInfo.setCaseId(caseInfoDna.getCaseId());
                limsConsignmentInfo.setDelegateDatetime(new Date());
                int reidentifyCount = 0;
                limsConsignmentInfo.setReidentifyCount((short)reidentifyCount);
                limsConsignmentInfo.setUpdatePerson(operateUser.getLoginName());
                limsConsignmentInfo.setUpdateDatetime(new Date());
                limsConsignmentInfo.setDelegateOrgCode(consignatioInfo.getDelegateOrgCode());
                limsConsignmentInfo.setDelegateOrgName(consignatioInfo.getDelegateOrgName());
                limsConsignmentInfo.setDelegator1Id(consignatioInfo.getDelegator1Id());
                limsConsignmentInfo.setDelegator1Name(consignatioInfo.getDelegator1Name());
                limsConsignmentInfo.setDelegator1Position(consignatioInfo.getDelegator1Position());
                limsConsignmentInfo.setDelegator1PaperworkType(consignatioInfo.getDelegator1PaperworkType());
                limsConsignmentInfo.setDelegator1PaperworkNo(consignatioInfo.getDelegator1PaperworkNo());
                limsConsignmentInfo.setDelegator1PhoneNumber(consignatioInfo.getDelegator1PhoneNumber());
                limsConsignmentInfo.setDelegator2Id(consignatioInfo.getDelegator2Id());
                limsConsignmentInfo.setDelegator2Name(consignatioInfo.getDelegator2Name());
                limsConsignmentInfo.setDelegator2Position(consignatioInfo.getDelegator2Position());
                limsConsignmentInfo.setDelegator2PaperworkType(consignatioInfo.getDelegator2PaperworkType());
                limsConsignmentInfo.setDelegator2PaperworkNo(consignatioInfo.getDelegator2PaperworkNo());
                limsConsignmentInfo.setDelegator2PhoneNumber(consignatioInfo.getDelegator2PhoneNumber());
                limsConsignmentInfo.setIdentifyRequirement(consignatioInfo.getIdentifyRequirement());
                limsConsignmentInfo.setAreaOrgCode(consignatioInfo.getAreaOrgCode());
                //鉴定中心orgId，设置入LimsConsignmentInfo表中
                consignatioInfo.setAcceptOrgId(evaluationCenterId);
                limsConsignatioInfoMapper.updateConsignatioInfo(limsConsignmentInfo);
            }

            //添加被鉴定人信息
            List<LimsPersonInfo> limsPersonInfoList = delegateDataModel.getLimsPersonInfoList();
            if(null != limsPersonInfoList && limsPersonInfoList.size() > 0){
                for(LimsPersonInfo limsPersonInfo:limsPersonInfoList){

                    //对人员照片进行上传
                    if(StringUtils.isNotBlank(limsPersonInfo.getPersonFrontPicture())){
                        String personFrontPicturePath = ImgUtils.generateImage(limsPersonInfo.getPersonFrontPicture(), personImg);
                        String personFrontPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpPersonImg, personFrontPicturePath);
                        limsPersonInfo.setPersonFrontPicturePath(personFrontPicturePathFtp == null ? "" : personFrontPicturePathFtp);
                    }

                    PersonDetail personDetail = new PersonDetail();
                    LimsPersonInfo limsPersonInfo1 = new LimsPersonInfo();
                    if(StringUtils.isBlank(limsPersonInfo.getPersonId())){
                        //人员详细表
                        personDetail.setPersonDetailId(UUID.randomUUID().toString());
                        personDetail.setPersonName(limsPersonInfo.getPersonName());
                        personDetail.setPersonType(limsPersonInfo.getPersonType());
                        personDetail.setPersonGender(limsPersonInfo.getPersonGender());
                        personDetail.setPerosnAge(limsPersonInfo.getPerosnAge());
                        personDetail.setPersonHeight(limsPersonInfo.getPersonHeight());
                        personDetail.setPersonWeight(limsPersonInfo.getPersonWeight());
                        personDetail.setIdCardFlag(limsPersonInfo.getIdCardFlag());
                        personDetail.setPersonIdCard(limsPersonInfo.getPersonIdCard());
                        personDetail.setNoIdCardDesc(limsPersonInfo.getNoIdCardDesc());
                        personDetail.setPersonCurrentAddress(limsPersonInfo.getPersonCurrentAddress());
                        personDetail.setCreateDatetime(new Date());
                        personDetail.setCreatePerson(operateUser.getLoginName());
                        personDetail.setDeleteFlag("0");
                        if(StringUtils.isNotBlank(limsPersonInfo.getPersonFrontPicture())){
                            personDetail.setPersonFrontPicture(limsPersonInfo.getPersonFrontPicture());
                            personDetail.setPersonFrontPicturePath(limsPersonInfo.getPersonFrontPicturePath());
                        }
                        limsPersonInfoMapper.insertPersonDetail(personDetail);

                        //被鉴定人信息
                        String personId = UUID.randomUUID().toString();
                        limsPersonInfo1.setPersonId(personId);
                        limsPersonInfo1.setCaseId(caseInfoDna.getCaseId());
                        limsPersonInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsPersonInfo1.setPersonName(limsPersonInfo.getPersonName());
                        limsPersonInfo1.setPersonType(limsPersonInfo.getPersonType());
                        limsPersonInfo1.setCreateDatetime(new Date());
                        limsPersonInfo1.setCreatePerson(operateUser.getLoginName());
                        limsPersonInfo1.setDeleteFlag("0");
                        limsPersonInfo1.setPersonDetailId(personDetail.getPersonDetailId());
                        limsPersonInfoMapper.insertPersonInfo(limsPersonInfo1);

                        //判断亲缘关系是否为空
                        LimsPerosnRelation limsPerosnRelation = new LimsPerosnRelation();
                        limsPerosnRelation.setRelationId(UUID.randomUUID().toString());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfo1.getPersonId());
                        limsPerosnRelation.setRelationType(limsPersonInfo.getRelationType());
                        limsPerosnRelation.setCreateDatetime(new Date());
                        limsPerosnRelation.setCreatePerson(operateUser.getLoginName());
                        limsPerosnRelation.setDeleteFlag("0");
                        limsPerosnRelationMapper.insertPersonRelation(limsPerosnRelation);

                    }else{
                        LimsPersonInfo limsPersonInfo3 = limsPersonInfoMapper.selectPersonInfoById(limsPersonInfo.getPersonId());
                        PersonDetail personDetail1 = personDetailMapper.selectByDetailId(limsPersonInfo3.getPersonDetailId());
                        //修改人员详情表
                        personDetail1.setPersonDetailId(limsPersonInfo3.getPersonDetailId());
                        personDetail1.setPersonName(limsPersonInfo.getPersonName());
                        personDetail1.setPersonType(limsPersonInfo.getPersonType());
                        personDetail1.setPersonGender(limsPersonInfo.getPersonGender());
                        personDetail1.setPerosnAge(limsPersonInfo.getPerosnAge());
                        personDetail1.setPersonHeight(limsPersonInfo.getPersonHeight());
                        personDetail1.setPersonWeight(limsPersonInfo.getPersonWeight());
                        personDetail1.setIdCardFlag(limsPersonInfo.getIdCardFlag());
                        personDetail1.setPersonIdCard(limsPersonInfo.getPersonIdCard());
                        personDetail1.setNoIdCardDesc(limsPersonInfo.getNoIdCardDesc());
                        personDetail1.setPersonCurrentAddress(limsPersonInfo.getPersonCurrentAddress());
                        personDetail1.setUpdateDatetime(new Date());
                        personDetail1.setUpdatePerson(operateUser.getLoginName());
                        if(StringUtils.isNotBlank(limsPersonInfo.getPersonFrontPicture())){
                            personDetail1.setPersonFrontPicture(limsPersonInfo.getPersonFrontPicture());
                            personDetail1.setPersonFrontPicturePath(limsPersonInfo.getPersonFrontPicturePath());
                        }
                        personDetailMapper.updatePersonDetail1(personDetail1);

                        //修改人员表
                        LimsPersonInfo limsPersonInfo2 = limsPersonInfoMapper.selectPersonInfoById(limsPersonInfo.getPersonId());
                        limsPersonInfo2.setCaseId(caseInfoDna.getCaseId());
                        limsPersonInfo2.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsPersonInfo2.setPersonName(limsPersonInfo.getPersonName());
                        limsPersonInfo2.setPersonType(limsPersonInfo.getPersonType());
                        limsPersonInfo2.setUpdateDatetime(new Date());
                        limsPersonInfo2.setUpdatePerson(operateUser.getLoginName());
                        limsPersonInfo2.setPersonId(limsPersonInfo.getPersonId());
                        limsPersonInfoMapper.updatePersonInfo(limsPersonInfo2);

                        //修改人员关系
                        LimsPerosnRelation limsPerosnRelation = limsPerosnRelationMapper.selectBySourcePersonId(limsPersonInfo.getPersonId());
                        limsPerosnRelation.setRelationId(limsPerosnRelation.getRelationId());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfo.getPersonId());
                        limsPerosnRelation.setRelationType(limsPersonInfo.getRelationType());
                        limsPerosnRelation.setUpdateDatetime(new Date());
                        limsPerosnRelation.setUpdatePerson(operateUser.getLoginName());
                        limsPerosnRelationMapper.updatePerosnRelation(limsPerosnRelation);
                    }

                    //样本信息
                    List<LimsSampleInfoDna> limsSampleInfoDnaList = limsPersonInfo.getSampleInfoDnaList();
                    if(null != limsSampleInfoDnaList && limsSampleInfoDnaList.size() > 0){
                        for(LimsSampleInfoDna limsSampleInfoDna:limsSampleInfoDnaList){
                            //对人员样本照片进行上传
                            if(StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())){
                                String sampleDnaPicturePath = ImgUtils.generateImage(limsSampleInfoDna.getSampleDnaPicture(), personImg);
                                String sampleDnaPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpSampleImg, sampleDnaPicturePath);
                                limsSampleInfoDna.setSampleDnaPicturePath(sampleDnaPicturePathFtp == null ? "" : sampleDnaPicturePathFtp);
                            }

                            if(StringUtils.isBlank(limsSampleInfoDna.getSampleId()) && StringUtils.isNotBlank(limsSampleInfoDna.getSampleType())){
                                LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
                                String sampleId = UUID.randomUUID().toString();
                                sampleInfoDna.setSampleId(sampleId);
                                sampleInfoDna.setConsignmentId(consignatioInfo.getConsignmentId());
                                sampleInfoDna.setCaseId(caseInfoDna.getCaseId());
                                sampleInfoDna.setSampleName(limsSampleInfoDna.getSampleName());
                                sampleInfoDna.setSampleType(limsSampleInfoDna.getSampleType());
                                sampleInfoDna.setSampleDesc(limsSampleInfoDna.getSampleDesc());
                                sampleInfoDna.setSamplePacking(limsSampleInfoDna.getSamplePacking());
                                sampleInfoDna.setExtractDatetime(limsSampleInfoDna.getExtractDatetime());
                                sampleInfoDna.setExtractMethod(limsSampleInfoDna.getExtractMethod());

                                sampleInfoDna.setExtractPerson(operateUser.getLoginName());
                                sampleInfoDna.setSampleFlag("1");
                                sampleInfoDna.setLinkId(limsPersonInfo1.getPersonId());
                                sampleInfoDna.setSamplePurpose(limsSampleInfoDna.getSamplePurpose());
                                sampleInfoDna.setSampleStatus("01");
                                sampleInfoDna.setInstoredFlag("0");
                                sampleInfoDna.setCreateDatetime(new Date());
                                sampleInfoDna.setCreatePerson(operateUser.getLoginName());
                                sampleInfoDna.setDeleteFlag("0");
                                if(StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())){
                                    sampleInfoDna.setSampleDnaPicture(limsSampleInfoDna.getSampleDnaPicture());
                                    sampleInfoDna.setSampleDnaPicturePath(limsSampleInfoDna.getSampleDnaPicturePath());
                                }
                                sampleInfoDna.setSampleCarrier(limsSampleInfoDna.getSampleCarrier());
                                limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
                            }else if(StringUtils.isNotBlank(limsSampleInfoDna.getSampleId()) && StringUtils.isNotBlank(limsSampleInfoDna.getSampleType())){
                                LimsSampleInfoDna sampleInfoDna2 = limsSampleInfoDnaMapper.selectSampleInfoDnaById(limsSampleInfoDna.getSampleId());
                                sampleInfoDna2.setConsignmentId(consignatioInfo.getConsignmentId());
                                sampleInfoDna2.setCaseId(caseInfoDna.getCaseId());
                                sampleInfoDna2.setSampleName(limsSampleInfoDna.getSampleName());
                                sampleInfoDna2.setSampleType(limsSampleInfoDna.getSampleType());
                                sampleInfoDna2.setSampleDesc(limsSampleInfoDna.getSampleDesc());
                                sampleInfoDna2.setSamplePacking(limsSampleInfoDna.getSamplePacking());
                                sampleInfoDna2.setExtractDatetime(limsSampleInfoDna.getExtractDatetime());
                                sampleInfoDna2.setExtractMethod(limsSampleInfoDna.getExtractMethod());
                                sampleInfoDna2.setExtractPerson(operateUser.getLoginName());
                                sampleInfoDna2.setSampleFlag("1");
                                sampleInfoDna2.setSamplePurpose(limsSampleInfoDna.getSamplePurpose());
                                sampleInfoDna2.setSampleStatus("01");
                                sampleInfoDna2.setInstoredFlag("0");
                                sampleInfoDna2.setUpdateDatetime(new Date());
                                sampleInfoDna2.setUpdatePerson(operateUser.getLoginName());
                                if(StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())){
                                    sampleInfoDna2.setSampleDnaPicture(limsSampleInfoDna.getSampleDnaPicture());
                                    sampleInfoDna2.setSampleDnaPicturePath(limsSampleInfoDna.getSampleDnaPicturePath());
                                }
                                sampleInfoDna2.setSampleCarrier(limsSampleInfoDna.getSampleCarrier());
                                limsSampleInfoDnaMapper.updateSampleInfoDna(sampleInfoDna2);
                            }
                        }
                    }
                }
            }


            //添加检材信息
            List<LimsSampleInfoDna> sampleInfoDnaList = delegateDataModel.getSampleInfoDnaList();
            if(null != sampleInfoDnaList && sampleInfoDnaList.size() > 0){
                for(LimsSampleInfoDna sampleInfoDna:sampleInfoDnaList){

                    //对物证检材照片进行上传
                    if(StringUtils.isNotBlank(sampleInfoDna.getSampleMaterialPicture())){
                        String sampleMaterialPicturePath = ImgUtils.generateImage(sampleInfoDna.getSampleMaterialPicture(), personImg);
                        String sampleMaterialPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpSampleImg, sampleMaterialPicturePath);
                        sampleInfoDna.setSampleMaterialPicturePath(sampleMaterialPicturePathFtp == null ? "" : sampleMaterialPicturePathFtp);
                    }

                    if(StringUtils.isBlank(sampleInfoDna.getSampleIdwz())){
                        sampleInfoDna.setSampleId(UUID.randomUUID().toString());
                        sampleInfoDna.setConsignmentId(consignatioInfo.getConsignmentId());
                        sampleInfoDna.setCaseId(caseInfoDna.getCaseId());
                        sampleInfoDna.setEvidenceNo(sampleInfoDna.getEvidenceNo());
                        sampleInfoDna.setSampleName(sampleInfoDna.getSampleName());
                        sampleInfoDna.setSampleType(sampleInfoDna.getSampleType());
                        sampleInfoDna.setSampleDesc(sampleInfoDna.getSampleDesc());
                        sampleInfoDna.setSamplePacking(sampleInfoDna.getSamplePacking());
                        sampleInfoDna.setExtractDatetime(sampleInfoDna.getExtractDatetime());
                        sampleInfoDna.setExtractMethod(sampleInfoDna.getExtractMethod());
                        sampleInfoDna.setExtractPerson(operateUser.getLoginName());
                        sampleInfoDna.setSampleFlag("0");
                        //物证id不知道
                        sampleInfoDna.setSamplePurpose(sampleInfoDna.getSamplePurpose());
                        sampleInfoDna.setSampleStatus("01");
                        sampleInfoDna.setInstoredFlag("0");
                        sampleInfoDna.setCreateDatetime(new Date());
                        sampleInfoDna.setCreatePerson(operateUser.getLoginName());
                        sampleInfoDna.setDeleteFlag("0");
                        sampleInfoDna.setSampleCarrier(sampleInfoDna.getSampleCarrier());
                        //物证检材照片
                        sampleInfoDna.setSampleMaterialPicture(sampleInfoDna.getSampleMaterialPicture());
                        //物证检材照片路径
                        sampleInfoDna.setSampleMaterialPicturePath(sampleInfoDna.getSampleMaterialPicturePath());
                        limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
                    }else{
                        LimsSampleInfoDna sampleInfoDna3 = limsSampleInfoDnaMapper.selectSampleInfoDnaById(sampleInfoDna.getSampleIdwz());
                        sampleInfoDna3.setConsignmentId(consignatioInfo.getConsignmentId());
                        sampleInfoDna3.setCaseId(caseInfoDna.getCaseId());
                        sampleInfoDna3.setEvidenceNo(sampleInfoDna.getEvidenceNo());
                        sampleInfoDna3.setSampleName(sampleInfoDna.getSampleName());
                        sampleInfoDna3.setSampleType(sampleInfoDna.getSampleType());
                        sampleInfoDna3.setSampleDesc(sampleInfoDna.getSampleDesc());
                        sampleInfoDna3.setSamplePacking(sampleInfoDna.getSamplePacking());
                        sampleInfoDna3.setExtractDatetime(sampleInfoDna.getExtractDatetime());
                        sampleInfoDna3.setExtractMethod(sampleInfoDna.getExtractMethod());
                        sampleInfoDna3.setExtractPerson(operateUser.getLoginName());
                        sampleInfoDna3.setSampleFlag("0");
                        sampleInfoDna3.setSamplePurpose(sampleInfoDna.getSamplePurpose());
                        sampleInfoDna3.setSampleStatus("01");
                        sampleInfoDna3.setInstoredFlag("0");
                        sampleInfoDna3.setUpdateDatetime(new Date());
                        sampleInfoDna3.setUpdatePerson(operateUser.getLoginName());
                        sampleInfoDna3.setSampleCarrier(sampleInfoDna.getSampleCarrier());
                        //物证检材照片
                        sampleInfoDna3.setSampleMaterialPicture(sampleInfoDna.getSampleMaterialPicture());
                        //物证检材照片路径
                        sampleInfoDna3.setSampleMaterialPicturePath(sampleInfoDna.getSampleMaterialPicturePath());
                        limsSampleInfoDnaMapper.updateSampleInfoDna(sampleInfoDna3);
                    }
                }
            }

            result.put("caseId", caseInfoDna.getCaseId());
            result.put("consignmentId", consignatioInfo.getConsignmentId());
        } catch (Exception ex) {
            logger.info("非案件委托登记报错："+ex);
            throw ex;
        }

        return result;
    }

    @Override
    public List<LimsConsignmentInfo> selectByCaseId(String caseId) {
        return limsConsignatioInfoMapper.selectByCaseId(caseId);
    }

    /**
     * 根据委托编号查询委托信息
     * @param consignmentNo
     * @return
     */
    @Override
    public LimsConsignmentInfo selectByConsignmentNo(String consignmentNo) {
        LimsConsignmentInfo consignatioInfo = limsConsignatioInfoMapper.selectByConsignmentNo(consignmentNo);
        return consignatioInfo;
    }

    @Override
    public LimsConsignmentInfo getCaseidByAppendflag(String consignmentNo) {
        LimsConsignmentInfo consignatioInfo = limsConsignatioInfoMapper.getCaseidByAppendflag(consignmentNo);
        return consignatioInfo;
    }

    @Override
    public LimsConsignmentInfo selectByCaseNo(String caseNo) {
        LimsConsignmentInfo limsConsignmentInfo = null;
        try {
            List<LimsConsignmentInfo> consignmentInfoList = limsConsignatioInfoMapper.selectByCaseNo(caseNo);
            if (ListUtils.isNotNullAndEmptyList(consignmentInfoList)){
                limsConsignmentInfo = consignmentInfoList.get(0);
            }
        }catch (Exception e) {
            logger.debug("查询委托信息报错！" + caseNo);
            e.getStackTrace();
        }
        return limsConsignmentInfo;
    }
    
    /**
     * 在逃人员登记
     * @param delegateDataModel
     * @param operateUser
     * @param personIds
     * @param sampleIds
     * @param evaluationCenterId
     * @param sampleIdWzs
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, String> submitFugitivesDelegate(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIds, String evaluationCenterId,String sampleIdWzs,String appendFlag) {
        Map<String, String> result = new HashMap<>();
        try {

            if (ListUtils.isNull(sampleIdWzs)) {
                String sampleIdWz = sampleIdWzs.substring(1);
                String[] sampleIdWzsArr = sampleIdWz.split(",");
                for (String string : sampleIdWzsArr) {
                    LimsSampleInfoDna limsSampleInfoDna = limsSampleInfoDnaMapper.selectById(string);
                    limsSampleInfoDna.setSampleId(string);
                    limsSampleInfoDna.setDeleteFlag(Constants.DELETE_FLAG_1);
                    limsSampleInfoDna.setUpdateDatetime(new Date());
                    limsSampleInfoDna.setUpdatePerson(operateUser.getLoginName());
                    limsSampleInfoDnaMapper.updateSampleInfoDna(limsSampleInfoDna);
                }
            }

            if (ListUtils.isNull(sampleIds)) {
                String personId = sampleIds.substring(1);
                String[] sampleIdsArr = personId.split(",");
                for (String string : sampleIdsArr) {
                    LimsSampleInfoDna limsSampleInfoDna = limsSampleInfoDnaMapper.selectById(string);
                    limsSampleInfoDna.setSampleId(string);
                    limsSampleInfoDna.setDeleteFlag(Constants.DELETE_FLAG_1);
                    limsSampleInfoDna.setUpdateDatetime(new Date());
                    limsSampleInfoDna.setUpdatePerson(operateUser.getLoginName());
                    limsSampleInfoDnaMapper.updateSampleInfoDna(limsSampleInfoDna);
                }
            }

            if (ListUtils.isNull(personIds)) {
                String personId = personIds.substring(1);
                String[] personIdsArr = personId.split(",");
                for (String string : personIdsArr) {
                    LimsPersonInfo limsPersonInfo = limsPersonInfoMapper.selectPersonInfoById(string);
                    if(null != limsPersonInfo){
                        LimsPerosnRelation limsPerosnRelation = limsPerosnRelationMapper.selectBySourcePersonId(limsPersonInfo.getPersonId());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfo.getPersonId());
                        limsPerosnRelation.setDeleteFlag(Constants.DELETE_FLAG_1);
                        limsPerosnRelation.setUpdateDatetime(new Date());
                        limsPerosnRelation.setUpdatePerson(operateUser.getLoginName());
                        limsPerosnRelationMapper.updatePerosnRelation(limsPerosnRelation);
                        PersonDetail personDetail = personDetailMapper.selectByDetailId(limsPersonInfo.getPersonDetailId());
                        personDetail.setPersonDetailId(limsPersonInfo.getPersonDetailId());
                        personDetail.setDeleteFlag(Constants.DELETE_FLAG_1);
                        personDetail.setUpdatePerson(operateUser.getLoginName());
                        personDetail.setUpdateDatetime(new Date());
                        personDetailMapper.updatePersonDetail1(personDetail);
                        limsPersonInfo.setPersonId(string);
                        limsPersonInfo.setDeleteFlag(Constants.DELETE_FLAG_1);
                        limsPersonInfo.setUpdateDatetime(new Date());
                        limsPersonInfo.setUpdatePerson(operateUser.getLoginName());
                        limsPersonInfoMapper.updatePersonInfo(limsPersonInfo);
                    }
                }
            }

            //是否更新标识
            boolean isUpdateFlag = false;
            //添加案件信息
            LimsCaseInfo caseInfoDna = delegateDataModel.getCaseInfoDna();
            if (caseInfoDna != null) {
                if (StringUtils.isBlank(caseInfoDna.getCaseId())) {
                    String caseId = UUID.randomUUID().toString();
                    caseInfoDna.setCaseId(caseId);
                    caseInfoDna.setCaseStatus(Constants.CASE_STATUS_01);
                    caseInfoDna.setCreateDatetime(new Date());
                    caseInfoDna.setCreatePerson(operateUser.getLoginName());
                    caseInfoDna.setDeleteFlag(Constants.DELETE_FLAG_0);
                    caseInfoDna.setHasAppendFlag(Constants.APPEND_FLAG_0);
                    //委托登记
                    caseInfoDna.setEntrustStatus(Constants.ENTRUST_STATUS_2);
                    limsCaseInfoMapper.insertCaseInfo(caseInfoDna);
                } else {
                    isUpdateFlag = true;
                    LimsCaseInfo limsCaseInfo = limsCaseInfoMapper.selectByCaseId(caseInfoDna.getCaseId());
                    limsCaseInfo.setCaseId(caseInfoDna.getCaseId());
                    limsCaseInfo.setCaseStatus(Constants.CASE_STATUS_01);
                    limsCaseInfo.setMajorNo(Constants.MAJOR_NO_DNA);
                    limsCaseInfo.setMajorType(Constants.IDENTIFICATION_TYPE_01);
                    limsCaseInfo.setCaseName(caseInfoDna.getCaseName());
                    limsCaseInfo.setCaseLocation(caseInfoDna.getCaseLocation());
                    limsCaseInfo.setCaseDatetime(caseInfoDna.getCaseDatetime());
                    limsCaseInfo.setCaseType(caseInfoDna.getCaseType());
                    limsCaseInfo.setCaseProperty(caseInfoDna.getCaseProperty());
                    limsCaseInfo.setCaseLevel(caseInfoDna.getCaseLevel());
                    limsCaseInfo.setCaseBrief(caseInfoDna.getCaseBrief());
                    limsCaseInfo.setCaseRemark(caseInfoDna.getCaseRemark());
                    limsCaseInfo.setUpdateDatetime(new Date());
                    limsCaseInfo.setUpdatePerson(operateUser.getLoginName());
                    //判断是否是补送
                    if (Constants.APPEND_FLAG_1.equals(appendFlag)) {
                        limsCaseInfo.setHasAppendFlag(Constants.APPEND_FLAG_1);
                    }
                    limsCaseInfoMapper.updateCaseInfoDna(limsCaseInfo);
                }
            }

            //添加委托人信息
            LimsConsignmentInfo consignatioInfo = delegateDataModel.getConsignatioInfo();
            if (consignatioInfo != null) {
                if (StringUtils.isBlank(consignatioInfo.getConsignmentId())) {
                    String consignmentId = UUID.randomUUID().toString();
                    consignatioInfo.setConsignmentId(consignmentId);
                    consignatioInfo.setCaseId(caseInfoDna.getCaseId());
                    if (consignatioInfo.getConsignmentNo().equals("委托书编号自动生成") ) {
                        //获取并生成流水号
                        String code = seqNoGenerateService.getNextNoVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(evaluationCenterId), operateUser.getOrgId());
                        updateConsignationSerialNumber(evaluationCenterId,operateUser.getOrgId());

                        if(StringUtils.isNotBlank(operateUser.getOrgId())){
                            LimsConsignmentInfo limsConsignatioInfo = limsConsignatioInfoMapper.selectByConsignmentNo(code,operateUser.getOrgId());
                            while(limsConsignatioInfo != null){
                                code = seqNoGenerateService.getNextNoVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(evaluationCenterId), operateUser.getOrgId());
                                updateConsignationSerialNumber(evaluationCenterId,operateUser.getOrgId());

                                limsConsignatioInfo = limsConsignatioInfoMapper.selectByConsignmentNo(code,operateUser.getOrgId());
                            }
                            consignatioInfo.setConsignmentNo(code);
                        }
//                    consignatioInfo.setConsignmentNo(code);

                    } else if(consignatioInfo.getConsignmentNo().equals("")) {
                        consignatioInfo.setConsignmentNo(null);
                    }else{
                        consignatioInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());
                    }
                    //consignatioInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());
                    consignatioInfo.setDelegateDatetime(new Date());
                    int reidentifyCount = 0;
                    consignatioInfo.setReidentifyCount((short) reidentifyCount);
                    consignatioInfo.setCreateDatetime(new Date());
                    consignatioInfo.setCreatePerson(operateUser.getLoginName());
                    consignatioInfo.setDeleteFlag(Constants.DELETE_FLAG_0);
                    //判断是否是补送
                    if (Constants.APPEND_FLAG_1.equals(appendFlag)) {
                        consignatioInfo.setAppendFlag(Constants.APPEND_FLAG_1);
                    }else {
                        consignatioInfo.setAppendFlag(Constants.APPEND_FLAG_0);
                    }
                    consignatioInfo.setStatus(Constants.STATUS_01);
                    //鉴定项目：在逃人员委托
                    consignatioInfo.setIdentifyItem(Constants.IDENTIFY_ITEM_FUGITIVES);
                    //鉴定中心orgId，设置入LimsConsignmentInfo表中
                    consignatioInfo.setAcceptOrgId(evaluationCenterId);
                    //委托类型（0：案件委托；1：在逃人员委托）
                    consignatioInfo.setConsignmentType(Constants.CONSIGNMENT_TYPE_1);
                    try {
                        limsConsignatioInfoMapper.insertConsignatioInfo(consignatioInfo);
                    } catch (Exception ex) {
                        logger.info("保存在逃人员委托信息失败：" + ex);
                    }

                } else {
                    LimsConsignmentInfo limsConsignmentInfo = limsConsignatioInfoMapper.selectByConsignmentId(consignatioInfo.getConsignmentId());
                    limsConsignmentInfo.setConsignmentId(consignatioInfo.getConsignmentId());
                    limsConsignmentInfo.setCaseId(caseInfoDna.getCaseId());
                    //limsConsignmentInfo.setDelegateDatetime(new Date());
                    limsConsignmentInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());//修改委托书编号
                    int reidentifyCount = 0;
                    limsConsignmentInfo.setReidentifyCount((short) reidentifyCount);
                    limsConsignmentInfo.setUpdatePerson(operateUser.getLoginName());
                    limsConsignmentInfo.setUpdateDatetime(new Date());
                    limsConsignmentInfo.setDelegateOrgCode(consignatioInfo.getDelegateOrgCode());
                    limsConsignmentInfo.setDelegateOrgName(consignatioInfo.getDelegateOrgName());
                    limsConsignmentInfo.setDelegator1Id(consignatioInfo.getDelegator1Id());
                    limsConsignmentInfo.setDelegator1Name(consignatioInfo.getDelegator1Name());
                    limsConsignmentInfo.setDelegator1Position(consignatioInfo.getDelegator1Position());
                    limsConsignmentInfo.setDelegator1PaperworkType(consignatioInfo.getDelegator1PaperworkType());
                    limsConsignmentInfo.setDelegator1PaperworkNo(consignatioInfo.getDelegator1PaperworkNo());
                    limsConsignmentInfo.setDelegator1PhoneNumber(consignatioInfo.getDelegator1PhoneNumber());
                    limsConsignmentInfo.setDelegator2Id(consignatioInfo.getDelegator2Id());
                    limsConsignmentInfo.setDelegator2Name(consignatioInfo.getDelegator2Name());
                    limsConsignmentInfo.setDelegator2Position(consignatioInfo.getDelegator2Position());
                    limsConsignmentInfo.setDelegator2PaperworkType(consignatioInfo.getDelegator2PaperworkType());
                    limsConsignmentInfo.setDelegator2PaperworkNo(consignatioInfo.getDelegator2PaperworkNo());
                    limsConsignmentInfo.setDelegator2PhoneNumber(consignatioInfo.getDelegator2PhoneNumber());
                    limsConsignmentInfo.setIdentifyRequirement(consignatioInfo.getIdentifyRequirement());
                    limsConsignmentInfo.setAreaOrgCode(consignatioInfo.getAreaOrgCode());
                    limsConsignmentInfo.setPreIdentifyDesc(consignatioInfo.getPreIdentifyDesc());
                    limsConsignmentInfo.setReidentifyReason(consignatioInfo.getReidentifyReason());
                    //鉴定中心orgId，设置入LimsConsignmentInfo表中
                    limsConsignmentInfo.setAcceptOrgId(evaluationCenterId);
                    //委托类型（0：案件委托；1：在逃人员委托）
                    limsConsignmentInfo.setConsignmentType(Constants.CONSIGNMENT_TYPE_1);
                    limsConsignatioInfoMapper.updateConsignatioInfo(limsConsignmentInfo);
                }
            }

            //添加在逃人员中间表
            FugitivesInfo fugitives = delegateDataModel.getFugitivesInfo();
            //更新案件时不插入在逃人员中间表
            if ((fugitives != null && !isUpdateFlag) || Constants.APPEND_FLAG_1.equals(appendFlag)) {
                FugitivesMiddleTable fugitivesMiddleTable = new FugitivesMiddleTable();
                fugitivesMiddleTable.setId(UUID.randomUUID().toString());
                fugitivesMiddleTable.setFugitivesInfoId(fugitives.getId());
                fugitivesMiddleTable.setConsignmentInfoId(consignatioInfo.getConsignmentId());
                fugitivesMiddleTable.setCaseId(caseInfoDna.getCaseId());
                fugitivesMiddleTable.setCreateDatetime(new Date());
                fugitivesMiddleTable.setCreatePerson(operateUser.getLoginName());

                fugitivesMiddleTableMapper.insert(fugitivesMiddleTable);
            }

            //添加被鉴定人信息
            List<LimsPersonInfo> limsPersonInfoList = delegateDataModel.getLimsPersonInfoList();
            if (null != limsPersonInfoList && limsPersonInfoList.size() > 0) {
                for (LimsPersonInfo limsPersonInfo : limsPersonInfoList) {

                    //对人员照片进行上传
                    if (StringUtils.isNotBlank(limsPersonInfo.getPersonFrontPicture())) {
                        String personFrontPicturePath = ImgUtils.generateImage(limsPersonInfo.getPersonFrontPicture(), personImg);
                        String personFrontPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpPersonImg, personFrontPicturePath);
                        limsPersonInfo.setPersonFrontPicturePath(personFrontPicturePathFtp == null ? "" : personFrontPicturePathFtp);
                    }

                    PersonDetail personDetail = new PersonDetail();
                    LimsPersonInfo limsPersonInfo1 = new LimsPersonInfo();

                    LimsPersonInfo limsPersonInfo3 = limsPersonInfoMapper.selectPersonInfoById(limsPersonInfo.getPersonId());

                    if (StringUtils.isBlank(limsPersonInfo.getPersonId())  || limsPersonInfo3 == null) {
                        //人员详细表
                        personDetail.setPersonDetailId(UUID.randomUUID().toString());
                        personDetail.setPersonName(limsPersonInfo.getPersonName());
                        personDetail.setPersonNo(limsPersonInfo.getPersonNo());
                        personDetail.setPersonType(limsPersonInfo.getPersonType());
                        personDetail.setPersonGender(limsPersonInfo.getPersonGender());
                        personDetail.setPerosnAge(limsPersonInfo.getPerosnAge());
                        personDetail.setPersonHeight(limsPersonInfo.getPersonHeight());
                        personDetail.setPersonWeight(limsPersonInfo.getPersonWeight());
                        personDetail.setIdCardFlag(limsPersonInfo.getIdCardFlag());
                        personDetail.setPersonIdCard(limsPersonInfo.getPersonIdCard());
                        personDetail.setNoIdCardDesc(limsPersonInfo.getNoIdCardDesc());
                        personDetail.setPersonCurrentAddress(limsPersonInfo.getPersonCurrentAddress());
                        personDetail.setCreateDatetime(new Date());
                        personDetail.setCreatePerson(operateUser.getLoginName());
                        personDetail.setDeleteFlag(Constants.DELETE_FLAG_0);
                        personDetail.setPersonRace(limsPersonInfo.getNation());
                        personDetail.setCertificateType(limsPersonInfo.getCertificateType());
                        if (StringUtils.isNotBlank(limsPersonInfo.getPersonFrontPicture())) {
                            personDetail.setPersonFrontPicture(limsPersonInfo.getPersonFrontPicture());
                            personDetail.setPersonFrontPicturePath(limsPersonInfo.getPersonFrontPicturePath());
                        }
                        limsPersonInfoMapper.insertPersonDetail(personDetail);

                        //被鉴定人信息
                        String personId = UUID.randomUUID().toString();
                        limsPersonInfo1.setPersonId(personId);
                        limsPersonInfo1.setCaseId(caseInfoDna.getCaseId());
                        limsPersonInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsPersonInfo1.setPersonName(limsPersonInfo.getPersonName());
                        limsPersonInfo1.setPersonNo(limsPersonInfo.getPersonNo());
                        limsPersonInfo1.setPersonType(limsPersonInfo.getPersonType());
                        limsPersonInfo1.setCreateDatetime(new Date());
                        limsPersonInfo1.setCreatePerson(operateUser.getLoginName());
                        limsPersonInfo1.setDeleteFlag(Constants.DELETE_FLAG_0);
                        limsPersonInfo1.setPersonDetailId(personDetail.getPersonDetailId());
                        limsPersonInfoMapper.insertPersonInfo(limsPersonInfo1);

                        //判断亲缘关系是否为空
                        LimsPerosnRelation limsPerosnRelation = new LimsPerosnRelation();
                        limsPerosnRelation.setRelationId(UUID.randomUUID().toString());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfo1.getPersonId());
                        limsPerosnRelation.setRelationType(limsPersonInfo.getRelationType());
                        limsPerosnRelation.setCreateDatetime(new Date());
                        limsPerosnRelation.setCreatePerson(operateUser.getLoginName());
                        limsPerosnRelation.setDeleteFlag(Constants.DELETE_FLAG_0);
                        limsPerosnRelationMapper.insertPersonRelation(limsPerosnRelation);

                    } else {
                        PersonDetail personDetail1 = personDetailMapper.selectByDetailId(limsPersonInfo3.getPersonDetailId());
                        //修改人员详情表
                        personDetail1.setPersonDetailId(limsPersonInfo3.getPersonDetailId());
                        personDetail1.setPersonName(limsPersonInfo.getPersonName());
                        personDetail.setPersonNo(limsPersonInfo.getPersonNo());
                        personDetail1.setPersonType(limsPersonInfo.getPersonType());
                        personDetail1.setPersonGender(limsPersonInfo.getPersonGender());
                        personDetail1.setPerosnAge(limsPersonInfo.getPerosnAge());
                        personDetail1.setPersonHeight(limsPersonInfo.getPersonHeight());
                        personDetail1.setPersonWeight(limsPersonInfo.getPersonWeight());
                        personDetail1.setIdCardFlag(limsPersonInfo.getIdCardFlag());
                        personDetail1.setPersonIdCard(limsPersonInfo.getPersonIdCard());
                        personDetail1.setNoIdCardDesc(limsPersonInfo.getNoIdCardDesc());
                        personDetail1.setPersonCurrentAddress(limsPersonInfo.getPersonCurrentAddress());
                        personDetail1.setPersonRace(limsPersonInfo.getNation());
                        personDetail1.setCertificateType(limsPersonInfo.getCertificateType());
                        personDetail1.setUpdateDatetime(new Date());
                        personDetail1.setUpdatePerson(operateUser.getLoginName());
                        if (StringUtils.isNotBlank(limsPersonInfo.getPersonFrontPicture())) {
                            personDetail1.setPersonFrontPicture(limsPersonInfo.getPersonFrontPicture());
                            personDetail1.setPersonFrontPicturePath(limsPersonInfo.getPersonFrontPicturePath());
                        }
                        personDetailMapper.updatePersonDetail1(personDetail1);

                        //修改人员表
                        LimsPersonInfo limsPersonInfo2 = limsPersonInfoMapper.selectPersonInfoById(limsPersonInfo.getPersonId());
                        limsPersonInfo2.setCaseId(caseInfoDna.getCaseId());
                        limsPersonInfo2.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsPersonInfo2.setPersonName(limsPersonInfo.getPersonName());
                        limsPersonInfo2.setPersonNo(limsPersonInfo.getPersonNo());
                        limsPersonInfo2.setPersonType(limsPersonInfo.getPersonType());
                        limsPersonInfo2.setUpdateDatetime(new Date());
                        limsPersonInfo2.setUpdatePerson(operateUser.getLoginName());
                        limsPersonInfo2.setPersonId(limsPersonInfo.getPersonId());
                        limsPersonInfo2.setPersonRemark(limsPersonInfo.getPersonRemark());
                        limsPersonInfoMapper.updatePersonInfo(limsPersonInfo2);

                        //修改人员关系
                        LimsPerosnRelation limsPerosnRelation = limsPerosnRelationMapper.selectBySourcePersonId(limsPersonInfo.getPersonId());
                        limsPerosnRelation.setRelationId(limsPerosnRelation.getRelationId());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfo.getPersonId());
                        limsPerosnRelation.setRelationType(limsPersonInfo.getRelationType());
                        limsPerosnRelation.setUpdateDatetime(new Date());
                        limsPerosnRelation.setUpdatePerson(operateUser.getLoginName());
                        limsPerosnRelationMapper.updatePerosnRelation(limsPerosnRelation);
                    }

                    //样本信息
                    List<LimsSampleInfoDna> limsSampleInfoDnaList = limsPersonInfo.getSampleInfoDnaList();
                    if (null != limsSampleInfoDnaList && limsSampleInfoDnaList.size() > 0) {
                        for (LimsSampleInfoDna limsSampleInfoDna : limsSampleInfoDnaList) {

                            //对人员样本照片进行上传
                            if (StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())) {
                                String sampleDnaPicturePath = ImgUtils.generateImage(limsSampleInfoDna.getSampleDnaPicture(), personImg);
                                String sampleDnaPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpSampleImg, sampleDnaPicturePath);
                                limsSampleInfoDna.setSampleDnaPicturePath(sampleDnaPicturePathFtp == null ? "" : sampleDnaPicturePathFtp);
                            }

                            if (StringUtils.isBlank(limsSampleInfoDna.getSampleId()) && StringUtils.isNotBlank(limsSampleInfoDna.getSampleType())) {
                                LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
                                String sampleId = UUID.randomUUID().toString();
                                sampleInfoDna.setSampleId(sampleId);
                                sampleInfoDna.setConsignmentId(consignatioInfo.getConsignmentId());
                                sampleInfoDna.setCaseId(caseInfoDna.getCaseId());
                                sampleInfoDna.setSampleName(limsSampleInfoDna.getSampleName());
                                sampleInfoDna.setSampleType(limsSampleInfoDna.getSampleType());
                                sampleInfoDna.setSampleDesc(limsSampleInfoDna.getSampleDesc());
                                sampleInfoDna.setSamplePacking(limsSampleInfoDna.getSamplePacking());
                                sampleInfoDna.setExtractDatetime(limsSampleInfoDna.getExtractDatetime());
                                sampleInfoDna.setExtractMethod(limsSampleInfoDna.getExtractMethod());

                                sampleInfoDna.setExtractPerson(operateUser.getLoginName());
                                sampleInfoDna.setSampleFlag(Constants.SAMPLE_FLAG_1);
                                if(StringUtils.isBlank(limsPersonInfo.getPersonId()) || limsPersonInfo3 == null){
                                    sampleInfoDna.setLinkId(limsPersonInfo1.getPersonId());
                                }else{
                                    sampleInfoDna.setLinkId(limsPersonInfo3.getPersonId());
                                }
                                sampleInfoDna.setSamplePurpose(limsSampleInfoDna.getSamplePurpose());
                                sampleInfoDna.setSampleStatus(Constants.SAMPLE_STATUS_01);
                                sampleInfoDna.setInstoredFlag(Constants.INSTORED_FLAG_0);
                                sampleInfoDna.setCreateDatetime(new Date());
                                sampleInfoDna.setCreatePerson(operateUser.getLoginName());
                                sampleInfoDna.setDeleteFlag(Constants.DELETE_FLAG_0);
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())) {
                                    sampleInfoDna.setSampleDnaPicture(limsSampleInfoDna.getSampleDnaPicture());
                                    sampleInfoDna.setSampleDnaPicturePath(limsSampleInfoDna.getSampleDnaPicturePath());
                                }
                                sampleInfoDna.setSampleCarrier(limsSampleInfoDna.getSampleCarrier());
                                //添加是否事主样本
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreVictimStats())) {
                                    sampleInfoDna.setCoreVictimStats(limsSampleInfoDna.getCoreVictimStats());
                                }
                                //是否中心取
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreTakenStats())) {
                                    sampleInfoDna.setCoreTakenStats(limsSampleInfoDna.getCoreTakenStats());
                                }
                                limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
                            } else if (StringUtils.isNotBlank(limsSampleInfoDna.getSampleId()) && StringUtils.isNotBlank(limsSampleInfoDna.getSampleType())) {
                                LimsSampleInfoDna sampleInfoDna2 = limsSampleInfoDnaMapper.selectSampleInfoDnaById(limsSampleInfoDna.getSampleId());
                                sampleInfoDna2.setConsignmentId(consignatioInfo.getConsignmentId());
                                sampleInfoDna2.setCaseId(caseInfoDna.getCaseId());
                                sampleInfoDna2.setSampleName(limsSampleInfoDna.getSampleName());
                                sampleInfoDna2.setSampleType(limsSampleInfoDna.getSampleType());
                                sampleInfoDna2.setSampleDesc(limsSampleInfoDna.getSampleDesc());
                                sampleInfoDna2.setSamplePacking(limsSampleInfoDna.getSamplePacking());
                                sampleInfoDna2.setExtractDatetime(limsSampleInfoDna.getExtractDatetime());
                                sampleInfoDna2.setExtractMethod(limsSampleInfoDna.getExtractMethod());
                                sampleInfoDna2.setExtractPerson(operateUser.getLoginName());
                                sampleInfoDna2.setSampleFlag(Constants.SAMPLE_FLAG_1);
                                sampleInfoDna2.setSamplePurpose(limsSampleInfoDna.getSamplePurpose());
                                sampleInfoDna2.setSampleStatus(Constants.SAMPLE_STATUS_01);
                                sampleInfoDna2.setInstoredFlag(Constants.INSTORED_FLAG_0);
                                sampleInfoDna2.setUpdateDatetime(new Date());
                                sampleInfoDna2.setUpdatePerson(operateUser.getLoginName());
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())) {
                                    sampleInfoDna2.setSampleDnaPicture(limsSampleInfoDna.getSampleDnaPicture());
                                    sampleInfoDna2.setSampleDnaPicturePath(limsSampleInfoDna.getSampleDnaPicturePath());
                                }

                                //是否中心取
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreTakenStats())) {
                                    sampleInfoDna2.setCoreTakenStats(limsSampleInfoDna.getCoreTakenStats());
                                }
                                //是否为事主样本
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreVictimStats())) {
                                    sampleInfoDna2.setCoreVictimStats(limsSampleInfoDna.getCoreVictimStats());
                                }
                                sampleInfoDna2.setSampleCarrier(limsSampleInfoDna.getSampleCarrier());
                                limsSampleInfoDnaMapper.updateSampleInfoDna(sampleInfoDna2);
                            }
                        }
                    }

                }
            }

            //添加检材信息
            List<LimsSampleInfoDna> sampleInfoDnaList = delegateDataModel.getSampleInfoDnaList();
            if (null != sampleInfoDnaList && sampleInfoDnaList.size() > 0) {
                for (LimsSampleInfoDna sampleInfoDna : sampleInfoDnaList) {

                    //对物证检材照片进行上传
                    if (StringUtils.isNotBlank(sampleInfoDna.getSampleMaterialPicture())) {
                        String sampleMaterialPicturePath = ImgUtils.generateImage(sampleInfoDna.getSampleMaterialPicture(), personImg);
                        String sampleMaterialPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpSampleImg, sampleMaterialPicturePath);
                        sampleInfoDna.setSampleMaterialPicturePath(sampleMaterialPicturePathFtp == null ? "" : sampleMaterialPicturePathFtp);
                    }

                    if (StringUtils.isBlank(sampleInfoDna.getSampleIdwz())) {
                        sampleInfoDna.setSampleId(UUID.randomUUID().toString());
                        sampleInfoDna.setConsignmentId(consignatioInfo.getConsignmentId());
                        sampleInfoDna.setCaseId(caseInfoDna.getCaseId());
                        sampleInfoDna.setEvidenceNo(sampleInfoDna.getEvidenceNo());
                        sampleInfoDna.setSampleName(sampleInfoDna.getSampleName());
                        sampleInfoDna.setSampleType(sampleInfoDna.getSampleType());
                        sampleInfoDna.setSampleDesc(sampleInfoDna.getSampleDesc());
                        sampleInfoDna.setSamplePacking(sampleInfoDna.getSamplePacking());
                        sampleInfoDna.setExtractDatetime(sampleInfoDna.getExtractDatetime());
                        sampleInfoDna.setExtractMethod(sampleInfoDna.getExtractMethod());
                        sampleInfoDna.setExtractPerson(operateUser.getLoginName());
                        sampleInfoDna.setSampleFlag(Constants.SAMPLE_FLAG_0);
                        //物证id不知道
                        sampleInfoDna.setSamplePurpose(sampleInfoDna.getSamplePurpose());
                        sampleInfoDna.setSampleStatus(Constants.SAMPLE_STATUS_01);
                        sampleInfoDna.setInstoredFlag(Constants.INSTORED_FLAG_0);
                        sampleInfoDna.setCreateDatetime(new Date());
                        sampleInfoDna.setCreatePerson(operateUser.getLoginName());
                        sampleInfoDna.setDeleteFlag(Constants.DELETE_FLAG_0);
                        sampleInfoDna.setSampleCarrier(sampleInfoDna.getSampleCarrier());
                        //物证检材照片
                        sampleInfoDna.setSampleMaterialPicture(sampleInfoDna.getSampleMaterialPicture());
                        //物证检材照片路径
                        sampleInfoDna.setSampleMaterialPicturePath(sampleInfoDna.getSampleMaterialPicturePath());
                        //是否中心取
                        if (StringUtils.isNotBlank(sampleInfoDna.getCoreTakenStats())) {
                            sampleInfoDna.setCoreTakenStats(sampleInfoDna.getCoreTakenStats());
                        }
                        //是否为事主样本
                        if (StringUtils.isNotBlank(sampleInfoDna.getCoreVictimStats())) {
                            sampleInfoDna.setCoreVictimStats(sampleInfoDna.getCoreVictimStats());
                        }
                        limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
                    } else {
                        LimsSampleInfoDna sampleInfoDna3 = limsSampleInfoDnaMapper.selectSampleInfoDnaById(sampleInfoDna.getSampleIdwz());
                        sampleInfoDna3.setConsignmentId(consignatioInfo.getConsignmentId());
                        sampleInfoDna3.setCaseId(caseInfoDna.getCaseId());
                        sampleInfoDna3.setEvidenceNo(sampleInfoDna.getEvidenceNo());
                        sampleInfoDna3.setSampleName(sampleInfoDna.getSampleName());
                        sampleInfoDna3.setSampleType(sampleInfoDna.getSampleType());
                        sampleInfoDna3.setSampleDesc(sampleInfoDna.getSampleDesc());
                        sampleInfoDna3.setSamplePacking(sampleInfoDna.getSamplePacking());
                        sampleInfoDna3.setExtractDatetime(sampleInfoDna.getExtractDatetime());
                        sampleInfoDna3.setExtractMethod(sampleInfoDna.getExtractMethod());
                        sampleInfoDna3.setExtractPerson(operateUser.getLoginName());
                        sampleInfoDna3.setSampleFlag(Constants.SAMPLE_FLAG_0);
                        sampleInfoDna3.setSamplePurpose(sampleInfoDna.getSamplePurpose());
                        sampleInfoDna3.setSampleStatus(Constants.SAMPLE_STATUS_01);
                        sampleInfoDna3.setInstoredFlag(Constants.INSTORED_FLAG_0);
                        sampleInfoDna3.setUpdateDatetime(new Date());
                        sampleInfoDna3.setUpdatePerson(operateUser.getLoginName());
                        sampleInfoDna3.setSampleCarrier(sampleInfoDna.getSampleCarrier());
                        //物证检材照片
                        sampleInfoDna3.setSampleMaterialPicture(sampleInfoDna.getSampleMaterialPicture());
                        //物证检材照片路径
                        sampleInfoDna3.setSampleMaterialPicturePath(sampleInfoDna.getSampleMaterialPicturePath());
                        //是否中心取
                        if(StringUtils.isNotBlank(sampleInfoDna.getCoreTakenStats())) {
                            sampleInfoDna3.setCoreTakenStats(sampleInfoDna.getCoreTakenStats());
                        }
                        //是否事主样本
                        if (StringUtils.isNotBlank(sampleInfoDna.getCoreVictimStats())) {
                            sampleInfoDna3.setCoreVictimStats(sampleInfoDna.getCoreVictimStats());
                        }
                        limsSampleInfoDnaMapper.updateSampleInfoDna(sampleInfoDna3);
                    }
                }
            }

            if (Constants.APPEND_FLAG_1.equals(appendFlag)) {
                //记录该案件是第几次补送
                int replacementNum = 0;
                //查询之前是否补送
                LimsConsignmentInfo limsConsignmentInfo = limsConsignmentInfoMapper.selectMaxReplacementNum(caseInfoDna.getCaseId());
                if(null != limsConsignmentInfo){
                    if(null != limsConsignmentInfo.getReplacementNum()){
                        Integer replacementNum1 = limsConsignmentInfo.getReplacementNum();
                        replacementNum = replacementNum1+1;
                        //更新补送第几次数
                        LimsConsignmentInfo limsConsignmentInfo1 = new LimsConsignmentInfo();
                        limsConsignmentInfo1.setReplacementNum(replacementNum);
                        limsConsignmentInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsConsignmentInfo1.setCaseId(caseInfoDna.getCaseId());
                        limsConsignmentInfoMapper.updateReplacementNum(limsConsignmentInfo1);
                    }
                }else{
                    //更新补送第几次数
                    LimsConsignmentInfo limsConsignmentInfo1 = new LimsConsignmentInfo();
                    limsConsignmentInfo1.setReplacementNum(1);
                    limsConsignmentInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                    limsConsignmentInfo1.setCaseId(caseInfoDna.getCaseId());
                    limsConsignmentInfoMapper.updateReplacementNum(limsConsignmentInfo1);
                }
            }
            result.put("caseId", caseInfoDna.getCaseId());
            result.put("consignmentId", consignatioInfo.getConsignmentId());
        } catch (Exception ex) {
            logger.info("在逃人员案件委托登记报错：" + ex);
        }

        return result;
    }
    
    /**
     * 根据委托编号查询委托信息
     *
     * @param consignmentNo
     * @return
     */
    @Override
    public LimsConsignmentInfo selectByConsignmentNo(String consignmentNo, String delegateOrgCode) {
        LimsConsignmentInfo consignatioInfo = limsConsignatioInfoMapper.selectByConsignmentNo(consignmentNo, delegateOrgCode);
        return consignatioInfo;
    }
    
    @Override
    public List<LimsConsignmentInfo> selectByCaseXkNoTwo(String caseXkNo) {
        return limsConsignatioInfoMapper.selectByCaseXkNoTwo(caseXkNo);
    }
}
