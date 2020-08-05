package com.bazl.dna.database.nation.converter.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.ICaseConverterClient;
import com.bazl.dna.database.nation.converter.client.ICriminalConverterClient;
import com.bazl.dna.database.nation.converter.client.IOrgInfoConverterClient;
import com.bazl.dna.database.nation.converter.client.IReagentConverterClient;
import com.bazl.dna.database.nation.converter.client.ISampleConverterClient;
import com.bazl.dna.database.nation.converter.client.ISampleGeneConverterClient;
import com.bazl.dna.database.nation.converter.dao.CaseInfoMapper;
import com.bazl.dna.database.nation.converter.dao.ConsignmentMapper;
import com.bazl.dna.database.nation.converter.dao.LabInfoMapper;
import com.bazl.dna.database.nation.converter.dao.LocusInfoMapper;
import com.bazl.dna.database.nation.converter.dao.PersonInfoMapper;
import com.bazl.dna.database.nation.converter.dao.ReagentKitMapper;
import com.bazl.dna.database.nation.converter.dao.ReagentLocusMapper;
import com.bazl.dna.database.nation.converter.dao.SampleDnaGeneMapper;
import com.bazl.dna.database.nation.converter.dao.SampleInfoMapper;
import com.bazl.dna.database.nation.converter.dao.SamplePersonMapMapper;
import com.bazl.dna.database.nation.converter.dao.SysDictMapper;
import com.bazl.dna.database.nation.converter.model.po.CaseInfo;
import com.bazl.dna.database.nation.converter.model.po.Consignment;
import com.bazl.dna.database.nation.converter.model.po.LabInfo;
import com.bazl.dna.database.nation.converter.model.po.LocusInfo;
import com.bazl.dna.database.nation.converter.model.po.PersonInfo;
import com.bazl.dna.database.nation.converter.model.po.ReagentKit;
import com.bazl.dna.database.nation.converter.model.po.ReagentLocus;
import com.bazl.dna.database.nation.converter.model.po.SampleDnaGene;
import com.bazl.dna.database.nation.converter.model.po.SampleInfo;
import com.bazl.dna.database.nation.converter.model.vo.ConsignmentVo;
import com.bazl.dna.database.nation.converter.service.CaseInfoService;
import com.bazl.dna.database.nation.converter.utils.LikeKeyUtils;
import com.bazl.dna.database.nation.converter.utils.ListUtils;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.po.DnaPanelInfo;
import com.bazl.dna.database.service.model.po.DnaPanelLocus;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;
import com.bazl.dna.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Service
public class CaseInfoServiceImpl extends BaseService implements CaseInfoService {

    @Autowired
    CaseInfoMapper caseInfoMapper;
    @Autowired
    ConsignmentMapper consignmentMapper;
    @Autowired
    SampleInfoMapper sampleInfo;
    @Autowired
    SampleDnaGeneMapper sampleDnaGeneMapper;
    @Autowired
    PersonInfoMapper personInfoMapper;
    @Autowired
    SysDictMapper sysDictMapper;
    @Autowired
    SamplePersonMapMapper samplePersonMapMapper;
    @Autowired
    LabInfoMapper labInfoMapper;
    @Autowired
    ReagentKitMapper reagentKitMapper;
    @Autowired
    ReagentLocusMapper reagentLocusMapper;
    @Autowired
    LocusInfoMapper locusInfoMapper;

    @Autowired
    ICaseConverterClient iCaseConverterClient;
    @Autowired
    ISampleConverterClient iSampleConverterClient;
    @Autowired
    ISampleGeneConverterClient iSampleGeneConverterClient;
    @Autowired
    IOrgInfoConverterClient iOrgInfoConverterClient;
    @Autowired
    IReagentConverterClient iReagentConverterClient;
    @Autowired
    ICriminalConverterClient iCriminalConverterClient;

    /**
     * 根据委托类别查询委托列表
     * @param category 委托类型
     * @param pageIndex 页码
     * @param pageSize 每页显示条数
     * @return
     */
    @Override
    public List<Consignment> selectListByCategory(String category, String initServerNoLike, int pageIndex, int pageSize) {
        try {
            Consignment entity = new Consignment();
            entity.setCategory(category);
            entity.setInitServerNoLike(initServerNoLike);
            ConsignmentVo query = new ConsignmentVo(entity);

            query.setOffset((pageIndex - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            return consignmentMapper.listSelectListData(query);
        }catch(Exception ex){
            logger.error("invokie CaseInfoService.selectListByCategory error.");
            return null;
        }
    }





    /**
     * 获取案件委托及相关联信息
     */
    @Override
    public void selectCaseInfoData(String labServerNoPrefix) {

    }

    @Override
    public void selectOrgInfoDataBase(Integer page,String initServerNoLike){
        List<LabInfo>  labInfoList = labInfoMapper.getlabInfoList(initServerNoLike);
        for (LabInfo LabInfo: labInfoList) {
            iOrgInfoConverterClient.orgInfoSave(JSON.toJSONString(LabInfo));
        }

    }

    //建库数据同步
    @Override
    public void selectInfoListData(Integer page,Integer pagesize,String initServerNoIn){
        /*try{
            Gson gson = new Gson();
            List<String> strlist = sampleDnaGeneMapper.selectByLocusName();//查询基因座str
            List<String> ystrlist = sampleDnaGeneMapper.selectByLocusYstrName();//查询基因座ystr
            ConsignmentVo query = new  ConsignmentVo();
            query.setOffset((page - 1) * pagesize);
            query.setRows(query.getOffset() + pagesize);
            query.getEntity().setIsNull("1");
            if (StringUtils.isNotBlank(initServerNoIn)) {
                query.getEntity().setInitServerNoLike(initServerNoIn);
            }

            List<Consignment> consignments = consignmentMapper.listSelectListData(query);//获取委托数据
            if (consignments.size()!=0){
                for (Consignment consignment :consignments){
                    com.bazl.dna.database.service.model.po.ConsignmentInfo consignmentInf = consignmentInfo(null,consignment,null);//组装委托数据
                    //存储委托信息
                    ResponseData ConsignmentReq = iCaseConverterClient.consignmentInfoSave(consignmentInf);
                    if(1 == ConsignmentReq.getCode()){
                        HashMap<String,String> map2  = gson.fromJson(gson.toJson(ConsignmentReq.getResult()), new TypeToken<HashMap<String,String>>(){}. getType());
                        Integer ConsignmentId = Integer.parseInt(map2.get("id")) ;
                        //存储检材信息
                        SampleInfo getSampleInfo = new SampleInfo();
                        getSampleInfo.setConsignmentId(consignment.getId());
//                        if (StringUtils.isNotBlank(initServerNoLike))
//                            getSampleInfo.setInitServerNo(initServerNoLike);

                        List<SampleInfo> sampleInfos = sampleInfo.selectByPrimaryKeys(getSampleInfo);
                        continueOut:for (SampleInfo sampleInfo:sampleInfos) {
                            Integer PersonId = null;
                            PersonInfo personinfo = new PersonInfo();

                            if (StringUtils.isBlank(sampleInfo.getPhysicalEvidenceId())) {//物证id为空时这个检材是人员样本
                                //查询样本人员信息
                                SamplePersonMap samplePersonMap = samplePersonMapMapper.selectByPrimaryKeySample(sampleInfo.getId());//查询关系表
                                if (null != samplePersonMap) {
                                    PersonInfo getPersonInfo = new PersonInfo();
                                    getPersonInfo.setId(samplePersonMap.getPersonObjectId());

//                                    if (StringUtils.isNotBlank( initServerNoLike))
//                                        getPersonInfo.setInitServerNoLike(initServerNoLike);

                                    personinfo = personInfoMapper.selectByPrimaryKey(getPersonInfo);//查询人员信息
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
                                    }
                                    if (null != personinfo){ //存储人员和亲缘
                                        if(!"1".equals(personinfo.getDbCategory()) && !"0".equals(personinfo.getDbCategory())){
                                            CriminalPersonInfo criminalPersonInfo = criminalPersonInfo(personinfo, ConsignmentId);//组装人员数据
                                            ResponseData responseData = iCriminalConverterClient.personInfoSave(criminalPersonInfo);
                                            if (1 == responseData.getCode()) {
                                                HashMap<String, String> map3 = gson.fromJson(gson.toJson(responseData.getResult()), new TypeToken<HashMap<String, String>>() {
                                                }.getType());
                                                PersonId = Integer.parseInt(map3.get("id"));
                                            }
                                        }else if("1".equals(personinfo.getDbCategory()) && !"0".equals(personinfo.getDbCategory())){
                                            //转换到质控样本表  //qc_sample_info
                                            //存储检材基因数据
                                            SampleDnaGene getSampleDnaGene = new SampleDnaGene();
                                            getSampleDnaGene.setId(sampleInfo.getId());
//                                            if (null != initServerNoLike && !"".equals(initServerNoLike))
//                                                getSampleDnaGene.setInitServerNoLike(initServerNoLike);

                                            SampleDnaGene sampleDnaGene = sampleDnaGeneMapper.selectByPrimaryKeySampleId(getSampleDnaGene);
                                            com.bazl.dna.database.service.model.po.QcSampleInfo qcbean= QcSampleInfo(sampleInfo,sampleDnaGene,personinfo,strlist,ystrlist);
                                            iSampleConverterClient.qcsave(qcbean);
                                        }
                                    }


                                }
                                //补充人员信息亲缘!!!!
                                List<SamplePersonMap> samplePersonInfoList = samplePersonMapMapper.selectByPrimarySample(sampleInfo.getId());
                                for (SamplePersonMap samplePersonInfo: samplePersonInfoList){
                                    if (null != samplePersonInfo){
                                        Integer Person = null;
                                        PersonInfo getPersonInfo = new PersonInfo();
                                        getPersonInfo.setId(samplePersonInfo.getPersonObjectId());
//                                        if (StringUtils.isNotBlank(initServerNoLike))
//                                            getPersonInfo.setInitServerNoLike(initServerNoLike);

                                        PersonInfo person = personInfoMapper.selectByPrimaryKey(getPersonInfo);//查询人员信息
                                        if (null != person){
                                            com.bazl.dna.database.service.model.po.CasePersonInfo casePersonInfo = casePersonInfo(person,null,ConsignmentId);//组装人员数据
                                            ResponseData responseData = iCaseConverterClient.casePersonInfoSave(casePersonInfo);//存储人员数据
                                            if (1 == responseData.getCode()){
                                                HashMap<String,String> map3  = gson.fromJson(gson.toJson(responseData.getResult()), new TypeToken<HashMap<String,String>>(){}. getType());
                                                Person = Integer.parseInt(map3.get("id")) ;
                                            }
                                            //存储关系表
                                            com.bazl.dna.database.service.model.po.PersonRelativeInfo bean = PersonRelativeInfo(PersonId,Person,samplePersonInfo);
                                            iCaseConverterClient.savePerson(bean);
                                        }
                                    }
                                }
                            }
                            //获取检材类型
                            SamplePersonMap samplekey = samplePersonMapMapper.selectByPrimaryKeySample(sampleInfo.getId());
                            if (null != samplekey) {
                                PersonInfo getPersonInfo = new PersonInfo();
                                getPersonInfo.setId(samplekey.getPersonObjectId());
//                                if (StringUtils.isNotBlank(initServerNoLike))
//                                    getPersonInfo.setInitServerNoLike(initServerNoLike);

                                personinfo = personInfoMapper.selectByPrimaryKey(getPersonInfo);//查询人员信息
                                if (null != personinfo){
                                    if(StringUtils.isNotBlank(personinfo.getDbCategory())){
                                        String intByType = getIntByType(personinfo.getDbCategory());
                                        if (null != intByType ){
                                            if (intByType.equals("0")){
                                                personinfo.setDbCategory("0");
                                                continue continueOut;
                                            }else if(intByType.equals("1")){
                                                personinfo.setDbCategory("1");
                                                continue continueOut;
                                            }else {
                                                personinfo.setDbCategory(intByType);
                                            }
                                        }
                                    }
                                }
                            }


                            //处理检材信息
                            CriminalSampleInfo criminalSampleInfo = criminalSampleInfo(sampleInfo, ConsignmentId, PersonId,personinfo);//组装检材数据
                            ResponseData dnaSampleInfoReq = iCriminalConverterClient.sampleInfoSave(criminalSampleInfo);
                            if(1 == dnaSampleInfoReq.getCode()){
                                HashMap<String,String> map3  = gson.fromJson(gson.toJson(dnaSampleInfoReq.getResult()), new TypeToken<HashMap<String,String>>(){}. getType());
                                Integer dnaSampleInfoId = Integer.parseInt(map3.get("id")) ;
                                //存储检材基因数据
                                SampleDnaGene getSampleDnaGene = new SampleDnaGene();
                                getSampleDnaGene.setId(sampleInfo.getId());
//                                if (   StringUtils.isNotBlank(initServerNoLike))
//                                    getSampleDnaGene.setInitServerNoLike(initServerNoLike);

                                SampleDnaGene sampleDnaGene = sampleDnaGeneMapper.selectByPrimaryKeySampleId(getSampleDnaGene);
                                if (null != sampleDnaGene){
                                    if (null != sampleDnaGene.getGeneType()){
                                        if (sampleDnaGene.getGeneType().equals("1")){//str
                                            sampleDnaGene.setGeneInfo(geneFormatList(sampleDnaGene.getGeneInfo(), strlist));//基因信息格式转换
                                            com.bazl.dna.database.service.model.po.DnaStrGeneInfo dnaStrGeneInfo =  dnaStrGeneInfo(sampleDnaGene,dnaSampleInfoId);//组装基因数据
                                            iSampleGeneConverterClient.strGeneInfoSave(dnaStrGeneInfo);
                                        }else{//ystr
                                            sampleDnaGene.setGeneInfo(geneFormatList(sampleDnaGene.getGeneInfo(), ystrlist));//基因信息格式转换
                                            com.bazl.dna.database.service.model.po.DnaYstrGeneInfo dnaYstrGeneInfo =  dnaYstrGeneInfo(sampleDnaGene,dnaSampleInfoId);//组装基因数据
                                           iSampleGeneConverterClient.ystrGeneInfoSave(dnaYstrGeneInfo);
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }*/
    }


    @Override
    public void selectCaseInfoListData( Integer pageNo,Integer pageSize,String initServerNoLike) {
        /*try {
            CaseInfoVo query = new CaseInfoVo();
            List<String> strlist = sampleDnaGeneMapper.selectByLocusName();//查询基因座str
            List<String> ystrlist = sampleDnaGeneMapper.selectByLocusYstrName();//查询基因座ystr
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);
            if (null != initServerNoLike && !"".equals(initServerNoLike))
                query.getEntity().setInitServerNoLike(initServerNoLike);
            List<CaseInfo> queryList = caseInfoMapper.selectCaseInfoListData(query);
            if (queryList.size() != 0){
                for (CaseInfo caseInfo: queryList) {
                    Consignment getConsignment = new Consignment();
                    getConsignment.setEventId(caseInfo.getId());

                    if ( StringUtils.isNotBlank(initServerNoLike))
                        getConsignment.setInitServerNoLike(initServerNoLike);

                    List<Consignment> consignments = consignmentMapper.ListSelectByPrimaryKeyEventId(getConsignment);//获取委托数据
                    for (Consignment consignment :consignments){
                        com.bazl.dna.database.service.model.po.CaseInfo consignmentInfo = new  com.bazl.dna.database.service.model.po.CaseInfo();
                        if (null != consignment)
                            consignmentInfo = caseInfo(caseInfo, consignment);//组装案件数据

                        //存储案件信息
                        ResponseData caseInfoReq = iCaseConverterClient.caseInfoSave(consignmentInfo);
                        if(1 == caseInfoReq.getCode()){
                            //获取返回的caseid
                            Gson gson = new Gson();
                            HashMap<String,String> map  = gson.fromJson(gson.toJson(caseInfoReq.getResult()), new TypeToken<HashMap<String,String>>(){}. getType());
                            Integer caseId = Integer.parseInt(map.get("id")) ;

                            com.bazl.dna.database.service.model.po.ConsignmentInfo consignmentInf = consignmentInfo(caseInfo,consignment,caseId);//组装委托数据
                            //存储委托信息
                            ResponseData ConsignmentReq = iCaseConverterClient.consignmentInfoSave(consignmentInf);
                            if(1 == ConsignmentReq.getCode()){
                                HashMap<String,String> map2  = gson.fromJson(gson.toJson(ConsignmentReq.getResult()), new TypeToken<HashMap<String,String>>(){}. getType());
                                Integer ConsignmentId = Integer.parseInt(map2.get("id")) ;
                                //存储检材信息
                                SampleInfo getSampleInfo = new SampleInfo();
                                getSampleInfo.setConsignmentId(consignment.getId());
                                if (null != initServerNoLike && !"".equals(initServerNoLike))
                                    getSampleInfo.setInitServerNo(initServerNoLike);

                                List<SampleInfo> sampleInfos = sampleInfo.selectByPrimaryKeys(getSampleInfo);

                                continueOut:for (SampleInfo sampleInfo:sampleInfos) {
                                    Integer PersonId = null;
                                    PersonInfo personinfo = null;
                                    if (StringUtils.isBlank(sampleInfo.getPhysicalEvidenceId())){//物证id为空时这个检材是人员样本
                                        //查询样本人员信息
                                        SamplePersonMap samplePersonMap = samplePersonMapMapper.selectByPrimaryKeySample(sampleInfo.getId());//查询关系表/类型9自身
                                        if (null != samplePersonMap){
                                            PersonInfo getPersonInfo = new PersonInfo();
                                            getPersonInfo.setId(samplePersonMap.getPersonObjectId());

                                            if (StringUtils.isNotBlank(initServerNoLike))
                                                getPersonInfo.setInitServerNoLike(initServerNoLike);

                                            personinfo = personInfoMapper.selectByPrimaryKey(getPersonInfo);//查询人员信息
                                            if (null != personinfo){
                                                if(StringUtils.isNotBlank(personinfo.getDbCategory())){
                                                    String intByType = getIntByType(personinfo.getDbCategory());
                                                    if (null != intByType ){
                                                        if (intByType.equals("0")){
                                                            personinfo.setDbCategory("0");
                                                            continue continueOut;
                                                        }else if(intByType.equals("1")){
                                                            personinfo.setDbCategory("1");
                                                        }else {
                                                            personinfo.setDbCategory(intByType);
                                                        }
                                                    }
                                                }
                                            }
                                            if (null != personinfo){ //存储人员和亲缘
                                                if(!"1".equals(personinfo.getDbCategory()) && !"0".equals(personinfo.getDbCategory())){
                                                    com.bazl.dna.database.service.model.po.CasePersonInfo casePersonInfo = casePersonInfo(personinfo,caseId,ConsignmentId);//组装人员数据
                                                    ResponseData responseData = iCaseConverterClient.casePersonInfoSave(casePersonInfo);//存储人员数据
                                                    if (1 == responseData.getCode()){
                                                        HashMap<String,String> map3  = gson.fromJson(gson.toJson(responseData.getResult()), new TypeToken<HashMap<String,String>>(){}. getType());
                                                        PersonId = Integer.parseInt(map3.get("id")) ;
                                                    }
                                                }else if("1".equals(personinfo.getDbCategory()) && !"0".equals(personinfo.getDbCategory())){
                                                    //转换到质控样本表  //qc_sample_info
                                                    //存储检材基因数据
                                                    SampleDnaGene getSampleDnaGene = new SampleDnaGene();
                                                    getSampleDnaGene.setId(sampleInfo.getId());
                                                    if (null != initServerNoLike && !"".equals(initServerNoLike))
                                                        getSampleDnaGene.setInitServerNoLike(initServerNoLike);

                                                    SampleDnaGene sampleDnaGene = sampleDnaGeneMapper.selectByPrimaryKeySampleId(getSampleDnaGene);
                                                    com.bazl.dna.database.service.model.po.QcSampleInfo qcbean= QcSampleInfo(sampleInfo,sampleDnaGene,personinfo,strlist,ystrlist);
                                                    iSampleConverterClient.qcsave(qcbean);
                                                }
                                            }
                                        }
                                        //补充人员信息亲缘!!!!
                                        List<SamplePersonMap> samplePersonInfoList = samplePersonMapMapper.selectByPrimarySample(sampleInfo.getId());
                                        for (SamplePersonMap samplePersonInfo: samplePersonInfoList){
                                            if (null != samplePersonInfo){
                                                Integer Person = null;
                                                PersonInfo getPersonInfo = new PersonInfo();
                                                getPersonInfo.setId(samplePersonInfo.getPersonObjectId());
                                                if (StringUtils.isNotBlank(initServerNoLike))
                                                    getPersonInfo.setInitServerNoLike(initServerNoLike);

                                                PersonInfo person = personInfoMapper.selectByPrimaryKey(getPersonInfo);//查询人员信息
                                                if (null != person){
                                                    com.bazl.dna.database.service.model.po.CasePersonInfo casePersonInfo = casePersonInfo(person,caseId,ConsignmentId);//组装人员数据
                                                    ResponseData responseData = iCaseConverterClient.casePersonInfoSave(casePersonInfo);//存储人员数据
                                                    if (1 == responseData.getCode()){
                                                        HashMap<String,String> map3  = gson.fromJson(gson.toJson(responseData.getResult()), new TypeToken<HashMap<String,String>>(){}. getType());
                                                        Person = Integer.parseInt(map3.get("id")) ;
                                                    }
                                                    //存储关系表
                                                    com.bazl.dna.database.service.model.po.PersonRelativeInfo bean = PersonRelativeInfo(PersonId,Person,samplePersonInfo);
                                                    iCaseConverterClient.savePerson(bean);
                                                }
                                            }
                                        }
                                    }
                                    //获取检材类型
                                    SamplePersonMap samplekey = samplePersonMapMapper.selectByPrimaryKeySample(sampleInfo.getId());
                                    if (null != samplekey) {
                                        PersonInfo getPersonInfo = new PersonInfo();
                                        getPersonInfo.setId(samplekey.getPersonObjectId());
                                        if (StringUtils.isNotBlank(initServerNoLike))
                                            getPersonInfo.setInitServerNoLike(initServerNoLike);

                                        personinfo = personInfoMapper.selectByPrimaryKey(getPersonInfo);//查询人员信息
                                        if (null != personinfo){
                                            if(StringUtils.isNotBlank(personinfo.getDbCategory())){
                                                String intByType = getIntByType(personinfo.getDbCategory());
                                                if (null != intByType ){
                                                    if (intByType.equals("0")){
                                                        personinfo.setDbCategory("0");
                                                        continue continueOut;
                                                    }else if(intByType.equals("1")){
                                                        personinfo.setDbCategory("1");
                                                        continue continueOut;
                                                    }else {
                                                        personinfo.setDbCategory(intByType);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    //处理检材信息
                                    com.bazl.dna.database.service.model.po.DnaSampleInfo dnaSampleInfo = sampleInfo(sampleInfo,caseId,ConsignmentId,PersonId,personinfo);//组装检材数据
                                    ResponseData dnaSampleInfoReq = iSampleConverterClient.save(dnaSampleInfo);
                                    if(1 == dnaSampleInfoReq.getCode()){
                                        HashMap<String,String> map3  = gson.fromJson(gson.toJson(dnaSampleInfoReq.getResult()), new TypeToken<HashMap<String,String>>(){}. getType());
                                        Integer dnaSampleInfoId = Integer.parseInt(map3.get("id")) ;
                                        //存储检材基因数据
                                        SampleDnaGene getSampleDnaGene = new SampleDnaGene();
                                        getSampleDnaGene.setId(sampleInfo.getId());

                                        if (null != initServerNoLike && !"".equals(initServerNoLike))
                                            getSampleDnaGene.setInitServerNoLike(initServerNoLike);

                                        SampleDnaGene sampleDnaGene = sampleDnaGeneMapper.selectByPrimaryKeySampleId(getSampleDnaGene);
                                        if (null != sampleDnaGene){
                                            if (null != sampleDnaGene.getGeneType()){
                                                if (sampleDnaGene.getGeneType().equals("1")){//str
                                                    sampleDnaGene.setGeneInfo(geneFormatList(sampleDnaGene.getGeneInfo(), strlist));//基因信息格式转换
                                                    com.bazl.dna.database.service.model.po.DnaStrGeneInfo dnaStrGeneInfo =  dnaStrGeneInfo(sampleDnaGene,dnaSampleInfoId);//组装基因数据
                                                    iSampleGeneConverterClient.strGeneInfoSave(dnaStrGeneInfo);
                                                }else{//ystr
                                                    sampleDnaGene.setGeneInfo(geneFormatList(sampleDnaGene.getGeneInfo(), ystrlist));//基因信息格式转换
                                                    com.bazl.dna.database.service.model.po.DnaYstrGeneInfo dnaYstrGeneInfo =  dnaYstrGeneInfo(sampleDnaGene,dnaSampleInfoId);//组装基因数据
                                                    iSampleGeneConverterClient.ystrGeneInfoSave(dnaYstrGeneInfo);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }*/
    }

//    public  com.bazl.dna.database.service.model.po.PersonRelativeInfo PersonRelativeInfo(Integer targetPerson,Integer person,  SamplePersonMap samplePersonInfo){
//        com.bazl.dna.database.service.model.po.PersonRelativeInfo bean = new com.bazl.dna.database.service.model.po.PersonRelativeInfo();
//        bean.setId(null);
//        if (null != targetPerson)
//        bean.setTargetPersonUuid(targetPerson+"");
//        if (null != person)
//        bean.setRelationPersonUuid(person+"");
//        bean.setRelationTypeCode(samplePersonInfo.getRelation());
//        return bean;
//    }

    //处理质控
    public com.bazl.dna.database.service.model.po.QcSampleInfo QcSampleInfo(SampleInfo sampleInfo, SampleDnaGene sampleDnaGene, PersonInfo personinfo, List<String> strlist, List<String> ystrlist){
        com.bazl.dna.database.service.model.po.QcSampleInfo bean= new com.bazl.dna.database.service.model.po.QcSampleInfo();
        bean.setId(null);
        bean.setLabServerNo(sampleInfo.getSampleLabNo());
        bean.setQcSampleType("01");
        bean.setQcSampleName(sampleInfo.getSampleName());
        bean.setQcSampleNo(sampleInfo.getSampleNo());
        if(null != personinfo){
            bean.setQcPersonType(personinfo.getPersonType());
            bean.setQcPersonName(personinfo.getPersonName());
            bean.setQcPersonIdcardNo(personinfo.getPersonNo());
            bean.setQcPersonGender(personinfo.getGender());
            bean.setQcPersonOrgId(personinfo.getConsignOrgCode());
            bean.setQcPersonOrgName(personinfo.getConsignOrgCode());
        }
        if (sampleDnaGene.getGeneType().equals("1")) {//str
            bean.setStrPanelName(sampleDnaGene.getReagentKit());
            bean.setStrGeneInfo(geneFormatList(sampleDnaGene.getGeneInfo(), strlist));
        }else  if (sampleDnaGene.getGeneType().equals("2")){
            bean.setYStrPanelName(sampleDnaGene.getReagentKit());
            bean.setYStrGeneInfo(geneFormatList(sampleDnaGene.getGeneInfo(), ystrlist));
        }
        return bean;
    }

    //同步试剂盒和基因座信息
    @Override
    public void reagentDataBase(){
        Gson gson = new Gson();
        List<ReagentKit> reagentKits = reagentKitMapper.selectAll();//查询试剂盒信息
        for (ReagentKit reagentKit: reagentKits) {
            com.bazl.dna.database.service.model.po.DnaPanelInfo bean = dnaPanelInfo(reagentKit);//-组装试剂盒信息
            ResponseData responseData = iReagentConverterClient.reagentSave(bean);//插入试剂盒
            if(responseData.getCode() == 1){
                HashMap<String,String> map3  = gson.fromJson(gson.toJson(responseData.getResult()), new TypeToken<HashMap<String,String>>(){}. getType());
                Integer reagentSaveId = Integer.parseInt(map3.get("id")) ;
                List<ReagentLocus> reagentLocis = reagentLocusMapper.selectByKitIdToLocusID(reagentKit.getId());//查询试剂盒和基因座关系
                for (ReagentLocus reagentLocu: reagentLocis) {
                    LocusInfo locusInfo = locusInfoMapper.selectByPrimaryKey(reagentLocu.getLocusId());//查询基因座信息
                    DnaLocusInfo dnaLocusInfo = dnaLocusInfo(locusInfo);
                    ResponseData locusInfoSave = iReagentConverterClient.locusInfoSave(dnaLocusInfo);//插入基因座信息
                    if(locusInfoSave.getCode() == 1){
                        HashMap<String,String> map2  = gson.fromJson(gson.toJson(locusInfoSave.getResult()), new TypeToken<HashMap<String,String>>(){}. getType());
                        Integer locusInfoSaveId = Integer.parseInt(map2.get("id")) ;
                        iReagentConverterClient.panelLocusSave(panelLocusSave(reagentLocu,reagentSaveId,dnaLocusInfo,locusInfoSaveId));//插入试剂盒基因座关系表
                    }

                }
            }
        }
    }

    private DnaLocusInfo dnaLocusInfo(LocusInfo locusInfo){
        com.bazl.dna.database.service.model.po.DnaLocusInfo bean = new com.bazl.dna.database.service.model.po.DnaLocusInfo();
        bean.setId(null);
        bean.setLocusType(locusInfo.getLocusType());
        bean.setLocusName(locusInfo.getLocusName());
        bean.setNationalLocusName(locusInfo.getNationalLocusName());
        bean.setLocusAlias(locusInfo.getAlias());
        if(null != locusInfo.getValueScope()){
            bean.setValueScope(locusInfo.getValueScope());
        }else{
            bean.setValueScope("0");
        }
        bean.setCoreLocusFlag(locusInfo.getCoreLocusFlag().intValue());
        bean.setLocusOrd(locusInfo.getOrd());

        if (null != locusInfo.getCreateDatetime()) {
            bean.setCreateDatetime(DateUtil.dateToLocalDateTime(locusInfo.getCreateDatetime()));
        }
        if (null != locusInfo.getUpdateDatetime()){
            bean.setUpdateDatetime(DateUtil.dateToLocalDateTime(locusInfo.getUpdateDatetime()));
        }

        bean.setRemarks(locusInfo.getRemark());

        return bean;
    }

    private DnaPanelLocus panelLocusSave(ReagentLocus reagentLocu, Integer reagentSaveId, DnaLocusInfo info, Integer locusInfoSaveId){
        com.bazl.dna.database.service.model.po.DnaPanelLocus bean = new com.bazl.dna.database.service.model.po.DnaPanelLocus();
        bean.setId(null);
        bean.setPanelId(reagentSaveId);
        bean.setLocusId(locusInfoSaveId);
        bean.setLocusOrd(reagentLocu.getOrd());
        bean.setLocusName(info.getLocusName());
        return bean;
    }


    private DnaPanelInfo dnaPanelInfo(ReagentKit reagentKit){
        com.bazl.dna.database.service.model.po.DnaPanelInfo bean = new com.bazl.dna.database.service.model.po.DnaPanelInfo();
        bean.setId(null);
        bean.setPanelType(Integer.parseInt(reagentKit.getGeneType()));
        bean.setPanelCode(reagentKit.getCode());
        bean.setPanelName(reagentKit.getName());
        bean.setPanelFullName(reagentKit.getName());
        bean.setAliasName(reagentKit.getAlias());
        bean.setProducerName(null);
        bean.setPanelVersionNo(null);
        bean.setPanelFilePath(null);
        bean.setBinsFilePath(null);
        if (null != reagentKit.getCreateDatetime()) {
            bean.setCreateDatetime(DateUtil.dateToLocalDateTime(reagentKit.getCreateDatetime()));
        }

        bean.setCreatePerson(reagentKit.getCreateUser());
        bean.setCreatePersonOrgName(null);
        if (null != reagentKit.getUpdateDatetime()) {
            bean.setUpdateDatetime(DateUtil.dateToLocalDateTime(reagentKit.getUpdateDatetime()));
        }

        bean.setUpdatePerson(reagentKit.getUpdateUser());
        bean.setUpdatePersonOrgName(null);
        bean.setPanelDescription(reagentKit.getReagentKitDesc());
        return bean;
    }

    /**
     * 国家库基因信息格式转换
     *   目标类型 ;
     *    ["D8S1179": "11/12"......]
     */
    public static String geneFormatList(String geneInfo, List<String> stringList) {
        LinkedHashMap<Object, Object> sampleGeneInfo = new LinkedHashMap<>();
        String geneInfos = null;
        try {
            String[] split = geneInfo.split(";",-1);
            if (split.length>0){
                for (int i = 0;i<split.length &i<stringList.size();i++){
                    String s = split[i];
                    sampleGeneInfo.put(StringUtils.replace(stringList.get(i), " ", ""),s);
                }
            }
            geneInfos = JSON.toJSONString(sampleGeneInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geneInfos;
    }

    @SuppressWarnings("unused")
	private com.bazl.dna.database.service.model.po.CriminalPersonInfo criminalPersonInfo(PersonInfo info,Integer ConsignmentId){
        com.bazl.dna.database.service.model.po.CriminalPersonInfo bean = new com.bazl.dna.database.service.model.po.CriminalPersonInfo();
        bean.setId(null);
        bean.setLimsPersonUuid(info.getId());
        bean.setConsignmentId(ConsignmentId);
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


    public CasePersonInfo casePersonInfo(PersonInfo info,Integer caseId,Integer ConsignmentId) {
            com.bazl.dna.database.service.model.po.CasePersonInfo bean = new com.bazl.dna.database.service.model.po.CasePersonInfo();
            bean.setId(null);
            bean.setLimsPersonUuid(info.getId());
            bean.setLabServerNo(info.getInitServerNo());
            if(null != caseId)
            bean.setCaseId(caseId);

            if(null != ConsignmentId)
            bean.setConsignmentId(ConsignmentId);

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

    public DnaYstrGeneInfo dnaYstrGeneInfo(SampleDnaGene sampleDnaGene, Integer dnaSampleInfoId) {
        com.bazl.dna.database.service.model.po.DnaYstrGeneInfo bean = new com.bazl.dna.database.service.model.po.DnaYstrGeneInfo();
        bean.setId(null);
        bean.setLabServerNo(sampleDnaGene.getInitServerNo());
        bean.setLimsYstrUuid(sampleDnaGene.getId());
        bean.setDnaPanelId(null);//试剂盒
        bean.setKitname(sampleDnaGene.getKitname());
        bean.setSampleId(dnaSampleInfoId);
        bean.setLocusCount(sampleDnaGene.getAlleleCount());
        bean.setGeneInfo(sampleDnaGene.getGeneInfo());//需要转换处理
        bean.setGeneImage(null);
        bean.setDeleteFlag(sampleDnaGene.getDeleteFlag().intValue());
        bean.setDeleteDatetime(null);
        bean.setDeleteReason(null);
        bean.setDeletePersonId(null);
        bean.setDeletePersonName(null);
        bean.setStoreDatetime(null);
        bean.setStorePersonId(null);
        bean.setStorePersonName(sampleDnaGene.getCreateUser());
        bean.setUpdateDatetime(null);
        bean.setUpdatePersonId(null);
        bean.setUpdatePersonName(null);
        return bean;
    }

    public DnaStrGeneInfo dnaStrGeneInfo(SampleDnaGene sampleDnaGene, Integer dnaSampleInfoId) {
        com.bazl.dna.database.service.model.po.DnaStrGeneInfo bean = new  com.bazl.dna.database.service.model.po.DnaStrGeneInfo();
        bean.setId(null);
        bean.setLabServerNo(sampleDnaGene.getInitServerNo());
        bean.setLimsStrUuid(sampleDnaGene.getId());
        //bean.setGeneType(Integer.parseInt(sampleDnaGene.getGeneType()));
        bean.setDnaPanelId(null);//试剂盒
        bean.setKitname(sampleDnaGene.getKitname());
        bean.setDataSource(1);
        bean.setSampleId(dnaSampleInfoId);
        bean.setLocusCount(sampleDnaGene.getAlleleCount());
        bean.setGeneInfo(sampleDnaGene.getGeneInfo());//需要转换处理
        bean.setGeneImage(null);
        bean.setTransferFlag(sampleDnaGene.getTransferFlag().intValue());
        bean.setTransferDatetime(DateUtil.dateToLocalDateTime(sampleDnaGene.getTransferDatetime()));
        bean.setTransferPersonId(null);
        bean.setTransferPersonName(sampleDnaGene.getTransferUser());
        bean.setDeleteFlag(sampleDnaGene.getDeleteFlag().intValue());
        bean.setDeleteDatetime(null);
        bean.setDeleteReason(null);
        bean.setDeletePersonId(null);
        bean.setDeletePersonName(null);
        bean.setStoreDatetime(null);
        bean.setStorePersonId(null);
        bean.setStorePersonName(sampleDnaGene.getCreateUser());
        bean.setUpdateDatetime(null);
        bean.setUpdatePersonId(null);
        bean.setUpdatePersonName(null);
        return bean;
    }

    public ConsignmentInfo consignmentInfo(CaseInfo caseInfo, Consignment consignment, Integer caseid) {
        com.bazl.dna.database.service.model.po.ConsignmentInfo bean = new  com.bazl.dna.database.service.model.po.ConsignmentInfo();
        bean.setId(null);
        bean.setLimsConsignmentUuid(consignment.getId());
        if (null != caseid) {
            bean.setCaseId(caseid);
        }

        bean.setLabServerNo(consignment.getInitServerNo());
        bean.setConsignmentFileNo(consignment.getConsignmentNo());
        if (null != caseInfo) {
            bean.setSysXkWtno(caseInfo.getSysXkNo());
        }

        bean.setConsignOrgId(consignment.getLabId());
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
    @SuppressWarnings("unused")
	private com.bazl.dna.database.service.model.po.CriminalSampleInfo criminalSampleInfo(SampleInfo sampleInfo, Integer ConsignmentId, Integer PersonId, PersonInfo personinfo){
        com.bazl.dna.database.service.model.po.CriminalSampleInfo bean = new com.bazl.dna.database.service.model.po.CriminalSampleInfo();
        bean.setId(null);
        bean.setLimsSampleUuid(sampleInfo.getId());
        bean.setConsignmentId(ConsignmentId);
        bean.setLabServerNo(sampleInfo.getInitServerNo());
        if(null != personinfo) {
            bean.setInstoreDataType(personinfo.getDbCategory()); //PERSON_INFO  DB_CATEGORY 关联字典
        }
        
        bean.setRefPersonId(PersonId);//人员id
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

    public com.bazl.dna.database.service.model.po.DnaSampleInfo sampleInfo(SampleInfo sampleInfo, Integer caseid, Integer ConsignmentId, Integer PersonId, PersonInfo personinfo) {
        com.bazl.dna.database.service.model.po.DnaSampleInfo bean = new com.bazl.dna.database.service.model.po.DnaSampleInfo();
        bean.setId(null);
        bean.setLimsSampleUuid(sampleInfo.getId());
        bean.setLabServerNo(sampleInfo.getInitServerNo());

        if(null != personinfo) {
            bean.setInstoreDataType(personinfo.getDbCategory()); //PERSON_INFO  DB_CATEGORY 关联字典
        }
        if(null != caseid) {
            bean.setCaseId(caseid);
        }

        if(null != ConsignmentId) {
            bean.setConsignmentId(ConsignmentId);
        }

        if (null != sampleInfo.getPhysicalEvidenceId()){
            bean.setEvidenceFlag(1); //'是否为物证样本， 1-True，2-False'
        }else{
            bean.setEvidenceFlag(2); //'是否为物证样本， 1-True，2-False'
        }

        if(null != PersonId) {
            bean.setRefPersonId(PersonId);//人员id
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



    public static String getIntByType(String code){
        HashMap<String, String> map = new HashMap<>();
        map.put("010101","03");
        map.put("010102","03");
        map.put("010103","03");
        map.put("010104","03");
        map.put("010106","03");
        map.put("010107","03");
        map.put("010108","03");
        map.put("010109","03");
        map.put("010110","12");
        map.put("010201","01");
        map.put("010202","05");
        map.put("010301","06");
        map.put("010302","06");
        map.put("010303","06");
        map.put("010304","06");
        map.put("010305","06");
        map.put("010306","06");
        map.put("010307","10");
        map.put("010308","10");
        map.put("010310","06");
        map.put("010311","10");
        map.put("010401","07");
        map.put("010402","13");
        map.put("010403","13");
        map.put("010404","13");
        map.put("030101","1");//转换到质控样本
        map.put("030102","1");//转换到质控样本
        map.put("030103","1");//转换到质控样本
        map.put("030104","1");//转换到质控样本
        map.put("030105","1");//转换到质控样本
        map.put("030106","1");//转换到质控样本
        map.put("030107","1");//转换到质控样本
        map.put("030108","1");//转换到质控样本
        map.put("030109","1");//转换到质控样本
        map.put("040101","0");//跳过
        map.put("040102","0");//跳过
        map.put("040103","0");//跳过
        map.put("040104","0");//跳过
        return map.get(code);
    }

    public static String LikeKey(String name){
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


    @Override
    public CaseInfo selectByCaseNo(String caseNo) {
        try{
            List<CaseInfo> caseInfoList =caseInfoMapper.selectByCaseNo(caseNo);
            CaseInfo caseInfo = null;
            if (ListUtils.isNotNullAndEmptyList(caseInfoList)) {
                caseInfo = caseInfoList.get(0);
            }
            return caseInfo;
        }catch (Exception e) {
            e.printStackTrace();
            logger.info("获取国家库案件信息错误错误"+e);
            return null;
        }
    }

    @Override
    public CaseInfo selectByCaseId(String caseId) {
        try{
            CaseInfo caseInfo =caseInfoMapper.selectByCaseId(caseId);
            return caseInfo;
        }catch (Exception e) {
            logger.info("获取国家库案件id错误"+e);
            return null;
        }
    }

    @Override
    public CaseInfo selectBySampleId(String sampleId) {
        try{
            CaseInfo caseInfo =caseInfoMapper.selectBySampleId(sampleId);
            return caseInfo;
        }catch (Exception e) {
            logger.info("根据样本id获取案件信息错误"+e);
            return null;
        }
    }

}
