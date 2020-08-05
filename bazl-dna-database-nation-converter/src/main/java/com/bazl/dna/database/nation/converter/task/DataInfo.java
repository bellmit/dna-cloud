package com.bazl.dna.database.nation.converter.task;

import static com.bazl.dna.database.nation.converter.service.impl.CaseInfoServiceImpl.getIntByType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bazl.dna.database.nation.converter.client.IDataConverterClient;
import com.bazl.dna.database.nation.converter.constants.ConverterConstants;
import com.bazl.dna.database.nation.converter.dao.CaseInfoMapper;
import com.bazl.dna.database.nation.converter.dao.PersonInfoMapper;
import com.bazl.dna.database.nation.converter.dao.SampleDnaGeneMapper;
import com.bazl.dna.database.nation.converter.dao.SampleInfoMapper;
import com.bazl.dna.database.nation.converter.dao.SamplePersonMapMapper;
import com.bazl.dna.database.nation.converter.model.po.CaseInfo;
import com.bazl.dna.database.nation.converter.model.po.Consignment;
import com.bazl.dna.database.nation.converter.model.po.PersonInfo;
import com.bazl.dna.database.nation.converter.model.po.SampleDnaGene;
import com.bazl.dna.database.nation.converter.model.po.SampleInfo;
import com.bazl.dna.database.nation.converter.model.po.SamplePersonMap;
import com.bazl.dna.database.nation.converter.model.po.SysDict;
import com.bazl.dna.database.nation.converter.model.vo.SampleDnaGeneVo;
import com.bazl.dna.database.nation.converter.service.CaseInfoService;
import com.bazl.dna.database.nation.converter.service.PersonInfoService;
import com.bazl.dna.database.nation.converter.service.SampleDnaGeneService;
import com.bazl.dna.database.nation.converter.service.SampleInfoService;
import com.bazl.dna.database.nation.converter.service.SysDictService;
import com.bazl.dna.database.nation.converter.service.impl.CaseInfoServiceImpl;
import com.bazl.dna.database.nation.converter.utils.Constants;
import com.bazl.dna.database.nation.converter.utils.LikeKeyUtils;
import com.bazl.dna.database.nation.converter.utils.ListUtils;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.model.po.CriminalPersonInfo;
import com.bazl.dna.database.service.model.po.CriminalSampleInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;
import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import com.bazl.dna.database.service.model.vo.ConverterDataSampleAndPersonVo;
import com.bazl.dna.database.service.model.vo.ConverterDataVo;
import com.bazl.dna.util.DateUtil;

@Component
public class DataInfo {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ConverterConfigs converterConfigs;

    @Autowired
    CaseInfoService caseInfoService;


    @Autowired
    CaseInfoMapper caseInfoMapper;
    @Autowired
    SampleInfoMapper sampleInfoMapper;
    @Autowired
    SampleDnaGeneMapper sampleDnaGeneMapper;
    @Autowired
    SamplePersonMapMapper samplePersonMapMapper;
    @Autowired
    PersonInfoMapper personInfoMapper;
    @Autowired
    CaseInfoServiceImpl caseInfoServiceImpl;



    @Autowired
    SampleInfoService sampleInfoService;

    @Autowired
    PersonInfoService personInfoService;

    @Autowired
    SampleDnaGeneService sampleDnaGeneService;


    @Autowired
    SysDictService sysDictService;

    @Autowired
    IDataConverterClient iDataConverterClient;

   /* *//**
     * @param currentPageIdx 当前页码
     * @param pageSize  每页条数
     *//*
    public void taskDBData(int currentPageIdx, int pageSize, String initServerNoIn){
        caseInfoService.selectInfoListData(currentPageIdx, pageSize, initServerNoIn);
    }


    *//**
     * @param currentPageIdx 当前页码
     * @param pageSize  每页条数
     *//*
    public void taskCaseinfo(int currentPageIdx, int pageSize, String initServerNoLike){
        caseInfoService.selectCaseInfoListData(currentPageIdx,pageSize,initServerNoLike);
    }*/

    /**
     * 案件数据转换
     * @param labServerNoPrefix 实验室编号前缀
     */
    public void doConvertCaseInfo(String labServerNoPrefix, String labServerName){
        logger.info("*********开始转换案件数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");

        /**
         * Step 1. 分页查询案件委托
         * Step 2. 逐条根据委托查询出关联的案件、人员、亲缘关系、样本、基因等数据
         * Step 3. 调用新库的Service进行保存（放在一个事务中）
         */
        int pageIndex = 1;
        int pageSize = converterConfigs.getPageSize();
        List<Consignment> consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_CASE, labServerNoPrefix, pageIndex, pageSize);
        while(ListUtils.isNotNullAndEmptyList(consignmentList)){
            for(Consignment consignment : consignmentList){
                ConverterDataVo reqBean = new ConverterDataVo();//保存服务数据
                //Step-1:
                //根据委托对象的event_id查询案件信息
                CaseInfo caseInfo = caseInfoService.selectByCaseId(consignment.getEventId());

                //转换委托信息
                reqBean.setConsignmentInfo(getNewConsignmentInfo(consignment, caseInfo));
                //转换案件信息
                reqBean.setCaseInfo(getNewCaseInfo(caseInfo, consignment));

                //Step-2:
                //根据委托id查询关联的检材信息
                List<SampleInfo> sampleInfoList = sampleInfoService.selectAllSampleByConsignmentId(consignment.getId());//sampleInfoMapper.selectByPrimaryKeys(getSampleInfo);
                //根据委托id查询关联的人员信息
                List<PersonInfo> personInfoList = personInfoService.selectAllPersonByConsignmentId(consignment.getId());
                //根据委托id查询出所有的基因信息
                List<SampleDnaGeneVo> sampleDnaGeneVoList = sampleDnaGeneService.selectAllVoListByConsignmentId(consignment.getId());
                List<SampleDnaGeneVo> strSampleDnaGeneVoList = sampleDnaGeneVoList.stream().filter(sdg -> sdg.getEntity().getGeneType().equals(Constants.GENE_TYPE_STR)).collect(Collectors.toList());
                List<SampleDnaGeneVo> ystrSampleDnaGeneVoList = sampleDnaGeneVoList.stream().filter(sdg -> sdg.getEntity().getGeneType().equals(Constants.GENE_TYPE_YSTR)).collect(Collectors.toList());

                //样本相关数据
                List<ConverterDataSampleAndPersonVo> samplePersonList = new ArrayList<>();

                /**
                 * 先保存物证样本信息
                 */
                ConverterDataSampleAndPersonVo converterDataSampleAndPersonVo = null;
                //筛选出物证样本列表
                List<SampleInfo> physicEvidenceSampleList = sampleInfoList.stream().filter(evidence -> StringUtils.isNotBlank(evidence.getPhysicalEvidenceId())).collect(Collectors.toList());
                for(SampleInfo evidenceSample : physicEvidenceSampleList){
                    converterDataSampleAndPersonVo = new ConverterDataSampleAndPersonVo();

                    PersonInfo evidencePerson = personInfoList.stream().filter(per -> per.getId().equals(evidenceSample.getRefPersonId())).findFirst().get();
                    DnaSampleInfo dnaSampleInfo = getNewSampleInfo(evidenceSample, evidencePerson);
                    converterDataSampleAndPersonVo.setDnaSampleInfo(dnaSampleInfo);

                    List<DnaStrGeneInfo> dnaStrGeneInfoList = getDnaStrGeneInfo(strSampleDnaGeneVoList, evidenceSample, ConverterConstants.GENE_DATA_SOURCE_CASE);
                    converterDataSampleAndPersonVo.setDnaStrGeneInfoList(dnaStrGeneInfoList);

                    List<DnaYstrGeneInfo> dnaYstrGeneInfoList = getDnaYstrGeneInfo(ystrSampleDnaGeneVoList, evidenceSample);
                    converterDataSampleAndPersonVo.setDnaYstrGeneInfoList(dnaYstrGeneInfoList);

                    samplePersonList.add(converterDataSampleAndPersonVo);
                }

                /**
                 * 保存人员自身的样本和人员基本信息
                 */
                //筛选出人员自身样本列表
                List<SampleInfo> personSampleList = sampleInfoList.stream().filter(sample -> (StringUtils.isBlank(sample.getPhysicalEvidenceId()) && isSelfOrProssibleSelf(sample.getRelation()))).collect(Collectors.toList());
                for(SampleInfo personSample : personSampleList){
                    converterDataSampleAndPersonVo = new ConverterDataSampleAndPersonVo();

                    PersonInfo person = personInfoList.stream().filter(per -> per.getId().equals(personSample.getRefPersonId())).findFirst().get();
                    DnaSampleInfo dnaSampleInfo = getNewSampleInfo(personSample, person);
                    converterDataSampleAndPersonVo.setDnaSampleInfo(dnaSampleInfo);

                    CasePersonInfo casePersonInfo = getCasePersonInfo(person);
                    converterDataSampleAndPersonVo.setCasePersonInfo(casePersonInfo);

                    List<DnaStrGeneInfo> dnaStrGeneInfoList = getDnaStrGeneInfo(strSampleDnaGeneVoList, personSample, ConverterConstants.GENE_DATA_SOURCE_CASE);
                    converterDataSampleAndPersonVo.setDnaStrGeneInfoList(dnaStrGeneInfoList);

                    List<DnaYstrGeneInfo> dnaYstrGeneInfoList = getDnaYstrGeneInfo(ystrSampleDnaGeneVoList, personSample);
                    converterDataSampleAndPersonVo.setDnaYstrGeneInfoList(dnaYstrGeneInfoList);

                    samplePersonList.add(converterDataSampleAndPersonVo);
                }

                reqBean.setSamplePersonList(samplePersonList);

                /**
                 * 处理亲缘样本和人员信息
                 */
                List<PersonRelativeInfo> personRelativeInfoList = new ArrayList<>();
                List<SampleInfo> relationSampleList = sampleInfoList.stream().filter(sample -> (StringUtils.isBlank(sample.getPhysicalEvidenceId()) && !isSelfOrProssibleSelf(sample.getRelation()))).collect(Collectors.toList());
                SampleInfo selfSample = null;
                for(SampleInfo relationSample : relationSampleList){
                    selfSample = sampleInfoList.stream().filter(self -> (isSelfOrProssibleSelf(self.getRelation()) && self.getId().equals(relationSample.getId()))).findFirst().get();
                    PersonRelativeInfo personRelativeInfo = getPersonRelativeInfo(relationSample.getRefPersonId(), selfSample.getRefPersonId(), relationSample.getRelation());
                    personRelativeInfoList.add(personRelativeInfo);
                }
                reqBean.setPersonRelativeInfoList(personRelativeInfoList);

                try {
                    // 调用新库Service，Save当前案件委托的相关信息 （Service层加入事务处理代码）
                    iDataConverterClient.saveCase(reqBean);
                }catch(Exception ex){
                    logger.error("保存建库人员委托信息失败。", ex);
                }
/*
                for (SampleInfo sampleInfo : sampleInfoList) {
                    ConverterDataSampleAndPersonVo reqSampleBean = new ConverterDataSampleAndPersonVo();

                    //人员信息
                    if (StringUtils.isBlank(sampleInfo.getPhysicalEvidenceId())){
                        //TODO -3:
                        //人员信息
                        SamplePersonMap samplePersonMap = samplePersonMapMapper.selectByPrimaryKeySample(sampleInfo.getId());//人员关系表
                        if (null != samplePersonMap){
                            PersonInfo getPersonInfo = new PersonInfo();
                            getPersonInfo.setId(samplePersonMap.getPersonObjectId());
                            PersonInfo personinfo = personInfoMapper.selectByPrimaryKey(getPersonInfo);//人员信息
                            if (null != personinfo){
                                //组装人员数据
                                CasePersonInfo casePersonInfo = caseInfoServiceImpl.casePersonInfo(personinfo,null,null);
                                reqSampleBean.setCasePersonInfo(casePersonInfo);
                            }
                        }
                        List<RelativeAndPerson> relativeAndPerson = new ArrayList<>();
                        //TODO -4:
                        //亲属关系信息
                        List<SamplePersonMap> samplePersonInfoList = samplePersonMapMapper.selectByPrimarySample(sampleInfo.getId());//人员关系表
                        if (samplePersonInfoList.size() != 0){
                            for (SamplePersonMap samplePerson: samplePersonInfoList) {
                                RelativeAndPerson relativeAndPersonBean = new  RelativeAndPerson();
                                PersonInfo selectPersonInfo = new PersonInfo();
                                selectPersonInfo.setId(samplePerson.getPersonObjectId());
                                PersonInfo qyPersoninfo = personInfoMapper.selectByPrimaryKey(selectPersonInfo);//人员信息
                                if (null != qyPersoninfo){
                                    if(StringUtils.isNotBlank( qyPersoninfo.getDbCategory())){
                                        String intByType = getIntByType(qyPersoninfo.getDbCategory());//亲缘不会出现
                                        if (null != intByType ){
                                            if (intByType.equals("0")){
                                                qyPersoninfo.setDbCategory(null);
                                            }else if(intByType.equals("1")){
                                                qyPersoninfo.setDbCategory("1");//质控标识
                                            }else {
                                                qyPersoninfo.setDbCategory(intByType);
                                            }
                                        }
                                    }
                                    //亲缘的人员信息
                                    com.bazl.dna.database.service.model.po.CasePersonInfo casePersonInfo = caseInfoServiceImpl.casePersonInfo(qyPersoninfo,null,null);//组装人员数据
                                    relativeAndPersonBean.setCasePersonInfo(casePersonInfo);
                                    //亲缘的人员关系信息
                                    com.bazl.dna.database.service.model.po.PersonRelativeInfo personRelativeInfo = PersonRelativeInfo(samplePersonMap.getPersonObjectId(),qyPersoninfo.getId(),samplePerson);
                                    relativeAndPersonBean.setPersonRelativeInfo(personRelativeInfo);
                                }
                                relativeAndPerson.add(relativeAndPersonBean);
                            }
                        }
                        reqSampleBean.setRelativeAndPerson(relativeAndPerson);
                    }

                    com.bazl.dna.database.service.model.po.DnaSampleInfo dnaSampleInfo = caseInfoServiceImpl.sampleInfo(sampleInfo,null,null,null,null);//组装检材数据
                    reqSampleBean.setDnaSampleInfo(dnaSampleInfo);
                    //TODO -5:
                    //根据event_id查询关联的基因信息
                    SampleDnaGene getSampleDnaGene = new SampleDnaGene();
                    getSampleDnaGene.setId(sampleInfo.getId());
                    SampleDnaGene sampleDnaGene = sampleDnaGeneMapper.selectByPrimaryKeySampleId(getSampleDnaGene);
                    if (null != sampleDnaGene){
                        if (null != sampleDnaGene.getGeneType()){
                            List<DnaStrGeneInfo> strGeneInfo= new ArrayList<>();
                            List<DnaYstrGeneInfo> ystrGeneInfo= new ArrayList<>();
                            //str
                            if (sampleDnaGene.getGeneType().equals(Constants.GENE_TYPE_STR)){
                                //基因信息格式转换
                                sampleDnaGene.setGeneInfo(geneFormatList(sampleDnaGene.getGeneInfo(), StaticPages.strLocusNameList));
                                //组装基因数据
                                DnaStrGeneInfo dnaStrGeneInfo =  caseInfoServiceImpl.dnaStrGeneInfo(sampleDnaGene,null);
                                strGeneInfo.add(dnaStrGeneInfo);
                            }else if(sampleDnaGene.getGeneType().equals(Constants.GENE_TYPE_YSTR)){
                                //ystr
                                //基因信息格式转换
                                sampleDnaGene.setGeneInfo(geneFormatList(sampleDnaGene.getGeneInfo(), StaticPages.ystrLocusNameList));
                                //组装基因数据
                                DnaYstrGeneInfo dnaYstrGeneInfo =  caseInfoServiceImpl.dnaYstrGeneInfo(sampleDnaGene,null);
                                ystrGeneInfo.add(dnaYstrGeneInfo);
                            }
                            reqSampleBean.setDnaStrGeneInfoList(strGeneInfo);
                            reqSampleBean.setDnaYstrGeneInfoList(ystrGeneInfo);
                        }
                    }
                    samplePersonList.add(reqSampleBean);
                }
                reqBean.setSamplePersonList(samplePersonList);
                //TODO -6
                // 转换为新库的数据格式
                // 调用新库Service，Save当前案件委托的相关信息 （Service层加入事务处理代码）
                iDataConverterClient.saveCase(reqBean);*/
            }
            pageIndex++;
            consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_CASE, labServerNoPrefix, pageIndex, pageSize);
        }


        logger.info("*********完成转换案件数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");
    }

    /**
     * 质控人员数据转换
     * @param labServerNoPrefix 实验室编号前缀
     */
    public void doConvertQualityPersonInfo(String labServerNoPrefix, String labServerName){
        logger.info("*********开始转换质控人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");

        /**
         * Step 1. 分页查询灾难人员委托
         * Step 2. 逐条根据委托查询出关联的人员、亲缘关系、样本、基因等数据
         * Step 3. 调用新库的Service进行保存（放在一个事务中）
         */

        int pageIndex = 1;
        int pageSize = converterConfigs.getPageSize();
        List<String> strlist = sampleDnaGeneMapper.selectByLocusName();//查询基因座str
        List<String> ystrlist = sampleDnaGeneMapper.selectByLocusYstrName();//查询基因座ystr
        List<Consignment> consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_QUALITY_PERSON, labServerNoPrefix, pageIndex, pageSize);
        while (ListUtils.isNotNullAndEmptyList(consignmentList)){
            for(Consignment consignment : consignmentList){
                //TODO -1:
                //根据event_id查询关联的检材信息

                //TODO -2:
                //根据event_id查询关联的人员信息
                SampleInfo getSampleInfo = new SampleInfo();
                getSampleInfo.setConsignmentId(consignment.getId());
                List<SampleInfo> sampleInfos = sampleInfoMapper.selectByPrimaryKeys(getSampleInfo);
                for (SampleInfo sampleInfo:sampleInfos) {
                    if (StringUtils.isBlank(sampleInfo.getPhysicalEvidenceId())) {//人员信息
                        SamplePersonMap samplePersonMap = samplePersonMapMapper.selectByPrimaryKeySample(sampleInfo.getId());//人员关系表
                        if (null != samplePersonMap){
                            PersonInfo getPersonInfo = new PersonInfo();
                            getPersonInfo.setId(samplePersonMap.getPersonObjectId());
                            PersonInfo personinfo = personInfoMapper.selectByPrimaryKey(getPersonInfo);//人员信息
                            if (null != personinfo){
                                if(StringUtils.isNotBlank( personinfo.getDbCategory())){
                                    String intByType = getIntByType(personinfo.getDbCategory());
                                    if (null != intByType ){
                                        if (intByType.equals("0")){
                                            personinfo.setDbCategory(null);
                                        }else if(intByType.equals("1")){
                                            personinfo.setDbCategory("1");//质控标识
                                        }else {
                                            personinfo.setDbCategory(intByType);
                                        }
                                    }
                                }
                                if("1".equals(personinfo.getDbCategory())){
                                    //转换到质控样本表  //qc_sample_info
                                    SampleDnaGene getSampleDnaGene = new SampleDnaGene();
                                    getSampleDnaGene.setId(sampleInfo.getId());
                                    SampleDnaGene sampleDnaGene = sampleDnaGeneMapper.selectByPrimaryKeySampleId(getSampleDnaGene);
                                    iDataConverterClient.saveQcSample(caseInfoServiceImpl.QcSampleInfo(sampleInfo,sampleDnaGene,personinfo,strlist,ystrlist));
                                }

                            }
                        }

                    }
                }


                //TODO -5
                // 调用新库Service，Save当前案件委托的相关信息 （Service层加入事务处理代码）
            }
            pageIndex++;
            consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_CRIMINAL_PERSON, labServerNoPrefix, pageIndex, pageSize);

        }

        logger.info("*********完成转换质控人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");
    }

    /**
     * 身份不明人员数据转换
     * @param labServerNoPrefix 实验室编号前缀
     */
    @SuppressWarnings("unused")
	public void doConvertUnknownPersonInfo(String labServerNoPrefix, String labServerName){
        logger.info("*********开始转换身份不明人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");

        /**
         * Step 1. 分页查询身份不明人员委托
         * Step 2. 逐条根据委托查询出关联的人员、亲缘关系、样本、基因等数据
         * Step 3. 调用新库的Service进行保存（放在一个事务中）
         */

        int pageIndex = 1;
        int pageSize = converterConfigs.getPageSize();

        List<Consignment> consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_UNKONWN_PERSON, labServerNoPrefix, pageIndex, pageSize);
        if(ListUtils.isNotNullAndEmptyList(consignmentList)){
            for(Consignment consignment : consignmentList){
                //TODO -1:
                //根据event_id查询关联的检材信息

                //TODO -2:
                //根据event_id查询关联的人员信息

                //TODO -3:
                //根据event_id查询关联的亲属关系信息

                //TODO -4:
                //根据event_id查询关联的基因信息

                //TODO -5
                // 调用新库Service，Save当前案件委托的相关信息 （Service层加入事务处理代码）
            }
        }

        logger.info("*********完成转换身份不明人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");
    }

    /**
     * 失踪人员数据转换
     * @param labServerNoPrefix 实验室编号前缀
     */
    @SuppressWarnings("unused")
	public void doConvertMissingPersonInfo(String labServerNoPrefix, String labServerName){
        logger.info("*********开始转换失踪人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");

        /**
         * Step 1. 分页查询失踪人员委托
         * Step 2. 逐条根据委托查询出关联的人员、亲缘关系、样本、基因等数据
         * Step 3. 调用新库的Service进行保存（放在一个事务中）
         */

        int pageIndex = 1;
        int pageSize = converterConfigs.getPageSize();

        List<Consignment> consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_MISSING_PERSON, labServerNoPrefix, pageIndex, pageSize);
        if(ListUtils.isNotNullAndEmptyList(consignmentList)){
            for(Consignment consignment : consignmentList){
                //TODO -1:
                //根据event_id查询关联的检材信息

                //TODO -2:
                //根据event_id查询关联的人员信息

                //TODO -3:
                //根据event_id查询关联的亲属关系信息

                //TODO -4:
                //根据event_id查询关联的基因信息

                //TODO -5
                // 调用新库Service，Save当前案件委托的相关信息 （Service层加入事务处理代码）
            }
        }

        logger.info("*********完成转换失踪人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");
    }

    /**
     * 建库人员数据转换
     * @param labServerNoPrefix 实验室编号前缀
     */
    public void doConvertCriminalPersonInfo(String labServerNoPrefix, String labServerName){
        logger.info("*********开始转换建库人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");

        /**
         * Step 1. 分页查询建库人员委托
         * Step 2. 逐条根据委托查询出关联的人员、亲缘关系、样本、基因等数据
         * Step 3. 调用新库的Service进行保存（放在一个事务中）
         */

        int pageIndex = 1;
        int pageSize = converterConfigs.getPageSize();

        List<Consignment> consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_CRIMINAL_PERSON, labServerNoPrefix, pageIndex, pageSize);
        while(ListUtils.isNotNullAndEmptyList(consignmentList)){
            for(Consignment consignment : consignmentList){

                //Step-1:
                //根据委托id查询关联的人员信息
                List<PersonInfo> personInfoList = personInfoService.selectRealPersonByConsignmentId(consignment.getId());

                //Step-2:
                //根据委托id查询关联的自身检材信息
                List<SampleInfo> sampleInfoList = sampleInfoService.selectSelfSampleByConsignmentId(consignment.getId());


                //Step-3:
                //建库委托不存在亲缘信息， 跳过

                //Step-4:
                //根据委托id查询关联的基因信息
                List<SampleDnaGeneVo> sampleDnaGeneVoList = sampleDnaGeneService.selectSelfVoListByConsignmentId(consignment.getId());

                //Step-5
                //转换格式
                ConsignmentInfo newConsignment = getNewConsignmentInfo(consignment, null);
//                List<CriminalPersonInfo> newPersonInfoList = getCriminalPersonInfo(personInfoList);
//                List<CriminalSampleInfo> newSampleInfoList = getCriminalSampleInfo(sampleInfoList, personInfoList);

                List<SampleDnaGeneVo> strSampleDnaGeneVoList = sampleDnaGeneVoList.stream().filter(sdg -> sdg.getEntity().getGeneType().equals(Constants.GENE_TYPE_STR)).collect(Collectors.toList());
                List<SampleDnaGeneVo> ystrSampleDnaGeneVoList = sampleDnaGeneVoList.stream().filter(sdg -> sdg.getEntity().getGeneType().equals(Constants.GENE_TYPE_YSTR)).collect(Collectors.toList());

                ConverterDataVo converterDataVo = new ConverterDataVo();
                converterDataVo.setConsignmentInfo(newConsignment);
                converterDataVo.setCaseInfo(null);

                List<ConverterDataSampleAndPersonVo> converterDataSampleAndPersonVoList = new ArrayList<>();
                ConverterDataSampleAndPersonVo converterDataSampleAndPersonVo = null;

                for(PersonInfo personInfo : personInfoList){
                    converterDataSampleAndPersonVo = new ConverterDataSampleAndPersonVo();

                    CriminalPersonInfo criminalPersonInfo = getCriminalPersonInfo(personInfo);
                    converterDataSampleAndPersonVo.setCriminalPersonInfo(criminalPersonInfo);

                    SampleInfo sampleInfo = sampleInfoList.stream().filter(sample -> sample.getRefPersonId().equals(personInfo.getId())).findFirst().get();
                    CriminalSampleInfo criminalSampleInfo = getCriminalSampleInfo(sampleInfo, personInfo);
                    converterDataSampleAndPersonVo.setCriminalSampleInfo(criminalSampleInfo);

                    List<DnaStrGeneInfo> dnaStrGeneInfoList = getDnaStrGeneInfo(strSampleDnaGeneVoList, sampleInfo, ConverterConstants.GENE_DATA_SOURCE_CRIMINAL);
                    converterDataSampleAndPersonVo.setDnaStrGeneInfoList(dnaStrGeneInfoList);

                    List<DnaYstrGeneInfo> dnaYstrGeneInfoList = getDnaYstrGeneInfo(ystrSampleDnaGeneVoList, sampleInfo);
                    converterDataSampleAndPersonVo.setDnaYstrGeneInfoList(dnaYstrGeneInfoList);

                    converterDataSampleAndPersonVoList.add(converterDataSampleAndPersonVo);
                }
                converterDataVo.setSamplePersonList(converterDataSampleAndPersonVoList);

                try {
                    // 调用新库Service，Save当前案件委托的相关信息 （Service层加入事务处理代码）
                    iDataConverterClient.saveCriminal(converterDataVo);
                }catch(Exception ex){
                    logger.error("保存建库人员委托信息失败。", ex);
                }
            }

            pageIndex++;
            consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_CRIMINAL_PERSON, labServerNoPrefix, pageIndex, pageSize);
        }

        logger.info("*********完成转换建库人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");
    }

    /**
     * 灾难人员数据转换
     * @param labServerNoPrefix 实验室编号前缀
     */
    @SuppressWarnings("unused")
	public void doConvertDisasterPersonInfo(String labServerNoPrefix, String labServerName){
        logger.info("*********开始转换灾难人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");

        /**
         * Step 1. 分页查询灾难人员委托
         * Step 2. 逐条根据委托查询出关联的人员、亲缘关系、样本、基因等数据
         * Step 3. 调用新库的Service进行保存（放在一个事务中）
         */

        int pageIndex = 1;
        int pageSize = converterConfigs.getPageSize();

        List<Consignment> consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_DIASTER_PERSON, labServerNoPrefix, pageIndex, pageSize);
        if(ListUtils.isNotNullAndEmptyList(consignmentList)){
            for(Consignment consignment : consignmentList){
                //TODO -1:
                //根据event_id查询关联的检材信息

                //TODO -2:
                //根据event_id查询关联的人员信息

                //TODO -3:
                //根据event_id查询关联的亲属关系信息

                //TODO -4:
                //根据event_id查询关联的基因信息

                //TODO -5
                // 调用新库Service，Save当前案件委托的相关信息 （Service层加入事务处理代码）
            }
        }

        logger.info("*********完成转换灾难人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");
    }

    /**
     * 基础人员数据转换
     * @param labServerNoPrefix 实验室编号前缀
     */
    @SuppressWarnings("unused")
	public void doConvertBasicPersonInfo(String labServerNoPrefix, String labServerName){
        logger.info("*********开始转换基础人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");

        /**
         * Step 1. 分页查询基础人员委托
         * Step 2. 逐条根据委托查询出关联的人员、亲缘关系、样本、基因等数据
         * Step 3. 调用新库的Service进行保存（放在一个事务中）
         */

        int pageIndex = 1;
        int pageSize = converterConfigs.getPageSize();

        List<Consignment> consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_BASIC_PERSON, labServerNoPrefix, pageIndex, pageSize);
        if(ListUtils.isNotNullAndEmptyList(consignmentList)){
            for(Consignment consignment : consignmentList){
                //TODO -1:
                //根据event_id查询关联的检材信息

                //TODO -2:
                //根据event_id查询关联的人员信息

                //TODO -3:
                //根据event_id查询关联的亲属关系信息

                //TODO -4:
                //根据event_id查询关联的基因信息

                //TODO -5
                // 调用新库Service，Save当前案件委托的相关信息 （Service层加入事务处理代码）
            }
        }

        logger.info("*********完成转换基础人员数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");
    }

    /**
     * 打拐儿童数据转换
     * @param labServerNoPrefix 实验室编号前缀
     */
    @SuppressWarnings("unused")
	public void doConvertAbductionChildInfo(String labServerNoPrefix, String labServerName){
        logger.info("*********开始转换打拐儿童数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");

        /**
         * Step 1. 分页查询打拐儿童委托
         * Step 2. 逐条根据委托查询出关联的人员、亲缘关系、样本、基因等数据
         * Step 3. 调用新库的Service进行保存（放在一个事务中）
         */

        int pageIndex = 1;
        int pageSize = converterConfigs.getPageSize();

        List<Consignment> consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_ABDUCTION_CHILD, labServerNoPrefix, pageIndex, pageSize);
        if(ListUtils.isNotNullAndEmptyList(consignmentList)){
            for(Consignment consignment : consignmentList){
                //TODO -1:
                //根据event_id查询关联的检材信息

                //TODO -2:
                //根据event_id查询关联的人员信息

                //TODO -3:
                //根据event_id查询关联的亲属关系信息

                //TODO -4:
                //根据event_id查询关联的基因信息

                //TODO -5
                // 调用新库Service，Save当前案件委托的相关信息 （Service层加入事务处理代码）
            }
        }

        logger.info("*********完成转换打拐儿童委托数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");
    }

    /**
     * 打拐父母数据转换
     * @param labServerNoPrefix 实验室编号前缀
     */
    @SuppressWarnings("unused")
	public void doConvertAbductionParentInfo(String labServerNoPrefix, String labServerName){
        logger.info("*********开始转换打拐父母数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");

        /**
         * Step 1. 分页查询打拐父母委托
         * Step 2. 逐条根据委托查询出关联的人员、亲缘关系、样本、基因等数据
         * Step 3. 调用新库的Service进行保存（放在一个事务中）
         */
        int pageIndex = 1;
        int pageSize = converterConfigs.getPageSize();

        List<Consignment> consignmentList = caseInfoService.selectListByCategory(ConverterConstants.CONSIGNMENT_TYPE_ABDUCTION_PARENT, labServerNoPrefix, pageIndex, pageSize);
        if(ListUtils.isNotNullAndEmptyList(consignmentList)){
            for(Consignment consignment : consignmentList){
                //TODO -1:
                //根据event_id查询关联的检材信息

                //TODO -2:
                //根据event_id查询关联的人员信息

                //TODO -3:
                //根据event_id查询关联的亲属关系信息

                //TODO -4:
                //根据event_id查询关联的基因信息

                //TODO -5
                // 调用新库Service，Save当前案件委托的相关信息 （Service层加入事务处理代码）
            }
        }

        logger.info("*********完成转换打拐父母委托数据： [ " + labServerNoPrefix + " - " + labServerName + " ].....................");
    }


    /**
     * 转换委托信息
     * @param consignment
     * @param caseInfo
     * @return
     */
    private ConsignmentInfo getNewConsignmentInfo(Consignment consignment, CaseInfo caseInfo) {
        ConsignmentInfo bean = new ConsignmentInfo();
        bean.setId(null);
        bean.setLimsConsignmentUuid(consignment.getId());

        if (null != caseInfo) {
            bean.setSysXkWtno(caseInfo.getSysXkNo());
        }

        bean.setLabServerNo(consignment.getInitServerNo());
        bean.setConsignmentFileNo(consignment.getConsignmentNo());
        bean.setConsignOrgId(consignment.getConsignOrgRegionalism());
        bean.setConsignOrgCode(consignment.getConsignOrgRegionalism());
        bean.setConsignOrgName(consignment.getConsignOrgName());
        bean.setConsignOrgPhone(consignment.getConsignOrgPhone());
        bean.setConsignOrgFax(consignment.getConsignOrgFaxNo());
        bean.setConsignOrgAddress(consignment.getConsignerAddress());
        bean.setConsignOrgZipCode(consignment.getConsignOrgZipCode());
        bean.setConsignPerson1Id(null);
        bean.setConsignPerson1Name(consignment.getConsignerName());
        bean.setConsignPerson1Phone(consignment.getConsignerPhone());
        bean.setConsignPerson1CertificateType(consignment.getConsignerCertificateType());
        bean.setConsignPerson1CertificateNo(consignment.getConsignerCertificateNo());
        bean.setConsignPerson1PositionName(consignment.getConsignerDuty());
        bean.setConsignPerson2Id(null);
        bean.setConsignPerson2Name(consignment.getConsignerName2());
        bean.setConsignPerson2Phone(consignment.getConsignerPhone2());
        bean.setConsignPerson2CertificateType(consignment.getConsignerCertificateType2());
        bean.setConsignPerson2CertificateNo(consignment.getConsignerCertificateNo2());
        bean.setConsignPerson2PositionName(consignment.getConsignerDuty2());
        if (null != consignment.getConsignDatetime()){
            bean.setConsignmentRegDatetime(DateUtil.dateToLocalDateTime(consignment.getConsignDatetime()));
        }
        bean.setIdentifyRequirement(consignment.getIdentifyRequest());
        bean.setAcceptNo(consignment.getAcceptNo());
        bean.setAcceptPersonId(null);
        bean.setAcceptPersonName(consignment.getAcceptorName());
        bean.setAcceptDatetime(DateUtil.dateToLocalDateTime(consignment.getAcceptDatetime()));
        bean.setAppendFlag(consignment.getIsAppend().intValue());
        bean.setAppendSno(null);
        bean.setTransferFlag(consignment.getTransferFlag().intValue());
        bean.setTransferDatetime(DateUtil.dateToLocalDateTime(consignment.getTransferDatetime()));
        bean.setTransferPersonId(null);
        bean.setTransferPersonName(consignment.getTransferUser());
        bean.setDeleteFlag(consignment.getDeleteFlag().intValue());
        bean.setDeleteDatetime(null);
        bean.setDeletePersonId(null);
        bean.setDeletePersonName(null);
        bean.setStoreDatetime(null);
        bean.setStorePersonId(null);
        bean.setStorePersonName(consignment.getCreateUser());
        bean.setUpdateDatetime(null);
        bean.setUpdatePersonId(null);
        bean.setUpdatePersonName(null);
        bean.setMoreRemarks(consignment.getRemark());
        return bean;
    }

    /**
     * 转换案件信息
     * @param oldCaseInfo
     * @param oldConsignment
     * @return
     */
    public com.bazl.dna.database.service.model.po.CaseInfo getNewCaseInfo(CaseInfo oldCaseInfo, Consignment oldConsignment){
        com.bazl.dna.database.service.model.po.CaseInfo bean = new com.bazl.dna.database.service.model.po.CaseInfo();
        bean.setLimsCaseUuid(oldCaseInfo.getId());
        bean.setLabServerNo(oldCaseInfo.getInitServerNo());
        bean.setSysXkNo(oldCaseInfo.getSysXkNo());
        bean.setSysCaseAno(oldCaseInfo.getCaseNo());
        if (org.apache.commons.lang3.StringUtils.trimToNull(oldConsignment.getAcceptNo()) != null){
            bean.setCaseAcceptNo(oldConsignment.getAcceptNo());
        }

        bean.setCaseName(oldCaseInfo.getCaseName());
        if (null != oldCaseInfo.getCaseType()){
            if (oldCaseInfo.getCaseType().equals("1")){
                bean.setCaseType(Constants.CASE_TYPE_CRIMINAL);
            }else if (oldCaseInfo.getCaseType().equals("2")){
                bean.setCaseType(Constants.CASE_TYPE_CIVIL);
            }else {
                bean.setCaseType("0");
            }
        }

        if (null != oldCaseInfo.getCaseProperty()){
            SysDict sysDict = getSysDict("CASE_PROPERTY", oldCaseInfo.getCaseProperty());
            if (null != sysDict) {
                bean.setCaseProperty(convertCaseProperty(sysDict.getDictValue1()));
            }
        }

        bean.setCaseSubProperty(null);
        bean.setCaseLevel(oldCaseInfo.getCaseLevel());
        bean.setHarmLevel(oldCaseInfo.getHarmLevel());
        bean.setSceneRegionalism(oldCaseInfo.getSceneRegionalism());
        bean.setScenePlace(oldCaseInfo.getScenePlace());
        if (null != oldCaseInfo.getOccurrenceDatetime()){
            bean.setOccurrenceDatetime(DateUtil.dateToLocalDateTime(oldCaseInfo.getOccurrenceDatetime()));
        }
        bean.setCaseSummary(oldCaseInfo.getCaseBrief());
        bean.setMoreRemarks(oldCaseInfo.getRemark());
        bean.setTransferNationFlag(oldCaseInfo.getTransferFlag().intValue());
        if (null != oldCaseInfo.getCreateDatetime()){
            bean.setFirstTransferNationTime(DateUtil.dateToLocalDateTime(oldCaseInfo.getCreateDatetime()));
        }
        bean.setLastTransferNationTime(null);
        bean.setTransferPersonId(null);
        bean.setTransferPersonName(null);
        bean.setNationSysNo(oldCaseInfo.getCaseNo());
        bean.setDeleteFlag(oldCaseInfo.getDeleteFlag().intValue());
        bean.setDeleteDatetime(null);
        bean.setDeletePerson(null);
        bean.setDeleteReason(null);
        bean.setStoreDatetime(null);
        bean.setStorePersonId(null);
        bean.setStorePersonName(oldCaseInfo.getCreateUser());
        bean.setUpdateDatetime(null);
        bean.setUpdatePersonId(null);
        bean.setUpdatePersonName(null);
        if(null != oldCaseInfo.getCaseStatus()){
            if (oldCaseInfo.getCaseStatus().equals("1")){
                bean.setCaseStatus(0);
            }else if (oldCaseInfo.getCaseStatus().equals("3")){
                bean.setCaseStatus(1);
            }

        }
        return bean;
    }


    public DnaSampleInfo getNewSampleInfo(SampleInfo sampleInfo, PersonInfo personinfo) {
        DnaSampleInfo bean = new DnaSampleInfo();
        bean.setId(null);
        bean.setLimsSampleUuid(sampleInfo.getId());
        bean.setLabServerNo(sampleInfo.getInitServerNo());

        if(null != personinfo) {
            bean.setInstoreDataType(personinfo.getDbCategory()); //PERSON_INFO  DB_CATEGORY 关联字典
        }

        if (null != sampleInfo.getPhysicalEvidenceId()){
            bean.setEvidenceFlag(1); //'是否为物证样本， 1-True，2-False'
        }else{
            bean.setEvidenceFlag(2); //'是否为物证样本， 1-True，2-False'
        }

        bean.setSampleType(sampleInfo.getSampleType());
        bean.setSampleLabNo(sampleInfo.getSampleLabNo());
        bean.setSampleEvidenceNo(sampleInfo.getSampleNo());
        bean.setSampleName(sampleInfo.getSampleName());
        bean.setSampleDesc(sampleInfo.getSampleDesc());
        bean.setSamplePackage(sampleInfo.getSamplePackaging());
        bean.setSampleCarrier(null);
        bean.setTransferFlag(sampleInfo.getTransferFlag().intValue());
        bean.setTransferDatetime(DateUtil.dateToLocalDateTime(sampleInfo.getTransferDatetime()));
        bean.setTransferPersonId(null);
        bean.setTransferPersonName(sampleInfo.getTransferUser());
        bean.setDeleteFlag(sampleInfo.getDeleteFlag().intValue());
        bean.setDeleteReason(null);
        bean.setDeleteDatetime(null);
        bean.setDeletePersonName(null);
        bean.setStoreDatetime(null);
        bean.setStorePersonId(null);
        bean.setStorePersonName(sampleInfo.getCreateUser());
        bean.setUpdateDatetime(null);
        bean.setUpdatePersonId(null);
        bean.setUpdatePersonName(null);
        bean.setDnaSampleImageList(null);
        return bean;
    }


    public CasePersonInfo getCasePersonInfo(PersonInfo info) {
        CasePersonInfo bean = new CasePersonInfo();
        bean.setId(null);
        bean.setLimsPersonUuid(info.getId());
        bean.setLabServerNo(info.getInitServerNo());

        bean.setPersonType(info.getPersonType());
        bean.setPersonName(info.getPersonName());
        bean.setPersonAlias(info.getAlias());
        bean.setPersonGender(info.getGender());
        bean.setPersonIdcardNo(info.getIdCardNo());
        bean.setPersonAge(info.getAge()+"");
        bean.setNoneIdcardDesc(null);
        bean.setOtherCentificateType(info.getCertificateType());
        bean.setOtherCentificateNo(info.getCertificateNo());
        bean.setNationality(info.getNationality());
        bean.setPersonRace(info.getRace());
        bean.setPersonAddr(info.getResidenceAddr());
        bean.setPersonHeight(info.getHeight());
        bean.setPersonWeight(info.getBodilyForm());
        bean.setPhoneNumber(info.getMobilePhone());
        bean.setTransferFlag(info.getTransferFlag());
        bean.setTransferDatetime(DateUtil.dateToLocalDateTime(info.getTransferDatetime()));
        bean.setTransferPersonId(null);
        bean.setTransferPeresonName(info.getTransferUser());
        bean.setDeleteFlag(info.getDeleteFlag());
        bean.setDeleteDatetime(null);
        bean.setDeleteReason(null);
        bean.setDeletePersonId(null);
        bean.setDeletePersonName(null);
        bean.setStoreDatetime(null);
        bean.setStorePersonId(null);
        bean.setStorePersonName(info.getCreateUser());
        bean.setUpdateDatetime(null);
        bean.setUpdatePersonId(null);
        bean.setUpdatePersonName(null);
        return bean;
    }

    /**
     * 转换建库人员信息
     * @param info
     * @return
     */
    private CriminalPersonInfo getCriminalPersonInfo(PersonInfo info){
        CriminalPersonInfo bean = new CriminalPersonInfo();
        bean.setId(null);
        bean.setLimsPersonUuid(info.getId());
        bean.setLabServerNo(info.getInitServerNo());
        bean.setCriminalPersonType(info.getDbCategory());
        bean.setPersonName(info.getPersonName());
        bean.setPersonGender(info.getGender());
        bean.setPersonIdcardNo(info.getIdCardNo());
        bean.setPersonRace(info.getRace());
        bean.setPersonNation(info.getNationality());
        bean.setNativePlaceCode(info.getResidenceAddr());
        bean.setNativePlaceName(info.getResidenceAddr());
        bean.setPresentAddress(info.getResidenceAddr());
        bean.setPersonHeight(info.getHeight());
        bean.setPersonWeight(info.getBodilyForm());
        bean.setPersonAppearance(info.getExtrinsicSign());
        bean.setRemarks(info.getRemark());

        return bean;
    }

    /**
     * 转换建库样本信息
     * @param sampleInfo
     * @param personInfo
     * @return
     */
    private CriminalSampleInfo getCriminalSampleInfo(SampleInfo sampleInfo, PersonInfo personInfo){

            CriminalSampleInfo bean = new CriminalSampleInfo();
            bean.setId(null);
            bean.setLimsSampleUuid(sampleInfo.getId());
            bean.setLabServerNo(sampleInfo.getInitServerNo());

            //转换人员类别
            if (StringUtils.isNotBlank(sampleInfo.getRefPersonId())) {
                String dbCategory = converterConfigs.getInstoreDataTypeMap().get(personInfo.getDbCategory());
                bean.setInstoreDataType(dbCategory);
            }

//        bean.setRefPersonId(PersonId);//人员id
            bean.setSampleType(sampleInfo.getSampleType());
            bean.setSampleLabNo(sampleInfo.getSampleLabNo());
            bean.setSampleName(sampleInfo.getSampleName());
            bean.setSampleDesc(sampleInfo.getSampleDesc());
            bean.setSamplePackage(sampleInfo.getSamplePackaging());
            bean.setSampleCarrier(null);
            bean.setCollectOrgCode(sampleInfo.getCollectAgencyCode());
            bean.setCollectOrgName(sampleInfo.getCollectAgencyName());
            bean.setCollectDatetime(DateUtil.dateToLocalDateTime(sampleInfo.getCollectDatetime()));
            bean.setCollectPerson(sampleInfo.getCollectUser());
            bean.setCollectPhone(null);
            bean.setAcceptOrgCode(sampleInfo.getConsignOrgCode());
            bean.setAcceptOrgName(null);
            bean.setAcceptPersonId(null);
            bean.setAcceptPersonName(sampleInfo.getCreateUser());
            bean.setTransferFlag(sampleInfo.getTransferFlag().intValue());
            bean.setNationSysNo(null);
            bean.setTransferDatetime(DateUtil.dateToLocalDateTime(sampleInfo.getTestDatetime()));
            bean.setTransferPersonId(null);
            bean.setTransferPersonName(null);
            bean.setDeleteFlag(sampleInfo.getDeleteFlag().intValue());
            bean.setDeleteReason(null);
            bean.setDeleteDatetime(null);
            bean.setDeletePersonName(null);
            bean.setStoreDatetime(DateUtil.dateToLocalDateTime(sampleInfo.getCreateDatetime()));
            bean.setStorePersonId(null);
            bean.setStorePersonName(sampleInfo.getCreateUser());
            bean.setUpdateDatetime(null);
            bean.setUpdatePersonId(null);
            bean.setUpdatePersonName(null);

            return bean;
    }

    /**
     * 转换Str基因信息
     * @param strSampleDnaGeneVoList
     * @param sampleInfo
     * @return
     */
    private List<DnaStrGeneInfo> getDnaStrGeneInfo(List<SampleDnaGeneVo> strSampleDnaGeneVoList, SampleInfo sampleInfo, int dataSource) {
        List<DnaStrGeneInfo> dnaStrGeneInfoList = new ArrayList<>();

        for(SampleDnaGeneVo sampleDnaGene : strSampleDnaGeneVoList) {
            if(sampleDnaGene.getEntity().getSampleId().equals(sampleInfo.getId())){
                DnaStrGeneInfo bean = new DnaStrGeneInfo();
                bean.setId(null);
                bean.setLabServerNo(sampleDnaGene.getEntity().getInitServerNo());
                bean.setLimsStrUuid(sampleDnaGene.getEntity().getId());
//            bean.setGeneType(Integer.parseInt(sampleDnaGene.getGeneType()));
                bean.setDnaPanelId(null);//试剂盒
                bean.setKitname(sampleDnaGene.getEntity().getKitname());
                bean.setDataSource(dataSource);
                bean.setSampleId(null);

                //找到当前基因信息所属样本id
//                if(sampleInfo != null){
//                    bean.setLimsSampleUuid(sampleInfo.getId());
//                }

                bean.setLocusCount(sampleDnaGene.getEntity().getAlleleCount());
                //需要转换处理
                bean.setGeneInfo(geneFormatList(sampleDnaGene.getEntity().getGeneInfo(), StaticPages.strLocusNameList));
                bean.setGeneImage(null);
                bean.setTransferFlag(sampleDnaGene.getEntity().getTransferFlag().intValue());
                bean.setTransferDatetime(DateUtil.dateToLocalDateTime(sampleDnaGene.getEntity().getTransferDatetime()));
                bean.setTransferPersonId(null);
                bean.setTransferPersonName(sampleDnaGene.getEntity().getTransferUser());
                bean.setDeleteFlag(sampleDnaGene.getEntity().getDeleteFlag().intValue());
                bean.setDeleteDatetime(null);
                bean.setDeleteReason(null);
                bean.setDeletePersonId(null);
                bean.setDeletePersonName(null);
                bean.setStoreDatetime(null);
                bean.setStorePersonId(null);
                bean.setStorePersonName(sampleDnaGene.getEntity().getCreateUser());
                bean.setUpdateDatetime(null);
                bean.setUpdatePersonId(null);
                bean.setUpdatePersonName(null);
                dnaStrGeneInfoList.add(bean);
            }
        }

        return dnaStrGeneInfoList;
    }


    /**
     * 转换Ystr基因信息
     * @param ystrSampleDnaGeneList
     * @param sampleInfo
     * @return
     */
    private List<DnaYstrGeneInfo> getDnaYstrGeneInfo(List<SampleDnaGeneVo> ystrSampleDnaGeneList, SampleInfo sampleInfo) {
        List<DnaYstrGeneInfo> dnaYstrGeneInfoList = new ArrayList<>();

        for(SampleDnaGeneVo sampleDnaGene : ystrSampleDnaGeneList) {
            if(sampleDnaGene.getEntity().getSampleId().equals(sampleInfo.getId())) {
                DnaYstrGeneInfo bean = new DnaYstrGeneInfo();
                bean.setId(null);
                bean.setLabServerNo(sampleDnaGene.getEntity().getInitServerNo());
                bean.setLimsYstrUuid(sampleDnaGene.getEntity().getId());
//            bean.setGeneType(Integer.parseInt(sampleDnaGene.getGeneType()));
                bean.setDnaPanelId(null);//试剂盒
                bean.setKitname(sampleDnaGene.getEntity().getKitname());
                bean.setDataSource(1);
                bean.setSampleId(null);

                //找到当前基因信息所属样本id
//                if (sampleInfo != null) {
//                    bean.setLimsSampleUuid(sampleInfo.getId());
//                }

                bean.setLocusCount(sampleDnaGene.getEntity().getAlleleCount());
                //需要转换处理
                bean.setGeneInfo(geneFormatList(sampleDnaGene.getEntity().getGeneInfo(), StaticPages.ystrLocusNameList));
                bean.setGeneImage(null);
                bean.setTransferFlag(sampleDnaGene.getEntity().getTransferFlag().intValue());
                bean.setTransferDatetime(DateUtil.dateToLocalDateTime(sampleDnaGene.getEntity().getTransferDatetime()));
                bean.setTransferPersonId(null);
                bean.setTransferPersonName(sampleDnaGene.getEntity().getTransferUser());
                bean.setDeleteFlag(sampleDnaGene.getEntity().getDeleteFlag().intValue());
                bean.setDeleteDatetime(null);
                bean.setDeleteReason(null);
                bean.setDeletePersonId(null);
                bean.setDeletePersonName(null);
                bean.setStoreDatetime(null);
                bean.setStorePersonId(null);
                bean.setStorePersonName(sampleDnaGene.getEntity().getCreateUser());
                bean.setUpdateDatetime(null);
                bean.setUpdatePersonId(null);
                bean.setUpdatePersonName(null);
                dnaYstrGeneInfoList.add(bean);
            }
        }

        return dnaYstrGeneInfoList;
    }

    /**
     * 国家库基因信息格式转换
     *   目标类型 ;
     *    ["D8S1179": "11/12"......]
     */
    public String geneFormatList(String geneInfo, List<String> stringList) {
        LinkedHashMap<Object, Object> sampleGeneInfo = new LinkedHashMap<>();
        String geneInfos = null;
        try {
            String[] split = geneInfo.split(";",-1);
            if (split.length>0){
                for (int i = 0;i<split.length &i<stringList.size();i++){
                    String s = split[i];
                    sampleGeneInfo.put(org.apache.commons.lang3.StringUtils.replace(stringList.get(i), " ", ""),s);
                }
            }
            geneInfos = JSON.toJSONString(sampleGeneInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=======国家库基因信息转换失败========");
        }
        return geneInfos;
    }

    public PersonRelativeInfo getPersonRelativeInfo(String targetPersonUuid, String currentPersonUuid, String relation){
        PersonRelativeInfo bean = new PersonRelativeInfo();
        bean.setId(null);
        bean.setTargetPersonUuid(targetPersonUuid);
        bean.setRelationPersonUuid(currentPersonUuid);
        bean.setRelationTypeCode(relation);
        return bean;
    }

    private SysDict getSysDict(String dictCategory, String dictKey){
        SysDict sysDict = null;

        List<SysDict> allDict = StaticPages.allDictList;
        if(ListUtils.isNullOrEmptyList(allDict)){
            allDict = sysDictService.selectAll();
        }

        List<SysDict> dictListByCategory = allDict.stream().filter(dict -> dict.getDictCategory().equalsIgnoreCase(dictCategory)).collect(Collectors.toList());
        if(ListUtils.isNotNullAndEmptyList(dictListByCategory)){
            sysDict = dictListByCategory.stream().filter(dict1 -> dict1.getDictKey().equalsIgnoreCase(dictKey)).findFirst().get();
        }

        return sysDict;
    }

    public static String convertCaseProperty(String name){
        Map<String,Object> params = new HashMap<>(16);
        params.put("凶杀","01");
        params.put("伤害","02");
        params.put("其他盗窃","03");
        params.put("抢劫","04");
        params.put("强奸","05");
        params.put("非正常死亡","06");
        params.put("伤害致死","07");
        params.put("治安","08");
        params.put("走失人口","09");
        params.put("打拐","10");
        params.put("盗窃","100");
        params.put("交通事故","11");
        params.put("尸源认定","12");
        params.put("入室盗窃","13");
        params.put("入室盗窃（划片开锁）","131");
        params.put("盗窃机动车","14");
        params.put("盗窃车内财物","15");
        params.put("盗抢工地","16");
        params.put("入室盗窃（爬楼钻窗）","17");
        params.put("盗窃保险柜","18");
        params.put("亲缘鉴定","19");
        params.put("高空坠物","192");
        params.put("其他","20");
        params.put("诈骗","21");
        params.put("抢夺","22");
        params.put("扒窃","31");
        params.put("盗窃电缆","312");
        Map<String, Object> a = LikeKeyUtils.parseMapForFilter(params, name);
        String firstOrNull = (String) LikeKeyUtils.getFirstOrNull(a);
        if ((name.equals("入室盗窃（划片开锁）"))  ) {
            firstOrNull = "131";
        }else if(name.equals("入室盗窃（爬楼钻窗）") ){
            firstOrNull = "17";
        }else if (name.equals("入室盗窃") ){
            firstOrNull = "13";
        }
        return firstOrNull;
    }


    public static boolean isSelfOrProssibleSelf(String relation){
        if(StringUtils.isBlank(relation)){
            return false;
        }

        if(ConverterConstants.SAMPLE_RELATION_SELF.equalsIgnoreCase(relation)
                || ConverterConstants.SAMPLE_RELATION_SELF_PROBABLY.equalsIgnoreCase(relation)){
            return true;
        }

        return false;
    }
}
