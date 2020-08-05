package com.bazl.dna.lims.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.common.FeedBackXckyConstants;
import com.bazl.dna.lims.dao.FugitivesInfoMapper;
import com.bazl.dna.lims.dao.LimsCaseInfoMapper;
import com.bazl.dna.lims.dao.LimsConsignmentInfoMapper;
import com.bazl.dna.lims.dao.LimsPerosnRelationMapper;
import com.bazl.dna.lims.dao.LimsPersonInfoMapper;
import com.bazl.dna.lims.dao.LimsSampleInfoDnaMapper;
import com.bazl.dna.lims.dao.OrgInfoMapper;
import com.bazl.dna.lims.dao.PersonDetailMapper;
import com.bazl.dna.lims.dao.QueueDetailMapper;
import com.bazl.dna.lims.dao.QueueSampleMapper;
import com.bazl.dna.lims.model.bo.DelegateDataModel;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.FugitivesInfo;
import com.bazl.dna.lims.model.po.LimsCaseInfo;
import com.bazl.dna.lims.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.model.po.LimsPerosnRelation;
import com.bazl.dna.lims.model.po.LimsPersonInfo;
import com.bazl.dna.lims.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.po.PersonDetail;
import com.bazl.dna.lims.model.po.QueueDetail;
import com.bazl.dna.lims.model.po.QueueSample;
import com.bazl.dna.lims.model.vo.CaseEffectiveRatioVo;
import com.bazl.dna.lims.model.vo.CaseEvidenceDetectionRateVo;
import com.bazl.dna.lims.model.vo.CasePropertyStatsVo;
import com.bazl.dna.lims.model.vo.IdentifyBookVo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.service.SeqNoGenerateService;
import com.bazl.dna.lims.utils.DateUtils;
import com.bazl.dna.lims.utils.ImgUtils;
import com.bazl.dna.lims.utils.ListUtils;
import com.bazl.dna.lims.utils.UplodFtpUtils;


/**
 * Created by hj on 2018/12/20.
 */
@Service
public class LimsCaseInfoServiceImpl extends BaseService implements LimsCaseInfoService {

    @Autowired
    LimsCaseInfoMapper limsCaseInfoMapper;

    @Autowired
    DictItemService dictItemService;

    @Autowired
    SeqNoGenerateService seqNoGenerateService;

    @Autowired
    LimsConsignmentInfoMapper limsConsignmentInfoMapper;

    @Autowired
    LimsPersonInfoMapper limsPersonInfoMapper;

    @Autowired
    LimsPerosnRelationMapper limsPerosnRelationMapper;

    @Autowired
    LimsSampleInfoDnaMapper limsSampleInfoDnaMapper;

    @Autowired
    PersonDetailMapper personDetailMapper;

    @Autowired
    OrgInfoMapper orgInfoMapper;

    @Autowired
    QueueSampleMapper queueSampleMapper;

    @Autowired
    QueueDetailMapper queueDetailMapper;

    @Autowired
    FugitivesInfoMapper fugitivesInfoMapper;

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

    /**
     * 根据案件id查询案件信息
     * @param caseId
     * @return
     */
    @Override
    public LimsCaseInfo selectByCaseId(String caseId) {
        LimsCaseInfo caseInfoDna = limsCaseInfoMapper.selectByCaseId(caseId);
        return caseInfoDna;
    }
    
    /**
     * 根据案件id查询案件信息
     * @param caseXkNo
     * @return
     */
    @Override
    public List<LimsCaseInfo> selectByCaseXkNo(String caseXkNo, String acceptOrgId) {
        try {
            return limsCaseInfoMapper.selectByCaseXkNo(caseXkNo, acceptOrgId);
        }catch(Exception ex){
            logger.error("根据现勘K号获取案件信息错误！", ex);
            return null;
        }
    }

    /**
     * 查询案件数量
     */
    @Override
    public int selectCountByCaseStatus(String status, String acceptOrgId) {
        return limsCaseInfoMapper.selectCountByCaseStatus(status, acceptOrgId);
    }
    
    /**
     * 查询案件数量(是否补送)
     * @param status
     * @param appendFlag
     * @param loginOrgId
     * @return
     */
    @Override
    public int selectCntByCaseStatus(String status, String appendFlag, String loginOrgId) {
        return limsCaseInfoMapper.selectCntByCaseStatus(status,appendFlag,loginOrgId);
    }
    
    @Override
    public int selectCountByOrgCode(String loginOrgId) {
        return limsCaseInfoMapper.selectCountByOrgCode(loginOrgId);
    }

    @Override
    public int selectCountByCaseStatusAndAcceptorId(LimsCaseInfoVo caseInfoVo) {
        try {
            return limsCaseInfoMapper.selectCountByCaseStatusAndAcceptorId(caseInfoVo);
        }catch (Exception e) {
            e.getStackTrace();
            logger.debug("根据当前登录人和状态查询案件数量失败！", e);
            return 0;
        }
    }

    /**
     * 根据年份获取各个月份的案件数
     */
    @Override
    public Map<String, Object> selectMonthCountByYear(String year) {
        return limsCaseInfoMapper.selectMonthCountByYear(year);
    }

    /**
     * 查询与补送查询list
     * @param query
     * @param pageInfo
     * @return
     */
    @Override
    public List<LimsCaseInfoVo> selectCaseInfoList(LimsCaseInfoVo query, PageInfo pageInfo) {
        List<LimsCaseInfoVo> caseInfoVOList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            caseInfoVOList = limsCaseInfoMapper.selectVOPaginationList(query);
        } catch(Exception ex) {
            logger.info("查询与补送报错："+ex);
            return null;
        }

        return caseInfoVOList;
    }

    @Override
    public List<LimsCaseInfoVo> selectVOPaginationExportList(LimsCaseInfoVo query) {
        return limsCaseInfoMapper.selectVOPaginationExportList(query);
    }

    /**
     * 查询与补送查询count
     * @param consignationInfoVo
     * @return
     */
    @Override
    public int selectVOCount(LimsCaseInfoVo consignationInfoVo) {
        return limsCaseInfoMapper.selectVOCount(consignationInfoVo);
    }

    /**
     * 根据现勘编号查询现勘数据
     * @param xkNo
     * @return
     */
    @Override
    public Map<String, Object> findCaseSampleInfoByXkNo(String xkNo){
        //返回map
        Map<String, Object> returnMap = new HashMap<>();
        //案件信息
        LimsCaseInfo limsCaseInfo = new LimsCaseInfo();
        //物证检材信息
        List<LimsSampleInfoDna> limsSampleInfoList = new ArrayList();
        try
        {
            Object[] objects = null;
            //发布接口地址
//            String testUrl = "http://10.8.41.63:8089/limsbjwt/webservices/SceneDnaManagerPortService?wsdl";
//            HashMap<String,String> m=new HashMap<String,String>();
//            m.put("xkNo", xkNo);
//            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//            Client client = dcf.createClient(testUrl);
//            //设置连接时间
//            HTTPConduit conduit = (HTTPConduit) client.getConduit();
//            HTTPClientPolicy policy = new HTTPClientPolicy();
//            long timeout = 3 * 60 * 1000;//3分钟
//            policy.setConnectionTimeout(timeout);//TCP握手时的时间
//            policy.setReceiveTimeout(timeout);//请求后等待响应的时间
//            conduit.setClient(policy);
            //返回现勘xml数据
            //objects = client.invoke("findSceneInvestigationByNo", JSONObject.fromObject(m).toString());
            //============================================================================================================================

            //获取返回xml路径
            //File mainFile = new File("classpath:xk.xml");
            InputStream in = this.getClass().getResourceAsStream("/xk.xml");

            //dom4j解析
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            Element root = document.getRootElement();
            Element caseElement = root.element("CASE");
            //案件编号
            Element caseNoAttr = caseElement.element("CASE_NO");
            if (caseNoAttr != null) {
                limsCaseInfo.setCaseNo(caseNoAttr.getTextTrim());
            }
            //现堪编号
            Element kNoAttr = caseElement.element("K_NO");
            if (kNoAttr != null) {
                limsCaseInfo.setCaseXkNo(kNoAttr.getTextTrim());
            }
            //案件类型
            Element caseTypeAttr = caseElement.element("CASE_TYPE");
            if (caseTypeAttr != null) {
                limsCaseInfo.setCaseType(caseTypeAttr.getTextTrim());
            }
            //案件名称
            Element caseNameAttr = caseElement.element("CASE_NAME");
            if (caseNameAttr != null) {
                limsCaseInfo.setCaseName(caseNameAttr.getTextTrim());
            }
            //案发地点行政编号
            Element sceneRegionalismAttr = caseElement.element("SCENE_REGIONALISM");
            if (sceneRegionalismAttr != null) {
                limsCaseInfo.setCaseLocation(sceneRegionalismAttr.getTextTrim());
            }
            //案发地点
            Element scenePlaceAttr = caseElement.element("SCENE_PLACE");
            if (scenePlaceAttr != null) {
                limsCaseInfo.setCaseLocation(scenePlaceAttr.getTextTrim());
            }

            //案发时间
            Element occurrenceDateAttr = caseElement.element("OCCURRENCE_DATE");
            if (occurrenceDateAttr != null) {
                String occurrenceDateStr = occurrenceDateAttr.getTextTrim();
                Date occurrenceDate = DateUtils.stringToDate(occurrenceDateStr, "yyyy-MM-dd");
                limsCaseInfo.setCaseDatetime(occurrenceDate);
            }
            //简要案情
            Element caseSummaryAttr = caseElement.element("CASE_SUMMARY");
            if (caseSummaryAttr != null) {
                limsCaseInfo.setCaseBrief(caseSummaryAttr.getTextTrim());
            }

            Element reserve1Attr = caseElement.element("RESERVE1");
            Element externalCaseNoAttr = caseElement.element("EXTERNAL_CASE_NO");
            Element casePropertyAttr = caseElement.element("CASE_PROPERTY");
            if (casePropertyAttr != null) {
                limsCaseInfo.setCaseProperty(casePropertyAttr.getTextTrim());
            }
            Element wtbhAttr = caseElement.element("WTBH");

            //检材
            Element bioEvidenceListElement = root.element("BIO_EVIDENCE_LIST");
            List bioEvidenceElementList = bioEvidenceListElement.elements("BIO_EVIDENCE");
            for (int i = 0; i < bioEvidenceElementList.size(); i++) {
                LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
                Element bioEvidenceElement = (Element)bioEvidenceElementList.get(i);

                Element wnoAttr = bioEvidenceElement.element("W_NO");
                if(wnoAttr != null){
                    sampleInfoDna.setSampleNo(wnoAttr.getTextTrim());
                }
                //检材描述
                Element descriptionAttr = bioEvidenceElement.element("DESCRIPTION");
                if(descriptionAttr != null){
                    sampleInfoDna.setSampleDesc(descriptionAttr.getTextTrim());
                }
                //提取人
                Element collectByAttr = bioEvidenceElement.element("COLLECT_BY");
                if (collectByAttr != null) {
                    sampleInfoDna.setExtractPerson(collectByAttr.getTextTrim());
                }
                //提取时间
                Element collectDateAttr = bioEvidenceElement.element("COLLECT_DATE");
                if (collectDateAttr != null) {
                    String collectDateStr = collectDateAttr.getTextTrim();
                    Date collectDate = DateUtils.stringToDate(collectDateStr, "yyyy-MM-dd");
                    sampleInfoDna.setExtractDatetime(collectDate);
                }
                //检材名称
                Element evidenceNameAttr = bioEvidenceElement.element("EVIDENCE_NAME");
                if (evidenceNameAttr != null) {
                    sampleInfoDna.setSampleName(evidenceNameAttr.getTextTrim());
                }
                //检材类型
                Element sampleTypeAttr = bioEvidenceElement.element("SAMPLE_TYPE");
                if ((sampleTypeAttr != null) && (!"".equals(sampleTypeAttr))) {
                    sampleInfoDna.setSampleType(xkTypeToLimsType(sampleTypeAttr.getTextTrim()));
                    List<DictItem> sampleTypeList = dictItemService.selectListByDictTypeCode("SAMPLE_TYPE");
                    for (DictItem dictItem : sampleTypeList) {
                        if (sampleInfoDna.getSampleType().equals(dictItem.getDictCode())) {
                            sampleInfoDna.setSampleTypeName(dictItem.getDictName());
                        }
                    }
                }
                Element collectPosAttr = bioEvidenceElement.element("COLLECT_POS");
                System.out.print(collectPosAttr);
                Element testDescAttr = bioEvidenceElement.element("TEST_DESC");
                System.out.print(testDescAttr);
                Element warnAttr = bioEvidenceElement.element("WARN_MSG");
                System.out.print(warnAttr);
                //检材类别
                Element flagAttr = bioEvidenceElement.element("TYPE");
                if (flagAttr != null) {
                    String flag = flagAttr.getTextTrim();
                    if ("1".equals(flag)) {
                        sampleInfoDna.setSampleFlag("1");
                    } else {
                        sampleInfoDna.setSampleFlag("0");
                    }
                }
                Element personIdAttr = bioEvidenceElement.element("PERSON_ID");
                Element relationAttr;
                if (personIdAttr != null) {
                    relationAttr = bioEvidenceElement.element("SAMPLE_RELATION");
                }
                Element acceptStatus = bioEvidenceElement.element("IF_SJ");
                Element sceneAddressAttr = root.element("AUTHORADDRESS");

                //以下为默认
                sampleInfoDna.setExtractMethod("01");
                sampleInfoDna.setExtractMethodName("擦");
                sampleInfoDna.setSamplePacking("01");
                sampleInfoDna.setSamplePackingName("纸袋");
                sampleInfoDna.setSamplePurpose("DNA检验");


                limsSampleInfoList.add(sampleInfoDna);

            }
            returnMap.put("limsCaseInfo", limsCaseInfo);
            returnMap.put("limsSampleInfoList", limsSampleInfoList);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("目前系统没有对应的勘验信息"+e);
            throw new IllegalArgumentException("目标系统没有对应的勘验信息！");
        }

        return returnMap;
    }

    public String xkTypeToLimsType(String bioEvidenceType) {
        int bioEvidenctTypeInt = Integer.parseInt(bioEvidenceType);
        switch (bioEvidenctTypeInt) {
            case 2002:
                return "01";
            case 2003:
                return "03";
            case 2004:
                return "02";
            case 2005:
                return "06";
            case 2011:
                return "07";
        }
        return "12";
    }

    /**
     * 查询补送记录
     * @param limsCaseInfoVo
     * @return
     */
    @Override
    public List<LimsCaseInfoVo> selectReplacementRecord(LimsCaseInfoVo limsCaseInfoVo) {
        return limsCaseInfoMapper.selectReplacementRecord(limsCaseInfoVo);
    }

    @Override
    public LimsCaseInfo selectByConsignmentId(String consignmentId) {
        return limsCaseInfoMapper.selectByConsignmentId(consignmentId);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean submitAcceptCase(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIdWzs, String sampleIds){
        try {

            if(ListUtils.isNull(sampleIdWzs)){
                String sampleIdWz = sampleIdWzs.substring(1);
                String[] sampleIdWzsArr = sampleIdWz.split(",");
                for(String string:sampleIdWzsArr){
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

            //修改委托人信息
            LimsConsignmentInfo consignatioInfo = delegateDataModel.getConsignatioInfo();
            LimsConsignmentInfo limsConsignmentInfo = limsConsignmentInfoMapper.selectByConsignmentId(consignatioInfo.getConsignmentId());

            String serialNumber = "";

            //修改案件信息
            LimsCaseInfo caseInfoDna = delegateDataModel.getCaseInfoDna();
            LimsCaseInfo limsCaseInfo = null;
            if(StringUtils.isNotBlank(caseInfoDna.getCaseId())){
                limsCaseInfo = limsCaseInfoMapper.selectByCaseId(caseInfoDna.getCaseId());
                limsCaseInfo.setCaseId(caseInfoDna.getCaseId());
                limsCaseInfo.setMajorNo("DNA鉴定");
                limsCaseInfo.setMajorType("01");
                limsCaseInfo.setCaseName(caseInfoDna.getCaseName());
                limsCaseInfo.setCaseLocation(caseInfoDna.getCaseLocation());
                limsCaseInfo.setCaseDatetime(caseInfoDna.getCaseDatetime());
                limsCaseInfo.setCaseProperty(caseInfoDna.getCaseProperty());
                limsCaseInfo.setCaseLevel(caseInfoDna.getCaseLevel());
                limsCaseInfo.setCaseBrief(caseInfoDna.getCaseBrief());
                limsCaseInfo.setCaseRemark(caseInfoDna.getCaseRemark());
                limsCaseInfo.setUpdateDatetime(new Date());
                limsCaseInfo.setUpdatePerson(operateUser.getLoginName());
                //是否加急
                limsCaseInfo.setCaseUrgentFlag(caseInfoDna.getCaseUrgentFlag());
                //修改案件状态为已受理
                limsCaseInfo.setCaseStatus("02");
                limsCaseInfo.setCaseType("01");
                //案件编号
                //判断是否是补送案件如果是补送案件 则不生成案件编号
                if(("1").equals(limsConsignmentInfo.getAppendFlag()) || StringUtils.isNotBlank(limsCaseInfo.getCaseNo())){
                    limsCaseInfo.setCaseNo(limsCaseInfo.getCaseNo());
                }else{
                    limsCaseInfo.setCaseNo(seqNoGenerateService.getNextCaseNoVal("-", "WZ",limsConsignmentInfo.getAcceptOrgId()));
                    //第一送检人
                    limsCaseInfo.setFirstChecker(operateUser.getPersonalId());
                }

                limsCaseInfoMapper.updateCaseInfoDna(limsCaseInfo);
            }

            if(limsCaseInfo.getCaseNo().indexOf("WZ") != -1){
                serialNumber = limsCaseInfo.getCaseNo().split("WZ")[1];
            }else{
                serialNumber = limsCaseInfo.getCaseNo();
            }

            QueueSample queueSample2 = null;
            if(StringUtils.isNotBlank(consignatioInfo.getConsignmentId())){
                if(StringUtils.isNotBlank(consignatioInfo.getAutographPicture())){
                    String autographPicturePath = ImgUtils.generateImage(consignatioInfo.getAutographPicture(), personImg);
                    String autographPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpPersonImg, autographPicturePath);
                    consignatioInfo.setAutographPicturePath(autographPicturePathFtp == null ? "" : autographPicturePathFtp);
                }
                limsConsignmentInfo.setConsignmentId(consignatioInfo.getConsignmentId());
                limsConsignmentInfo.setCaseId(caseInfoDna.getCaseId());
                limsConsignmentInfo.setDelegateDatetime(new Date());
                limsConsignmentInfo.setAppendFlag(consignatioInfo.getAppendFlag());
                //判断丰台公安局委托信息表中委托编号是否为空
                if(StringUtils.isBlank(limsConsignmentInfo.getConsignmentNo())){
                    if ("110106000000".equals(operateUser.getOrgId())){
                        limsConsignmentInfo.setConsignmentNo(limsCaseInfo.getCaseNo());
                    }
                }else {
                    limsConsignmentInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());//修改委托书编号
                }
                //重新鉴定次数
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
                if(StringUtils.isNotBlank(consignatioInfo.getAutographPicture())){
                    limsConsignmentInfo.setAutographPicture(consignatioInfo.getAutographPicture());
                    limsConsignmentInfo.setAutographPicturePath(consignatioInfo.getAutographPicturePath());
                }
                limsConsignmentInfo.setStatus("02");
                /*
                 * 修改委托信息中的受理信息--liuchang
                 * Date:2020-7-25
                 */
                LimsConsignmentInfo acceptInfo = delegateDataModel.getAcceptInfo();
                if (null!=acceptInfo){
                    limsConsignmentInfo.setAcceptOrgId(acceptInfo.getAcceptOrgId() == null ? " " :acceptInfo.getAcceptOrgId());//受理机构编号
                    if (null!=acceptInfo.getAcceptDatetime()){
                        limsConsignmentInfo.setAcceptDatetime(acceptInfo.getAcceptDatetime());//受理时间
                    }
                    limsConsignmentInfo.setAcceptorId(acceptInfo.getAcceptorId() == null ? " " :acceptInfo.getAcceptorId());//受理人编号
                }
//                limsConsignmentInfo.setAcceptorId(operateUser.getPersonalId());
                limsConsignmentInfo.setAcceptDatetime(new Date());
                limsConsignmentInfo.setTakePerson(consignatioInfo.getTakePerson());

                /**
                 * 添加现场委托案件时插入一条数据到数据到队列中
                 */
                QueueSample queueSample = new QueueSample();
                String id = UUID.randomUUID().toString();
                queueSample.setId(id);
                queueSample.setForeignId(limsCaseInfo.getCaseId());
                queueSample.setCreateDatetime(new Date());// new Date()为获取当前系统时间
                queueSample.setQueueType(FeedBackXckyConstants.QUEUE_TYPE_CONSIGNMENT_ACCEPTED);//受理时队列类型FeedBackXckyConstants
                queueSample.setStatus(0);
                queueSample.setServerNo(limsConsignmentInfo.getAcceptOrgId());

                if(StringUtils.isNotEmpty(limsCaseInfo.getConsignationXkNo())){
                    queueSampleMapper.insertQueueSample(queueSample);
                }

                queueSample2 = new QueueSample();
                String strId = UUID.randomUUID().toString();
                queueSample2.setId(strId);
                queueSample2.setForeignId(limsCaseInfo.getCaseId());
                queueSample2.setCreateDatetime(new Date());// new Date()为获取当前系统时间
                queueSample2.setQueueType(FeedBackXckyConstants.QUEUE_TYPE_EVIDENCE_ACCEPTED);//受理时队列类型
                queueSample2.setStatus(0);
                queueSample2.setServerNo(limsConsignmentInfo.getAcceptOrgId());
                if(StringUtils.isNotEmpty(limsCaseInfo.getConsignationXkNo())){
                    queueSampleMapper.insertQueueSample(queueSample2);
                }
                limsConsignmentInfoMapper.updateConsignatioInfo(limsConsignmentInfo);
            }

            //添加被鉴定人信息
            List<LimsPersonInfo> limsPersonInfoList = delegateDataModel.getLimsPersonInfoList();
            if(null != limsPersonInfoList && limsPersonInfoList.size() > 0){
                for(int i=0; i< limsPersonInfoList.size(); i++){
                    //判断人员信息是否为空
                    if(limsPersonInfoList.get(i) == null) {
                        continue;
                    }

                    //对人员照片进行上传
                    if(StringUtils.isNotBlank(limsPersonInfoList.get(i).getPersonFrontPicture())){
                        String personFrontPicturePath = ImgUtils.generateImage(limsPersonInfoList.get(i).getPersonFrontPicture(), personImg);
                        String personFrontPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpPersonImg, personFrontPicturePath);
                        limsPersonInfoList.get(i).setPersonFrontPicturePath(personFrontPicturePathFtp == null ? "" : personFrontPicturePathFtp);
                    }

                    //根据在逃人员名称和身份证号查询在逃人员
                    FugitivesInfo fugitivesInfo = null;
                    if (StringUtils.isNotBlank(limsPersonInfoList.get(i).getFugitivesName()) && StringUtils.isNotBlank(limsPersonInfoList.get(i).getFugitivesCard())) {
                        fugitivesInfo = new FugitivesInfo();
                        fugitivesInfo.setPersonName(limsPersonInfoList.get(i).getFugitivesName());
                        fugitivesInfo.setPersonCard(limsPersonInfoList.get(i).getFugitivesCard());
                        fugitivesInfo.setDeleteFlag(Constants.DELETE_FLAG_0);
                        List<FugitivesInfo> fugitivesInfoList = fugitivesInfoMapper.selectList(fugitivesInfo);
                        if (ListUtils.isNotNullAndEmptyList(fugitivesInfoList)) {
                            fugitivesInfo = fugitivesInfoList.get(0);
                        }
                    }

                    PersonDetail personDetail = new PersonDetail();
                    LimsPersonInfo limsPersonInfo1 = new LimsPersonInfo();
                    if(StringUtils.isNotBlank(limsPersonInfoList.get(i).getPersonId())){
                        limsPersonInfo1 = limsPersonInfoMapper.selectPersonInfoById(limsPersonInfoList.get(i).getPersonId());
                        PersonDetail personDetail1 = personDetailMapper.selectByDetailId(limsPersonInfo1.getPersonDetailId());
                        //修改人员详情表
                        personDetail1.setPersonDetailId(limsPersonInfo1.getPersonDetailId());
                        personDetail1.setPersonName(limsPersonInfoList.get(i).getPersonName());
                        personDetail1.setPersonType(limsPersonInfoList.get(i).getPersonType());
                        personDetail1.setPersonGender(limsPersonInfoList.get(i).getPersonGender());
                        personDetail1.setPerosnAge(limsPersonInfoList.get(i).getPerosnAge());
                        personDetail1.setPersonHeight(limsPersonInfoList.get(i).getPersonHeight());
                        personDetail1.setPersonWeight(limsPersonInfoList.get(i).getPersonWeight());
                        personDetail1.setIdCardFlag(limsPersonInfoList.get(i).getIdCardFlag());
                        personDetail1.setPersonIdCard(limsPersonInfoList.get(i).getPersonIdCard());
                        personDetail1.setNoIdCardDesc(limsPersonInfoList.get(i).getNoIdCardDesc());
                        personDetail1.setPersonCurrentAddress(limsPersonInfoList.get(i).getPersonCurrentAddress());
                        personDetail1.setUpdateDatetime(new Date());
                        personDetail1.setUpdatePerson(operateUser.getLoginName());
                        if(StringUtils.isNotBlank(limsPersonInfoList.get(i).getPersonFrontPicture())){
                            personDetail1.setPersonFrontPicture(limsPersonInfoList.get(i).getPersonFrontPicture());
                            personDetail1.setPersonFrontPicturePath(limsPersonInfoList.get(i).getPersonFrontPicturePath());
                        }
                        personDetailMapper.updatePersonDetail1(personDetail1);

                        //修改人员表
                        LimsPersonInfo limsPersonInfo2 = limsPersonInfoMapper.selectPersonInfoById(limsPersonInfoList.get(i).getPersonId());
                        limsPersonInfo2.setCaseId(caseInfoDna.getCaseId());
                        limsPersonInfo2.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsPersonInfo2.setPersonName(limsPersonInfoList.get(i).getPersonName());
                        limsPersonInfo2.setPersonType(limsPersonInfoList.get(i).getPersonType());
                        limsPersonInfo2.setUpdateDatetime(new Date());
                        limsPersonInfo2.setUpdatePerson(operateUser.getLoginName());
                        limsPersonInfo2.setPersonId(limsPersonInfoList.get(i).getPersonId());
                        limsPersonInfoMapper.updatePersonInfo(limsPersonInfo2);


                        //修改人员关系
                        LimsPerosnRelation limsPerosnRelation = limsPerosnRelationMapper.selectBySourcePersonId(limsPersonInfoList.get(i).getPersonId());
                        limsPerosnRelation.setRelationId(limsPerosnRelation.getRelationId());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfoList.get(i).getPersonId());
                        //在逃人员亲属关系在逃人员表主键id
                        if (fugitivesInfo != null) {
                            limsPerosnRelation.setTargetPersonId(fugitivesInfo.getId());
                        }
                        limsPerosnRelation.setRelationType(limsPersonInfoList.get(i).getRelationType());
                        limsPerosnRelation.setUpdateDatetime(new Date());
                        limsPerosnRelation.setUpdatePerson(operateUser.getLoginName());
                        limsPerosnRelationMapper.updatePerosnRelation(limsPerosnRelation);
                    }else{
                        //人员详细表
                        personDetail.setPersonDetailId(UUID.randomUUID().toString());
                        personDetail.setPersonName(limsPersonInfoList.get(i).getPersonName());
                        personDetail.setPersonType(limsPersonInfoList.get(i).getPersonType());
                        personDetail.setPersonGender(limsPersonInfoList.get(i).getPersonGender());
                        personDetail.setPerosnAge(limsPersonInfoList.get(i).getPerosnAge());
                        personDetail.setPersonHeight(limsPersonInfoList.get(i).getPersonHeight());
                        personDetail.setPersonWeight(limsPersonInfoList.get(i).getPersonWeight());
                        personDetail.setIdCardFlag(limsPersonInfoList.get(i).getIdCardFlag());
                        personDetail.setPersonIdCard(limsPersonInfoList.get(i).getPersonIdCard());
                        personDetail.setNoIdCardDesc(limsPersonInfoList.get(i).getNoIdCardDesc());
                        personDetail.setPersonCurrentAddress(limsPersonInfoList.get(i).getPersonCurrentAddress());
                        personDetail.setCreateDatetime(new Date());
                        personDetail.setCreatePerson(operateUser.getLoginName());
                        personDetail.setDeleteFlag("0");
                        if(StringUtils.isNotBlank(limsPersonInfoList.get(i).getPersonFrontPicture())){
                            personDetail.setPersonFrontPicture(limsPersonInfoList.get(i).getPersonFrontPicture());
                            personDetail.setPersonFrontPicturePath(limsPersonInfoList.get(i).getPersonFrontPicturePath());
                        }
                        limsPersonInfoMapper.insertPersonDetail(personDetail);

                        //被鉴定人信息
                        String personId = UUID.randomUUID().toString();
                        limsPersonInfo1.setPersonId(personId);
                        limsPersonInfo1.setCaseId(caseInfoDna.getCaseId());
                        limsPersonInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsPersonInfo1.setPersonName(limsPersonInfoList.get(i).getPersonName());
                        limsPersonInfo1.setPersonType(limsPersonInfoList.get(i).getPersonType());
                        limsPersonInfo1.setCreateDatetime(new Date());
                        limsPersonInfo1.setCreatePerson(operateUser.getLoginName());
                        limsPersonInfo1.setDeleteFlag("0");
                        limsPersonInfo1.setPersonDetailId(personDetail.getPersonDetailId());
                        limsPersonInfoMapper.insertPersonInfo(limsPersonInfo1);

                        //判断亲缘关系是否为空
                        LimsPerosnRelation limsPerosnRelation = new LimsPerosnRelation();
                        limsPerosnRelation.setRelationId(UUID.randomUUID().toString());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfo1.getPersonId());
                        //在逃人员亲属关系在逃人员表主键id
                        if (fugitivesInfo != null) {
                            limsPerosnRelation.setTargetPersonId(fugitivesInfo.getId());
                        }
                        limsPerosnRelation.setRelationType(limsPersonInfoList.get(i).getRelationType());
                        limsPerosnRelation.setCreateDatetime(new Date());
                        limsPerosnRelation.setCreatePerson(operateUser.getLoginName());
                        limsPerosnRelation.setDeleteFlag("0");
                        limsPerosnRelationMapper.insertPersonRelation(limsPerosnRelation);
                    }


                    //样本信息
                    List<LimsSampleInfoDna> limsSampleInfoDnaList = limsPersonInfoList.get(i).getSampleInfoDnaList();
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
                                //是否留存
                                sampleInfoDna.setIsRetain(limsSampleInfoDna.getIsRetain());
                                //决绝原因
                                sampleInfoDna.setRefuseReason(limsSampleInfoDna.getRefuseReason());
                                //是否拒绝
                                sampleInfoDna.setSampleStatus(limsSampleInfoDna.getSampleStatus());

                                //是否中心取
                                sampleInfoDna.setCoreTakenStats(limsSampleInfoDna.getCoreTakenStats());
                                //是否为事主样本
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreVictimStats())) {
                                    sampleInfoDna.setCoreVictimStats(limsSampleInfoDna.getCoreVictimStats());
                                }
                                //如果样本拒绝sampleNo不生成
                                if(StringUtils.isNotBlank(limsSampleInfoDna.getSampleStatus()) && ("02").equals(limsSampleInfoDna.getSampleStatus())){
                                    sampleInfoDna.setSampleNo(seqNoGenerateService.getNextPerosnNoVal(Constants.selectPersonTypeSp(limsPersonInfoList.get(i).getPersonType()), Constants.selectOrgNameSp(consignatioInfo.getDelegateOrgCode()),limsConsignmentInfo.getAcceptOrgId()));
                                }
                                //载体
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
                                //是否留存
                                sampleInfoDna2.setIsRetain(limsSampleInfoDna.getIsRetain());
                                //决绝原因
                                sampleInfoDna2.setRefuseReason(limsSampleInfoDna.getRefuseReason());
                                //是否拒绝
                                sampleInfoDna2.setSampleStatus(limsSampleInfoDna.getSampleStatus());

                                //是否中心取
                                sampleInfoDna2.setCoreTakenStats(limsSampleInfoDna.getCoreTakenStats());
                                //是否为事主样本
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreVictimStats())) {
                                    sampleInfoDna2.setCoreVictimStats(limsSampleInfoDna.getCoreVictimStats());
                                }
                                //如果样本拒绝sampleNo不生成
                                if(StringUtils.isNotBlank(limsSampleInfoDna.getSampleStatus()) && ("02").equals(limsSampleInfoDna.getSampleStatus()) && StringUtils.isBlank(sampleInfoDna2.getSampleNo())){
                                    if(consignatioInfo.getDelegateOrgCode().contains("1100240")){
                                        //机场分局编号生成
                                        sampleInfoDna2.setSampleNo(seqNoGenerateService.getNextPerosnNoVal(Constants.selectPersonTypeSp(limsPersonInfoList.get(i).getPersonType()), Constants.selectOrgNameSp(limsConsignmentInfo.getAcceptOrgId()),limsConsignmentInfo.getAcceptOrgId()));
                                    }else{
                                        sampleInfoDna2.setSampleNo(seqNoGenerateService.getNextPerosnNoVal(Constants.selectPersonTypeSp(limsPersonInfoList.get(i).getPersonType()), Constants.selectOrgNameSp(consignatioInfo.getDelegateOrgCode()),limsConsignmentInfo.getAcceptOrgId()));
                                    }
                                }
                                //载体
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
                //查询物证检材编号最大值
                String maxSampleNo = limsSampleInfoDnaMapper.selectMaxSampleNoByCaseId(caseInfoDna.getCaseId());
                int count = 1;
                if(StringUtils.isNotBlank(maxSampleNo)){
                    //String maxSampleNo1 = maxSampleNo.substring(maxSampleNo.indexOf("-")+1);
                    count = (int)Integer.valueOf(maxSampleNo)+1;
                }
                for(int i=0; i<sampleInfoDnaList.size();i++){

                    //物证检材的条数
                    int sampleInfoCount = sampleInfoDnaList.size();


                    //对物证检材照片进行上传
                    if(StringUtils.isNotBlank(sampleInfoDnaList.get(i).getSampleMaterialPicture())){
                        String sampleMaterialPicturePath = ImgUtils.generateImage(sampleInfoDnaList.get(i).getSampleMaterialPicture(), personImg);
                        String sampleMaterialPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpSampleImg, sampleMaterialPicturePath);
                        sampleInfoDnaList.get(i).setSampleMaterialPicturePath(sampleMaterialPicturePathFtp == null ? "" : sampleMaterialPicturePathFtp);
                    }

                    LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
                    if(StringUtils.isBlank(sampleInfoDnaList.get(i).getSampleIdwz())){
                        sampleInfoDna.setSampleId(UUID.randomUUID().toString());
                        sampleInfoDna.setConsignmentId(consignatioInfo.getConsignmentId());
                        sampleInfoDna.setCaseId(caseInfoDna.getCaseId());
                        sampleInfoDna.setSampleNo(sampleInfoDnaList.get(i).getSampleNo());
                        sampleInfoDna.setSampleName(sampleInfoDnaList.get(i).getSampleName());
                        sampleInfoDna.setSampleType(sampleInfoDnaList.get(i).getSampleType());
                        sampleInfoDna.setSampleDesc(sampleInfoDnaList.get(i).getSampleDesc());
                        sampleInfoDna.setSamplePacking(sampleInfoDnaList.get(i).getSamplePacking());
                        sampleInfoDna.setExtractDatetime(sampleInfoDnaList.get(i).getExtractDatetime());
                        sampleInfoDna.setExtractMethod(sampleInfoDnaList.get(i).getExtractMethod());
                        sampleInfoDna.setExtractPerson(operateUser.getLoginName());
                        sampleInfoDna.setSampleFlag("0");
                        //物证id不知道
                        sampleInfoDna.setSamplePurpose(sampleInfoDnaList.get(i).getSamplePurpose());
                        sampleInfoDna.setSampleStatus("01");
                        sampleInfoDna.setInstoredFlag("0");
                        sampleInfoDna.setCreateDatetime(new Date());
                        sampleInfoDna.setCreatePerson(operateUser.getLoginName());
                        sampleInfoDna.setDeleteFlag("0");
                        //FOB
                        sampleInfoDna.setPreMethod1Result(sampleInfoDnaList.get(i).getPreMethod1Result());
                        //PSA
                        sampleInfoDna.setPreMethod2Result(sampleInfoDnaList.get(i).getPreMethod2Result());
                        //联苯胺
                        sampleInfoDna.setPreMethod3Result(sampleInfoDnaList.get(i).getPreMethod3Result());
                        //是否拒绝
                        sampleInfoDna.setSampleStatus(sampleInfoDnaList.get(i).getSampleStatus());

                        //是否中心取
                        sampleInfoDna.setCoreTakenStats(sampleInfoDnaList.get(i).getCoreTakenStats());

                        //是否为事主样本
                        sampleInfoDna.setCoreVictimStats(sampleInfoDnaList.get(i).getCoreVictimStats());


                        //如果样本拒绝sampleNo不生成
                        if(StringUtils.isNotBlank(sampleInfoDnaList.get(i).getSampleStatus()) && ("02").equals(sampleInfoDnaList.get(i).getSampleStatus())){
                            if(count<=9){

                                String sampleNoNum = seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), "0"+count);
                                String orgCodesValue = limsConsignmentInfo.getAcceptOrgId().substring(0,6);
                                String caseNoNum = limsCaseInfo.getCaseNo();
                                if(caseNoNum.indexOf("WZ")!=-1){//判断案件编号是否包含WZ
                                    String curYear = caseNoNum.split("WZ")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
//                                        String year = DateUtils.getCurrentYear();
//                                        String yearCut = year.substring(2,4);
                                    if("19".equals(strYear) && "19" == strYear){
                                        sampleInfoDna.setSampleNo(sampleNoNum);
                                    }else{

                                        //法医中心
                                        if("110230".equals(orgCodesValue)){
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(0, 2, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }else if("110107".equals(orgCodesValue) || "110109".equals(orgCodesValue)){//石景山分局  门头沟分局
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(3, 5, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }else{
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(2, 4, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }
                                    }
                                }else if(caseNoNum.indexOf("-FY")!=-1){
                                    String curYear = caseNoNum.split("-FY")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
                                    StringBuilder sb = new StringBuilder(sampleNoNum);
                                    sb.replace(0, 2, strYear);
                                    sampleInfoDna.setSampleNo(sb.toString());
                                } else{
                                    sampleInfoDna.setSampleNo(sampleNoNum);
                                }
                            }else{
                                String sampleNoNum = seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), ""+count);
                                String orgCodesValue = limsConsignmentInfo.getAcceptOrgId().substring(0,6);
                                String caseNoNum = limsCaseInfo.getCaseNo();
                                if(caseNoNum.indexOf("WZ")!=-1){//判断案件编号是否包含WZ
                                    String curYear = caseNoNum.split("WZ")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
//                                        String year = DateUtils.getCurrentYear();
//                                        String yearCut = year.substring(2,4);
                                    if("19".equals(strYear) && "19" == strYear){
                                        sampleInfoDna.setSampleNo(sampleNoNum);
                                    }else{

                                        //法医中心
                                        if("110230".equals(orgCodesValue)){
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(0, 2, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }else if("110107".equals(orgCodesValue) || "110109".equals(orgCodesValue)){//石景山分局  门头沟分局
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(3, 5, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }else{
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(2, 4, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }
                                    }
                                }else if(caseNoNum.indexOf("-FY")!=-1){
                                    String curYear = caseNoNum.split("-FY")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
                                    StringBuilder sb = new StringBuilder(sampleNoNum);
                                    sb.replace(0, 2, strYear);
                                    sampleInfoDna.setSampleNo(sb.toString());
                                } else{
                                    sampleInfoDna.setSampleNo(sampleNoNum);
                                }
//                                sampleInfoDna.setSampleNo(seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), ""+count));
                            }
                            count++;
                        }
                        sampleInfoDna.setIsRetain(sampleInfoDnaList.get(i).getIsRetain());
                        sampleInfoDna.setRefuseReason(sampleInfoDnaList.get(i).getRefuseReason());
                        sampleInfoDna.setSampleCarrier(sampleInfoDnaList.get(i).getSampleCarrier());
                        //物证检材照片
                        sampleInfoDna.setSampleMaterialPicture(sampleInfoDnaList.get(i).getSampleMaterialPicture());
                        //物证检材照片路径
                        sampleInfoDna.setSampleMaterialPicturePath(sampleInfoDnaList.get(i).getSampleMaterialPicturePath());

                        QueueDetail queueDetail = new QueueDetail();
                        //写入队列子表
                        String queueDetailId = UUID.randomUUID().toString();
                        queueDetail.setId(queueDetailId);
                        if(queueSample2 != null){
                            queueDetail.setQueueId(queueSample2.getId());
                        }
                        queueDetail.setSampleId(sampleInfoDna.getSampleId());
                        queueDetail.setSampleNo(sampleInfoDna.getSampleNo());
                        queueDetail.setCreateDatetime(new Date());
                        queueDetail.setConsignmentId(limsCaseInfo.getCaseId());//关联案件id
                        if(StringUtils.isNotEmpty(sampleInfoDna.getEvidenceNo())){
                            queueDetailMapper.insert(queueDetail);
                        }
                        limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);

                    }else{
                        LimsSampleInfoDna sampleInfoDna3 = limsSampleInfoDnaMapper.selectSampleInfoDnaById(sampleInfoDnaList.get(i).getSampleIdwz());
                        sampleInfoDna3.setConsignmentId(consignatioInfo.getConsignmentId());
                        sampleInfoDna3.setCaseId(caseInfoDna.getCaseId());
                        sampleInfoDna3.setEvidenceNo(sampleInfoDnaList.get(i).getEvidenceNo());
                        sampleInfoDna3.setSampleName(sampleInfoDnaList.get(i).getSampleName());
                        sampleInfoDna3.setSampleType(sampleInfoDnaList.get(i).getSampleType());
                        sampleInfoDna3.setSampleDesc(sampleInfoDnaList.get(i).getSampleDesc());
                        sampleInfoDna3.setSamplePacking(sampleInfoDnaList.get(i).getSamplePacking());
                        sampleInfoDna3.setExtractDatetime(sampleInfoDnaList.get(i).getExtractDatetime());
                        sampleInfoDna3.setExtractMethod(sampleInfoDnaList.get(i).getExtractMethod());
                        sampleInfoDna3.setExtractPerson(operateUser.getLoginName());
                        sampleInfoDna3.setSampleFlag("0");
                        sampleInfoDna3.setSamplePurpose(sampleInfoDnaList.get(i).getSamplePurpose());
                        sampleInfoDna3.setInstoredFlag("0");
                        sampleInfoDna3.setUpdateDatetime(new Date());
                        sampleInfoDna3.setUpdatePerson(operateUser.getLoginName());
                        //FOB
                        sampleInfoDna3.setPreMethod1Result(sampleInfoDnaList.get(i).getPreMethod1Result());
                        //PSA
                        sampleInfoDna3.setPreMethod2Result(sampleInfoDnaList.get(i).getPreMethod2Result());
                        //联苯胺
                        sampleInfoDna3.setPreMethod3Result(sampleInfoDnaList.get(i).getPreMethod3Result());
                        //是否拒绝
                        sampleInfoDna3.setSampleStatus(sampleInfoDnaList.get(i).getSampleStatus());

                        //是否中心取
                        sampleInfoDna3.setCoreTakenStats(sampleInfoDnaList.get(i).getCoreTakenStats());

                        //是否为事主样本
                        sampleInfoDna3.setCoreVictimStats(sampleInfoDnaList.get(i).getCoreVictimStats());

                        //如果样本拒绝sampleNo不生成
                        if(StringUtils.isNotBlank(sampleInfoDnaList.get(i).getSampleStatus()) && ("02").equals(sampleInfoDnaList.get(i).getSampleStatus()) && StringUtils.isBlank(sampleInfoDna3.getSampleNo())){
                            if(count<=9){
                                String sampleNoNum = seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), "0"+count);
                                String orgCodesValue = limsConsignmentInfo.getAcceptOrgId().substring(0,6);
                                String caseNoNum = limsCaseInfo.getCaseNo();
                                if(caseNoNum.indexOf("WZ")!=-1){//判断案件编号是否包含WZ
                                    String curYear = caseNoNum.split("WZ")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
//                                        String year = DateUtils.getCurrentYear();
//                                        String yearCut = year.substring(2,4);
                                    if("19".equals(strYear) && "19" == strYear){
                                        sampleInfoDna3.setSampleNo(sampleNoNum);
                                    }else{

                                        //法医中心
                                        if("110230".equals(orgCodesValue)){
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(0, 2, strYear);
                                            sampleInfoDna3.setSampleNo(sb.toString());
                                        }else if("110107".equals(orgCodesValue) || "110109".equals(orgCodesValue)){//石景山分局  门头沟分局
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(3, 5, strYear);
                                            sampleInfoDna3.setSampleNo(sb.toString());
                                        }else{
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(2, 4, strYear);
                                            sampleInfoDna3.setSampleNo(sb.toString());
                                        }
                                    }
                                }else if(caseNoNum.indexOf("-FY")!=-1){
                                    String curYear = caseNoNum.split("-FY")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
                                    StringBuilder sb = new StringBuilder(sampleNoNum);
                                    sb.replace(0, 2, strYear);
                                    sampleInfoDna3.setSampleNo(sb.toString());
                                } else{
                                    sampleInfoDna3.setSampleNo(sampleNoNum);
                                }

//                                sampleInfoDna3.setSampleNo(seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), "0"+count));
                            }else{
                                String sampleNoNum = seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), ""+count);
                                String orgCodesValue = limsConsignmentInfo.getAcceptOrgId().substring(0,6);
                                String caseNoNum = limsCaseInfo.getCaseNo();
                                if(caseNoNum.indexOf("WZ")!=-1){//判断案件编号是否包含WZ
                                    String curYear = caseNoNum.split("WZ")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
//                                        String year = DateUtils.getCurrentYear();
//                                        String yearCut = year.substring(2,4);
                                    if("19".equals(strYear) && "19" == strYear){
                                        sampleInfoDna3.setSampleNo(sampleNoNum);
                                    }else{

                                        //法医中心
                                        if("110230".equals(orgCodesValue)){
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(0, 2, strYear);
                                            sampleInfoDna3.setSampleNo(sb.toString());
                                        }else if("110107".equals(orgCodesValue) || "110109".equals(orgCodesValue)){//石景山分局  门头沟分局
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(3, 5, strYear);
                                            sampleInfoDna3.setSampleNo(sb.toString());
                                        }else{
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            System.out.println(sb.toString());
                                            sb.replace(2, 4, strYear);//含头不含尾
                                            sampleInfoDna3.setSampleNo(sb.toString());
                                        }
                                    }
                                }else if(caseNoNum.indexOf("-FY")!=-1){
                                    String curYear = caseNoNum.split("-FY")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
                                    StringBuilder sb = new StringBuilder(sampleNoNum);
                                    sb.replace(0, 2, strYear);
                                    sampleInfoDna3.setSampleNo(sb.toString());
                                } else{
                                    sampleInfoDna3.setSampleNo(sampleNoNum);
                                }
//                                sampleInfoDna3.setSampleNo(seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), ""+count));
                            }
                            count++;
                        }
                        sampleInfoDna3.setIsRetain(sampleInfoDnaList.get(i).getIsRetain());
                        sampleInfoDna3.setRefuseReason(sampleInfoDnaList.get(i).getRefuseReason());
                        sampleInfoDna3.setSampleCarrier(sampleInfoDnaList.get(i).getSampleCarrier());
                        //物证检材照片
                        sampleInfoDna3.setSampleMaterialPicture(sampleInfoDnaList.get(i).getSampleMaterialPicture());
                        //物证检材照片路径
                        sampleInfoDna3.setSampleMaterialPicturePath(sampleInfoDnaList.get(i).getSampleMaterialPicturePath());

                        QueueDetail queueDetail = new QueueDetail();
                        //写入队列子表
                        String queueDetailId = UUID.randomUUID().toString();
                        queueDetail.setId(queueDetailId);
                        if(queueSample2 != null){
                            queueDetail.setQueueId(queueSample2.getId());
                        }
                        queueDetail.setSampleId(sampleInfoDna3.getSampleId());
                        queueDetail.setCreateDatetime(new Date());
                        queueDetail.setConsignmentId(limsCaseInfo.getCaseId());//关联案件id
                        if(StringUtils.isNotEmpty(sampleInfoDna3.getEvidenceNo())){
                            queueDetail.setSampleNo(sampleInfoDna3.getEvidenceNo());
                            queueDetailMapper.insert(queueDetail);
                        }
                        limsSampleInfoDnaMapper.updateSampleInfoDna(sampleInfoDna3);
                    }
                }
            }


        } catch (Exception ex) {
            logger.info("案件受理报错："+ex);
            throw ex;
        }

        return true;
    }

    /**
     * 查询案件数量
     */
    @Override
    public List<HashMap<String, String>> selectCountGroupProperty(String acceptOrgId) {
        return limsCaseInfoMapper.selectCountGroupProperty(acceptOrgId);
    }

    @Override
    public List<LimsCaseInfoVo> selectVOCaseInfoList(LimsCaseInfoVo limsCaseInfoVo) {
        return limsCaseInfoMapper.selectVOCaseInfoList(limsCaseInfoVo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateCase(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIdWzs, String sampleIds){
        try {

            if(ListUtils.isNull(sampleIdWzs)){
                String sampleIdWz = sampleIdWzs.substring(1);
                String[] sampleIdWzsArr = sampleIdWz.split(",");
                for(String string:sampleIdWzsArr){
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

            //修改委托人信息
            LimsConsignmentInfo consignatioInfo = delegateDataModel.getConsignatioInfo();
            LimsConsignmentInfo limsConsignmentInfo = limsConsignmentInfoMapper.selectByConsignmentId(consignatioInfo.getConsignmentId());

            String serialNumber = "";

            //修改案件信息
            LimsCaseInfo caseInfoDna = delegateDataModel.getCaseInfoDna();
            LimsCaseInfo limsCaseInfo = null;
            if(StringUtils.isNotBlank(caseInfoDna.getCaseId())){
                limsCaseInfo = limsCaseInfoMapper.selectByCaseId(caseInfoDna.getCaseId());
                limsCaseInfo.setCaseId(caseInfoDna.getCaseId());
                limsCaseInfo.setCaseName(caseInfoDna.getCaseName());
                limsCaseInfo.setCaseLocation(caseInfoDna.getCaseLocation());
                limsCaseInfo.setCaseDatetime(caseInfoDna.getCaseDatetime());
                //limsCaseInfo.setCaseType(caseInfoDna.getCaseType());
                limsCaseInfo.setCaseProperty(caseInfoDna.getCaseProperty());
                limsCaseInfo.setCaseLevel(caseInfoDna.getCaseLevel());
                limsCaseInfo.setCaseBrief(caseInfoDna.getCaseBrief());
                limsCaseInfo.setCaseRemark(caseInfoDna.getCaseRemark());
                limsCaseInfo.setUpdateDatetime(new Date());
                limsCaseInfo.setUpdatePerson(operateUser.getLoginName());
                //是否加急
                limsCaseInfo.setCaseUrgentFlag(caseInfoDna.getCaseUrgentFlag());
                //修改案件状态为已受理
//                limsCaseInfo.setCaseStatus("02");

                //案件编号
                //limsCaseInfo.setCaseNo(seqNoGenerateService.getNextCaseNoVal("-", "WZ",limsConsignmentInfo.getAcceptOrgId()));
                //第一送检人
//                limsCaseInfo.setFirstChecker(operateUser.getPersonalId());
                limsCaseInfoMapper.updateCaseInfoDna(limsCaseInfo);
                if( StringUtils.isNotBlank(limsCaseInfo.getCaseNo()) ) {
                    serialNumber = limsCaseInfo.getCaseNo().split("WZ")[1];
                }
            }


            if(StringUtils.isNotBlank(consignatioInfo.getConsignmentId())){
                if(StringUtils.isNotBlank(consignatioInfo.getAutographPicture())){
                    String autographPicturePath = ImgUtils.generateImage(consignatioInfo.getAutographPicture(), personImg);
                    String autographPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpPersonImg, autographPicturePath);
                    consignatioInfo.setAutographPicturePath(autographPicturePathFtp == null ? "" : autographPicturePathFtp);
                }
                limsConsignmentInfo.setConsignmentId(consignatioInfo.getConsignmentId());
                limsConsignmentInfo.setCaseId(caseInfoDna.getCaseId());
//                limsConsignmentInfo.setDelegateDatetime(new Date());
                //判断丰台公安局委托信息表中委托编号是否为空
                if(StringUtils.isBlank(limsConsignmentInfo.getConsignmentNo())){
                    if ("110106000000".equals(operateUser.getOrgId())){
                        limsConsignmentInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());//修改委托书编号
                    }else {
                        limsConsignmentInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());//修改委托书编号
                    }
                }else {
                    limsConsignmentInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());//修改委托书编号
                }
                //重新鉴定次数
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

                /*
                 * 修改委托信息中的受理信息--liuchang
                 * Date:2020-7-25
                 */
                LimsConsignmentInfo acceptInfo = delegateDataModel.getAcceptInfo();
                if (null!=acceptInfo){
                    limsConsignmentInfo.setAcceptOrgId(acceptInfo.getAcceptOrgId() == null ? " " :acceptInfo.getAcceptOrgId());//受理机构编号
                if (null!=acceptInfo.getAcceptDatetime()){
                    limsConsignmentInfo.setAcceptDatetime(acceptInfo.getAcceptDatetime());//受理时间
                }
                    limsConsignmentInfo.setAcceptorId(acceptInfo.getAcceptorId() == null ? " " :acceptInfo.getAcceptorId());//受理人编号
                }

                if(StringUtils.isNotBlank(consignatioInfo.getAutographPicture())){
                    limsConsignmentInfo.setAutographPicture(consignatioInfo.getAutographPicture());
                    limsConsignmentInfo.setAutographPicturePath(consignatioInfo.getAutographPicturePath());
                }
//                limsConsignmentInfo.setStatus("02");
//                limsConsignmentInfo.setAcceptorId(operateUser.getPersonalId());
                limsConsignmentInfo.setTakePerson(consignatioInfo.getTakePerson());
                limsConsignmentInfoMapper.updateConsignatioInfo(limsConsignmentInfo);
            }

            //添加被鉴定人信息
            List<LimsPersonInfo> limsPersonInfoList = delegateDataModel.getLimsPersonInfoList();
            if(null != limsPersonInfoList && limsPersonInfoList.size() > 0){
                for(int i=0; i< limsPersonInfoList.size(); i++){
                    //判断人员信息是否为空
                    if (limsPersonInfoList.get(i) == null) {
                        continue;
                    }
                    //对人员照片进行上传
                    if(StringUtils.isNotBlank(limsPersonInfoList.get(i).getPersonFrontPicture())){
                        String personFrontPicturePath = ImgUtils.generateImage(limsPersonInfoList.get(i).getPersonFrontPicture(), personImg);
                        String personFrontPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpPersonImg, personFrontPicturePath);
                        limsPersonInfoList.get(i).setPersonFrontPicturePath(personFrontPicturePathFtp == null ? "" : personFrontPicturePathFtp);
                    }

                    //根据在逃人员名称和身份证号查询在逃人员
                    FugitivesInfo fugitivesInfo = null;
                    if (StringUtils.isNotBlank(limsPersonInfoList.get(i).getFugitivesName()) && StringUtils.isNotBlank(limsPersonInfoList.get(i).getFugitivesCard())) {
                        fugitivesInfo = new FugitivesInfo();
                        fugitivesInfo.setPersonName(limsPersonInfoList.get(i).getFugitivesName());
                        fugitivesInfo.setPersonCard(limsPersonInfoList.get(i).getFugitivesCard());
                        fugitivesInfo.setDeleteFlag(Constants.DELETE_FLAG_0);
                        List<FugitivesInfo> fugitivesInfoList = fugitivesInfoMapper.selectList(fugitivesInfo);
                        if (ListUtils.isNotNullAndEmptyList(fugitivesInfoList)) {
                            fugitivesInfo = fugitivesInfoList.get(0);
                        }
                    }
                    PersonDetail personDetail = new PersonDetail();
                    LimsPersonInfo limsPersonInfo1 = new LimsPersonInfo();
                    if(StringUtils.isNotBlank(limsPersonInfoList.get(i).getPersonId())){
                        limsPersonInfo1 = limsPersonInfoMapper.selectPersonInfoById(limsPersonInfoList.get(i).getPersonId());
                        PersonDetail personDetail1 = personDetailMapper.selectByDetailId(limsPersonInfo1.getPersonDetailId());
                        //修改人员详情表
                        personDetail1.setPersonDetailId(limsPersonInfo1.getPersonDetailId());
                        personDetail1.setPersonName(limsPersonInfoList.get(i).getPersonName());
                        personDetail1.setPersonNo(limsPersonInfoList.get(i).getPersonNo());
                        personDetail1.setPersonType(limsPersonInfoList.get(i).getPersonType());
                        personDetail1.setPersonGender(limsPersonInfoList.get(i).getPersonGender());
                        personDetail1.setPerosnAge(limsPersonInfoList.get(i).getPerosnAge());
                        personDetail1.setPersonHeight(limsPersonInfoList.get(i).getPersonHeight());
                        personDetail1.setPersonWeight(limsPersonInfoList.get(i).getPersonWeight());
                        personDetail1.setIdCardFlag(limsPersonInfoList.get(i).getIdCardFlag());
                        personDetail1.setPersonIdCard(limsPersonInfoList.get(i).getPersonIdCard());
                        personDetail1.setNoIdCardDesc(limsPersonInfoList.get(i).getNoIdCardDesc());
                        personDetail1.setPersonCurrentAddress(limsPersonInfoList.get(i).getPersonCurrentAddress());
                        personDetail1.setUpdateDatetime(new Date());
                        personDetail1.setUpdatePerson(operateUser.getLoginName());
                        if(StringUtils.isNotBlank(limsPersonInfoList.get(i).getPersonFrontPicture())){
                            personDetail1.setPersonFrontPicture(limsPersonInfoList.get(i).getPersonFrontPicture());
                            personDetail1.setPersonFrontPicturePath(limsPersonInfoList.get(i).getPersonFrontPicturePath());
                        }
                        personDetailMapper.updatePersonDetail1(personDetail1);

                        //修改人员表
                        LimsPersonInfo limsPersonInfo2 = limsPersonInfoMapper.selectPersonInfoById(limsPersonInfoList.get(i).getPersonId());
                        limsPersonInfo2.setCaseId(caseInfoDna.getCaseId());
                        limsPersonInfo2.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsPersonInfo2.setPersonName(limsPersonInfoList.get(i).getPersonName());
                        limsPersonInfo2.setPersonNo(limsPersonInfoList.get(i).getPersonNo());
                        limsPersonInfo2.setPersonType(limsPersonInfoList.get(i).getPersonType());
                        limsPersonInfo2.setUpdateDatetime(new Date());
                        limsPersonInfo2.setUpdatePerson(operateUser.getLoginName());
                        limsPersonInfo2.setPersonId(limsPersonInfoList.get(i).getPersonId());
                        limsPersonInfoMapper.updatePersonInfo(limsPersonInfo2);


                        //修改人员关系
                        LimsPerosnRelation limsPerosnRelation = limsPerosnRelationMapper.selectBySourcePersonId(limsPersonInfoList.get(i).getPersonId());
                        limsPerosnRelation.setRelationId(limsPerosnRelation.getRelationId());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfoList.get(i).getPersonId());
                        //在逃人员亲属关系在逃人员表主键id
                        if (fugitivesInfo != null) {
                            limsPerosnRelation.setTargetPersonId(fugitivesInfo.getId());
                        }
                        limsPerosnRelation.setRelationType(limsPersonInfoList.get(i).getRelationType());
                        limsPerosnRelation.setUpdateDatetime(new Date());
                        limsPerosnRelation.setUpdatePerson(operateUser.getLoginName());
                        limsPerosnRelationMapper.updatePerosnRelation(limsPerosnRelation);
                    }else{
                        //人员详细表
                        personDetail.setPersonDetailId(UUID.randomUUID().toString());
                        personDetail.setPersonName(limsPersonInfoList.get(i).getPersonName());
                        personDetail.setPersonNo(limsPersonInfoList.get(i).getPersonNo());
                        personDetail.setPersonType(limsPersonInfoList.get(i).getPersonType());
                        personDetail.setPersonGender(limsPersonInfoList.get(i).getPersonGender());
                        personDetail.setPerosnAge(limsPersonInfoList.get(i).getPerosnAge());
                        personDetail.setPersonHeight(limsPersonInfoList.get(i).getPersonHeight());
                        personDetail.setPersonWeight(limsPersonInfoList.get(i).getPersonWeight());
                        personDetail.setIdCardFlag(limsPersonInfoList.get(i).getIdCardFlag());
                        personDetail.setPersonIdCard(limsPersonInfoList.get(i).getPersonIdCard());
                        personDetail.setNoIdCardDesc(limsPersonInfoList.get(i).getNoIdCardDesc());
                        personDetail.setPersonCurrentAddress(limsPersonInfoList.get(i).getPersonCurrentAddress());
                        personDetail.setCreateDatetime(new Date());
                        personDetail.setCreatePerson(operateUser.getLoginName());
                        personDetail.setDeleteFlag("0");
                        if(StringUtils.isNotBlank(limsPersonInfoList.get(i).getPersonFrontPicture())){
                            personDetail.setPersonFrontPicture(limsPersonInfoList.get(i).getPersonFrontPicture());
                            personDetail.setPersonFrontPicturePath(limsPersonInfoList.get(i).getPersonFrontPicturePath());
                        }
                        limsPersonInfoMapper.insertPersonDetail(personDetail);

                        //被鉴定人信息
                        String personId = UUID.randomUUID().toString();
                        limsPersonInfo1.setPersonId(personId);
                        limsPersonInfo1.setCaseId(caseInfoDna.getCaseId());
                        limsPersonInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                        limsPersonInfo1.setPersonName(limsPersonInfoList.get(i).getPersonName());
                        limsPersonInfo1.setPersonNo(limsPersonInfoList.get(i).getPersonNo());
                        limsPersonInfo1.setPersonType(limsPersonInfoList.get(i).getPersonType());
                        limsPersonInfo1.setCreateDatetime(new Date());
                        limsPersonInfo1.setCreatePerson(operateUser.getLoginName());
                        limsPersonInfo1.setDeleteFlag("0");
                        limsPersonInfo1.setPersonDetailId(personDetail.getPersonDetailId());
                        limsPersonInfoMapper.insertPersonInfo(limsPersonInfo1);

                        //判断亲缘关系是否为空
                        LimsPerosnRelation limsPerosnRelation = new LimsPerosnRelation();
                        limsPerosnRelation.setRelationId(UUID.randomUUID().toString());
                        limsPerosnRelation.setSourcePersonId(limsPersonInfo1.getPersonId());
                        //在逃人员亲属关系在逃人员表主键id
                        if (fugitivesInfo != null) {
                            limsPerosnRelation.setTargetPersonId(fugitivesInfo.getId());
                        }
                        limsPerosnRelation.setRelationType(limsPersonInfoList.get(i).getRelationType());
                        limsPerosnRelation.setCreateDatetime(new Date());
                        limsPerosnRelation.setCreatePerson(operateUser.getLoginName());
                        limsPerosnRelation.setDeleteFlag("0");
                        limsPerosnRelationMapper.insertPersonRelation(limsPerosnRelation);
                    }


                    //样本信息
                    List<LimsSampleInfoDna> limsSampleInfoDnaList = limsPersonInfoList.get(i).getSampleInfoDnaList();
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
                                //是否留存
                                sampleInfoDna.setIsRetain(limsSampleInfoDna.getIsRetain());
                                //决绝原因
                                sampleInfoDna.setRefuseReason(limsSampleInfoDna.getRefuseReason());
                                //是否拒绝
                                sampleInfoDna.setSampleStatus(limsSampleInfoDna.getSampleStatus());
                                //如果样本拒绝sampleNo不生成
                                if(StringUtils.isNotBlank(limsSampleInfoDna.getSampleStatus()) && ("02").equals(limsSampleInfoDna.getSampleStatus())){
                                    sampleInfoDna.setSampleNo(seqNoGenerateService.getNextPerosnNoVal(Constants.selectPersonTypeSp(limsPersonInfoList.get(i).getPersonType()), Constants.selectOrgNameSp(consignatioInfo.getDelegateOrgCode()),limsConsignmentInfo.getAcceptOrgId()));
                                }
                                //是否事主样本
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreVictimStats())){
                                    sampleInfoDna.setCoreVictimStats(limsSampleInfoDna.getCoreVictimStats());
                                }else{
                                    sampleInfoDna.setCoreVictimStats(Constants.FLAG_FALSE);
                                }
                                //载体
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
                                //样本信息的 是否为事主状态值
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreVictimStats())){
                                    sampleInfoDna2.setCoreVictimStats(limsSampleInfoDna.getCoreVictimStats());
                                }
                                //样本信息的 是否为中心提取
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreTakenStats())){
                                    sampleInfoDna2.setCoreTakenStats(limsSampleInfoDna.getCoreTakenStats());
                                }
                                sampleInfoDna2.setSamplePurpose(limsSampleInfoDna.getSamplePurpose());
                                sampleInfoDna2.setSampleStatus("01");
                                sampleInfoDna2.setInstoredFlag("0");
                                sampleInfoDna2.setUpdateDatetime(new Date());
                                sampleInfoDna2.setUpdatePerson(operateUser.getLoginName());
                                if(StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())){
                                    sampleInfoDna2.setSampleDnaPicture(limsSampleInfoDna.getSampleDnaPicture());
                                    sampleInfoDna2.setSampleDnaPicturePath(limsSampleInfoDna.getSampleDnaPicturePath());
                                }
                                //是否留存
                                sampleInfoDna2.setIsRetain(limsSampleInfoDna.getIsRetain());
                                //决绝原因
                                sampleInfoDna2.setRefuseReason(limsSampleInfoDna.getRefuseReason());
                                //是否拒绝
                                sampleInfoDna2.setSampleStatus(limsSampleInfoDna.getSampleStatus());

                                //如果样本拒绝sampleNo不生成
                                /*if(StringUtils.isNotBlank(limsSampleInfoDna.getSampleStatus()) && ("02").equals(limsSampleInfoDna.getSampleStatus())){
                                    sampleInfoDna2.setSampleNo(seqNoGenerateService.getNextPerosnNoVal(Constants.selectPersonTypeSp(limsPersonInfoList.get(i).getPersonType()), Constants.selectOrgNameSp(consignatioInfo.getDelegateOrgCode()),limsConsignmentInfo.getAcceptOrgId()));
                                }*/
                                //载体
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
                //查询物证检材编号最大值
                String maxSampleNo = limsSampleInfoDnaMapper.selectMaxSampleNoByCaseId(caseInfoDna.getCaseId());
                int count = 1;
                if(StringUtils.isNotBlank(maxSampleNo)){
                    //String maxSampleNo1 = maxSampleNo.substring(maxSampleNo.indexOf("-")+1);
                    count = (int)Integer.parseInt(maxSampleNo)+1;
                }
                for(int i=0; i<sampleInfoDnaList.size();i++){

                    //对物证检材照片进行上传
                    if(StringUtils.isNotBlank(sampleInfoDnaList.get(i).getSampleMaterialPicture())){
                        String sampleMaterialPicturePath = ImgUtils.generateImage(sampleInfoDnaList.get(i).getSampleMaterialPicture(), personImg);
                        String sampleMaterialPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpSampleImg, sampleMaterialPicturePath);
                        sampleInfoDnaList.get(i).setSampleMaterialPicturePath(sampleMaterialPicturePathFtp == null ? "" : sampleMaterialPicturePathFtp);
                    }

                    LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
                    if(StringUtils.isBlank(sampleInfoDnaList.get(i).getSampleIdwz())){
                        sampleInfoDna.setSampleId(UUID.randomUUID().toString());
                        sampleInfoDna.setConsignmentId(consignatioInfo.getConsignmentId());
                        sampleInfoDna.setCaseId(caseInfoDna.getCaseId());
                        sampleInfoDna.setSampleNo(sampleInfoDnaList.get(i).getSampleNo());
                        sampleInfoDna.setSampleName(sampleInfoDnaList.get(i).getSampleName());
                        sampleInfoDna.setSampleType(sampleInfoDnaList.get(i).getSampleType());
                        sampleInfoDna.setSampleDesc(sampleInfoDnaList.get(i).getSampleDesc());
                        sampleInfoDna.setSamplePacking(sampleInfoDnaList.get(i).getSamplePacking());
                        sampleInfoDna.setExtractDatetime(sampleInfoDnaList.get(i).getExtractDatetime());
                        sampleInfoDna.setExtractMethod(sampleInfoDnaList.get(i).getExtractMethod());
                        sampleInfoDna.setExtractPerson(operateUser.getLoginName());
                        sampleInfoDna.setSampleFlag("0");
                        //物证id不知道
                        sampleInfoDna.setSamplePurpose(sampleInfoDnaList.get(i).getSamplePurpose());
                        sampleInfoDna.setSampleStatus("01");
                        sampleInfoDna.setInstoredFlag("0");
                        sampleInfoDna.setCreateDatetime(new Date());
                        sampleInfoDna.setCreatePerson(operateUser.getLoginName());
                        sampleInfoDna.setDeleteFlag("0");
                        //FOB
                        sampleInfoDna.setPreMethod1Result(sampleInfoDnaList.get(i).getPreMethod1Result());
                        //PSA
                        sampleInfoDna.setPreMethod2Result(sampleInfoDnaList.get(i).getPreMethod2Result());
                        //联苯胺
                        sampleInfoDna.setPreMethod3Result(sampleInfoDnaList.get(i).getPreMethod3Result());
                        //是否拒绝
                        sampleInfoDna.setSampleStatus(sampleInfoDnaList.get(i).getSampleStatus());
                        //如果样本拒绝sampleNo不生成
                        if(StringUtils.isNotBlank(sampleInfoDnaList.get(i).getSampleStatus()) && ("02").equals(sampleInfoDnaList.get(i).getSampleStatus())){
                            if(count<=9){

                                String sampleNoNum = seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), "0"+count);
                                String orgCodesValue = limsConsignmentInfo.getAcceptOrgId().substring(0,6);
                                String caseNoNum = limsCaseInfo.getCaseNo();
                                if(caseNoNum.indexOf("WZ")!=-1){//判断案件编号是否包含WZ
                                    String curYear = caseNoNum.split("WZ")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
//                                        String year = DateUtils.getCurrentYear();
//                                        String yearCut = year.substring(2,4);
                                    if("19".equals(strYear) && "19" == strYear){
                                        sampleInfoDna.setSampleNo(sampleNoNum);
                                    }else{

                                        //法医中心
                                        if("110230".equals(orgCodesValue)){
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(0, 2, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }else if("110107".equals(orgCodesValue) || "110109".equals(orgCodesValue)){//石景山分局  门头沟分局
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(3, 5, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }else{
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(2, 4, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }
                                    }
                                }else if(caseNoNum.indexOf("-FY")!=-1){
                                    String curYear = caseNoNum.split("-FY")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
                                    StringBuilder sb = new StringBuilder(sampleNoNum);
                                    sb.replace(0, 2, strYear);
                                    sampleInfoDna.setSampleNo(sb.toString());
                                } else{
                                    sampleInfoDna.setSampleNo(sampleNoNum);
                                }


//                                sampleInfoDna.setSampleNo(seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), "0"+count));
                            }else{

                                String sampleNoNum = seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), ""+count);
                                String orgCodesValue = limsConsignmentInfo.getAcceptOrgId().substring(0,6);
                                String caseNoNum = limsCaseInfo.getCaseNo();
                                if(caseNoNum.indexOf("WZ")!=-1){//判断案件编号是否包含WZ
                                    String curYear = caseNoNum.split("WZ")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
//                                        String year = DateUtils.getCurrentYear();
//                                        String yearCut = year.substring(2,4);
                                    if("19".equals(strYear) && "19" == strYear){
                                        sampleInfoDna.setSampleNo(sampleNoNum);
                                    }else{

                                        //法医中心
                                        if("110230".equals(orgCodesValue)){
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(0, 2, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }else if("110107".equals(orgCodesValue) || "110109".equals(orgCodesValue)){//石景山分局  门头沟分局
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            sb.replace(3, 5, strYear);
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }else{
                                            StringBuilder sb = new StringBuilder(sampleNoNum);
                                            System.out.println(sb.toString());
                                            sb.replace(2, 4, strYear);//含头不含尾
                                            sampleInfoDna.setSampleNo(sb.toString());
                                        }
                                    }
                                }else if(caseNoNum.indexOf("-FY")!=-1){
                                    String curYear = caseNoNum.split("-FY")[0];
                                    String strYear = curYear.substring(curYear.length() -2,curYear.length());
                                    StringBuilder sb = new StringBuilder(sampleNoNum);
                                    sb.replace(0, 2, strYear);
                                    sampleInfoDna.setSampleNo(sb.toString());
                                } else{
                                    sampleInfoDna.setSampleNo(sampleNoNum);
                                }


//                                sampleInfoDna.setSampleNo(seqNoGenerateService.getNextSampleNoVal("-",limsConsignmentInfo.getAcceptOrgId())+ i);
//                                sampleInfoDna.setSampleNo(seqNoGenerateService.getNextSampleNoVal(serialNumber, "-", limsConsignmentInfo.getAcceptOrgId(), ""+count));
                            }

                            count++;
                        }
                        sampleInfoDna.setIsRetain(sampleInfoDnaList.get(i).getIsRetain());
                        sampleInfoDna.setRefuseReason(sampleInfoDnaList.get(i).getRefuseReason());
                        sampleInfoDna.setSampleCarrier(sampleInfoDnaList.get(i).getSampleCarrier());
                        //物证检材照片
                        sampleInfoDna.setSampleMaterialPicture(sampleInfoDnaList.get(i).getSampleMaterialPicture());
                        //物证检材照片路径
                        sampleInfoDna.setSampleMaterialPicturePath(sampleInfoDnaList.get(i).getSampleMaterialPicturePath());
                        //物证是否为中心提取
                        sampleInfoDna.setCoreTakenStats(sampleInfoDnaList.get(i).getCoreTakenStats());
                        limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
                    }else{
                        LimsSampleInfoDna sampleInfoDna3 = limsSampleInfoDnaMapper.selectSampleInfoDnaById(sampleInfoDnaList.get(i).getSampleIdwz());
                        sampleInfoDna3.setConsignmentId(consignatioInfo.getConsignmentId());
                        sampleInfoDna3.setCaseId(caseInfoDna.getCaseId());
                        sampleInfoDna3.setEvidenceNo(sampleInfoDnaList.get(i).getEvidenceNo());
                        sampleInfoDna3.setSampleName(sampleInfoDnaList.get(i).getSampleName());
                        sampleInfoDna3.setSampleType(sampleInfoDnaList.get(i).getSampleType());
                        sampleInfoDna3.setSampleDesc(sampleInfoDnaList.get(i).getSampleDesc());
                        sampleInfoDna3.setSamplePacking(sampleInfoDnaList.get(i).getSamplePacking());
                        sampleInfoDna3.setExtractDatetime(sampleInfoDnaList.get(i).getExtractDatetime());
                        sampleInfoDna3.setExtractMethod(sampleInfoDnaList.get(i).getExtractMethod());
                        sampleInfoDna3.setExtractPerson(operateUser.getLoginName());
                        sampleInfoDna3.setSamplePurpose(sampleInfoDnaList.get(i).getSamplePurpose());
                        sampleInfoDna3.setUpdateDatetime(new Date());
                        sampleInfoDna3.setUpdatePerson(operateUser.getLoginName());
                        //FOB
                        sampleInfoDna3.setPreMethod1Result(sampleInfoDnaList.get(i).getPreMethod1Result());
                        //PSA
                        sampleInfoDna3.setPreMethod2Result(sampleInfoDnaList.get(i).getPreMethod2Result());
                        //联苯胺
                        sampleInfoDna3.setPreMethod3Result(sampleInfoDnaList.get(i).getPreMethod3Result());
                        //是否拒绝
                        sampleInfoDna3.setSampleStatus(sampleInfoDnaList.get(i).getSampleStatus());

                        sampleInfoDna3.setIsRetain(sampleInfoDnaList.get(i).getIsRetain());
                        sampleInfoDna3.setRefuseReason(sampleInfoDnaList.get(i).getRefuseReason());
                        sampleInfoDna3.setSampleCarrier(sampleInfoDnaList.get(i).getSampleCarrier());
                        //物证检材照片
                        sampleInfoDna3.setSampleMaterialPicture(sampleInfoDnaList.get(i).getSampleMaterialPicture());
                        //物证检材照片路径
                        sampleInfoDna3.setSampleMaterialPicturePath(sampleInfoDnaList.get(i).getSampleMaterialPicturePath());
                        //是否中心提取
                        sampleInfoDna3.setCoreTakenStats(sampleInfoDnaList.get(i).getCoreTakenStats());
                        limsSampleInfoDnaMapper.updateSampleInfoDna(sampleInfoDna3);
                    }
                }
            }


        } catch (Exception ex) {
            logger.info("修改案件报错："+ex);
            throw ex;
        }

        return true;
    }

    /**
     * 查询本案比对
     * @param query
     * @param pageInfo
     * @return
     */
    @Override
    public List<LimsCaseInfoVo> selectCaseCompare(LimsCaseInfoVo query, PageInfo pageInfo) {
        List<LimsCaseInfoVo> caseInfoVOList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            caseInfoVOList = limsCaseInfoMapper.selectCaseComparePaginationList(query);
        } catch(Exception ex) {
            logger.info("查询与补送报错："+ex);
            return null;
        }

        return caseInfoVOList;
    }

    /**
     * 本案比对count
     * @param limsCaseInfoVo
     * @return
     */
    @Override
    public int selectCaseCompareCount(LimsCaseInfoVo limsCaseInfoVo) {
        return limsCaseInfoMapper.selectCaseCompareCount(limsCaseInfoVo);
    }

    @Override
    public LimsCaseInfoVo selectByCaseIdAndConsignmentId(String caseId, String consignmentId) {
        return limsCaseInfoMapper.selectByCaseIdAndConsignmentId(caseId, consignmentId);
    }

    @Override
    public int getExamine(Map<String, Object> map) {
        return limsCaseInfoMapper.getExamine(map);
    }

    /**
     * 查询入国家库数据
     * @param query
     * @param pageInfo
     * @return
     */
    @Override
    public List<LimsCaseInfoVo> selectFindstate(LimsCaseInfoVo query, PageInfo pageInfo) {
        List<LimsCaseInfoVo> queuestateList = null;

        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);
            queuestateList = limsCaseInfoMapper.selectFindstate(query);
        } catch(Exception ex) {
            logger.info("查询入国家库数据报错："+ex);
            System.out.println("查询入国家库数据报错：");
            return null;
        }
        return queuestateList;
    }

    /**
     * 查询入国家库列表数量
     * @param query
     * @return
     */
    @Override
    public int selectStateCount(LimsCaseInfoVo query) {
        return limsCaseInfoMapper.selectStateCount(query);
    }

    @Override
    public List<LimsCaseInfoVo> selectCaseAll() {
        List<LimsCaseInfoVo> limsCaseInfoList = limsCaseInfoMapper.selectCaseAll();
        return limsCaseInfoList;
    }

    @Override
    public List<LimsCaseInfoVo> selectCaseEntrust() {
        List<LimsCaseInfoVo> limsCaseInfoList = limsCaseInfoMapper.selectCaseEntrust();
        return limsCaseInfoList;
    }

    @Override
    public List<LimsCaseInfoVo> selectCaseMatch() {
        List<LimsCaseInfoVo> limsCaseInfoList = limsCaseInfoMapper.selectCaseMatch();
        return limsCaseInfoList;
    }

    @Override
    public int selectFirsInsCount(LimsCaseInfoVo query) {
        return limsCaseInfoMapper.selectFirsInsCount(query);
    }

    @Override
    public int selectInstoredCount(LimsCaseInfoVo query) {
        return limsCaseInfoMapper.selectInstoredCount(query);
    }

    @Override
    public LimsCaseInfo selectByCaseNo(String caseNo) {
        try {
            List<LimsCaseInfo> caseInfoList = limsCaseInfoMapper.selectByCaseNo(caseNo);
            LimsCaseInfo limsCaseInfo = null;
            if (ListUtils.isNotNullAndEmptyList(caseInfoList)) {
                limsCaseInfo = caseInfoList.get(0);
            }
            return limsCaseInfo;
        }catch (Exception e) {
            e.printStackTrace();
            logger.info("获取案件信息错误错误"+e);
            return null;
        }
    }

    @Override
    public List<LimsCaseInfo> selectCaseInfoListData(LimsCaseInfoVo query,int page) {
        List<LimsCaseInfo> queuestateList = null;

        int pageNo;
        int pageSize;
        try {
            pageNo = page;
            pageSize = 1000;
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);
            queuestateList = limsCaseInfoMapper.selectCaseInfoListData(query);
        } catch(Exception ex) {
            logger.info("查询混合库数据报错："+ex);
            System.out.println("查询混合库数据报错");
        }
        return queuestateList;
    }
    
    /**
     * 案件性质统计受理案件数量
     * @return
     */
    @Override
    public List<CasePropertyStatsVo> caseStatsByCaseProperty(Map<String, Object> queryParams) {
        try {
            return limsCaseInfoMapper.caseStatsByCaseProperty(queryParams);
        } catch(Exception ex) {
            logger.error("invoke LimsCaseInfoService.caseStatsByCaseProperty error.", ex);
        }

        return new ArrayList<CasePropertyStatsVo>();
    }
    
    @Override
    public List<CasePropertyStatsVo> selectCaseStatsByOrg(Map<String, Object> queryParams) {
        try {
            return limsCaseInfoMapper.selectCaseStatsByOrg(queryParams);
        } catch(Exception ex) {
            logger.error("invoke LimsCaseInfoService.selectCaseStatsByOrg error.", ex);
        }

        return new ArrayList<CasePropertyStatsVo>();
    }
    
    /**
     * 二级案件性质统计受理案件数量
     * @return
     */
    @Override
    public List<CasePropertyStatsVo> caseStatsByCaseSubProperty(Map<String, Object> queryParams){
        try {
            return limsCaseInfoMapper.caseStatsByCaseSubProperty(queryParams);
        } catch(Exception ex) {
            logger.error("invoke LimsCaseInfoService.caseStatsByCaseProperty error.", ex);
        }

        return new ArrayList<CasePropertyStatsVo>();
    }

    /**
     * 二级案件性质统计受理案件数量
     * @return
     */
    @Override
    public List<CasePropertyStatsVo> caseStatsByCaseSubPropertyAndOrg(Map<String, Object> queryParams) {
        try {
            return limsCaseInfoMapper.caseStatsByCaseSubPropertyAndOrg(queryParams);
        } catch(Exception ex) {
            logger.error("invoke LimsCaseInfoService.caseStatsByCaseSubPropertyAndOrg error.", ex);
        }

        return new ArrayList<CasePropertyStatsVo>();
    }
    
    /**
     * 统计侵财类案件数量
     * @param queryParams
     * @return
     */
    @Override
    public List<CasePropertyStatsVo> selectAssetsCaseCount(Map<String, Object> queryParams) {
        try {
            return limsCaseInfoMapper.selectAssetsCaseCount(queryParams);
        } catch(Exception ex) {
            logger.error("invoke LimsCaseInfoService.selectAssetsCaseCount error.", ex);
        }

        return new ArrayList<CasePropertyStatsVo>();
    }

    /**
     * 统计案件物证检出率
     * @param queryParams
     * @return
     */
    @Override
    public List<CaseEvidenceDetectionRateVo> selectEvidenceDetectionRate(Map<String, Object> queryParams){
        try {
            return limsCaseInfoMapper.selectEvidenceDetectionRate(queryParams);
        } catch(Exception ex) {
            logger.error("invoke LimsCaseInfoService.selectEvidenceDetectionRate error.", ex);
        }

        return new ArrayList<CaseEvidenceDetectionRateVo>();
    }

    /**
     * 根据条件查询有效比中率
     * @param queryParams
     * @return
     */
    public List<CaseEffectiveRatioVo> selectCaseEffectiveRatio(Map<String, Object> queryParams) {
        try {
            return limsCaseInfoMapper.selectCaseEffectiveRatio(queryParams);
        }catch (Exception e) {
            logger.error("invoke LimsCaseInfoService.selectCaseEffectiveRatio error.", e);
        }
        return new ArrayList<CaseEffectiveRatioVo>();
    }
    
    @Override
    public List<IdentifyBookVo> selectIdentifyBookStatistics(Map<String, Object> queryParams) {
        try {
            return limsCaseInfoMapper.selectIdentifyBookStatistics(queryParams);
        } catch(Exception ex) {
            logger.error("invoke LimsCaseInfoService.selectIdentifyBookStatistics error.", ex);
        }

        return new ArrayList<IdentifyBookVo>();
    }

    @Override
    public int selectIssuedCount(Map<String, Object> queryParams) {
        return limsCaseInfoMapper.selectIssuedCount(queryParams);
    }

    @Override
    public int selectTotalIssuedCount(Map<String, Object> queryParams) {
        return limsCaseInfoMapper.selectTotalIssuedCount(queryParams);
    }
    
    /**
     * 通用查询分页list
     * @param query
     * @param pageInfo
     * @return
     */
    @Override
    public List<LimsCaseInfoVo> selectCaseQueryInfoList(LimsCaseInfoVo query, PageInfo pageInfo) {
        List<LimsCaseInfoVo> caseInfoVOList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            caseInfoVOList = limsCaseInfoMapper.selectCaseQueryInfoList(query);
        } catch(Exception ex) {
            logger.info("通用查询报错："+ex);
            return null;
        }

        return caseInfoVOList;
    }
    
    /**
     * 通用查询count
     * @param consignationInfoVo
     * @return
     */
    @Override
    public int selectCaseQueryVOCount(LimsCaseInfoVo consignationInfoVo) {
        return limsCaseInfoMapper.selectCaseQueryVOCount(consignationInfoVo);
    }
    
    @Override
    public int selectCntByAppraisalBook(String loginOrgId) {
        return limsCaseInfoMapper.selectCntByAppraisalBook(loginOrgId);
    }
    
    /**
     * 根据年份获取各个月份的案件数
     */
    @Override
    public Map<String, Object> selectMonthCountByYear(String year, String delegateOrgCode) {
        return limsCaseInfoMapper.selectMonthCountByYear(year, delegateOrgCode);
    }
    
    /**
     * 添加委托补送信息
     * @param delegateDataModel
     * @param operateUser
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, String> submitReplacement(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String evaluationCenterId){
        Map<String, String> result = new HashMap<>();
        try {

            //获取案件信息
            LimsCaseInfo limsCaseInfo = delegateDataModel.getCaseInfoDna();
            //修改案件补送标记为1  0：否  1：是
            limsCaseInfo.setHasAppendFlag("1");
            //数据推送修改crete_person(数据推送标识)
            this.datePushUpdateCreatePerson(limsCaseInfo);
            limsCaseInfoMapper.updateHasAppendFlagByCaseId(limsCaseInfo);

            //添加委托人信息
            LimsConsignmentInfo consignatioInfo = delegateDataModel.getConsignatioInfo();
            String consignmentId = UUID.randomUUID().toString();
            consignatioInfo.setConsignmentId(consignmentId);
            consignatioInfo.setCaseId(limsCaseInfo.getCaseId());
            //  consignatioInfo.setConsignmentNo(seqNoGenerateService.getNextNoVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(consignatioInfo.getDelegateOrgCode()),evaluationCenterId));

            if (consignatioInfo.getConsignmentNo().equals("委托书编号自动生成") ) {
                //获取并生成流水号
                String code = seqNoGenerateService.getNextVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(evaluationCenterId), evaluationCenterId);
                consignatioInfo.setConsignmentNo(code);
                try {
                    seqNoGenerateService.updateTypeVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(evaluationCenterId), evaluationCenterId);
                } catch (Exception ex) {
                    logger.info("更新流水号失败：" + ex);
                }
            } else if(consignatioInfo.getConsignmentNo().equals("")) {
                consignatioInfo.setConsignmentNo(null);
            }else{
                consignatioInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());
            }

            //consignatioInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());

            consignatioInfo.setDelegateDatetime(new Date());
            int reidentifyCount = 0;
            consignatioInfo.setReidentifyCount((short)reidentifyCount);
            consignatioInfo.setCreateDatetime(new Date());
            consignatioInfo.setCreatePerson(operateUser.getLoginName());
            consignatioInfo.setDeleteFlag("0");
            consignatioInfo.setAppendFlag("1");
            consignatioInfo.setAcceptOrgId(evaluationCenterId);
            consignatioInfo.setStatus("01");
            limsConsignmentInfoMapper.insertConsignatioInfo(consignatioInfo);


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
                    limsPersonInfo1.setCaseId(limsCaseInfo.getCaseId());
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


                    if(StringUtils.isNotBlank(limsPersonInfo.getSampleDnaPicture())){
                        //对人员样本照片进行上传
                        String sampleDnaPicturePath = ImgUtils.generateImage(limsPersonInfo.getSampleDnaPicture(), personImg);
                        String sampleDnaPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpSampleImg, sampleDnaPicturePath);
                        limsPersonInfo.setSampleDnaPicturePath(sampleDnaPicturePathFtp == null ? "" : sampleDnaPicturePathFtp);
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
                                sampleInfoDna.setCaseId(limsCaseInfo.getCaseId());
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
                                //判断是否为中心提取
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreTakenStats())){
                                    sampleInfoDna.setCoreTakenStats(limsSampleInfoDna.getCoreTakenStats());
                                }
                                //判断是否为事主样本信息
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreVictimStats())){
                                    sampleInfoDna.setCoreVictimStats(limsSampleInfoDna.getCoreVictimStats());
                                }
                                if(StringUtils.isNotBlank(limsSampleInfoDna.getSampleDnaPicture())){
                                    sampleInfoDna.setSampleDnaPicture(limsSampleInfoDna.getSampleDnaPicture());
                                    sampleInfoDna.setSampleDnaPicturePath(limsSampleInfoDna.getSampleDnaPicturePath());
                                }
                                sampleInfoDna.setSampleCarrier(limsSampleInfoDna.getSampleCarrier());
                                limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
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

                    sampleInfoDna.setSampleId(UUID.randomUUID().toString());
                    sampleInfoDna.setConsignmentId(consignatioInfo.getConsignmentId());
                    sampleInfoDna.setCaseId(limsCaseInfo.getCaseId());
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
                    //判断是否为中心提取
                    if (StringUtils.isNotBlank(sampleInfoDna.getCoreTakenStats())){
                        sampleInfoDna.setCoreTakenStats(sampleInfoDna.getCoreTakenStats());
                    }
                    limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
                }
            }

            //记录该案件是第几次补送
            int replacementNum = 0;
            //查询之前是否补送
            LimsConsignmentInfo limsConsignmentInfo = limsConsignmentInfoMapper.selectMaxReplacementNum(limsCaseInfo.getCaseId());
            if(null != limsConsignmentInfo){
                if(null != limsConsignmentInfo.getReplacementNum()){
                    Integer replacementNum1 = limsConsignmentInfo.getReplacementNum();
                    replacementNum = replacementNum1+1;
                    //更新补送第几次数
                    LimsConsignmentInfo limsConsignmentInfo1 = new LimsConsignmentInfo();
                    limsConsignmentInfo1.setReplacementNum(replacementNum);
                    limsConsignmentInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                    limsConsignmentInfo1.setCaseId(limsCaseInfo.getCaseId());
                    limsConsignmentInfoMapper.updateReplacementNum(limsConsignmentInfo1);
                }
            }else{
                //更新补送第几次数
                LimsConsignmentInfo limsConsignmentInfo1 = new LimsConsignmentInfo();
                limsConsignmentInfo1.setReplacementNum(1);
                limsConsignmentInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                limsConsignmentInfo1.setCaseId(limsCaseInfo.getCaseId());
                limsConsignmentInfoMapper.updateReplacementNum(limsConsignmentInfo1);
            }

            result.put("caseId", limsCaseInfo.getCaseId());
            result.put("consignmentId", consignatioInfo.getConsignmentId());

        } catch (Exception ex) {
            logger.info("添加委托补送信息报错："+ex);
            throw ex;
        }

        return result;
    }
    
    /**
     * 数据推送修改crete_person(数据推送标识)  不需要推送数据到老系统时可以注掉
     *
     * @param limsCaseInfo
     */
    private void datePushUpdateCreatePerson(LimsCaseInfo limsCaseInfo) {
        if (StringUtils.isNotEmpty(limsCaseInfo.getCaseId())) {
            LimsCaseInfo limsCaseInfo1 = this.selectByCaseId(limsCaseInfo.getCaseId());
            if(limsCaseInfo1!=null){
                String createPerson = limsCaseInfo1.getCreatePerson();
                if(StringUtils.isNotEmpty(createPerson)){
                    if(createPerson.indexOf("dbPush")!=-1){
                        //去除dbPush_
                        limsCaseInfo.setCreatePerson(createPerson.replace("dbPush_", ""));
                    }else{
                        limsCaseInfo.setCreatePerson(createPerson);
                    }
                }

            }
        }

    }
    
    /**
     * 添加非案件委托补送信息
     * @param delegateDataModel
     * @param operateUser
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, String> submitNonCaseReplacement(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String evaluationCenterId){
        Map<String, String> result = new HashMap<>();
        try {

            //获取案件信息
            LimsCaseInfo limsCaseInfo = delegateDataModel.getCaseInfoDna();
            //修改案件补送标记为1  0：否  1：是
            limsCaseInfo.setHasAppendFlag("1");
            //数据推送修改crete_person(数据推送标识)
            this.datePushUpdateCreatePerson(limsCaseInfo);
            limsCaseInfoMapper.updateHasAppendFlagByCaseId(limsCaseInfo);

            //添加委托人信息
            LimsConsignmentInfo consignatioInfo = delegateDataModel.getConsignatioInfo();
            String consignmentId = UUID.randomUUID().toString();
            consignatioInfo.setConsignmentId(consignmentId);
            consignatioInfo.setCaseId(limsCaseInfo.getCaseId());
//            consignatioInfo.setConsignmentNo(seqNoGenerateService.getNextNoVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(consignatioInfo.getDelegateOrgCode()),evaluationCenterId));
//            consignatioInfo.setConsignmentNo(consignatioInfo.getConsignmentNo());
            if (consignatioInfo.getConsignmentNo().equals("委托书编号自动生成") ) {
                //获取并生成流水号
                String code = seqNoGenerateService.getNextVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(evaluationCenterId), evaluationCenterId);
                consignatioInfo.setConsignmentNo(code);
                try {
                    seqNoGenerateService.updateTypeVal(DateUtils.getCurrentYear(), Constants.TYPE_CODE_CONSIGNMENT_NO, Constants.selectOrgNameSp(evaluationCenterId), evaluationCenterId);
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
            consignatioInfo.setAppendFlag("1");
            consignatioInfo.setAcceptOrgId(evaluationCenterId);
            consignatioInfo.setStatus("01");
            limsConsignmentInfoMapper.insertConsignatioInfo(consignatioInfo);


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
                    personDetail.setDeleteFlag("0");
                    if(StringUtils.isNotBlank(limsPersonInfo.getPersonFrontPicture())){
                        personDetail.setPersonFrontPicture(limsPersonInfo.getPersonFrontPicture());
                        personDetail.setPersonFrontPicturePath(limsPersonInfo.getPersonFrontPicturePath());
                    }
                    limsPersonInfoMapper.insertPersonDetail(personDetail);

                    //被鉴定人信息
                    String personId = UUID.randomUUID().toString();
                    limsPersonInfo1.setPersonId(personId);
                    limsPersonInfo1.setCaseId(limsCaseInfo.getCaseId());
                    limsPersonInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                    limsPersonInfo1.setPersonName(limsPersonInfo.getPersonName());
                    limsPersonInfo1.setPersonNo(limsPersonInfo.getPersonNo());
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


                    if(StringUtils.isNotBlank(limsPersonInfo.getSampleDnaPicture())){
                        //对人员样本照片进行上传
                        String sampleDnaPicturePath = ImgUtils.generateImage(limsPersonInfo.getSampleDnaPicture(), personImg);
                        String sampleDnaPicturePathFtp = UplodFtpUtils.uploadFtpFile(ftpIp, ftpPort, ftpUser, ftpPassword, ftpSampleImg, sampleDnaPicturePath);
                        limsPersonInfo.setSampleDnaPicturePath(sampleDnaPicturePathFtp == null ? "" : sampleDnaPicturePathFtp);
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
                                sampleInfoDna.setCaseId(limsCaseInfo.getCaseId());
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
                                //是否为中心送检
                                if(StringUtils.isNotBlank(limsSampleInfoDna.getCoreTakenStats())){
                                    sampleInfoDna.setCoreTakenStats(limsSampleInfoDna.getCoreTakenStats());
                                }
                                //是否是事主样本信息
                                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreVictimStats())){
                                    sampleInfoDna.setCoreVictimStats(limsSampleInfoDna.getCoreVictimStats());
                                }
                                limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
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

                    sampleInfoDna.setSampleId(UUID.randomUUID().toString());
                    sampleInfoDna.setConsignmentId(consignatioInfo.getConsignmentId());
                    sampleInfoDna.setCaseId(limsCaseInfo.getCaseId());
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
                    //是否为事主
                    if (StringUtils.isNotBlank(sampleInfoDna.getCoreVictimStats())){
                        sampleInfoDna.setCoreVictimStats(sampleInfoDna.getCoreVictimStats());
                    }
                    //是否为中心送检
                    if (StringUtils.isNotBlank(sampleInfoDna.getCoreTakenStats())){
                        sampleInfoDna.setCoreTakenStats(sampleInfoDna.getCoreTakenStats());
                    }
                    limsSampleInfoDnaMapper.insertSampleInfoDna(sampleInfoDna);
                }
            }

            //记录该案件是第几次补送
            int replacementNum = 0;
            //查询之前是否补送
            LimsConsignmentInfo limsConsignmentInfo = limsConsignmentInfoMapper.selectMaxReplacementNum(limsCaseInfo.getCaseId());
            if(null != limsConsignmentInfo){
                if(null != limsConsignmentInfo.getReplacementNum()){
                    Integer replacementNum1 = limsConsignmentInfo.getReplacementNum();
                    replacementNum = replacementNum1+1;
                    //更新补送第几次数
                    LimsConsignmentInfo limsConsignmentInfo1 = new LimsConsignmentInfo();
                    limsConsignmentInfo1.setReplacementNum(replacementNum);
                    limsConsignmentInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                    limsConsignmentInfo1.setCaseId(limsCaseInfo.getCaseId());
                    limsConsignmentInfoMapper.updateReplacementNum(limsConsignmentInfo1);
                }
            }else{
                //更新补送第几次数
                LimsConsignmentInfo limsConsignmentInfo1 = new LimsConsignmentInfo();
                limsConsignmentInfo1.setReplacementNum(1);
                limsConsignmentInfo1.setConsignmentId(consignatioInfo.getConsignmentId());
                limsConsignmentInfo1.setCaseId(limsCaseInfo.getCaseId());
                limsConsignmentInfoMapper.updateReplacementNum(limsConsignmentInfo1);
            }

            result.put("caseId", limsCaseInfo.getCaseId());
            result.put("consignmentId", consignatioInfo.getConsignmentId());

        } catch (Exception ex) {
            logger.info("添加非案件委托补送信息报错："+ex);
            throw ex;
        }

        return result;
    }

}
