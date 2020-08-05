package com.bazl.dna.database.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.*;
import com.bazl.dna.database.service.model.bo.CaseInstoreModel;
import com.bazl.dna.database.service.model.po.*;
import com.bazl.dna.database.service.model.qo.CaseInfoQuery;
import com.bazl.dna.database.service.model.qo.CaseReportQuery;
import com.bazl.dna.database.service.model.vo.CaseInfoVo;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.utils.ListUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 案件信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class CaseInfoServiceImpl extends ServiceImpl<CaseInfoMapper, CaseInfo> implements CaseInfoService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ConsignmentInfoMapper consignmentInfoMapper;

    @Autowired
    DnaSampleInfoMapper dnaSampleInfoMapper;

    @Autowired
    CasePersonInfoMapper casePersonInfoMapper;

    @Autowired
    PersonRelativeInfoMapper personRelativeInfoMapper;

    @Autowired
    DnaGeneInfoMapper dnaGeneInfoMapper;

    @Autowired
    GeneSyncQueueMapper geneSyncQueueMapper;

    @Autowired
    TransferCaseQueueMapper transferCaseQueueMapper;

    @Autowired
    TransferCaseSampleMapper transferCaseSampleMapper;

    @Autowired
    AutoCompareQueueMapper autoCompareQueueMapper;

    @Autowired
    AutoCompareSettingsMapper autoCompareSettingsMapper;

    @Autowired
    LabServerInfoMapper labServerInfoMapper;

    @Autowired
    CaseInfoMapper caseInfoMapper;


    @Override
    public CaseInfo selectByPrimaryKey(Integer id) {
        try {
            return caseInfoMapper.selectByPrimaryKey(id);
        }catch(Exception ex) {
            logger.error("invoke caseInfoService.selectVoById error.", ex);
        }
        return null;
    }

    @Override
    public CaseInfoVo selectByIdDetail(Integer id) {
        try {
            return caseInfoMapper.selectByIdDetail(id);
        } catch (Exception e) {
        		logger.error("根据案件id查询案件详细信息失败！" + e);
        }
        return null;
    }


    /**
     * 保存入本地库的案件和样本信息
     * @param caseInstoreModel
     *  caseInfo: 案件主体信息
     *  consignmentList：入库样本所属的的委托列表
     *  sampleInfoList: 入库的样本列表
     *  personInfoList: 入库的人员列表
     *  personRelativeList: 入库的人员亲属关系列表
     * @return
     */
        @Override
        @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public boolean saveInstoreCaseAndSamples(CaseInstoreModel caseInstoreModel) throws Exception {
            Integer caseId = -1;

            try {
                //保存案件信息
                CaseInfo caseInfo = caseInstoreModel.getCaseInfo();
                caseId = saveCaseInfo(caseInfo);
            }catch(Exception ex) {
                logger.error("invoke CaseInfoService.saveInstoreCaseAndSamples, save caseInfo error.", ex);
                throw new Exception("案件信息入库保存失败！");
            }

        try{
            //保存委托信息
            List<ConsignmentInfo> consignmentInfoList = caseInstoreModel.getConsignmentInfoList();
            if(ListUtils.isNotNullAndEmptyList(consignmentInfoList)){
                saveConsignmentList(caseId, consignmentInfoList);
            }
        }catch(Exception ex){
            logger.error("invoke CaseInfoService.saveInstoreCaseAndSamples, save consignmentInfo error.", ex);
            throw new Exception("委托信息入库保存失败！");
        }

        try{
            //保存人员信息
            List<CasePersonInfo> casePersonInfoList = caseInstoreModel.getCasePersonInfoList();
            if(ListUtils.isNotNullAndEmptyList(casePersonInfoList)){
                savePersonInfoList(caseId, casePersonInfoList,caseInstoreModel.getConsignmentInfoList());
            }
        }catch(Exception ex){
            logger.error("invoke CaseInfoService.saveInstoreCaseAndSamples, save casePersonInfo error.", ex);
            throw new Exception("案件人员信息入库保存失败！");
        }

        try{
            //保存亲缘关系信息
            List<PersonRelativeInfo> personRelativeInfoList = caseInstoreModel.getPersonRelativeInfoList();
            if(ListUtils.isNotNullAndEmptyList(personRelativeInfoList)){
                savePersonRelativeInfoList(personRelativeInfoList);
            }
        }catch(Exception ex){
            logger.error("invoke CaseInfoService.saveInstoreCaseAndSamples, save personRelativeInfoList error.", ex);
            throw new Exception("案件人员亲缘关系信息入库保存失败！");
        }

        try{
            //保存样本信息
            List<DnaSampleInfo> dnaSampleInfoList = caseInstoreModel.getDnaSampleInfoList();
            if(ListUtils.isNotNullAndEmptyList(dnaSampleInfoList)){
                saveDnaSampleInfoList(caseId, dnaSampleInfoList);
            }
        }catch(Exception ex){
            logger.error("invoke CaseInfoService.saveInstoreCaseAndSamples, save dnaSampleInfoList error.", ex);
            throw new Exception("样本信息入库保存失败！");
        }

        try{
            //保存基因信息
            List<DnaGeneInfo> dnaGeneInfoList = caseInstoreModel.getDnaGeneInfoList();
            if(ListUtils.isNotNullAndEmptyList(dnaGeneInfoList)){
                saveDnaGeneInfoList(dnaGeneInfoList);
            }
        }catch(Exception ex){
            logger.error("invoke CaseInfoService.saveInstoreCaseAndSamples, save dnaGeneInfoList error.", ex);
            throw new Exception("样本信息入库保存失败！");
        }

        // 插入基因同步比对队列
            List<DnaGeneInfo> dnaGeneInfoList = caseInstoreModel.getDnaGeneInfoList();
            if(ListUtils.isNotNullAndEmptyList(dnaGeneInfoList)){
                Boolean aBoolean = saveComparisonQueue(caseInstoreModel.getCaseInfo().getLabServerNo(), dnaGeneInfoList);
                if (aBoolean == false){
                    throw new Exception("基因信息同步队列保存失败！"+"LabServerNo =="+caseInstoreModel.getCaseInfo().getLabServerNo()+"dnaGeneInfoList = "+dnaGeneInfoList);
                }
            }
        //插入上报国家库队列
            saveNationalTreasuryQueue(caseInstoreModel.getCaseInfo(),caseInstoreModel.getDnaSampleInfoList());
         //插入比对队列
            saveCompareQueue(caseInstoreModel.getDnaSampleInfoList());
        return true;
    }

    //插入比对队列
    private void saveCompareQueue(List<DnaSampleInfo> dnaSampleInfoList) {
        for (DnaSampleInfo dnaSampleInfo : dnaSampleInfoList) {
            DnaGeneInfo dnaGeneInfo = dnaGeneInfoMapper.selectOne(new QueryWrapper<DnaGeneInfo>().eq("SAMPLE_ID", dnaSampleInfo.getId()));
            List<AutoCompareSettings> compareSettingsList = autoCompareSettingsMapper.selectList(new QueryWrapper<AutoCompareSettings>().eq("INSTORE_DATA_TYPE", dnaSampleInfo.getInstoreDataType()));
            for (AutoCompareSettings compareSettings : compareSettingsList) {
                insertCompareQueue(compareSettings,dnaGeneInfo);
            }
        }
    }
    //插入比对队列
    private void insertCompareQueue(AutoCompareSettings autoCompareSettings,DnaGeneInfo dnaGeneInfo) {
         if (dnaGeneInfo.getId() != null){
             List<LabServerInfo> labServerInfos = labServerInfoMapper.selectList(new QueryWrapper<LabServerInfo>());
             ArrayList<String> labServerNoList = new ArrayList<>();
             for (LabServerInfo labServerInfo : labServerInfos) {
                    labServerNoList.add(labServerInfo.getLabServerNo());
             }

             AutoCompareQueue compareQueue = new AutoCompareQueue();
             compareQueue.setGeneId(dnaGeneInfo.getId());
             //权重
             compareQueue.setCompareWeight(1);
             //str
             compareQueue.setCompareMode(Integer.valueOf(autoCompareSettings.getCompareMode()));
             // 0 未比对
             compareQueue.setCompareStatus("0");
             CompareParams compareParams = JSONObject.parseObject(compareQueue.getCompareParams(), CompareParams.class);
            	 compareParams.setLowestSameLimit(autoCompareSettings.getLowestSameLimit());
             compareParams.setMostDiffLimit(autoCompareSettings.getMostDiffLimit());
             compareParams.setTargetLabServerNo(labServerNoList);
             String jsonStr = JSONObject.toJSONString(autoCompareSettings.getInstoreDataType());
             List<String> list = JSONObject.parseArray(jsonStr,  String.class);
             compareParams.setTargetDataType(list);
             compareQueue.setCompareParams(JSONObject.toJSONString(compareParams));
             autoCompareQueueMapper.insert(compareQueue);

         }


    }
    //插入上报国家库队列
    private void saveNationalTreasuryQueue(CaseInfo caseInfo,List<DnaSampleInfo> dnaSampleInfoList) {
        //案件队列
        TransferCaseQueue transferCaseQueueDb = transferCaseQueueMapper.selectOne(new QueryWrapper<TransferCaseQueue>().eq("CASE_ID", caseInfo.getId()));
        //样本队列
		if (transferCaseQueueDb == null) {
        		transferCaseQueueDb = new TransferCaseQueue();
            transferCaseQueueDb.setCaseId(caseInfo.getId());
            transferCaseQueueDb.setLabServerNo(caseInfo.getLabServerNo());
            transferCaseQueueDb.setCreateDatetime(LocalDateTime.now());
            transferCaseQueueMapper.insert(transferCaseQueueDb);
            //查出案件队列主键id
            TransferCaseQueue transferCaseQueue = transferCaseQueueMapper.selectOne(new QueryWrapper<TransferCaseQueue>().eq("CASE_ID", caseInfo.getId()));
            for (DnaSampleInfo dnaSampleInfo : dnaSampleInfoList) {
            		TransferCaseSample transferCaseSampleDb = new TransferCaseSample();
                transferCaseSampleDb.setDnaSampleId(dnaSampleInfo.getId());
                transferCaseSampleDb.setTransferCaseQueueId(transferCaseQueue.getId());
                transferCaseSampleMapper.insert(transferCaseSampleDb);
            }
        }
    }
    //插入本地比对队列
    private Boolean saveComparisonQueue(String labServerNo,List<DnaGeneInfo> dnaGeneInfoList) {
        if (StringUtils.isNotBlank(labServerNo) && ListUtils.isNotNullAndEmptyList(dnaGeneInfoList)){
            for (DnaGeneInfo dnaGeneInfo : dnaGeneInfoList) {
                GeneSyncQueue geneSyncQueue = new GeneSyncQueue();
                geneSyncQueue.setLabServeNo(labServerNo);
                geneSyncQueue.setGeneId(dnaGeneInfo.getId());
                //0 未同步
                geneSyncQueue.setSyncStatus("0");
                // 0 新增 1修改
                geneSyncQueue.setSyncType("0");
                geneSyncQueue.setCreateDatetime(LocalDateTime.now());
                int insert = geneSyncQueueMapper.insert(geneSyncQueue);
                if (insert == 0){
                    return  false;
                }
            }
        }
        return true;
    }

    private void savePersonRelativeInfoList(List<PersonRelativeInfo> personRelativeInfoList) {
        for (PersonRelativeInfo personRelativeInfo : personRelativeInfoList) {
        		PersonRelativeInfo personRelativeInfoDb = personRelativeInfoMapper.selectOne(new QueryWrapper<PersonRelativeInfo>().eq("TARGET_PERSON_UUID", personRelativeInfo.getTargetPersonUuid()));
            if (personRelativeInfoDb == null) {
                personRelativeInfoMapper.insert(personRelativeInfo);
            } else {
                BeanUtils.copyProperties(personRelativeInfo, personRelativeInfoDb, "id");
                personRelativeInfoMapper.updateById(personRelativeInfoDb);
            }

        }
    }

    private void saveDnaGeneInfoList( List<DnaGeneInfo> dnaGeneInfoList) {
        for (DnaGeneInfo dnaGeneInfo : dnaGeneInfoList) {
        		DnaSampleInfo dnaSampleInfoDb = dnaSampleInfoMapper.selectOne(new QueryWrapper<DnaSampleInfo>().eq("LIMS_SAMPLE_UUID", dnaGeneInfo.getSampleId()));
            if (dnaSampleInfoDb != null){
                dnaGeneInfo.setSampleId(dnaSampleInfoDb.getId());
                dnaGeneInfoMapper.insert(dnaGeneInfo);
            }
        }
    }

    private void saveDnaSampleInfoList(Integer caseId, List<DnaSampleInfo> dnaSampleInfoList) {
        //物证
        ConsignmentInfo consignmentInfoInDb = null;
        for (DnaSampleInfo dnaSampleInfo : dnaSampleInfoList) {
            consignmentInfoInDb = consignmentInfoMapper.selectOne(new QueryWrapper<ConsignmentInfo>().eq("LIMS_CONSIGNMENT_UUID", dnaSampleInfo.getConsignmentId()));
            if (consignmentInfoInDb != null){
            		DnaSampleInfo dnaSampleInfoDb = dnaSampleInfoMapper.selectOne(new QueryWrapper<DnaSampleInfo>().eq("LIMS_SAMPLE_UUID", dnaSampleInfo.getLimsSampleUuid()));
                
                if (dnaSampleInfoDb == null) {
                		dnaSampleInfoDb = new DnaSampleInfo();
                		dnaSampleInfoDb.setConsignmentId(consignmentInfoInDb.getId());
                    dnaSampleInfoDb.setCaseId(caseId);
                    dnaSampleInfoMapper.insert(dnaSampleInfoDb);
                } else {
                		dnaSampleInfoDb.setConsignmentId(consignmentInfoInDb.getId());
                    dnaSampleInfoDb.setCaseId(caseId);
                    BeanUtils.copyProperties(dnaSampleInfo, dnaSampleInfoDb, "id", "limsSampleUuid");
                    dnaSampleInfoMapper.updateById(dnaSampleInfoDb);
                }
            }
        }
    }

    private void savePersonInfoList(Integer caseId, List<CasePersonInfo> casePersonInfoList, List<ConsignmentInfo> consignmentInfoList) {
        //人员
        CasePersonInfo casePersonInfoDb = new CasePersonInfo();
        //委托
        ConsignmentInfo consignmentInfoInDb = null;
        casePersonInfoDb.setCaseId(caseId);
        for (CasePersonInfo casePersonInfo : casePersonInfoList) {

            /**
             * 根据lims中的人员id查询当前人员是否已经存在，如果存在，则更新；不存在，则插入
             */
            consignmentInfoInDb = consignmentInfoMapper.selectOne(new QueryWrapper<ConsignmentInfo>().eq("LIMS_CONSIGNMENT_UUID", casePersonInfo.getConsignmentId()));
            if (consignmentInfoInDb != null){
                casePersonInfoDb.setConsignmentId(consignmentInfoInDb.getId());
            }

            casePersonInfoDb = casePersonInfoMapper.selectOne(new QueryWrapper<CasePersonInfo>().eq("LIMS_PERSON_UUID", casePersonInfo.getLimsPersonUuid()));
            if (casePersonInfoDb == null) {
                casePersonInfoMapper.insert(casePersonInfo);
            } else {
                //将casePersonInfo的属性赋值于casePersonInfoDb对应的属性
                BeanUtils.copyProperties(casePersonInfo, casePersonInfoDb, "id", "limsPersonUuid");
                casePersonInfoMapper.updateById(casePersonInfo);
            }
            //修改委托表 委托人1、2 关联id
            for (ConsignmentInfo consignmentInfo : consignmentInfoList) {
                if (consignmentInfo.getConsignPerson1Id() != null){
                    casePersonInfoDb = casePersonInfoMapper.selectOne(new QueryWrapper<CasePersonInfo>().eq("LIMS_PERSON_UUID", consignmentInfo.getConsignPerson1Id()));
                    consignmentInfo.setConsignPerson1Id(casePersonInfoDb.getId().toString());
                    consignmentInfoMapper.updateById(consignmentInfoInDb);
                }else if (consignmentInfo.getConsignPerson2Id() != null){
                    casePersonInfoDb = casePersonInfoMapper.selectOne(new QueryWrapper<CasePersonInfo>().eq("LIMS_PERSON_UUID", consignmentInfo.getConsignPerson2Id()));
                    consignmentInfo.setConsignPerson2Id(casePersonInfoDb.getId().toString());
                    consignmentInfoMapper.updateById(consignmentInfoInDb);
                }

            }

        }

    }

    /**
     * 保存案件信息
     * @param caseInfo
     * @return
     * @throws Exception
     */
    private Integer saveCaseInfo(CaseInfo caseInfo) throws Exception {
        /**
         * 根据lims中的案件id查询案件信息是否已经存在，如果存在，更新案件，如果不存在，插入案件
         */
        String limsCaseUuid = caseInfo.getLimsCaseUuid();
        CaseInfo caseInfoInDb = this.getBaseMapper().selectOne(new QueryWrapper<CaseInfo>().eq("lims_case_uuid", limsCaseUuid));
        if (caseInfoInDb == null) {
            this.getBaseMapper().insert(caseInfo);
            return caseInfo.getId();
        } else {
            //将caseInfo的属性赋值于caseInfoInDb对应的属性
            BeanUtils.copyProperties(caseInfo, caseInfoInDb, "id", "limsCaseUuid");

            this.getBaseMapper().updateById(caseInfoInDb);
            return caseInfoInDb.getId();
        }
    }

    /**
     * 保存委托信息
     * @param consignmentInfoList
     * @return
     * @throws Exception
     */
    private void saveConsignmentList(Integer caseId, List<ConsignmentInfo> consignmentInfoList) throws Exception {
        for(ConsignmentInfo consignmentInfo : consignmentInfoList){
            /**
             * 根据lims中的委托id查询当前委托是否已经存在，如果存在，则更新；不存在，则插入
             */
        		ConsignmentInfo consignmentInfoInDb = consignmentInfoMapper.selectOne(new QueryWrapper<ConsignmentInfo>().eq("LIMS_CONSIGNMENT_UUID", consignmentInfo.getLimsConsignmentUuid()));
            if (consignmentInfoInDb == null) {
                consignmentInfoMapper.insert(consignmentInfo);
            } else {
                //将consignmentInfo的属性赋值于consignmentInfoInDb对应的属性
                BeanUtils.copyProperties(consignmentInfo, consignmentInfoInDb, "id", "limsConsignmentUuid");

                consignmentInfoMapper.updateById(consignmentInfoInDb);
            }
        }
    }


    @Override
    public boolean insertWarehousingNum(CaseInfo caseInfo) {
        String limsCaseUuid = caseInfo.getLimsCaseUuid();
        CaseInfo caseInfoInDb = this.getBaseMapper().selectOne(new QueryWrapper<CaseInfo>().eq("lims_case_uuid", limsCaseUuid));
        if (caseInfoInDb != null) {
            BeanUtils.copyProperties(caseInfo, caseInfoInDb, "id", "limsCaseUuid");
            this.getBaseMapper().updateById(caseInfoInDb);
        }
        return false;
    }


    @Override
    public List<CaseInfoVo> casePaginationQuery(CaseInfoQuery caseInfoQuery) {
        try {
            return caseInfoMapper.paginationQuery(caseInfoQuery);
        }catch(Exception ex) {
            logger.error("invoke caseInfoService.casePaginationQuery error.", ex);
        }
        return Lists.newArrayList();
    }


    /**
     * 通用查询 - 案件信息总数
     * @param caseInfoQuery
     * @return
     */
    @Override
    public int casePaginationCount(CaseInfoQuery caseInfoQuery) {
        try {
            int count = caseInfoMapper.paginationCount(caseInfoQuery);
            return count;
        }catch(Exception ex) {
            logger.error("invoke caseInfoService.casePaginationCount error.", ex);
            return 0;
        }
    }

    @Override
    public List<CaseInfo> selectByCaseTransferFlag(int transferNationFlag) {
        try {
            return caseInfoMapper.selectByCaseTransferFlag(transferNationFlag);
            } catch (Exception ex) {
                log.error("invoke caseInfoService.selectByCaseTransferFlag error.", ex);
                return null;
            }
         }

    @Override
    public List<CaseInfo> selectCaseByLabServerNo(String labServerNo) {
        try{
           return  caseInfoMapper.selectCaseByLabServerNo(labServerNo);
        }catch (Exception ex){
            log.error("invoke caseInfoService.selectCaseByLabServerNo error.", ex);
            return null;
        }
    }

    @Override
    public int selectAllCaseCount() {
        try {
            int count = caseInfoMapper.selectAllCaseCount();
            return count;
        } catch (Exception ex) {
            log.error("invoke caseInfoService.selectAllCase error", ex);
            return  0;
        }
    }

    @Override
    public int selectAcceptNoCount(String id) {
        try {
            int count = caseInfoMapper.selectAcceptNoCount(id);
            return count;
        } catch (Exception ex) {
            log.error("invoke caseInfoService.selectAllCase error", ex);
            return  0;
        }
    }

    @Override
    public List<CaseReportQuery> selectCaseReportList(CaseReportQuery query) {
        try {
            return caseInfoMapper.selectCaseReportList(query);
        } catch (Exception ex) {
            log.error("invoke caseInfoService.selectCaseReportList error",ex);
            return null;
        }
    }

    @Override
    public int caseReportpaginationCount(CaseReportQuery query) {
        try{
            int count = caseInfoMapper.caseReportpaginationCount(query);//总计案件上报条数
            return count;
        }catch (Exception ex){
            log.error("invoke caseInfoService.caseReportPaginationCount error",ex);
            return 0;
        }
    }
}
