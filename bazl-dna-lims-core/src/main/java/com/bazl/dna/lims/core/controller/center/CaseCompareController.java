package com.bazl.dna.lims.core.controller.center;

import static com.bazl.dna.lims.core.utils.DateUtils.addDays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.core.LimsConfigure;
import com.bazl.dna.lims.core.common.Constants;
import com.bazl.dna.lims.core.controller.BaseController;
import com.bazl.dna.lims.core.model.PageInfo;
import com.bazl.dna.lims.core.model.po.DictItem;
import com.bazl.dna.lims.core.model.po.ErsFile;
import com.bazl.dna.lims.core.model.po.LimsCaseInfo;
import com.bazl.dna.lims.core.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.core.model.po.LimsPersonInfo;
import com.bazl.dna.lims.core.model.po.LimsSampleGene;
import com.bazl.dna.lims.core.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import com.bazl.dna.lims.core.model.po.MatchAuditedGene;
import com.bazl.dna.lims.core.model.po.MatchRelativeLib;
import com.bazl.dna.lims.core.model.po.OrgInfo;
import com.bazl.dna.lims.core.model.po.PersonDetail;
import com.bazl.dna.lims.core.model.po.QueueDetail;
import com.bazl.dna.lims.core.model.po.QueueSample;
import com.bazl.dna.lims.core.model.vo.AmPersonalInfoVo;
import com.bazl.dna.lims.core.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.core.model.vo.LimsSampleInfoDnaVo;
import com.bazl.dna.lims.core.service.AmPersonalInfoService;
import com.bazl.dna.lims.core.service.ErsFileService;
import com.bazl.dna.lims.core.service.LimsCaseInfoService;
import com.bazl.dna.lims.core.service.LimsConsignmentInfoService;
import com.bazl.dna.lims.core.service.LimsPersonInfoService;
import com.bazl.dna.lims.core.service.LimsSampleGeneService;
import com.bazl.dna.lims.core.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.core.service.MatchAuditedGeneService;
import com.bazl.dna.lims.core.service.MatchRelativeLibService;
import com.bazl.dna.lims.core.service.OrgInfoService;
import com.bazl.dna.lims.core.service.PersonDetailService;
import com.bazl.dna.lims.core.service.QueueDetailService;
import com.bazl.dna.lims.core.service.QueueSampleService;
import com.bazl.dna.lims.core.utils.DateUtils;
import com.bazl.dna.lims.core.utils.DictUtil;
import com.bazl.dna.lims.core.utils.ListUtils;
import com.bazl.dna.lims.core.utils.SystemUtil;
import com.bazl.dna.lims.core.utils.TransferHelper;
import com.bazl.dna.lims.core.webservice.domain.SubmitInfoDomain;

/**
 * Created by Sun on 2018/12/20.
 */
@Controller
@RequestMapping("/center")
public class CaseCompareController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    MatchAuditedGeneService matchAuditedGeneService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    MatchRelativeLibService matchRelativeLibService;
    @Autowired
    QueueSampleService queueSampleService;
    @Autowired
    QueueDetailService queueDetailService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    AmPersonalInfoService amPersonalInfoService;
    /*@Autowired
    QueueSampleMapper queueSampleMapper;*/
    @Autowired
    OrgInfoService orgInfoService;
    @Autowired
    PersonDetailService personDetailService;
//    @Autowired
//    WzService wzService;
//    //是否入本地库
//    @Value("${isInStorge}")
//    private boolean isInStorge;
//    //单位code
//    @Value("${labServerNo}")
//    private String labServerNo;
    
    @Autowired
    private RestTemplate restTemplate;
    @Value("${dna_url}")
    private String dnaUrl;
    @Autowired
    ErsFileService ersFileService;
    @Autowired
    LimsConfigure limsConfigure;
    
    /**
     * 本案比对
     *
     * @param request
     * @param query
     * @param pageInfo
     * @return
     */
    @RequestMapping("/caseCompare")
    public ModelAndView caseCompare(HttpServletRequest request, LimsCaseInfoVo query, PageInfo pageInfo) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }

        String userOrgId = operateUser.getOrgId();
//        //将当前用户org_id设置进query
        if (StringUtils.isNotBlank(userOrgId) && userOrgId.contains("110230")) {
            userOrgId = "110230000000";
        }

        try {
            //初始化
            query = initializationData.resetCaseInfoQuery(query);

            //查询委托单位（分局）
//            String orgParentsId = "110000000000";
            List<OrgInfo> orgInfoList = orgInfoService.selectAll();//.selectDelegateByParentsId(orgParentsId);

            String orgId = StringUtils.isBlank(operateUser.getSubOrgId()) ? operateUser.getOrgId() : operateUser.getSubOrgId();
//            query.setDelegateOrgCode(userOrgId);
            query.setAcceptOrgId(userOrgId);
            //获取受理人信息
            List<AmPersonalInfoVo> amPersonalInfoVoList = amPersonalInfoService.queryAmPersonalInfoVoList(operateUser.getOrgId());
            //判断受理人id是否为空
            if (StringUtils.isNotBlank(query.getAcceptorId())) {
                //查询全部
                if (Constants.SELECT_ACCEPTOR_ID.equals(query.getAcceptorId())) {
                    query.setAcceptorId(null);
                } else {
                    query.setAcceptorId(query.getAcceptorId());
                }

            } else {

                if (!CollectionUtils.isEmpty(amPersonalInfoVoList)) {
                    for (AmPersonalInfoVo amPersonalInfoVo : amPersonalInfoVoList) {
                        if (amPersonalInfoVo.getEntity().getPersonalId().equals(operateUser.getPersonalId())) {
                            query.setAcceptorId(operateUser.getPersonalId());
                            break;
                        }
                    }
                }
            }
            //案件状态在检验和已完成
            query.setCaseStatusList(Arrays.asList(Constants.CASE_STATUS_EXAMINING, Constants.CASE_STATUS_FINISHED));
            //不补送
            query.setAppendFlag(Constants.APPEND_FLAG_NON);
            //排序条件设置
            query.setOrderByClause("lci.ACCEPT_DATETIME desc nulls last,lc.case_no desc");
            //查询本案比对列表
            if (null != query.getAcceptEndDatetime()) {//解决时间无时分秒造成的00:00:00查询
                query.setAcceptEndDatetime(addDays(query.getAcceptEndDatetime(), 1));//增加一天
            }
            List<LimsCaseInfoVo> limsCaseCompareList = limsCaseInfoService.selectCaseCompare(query, pageInfo);
            int limsCaseInfoCount = limsCaseInfoService.selectCaseCompareCount(query);
            if (null != query.getAcceptEndDatetime()) {//解决时间无时分秒造成的00:00:00查询
                query.setAcceptEndDatetime(addDays(query.getAcceptEndDatetime(), -1));//减少一天
            }

            if (ListUtils.isNotNullAndEmptyList(limsCaseCompareList)) {
                for (LimsCaseInfoVo bean : limsCaseCompareList) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("caseid", bean.getEntity().getCaseId());
                    map.put("type", "1");
//                bean.setStorage(limsCaseInfoService.getExamine(map));
                    bean.setStorage(0);
                    map.put("type", "0");
//                bean.setNotStorage(limsCaseInfoService.getExamine(map));
                    bean.setNotStorage(0);
                    map.put("type", null);
//                bean.setExamine(limsCaseInfoService.getExamine(map));
                    bean.setExamine(0);

                    ErsFile ersFile = new ErsFile();
                    ersFile.setCaseId(bean.getEntity().getCaseId());
                    ersFile.setFileCategory(Constants.FILE_CATEGORY_11);
                    List<ErsFile> ersFileList = ersFileService.selectList(ersFile);
                    if (ListUtils.isNotNullAndEmptyList(ersFileList)) {
                        ersFile = ersFileList.get(0);
                        bean.setOriginalFileDir(ersFile.getOriginalFileDir());
                        bean.setNewFileDir(ersFile.getNewFileDir());
                        bean.setExistFlag(Constants.DELETE_FLAG_1);
                    }else {
                        bean.setExistFlag(Constants.DELETE_FLAG_0);
                    }
                }
            }
            view = initializationData.initDictList();
            view.addObject("query", query);
            view.addObject("orgInfoList", orgInfoList);
            view.addObject("limsCaseCompareList", limsCaseCompareList);
            view.addObject("amPersonalInfoVoList", amPersonalInfoVoList);
            view.addObject("pageInfo", pageInfo.updatePageInfo(limsCaseInfoCount));
            view.addObject("operateUser", operateUser);
            view.addObject("limsConfigure", limsConfigure);
        } catch (Exception ex) {
            logger.info("查询本案比对列表失败:" + ex);
        }
        view.setViewName("caseCompare/caseCompare");
        return view;
    }

    /**
     * 缓存要入库的检材
     *
     * @param request
     * @param session
     * @param limsSampleInfoDnaList
     * @return
     */
    @RequestMapping(value = "/cacheSampleInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> cacheSampleInfo(HttpServletRequest request, HttpSession session, @RequestBody List<LimsSampleInfoDna> limsSampleInfoDnaList) {
        Map<String, Object> result = new HashMap<>();
        if (ListUtils.isNotNullAndEmptyList(limsSampleInfoDnaList)) {
            session.setAttribute("limsSampleInfoDnaList", limsSampleInfoDnaList);
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return result;
    }

    /**
     * 入库比对
     *
     * @param request
     * @param session
     * @param caseId
     * @return
     */
    @RequestMapping("/warehouseCompare")
    public ModelAndView warehouseCompare(HttpServletRequest request, HttpSession session, String caseId, String consignmentId) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        try {
            //查询案件基本信息 根据案件id查询案件信息
            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
            view.addObject("limsCaseInfo", limsCaseInfo);
            //获取选择要入库的检材列表
            List<LimsSampleInfoDna> limsSampleInfoDnaList = (List<LimsSampleInfoDna>) session.getAttribute("limsSampleInfoDnaList");
            List<LimsSampleInfoDnaVo> sampleInfoDnaVos = new ArrayList<>();
            for (LimsSampleInfoDna sampleInfo : limsSampleInfoDnaList) {
                LimsSampleInfoDnaVo sampleInfoDnaVo = new LimsSampleInfoDnaVo();
                sampleInfo = limsSampleInfoDnaService.selectSampleInfoDnaById(sampleInfo.getSampleId());
                if (sampleInfo == null) {
                    continue;
                }
                if (sampleInfo.getInstoredType() != null &&
                        (sampleInfo.getInstoredType().equals("08") || sampleInfo.getInstoredType().equals("09") || sampleInfo.getInstoredType().equals("10"))) {
                    MatchRelativeLib matchRelativeLib = matchRelativeLibService.selectBySampleaId(sampleInfo.getSampleId());
                    if (matchRelativeLib != null) {
                        sampleInfo.setTargetSampleRole(matchRelativeLib.getSamplebRole());
                        sampleInfo.setSameGroupSample(matchRelativeLib.getSamplebId());
                        sampleInfo.setSameGroupSampleRole(matchRelativeLib.getSampleaRole());
                    }
                }
                sampleInfoDnaVo.setEntity(sampleInfo);

                //如果是嫌疑人默认入库类型为违法犯罪人员
                if (Constants.SAMPLE_FLAG_1.equals(sampleInfo.getSampleFlag()) && StringUtils.isNotBlank(sampleInfo.getLinkId())
                        && StringUtils.isBlank(sampleInfoDnaVo.getEntity().getInstoredType())) {
                    LimsPersonInfo personInfo = limsPersonInfoService.selectPersonInfoById(sampleInfo.getLinkId());
                    if (personInfo != null && Constants.PERSON_TYPE_01.equals(personInfo.getPersonType())) {
                        sampleInfoDnaVo.getEntity().setInstoredType(Constants.INSTORED_TYPE_03);
                    }
                }
                /*
                入库页面不需要显示样本板孔位置     edit by lizhihua  2019/09/06
                List<LimsSampleGene> limsSampleGeneList = limsSampleGeneService.selectAuditedBySampleId(sampleInfo.getSampleId());
                if (ListUtils.isNotNullAndEmptyList(limsSampleGeneList)) {
                    LimsSampleGene sampleGene = limsSampleGeneList.get(0);
                    sampleInfoDnaVo.setBoardPosition(sampleGene == null ? "" : sampleGene.getBoardPosition());
                }
                */
                sampleInfoDnaVos.add(sampleInfoDnaVo);
            }

            //如果样本为物证 入库类型默认选择现场物证
            for (LimsSampleInfoDnaVo sampleInfoDnaVo : sampleInfoDnaVos) {
                if ("0".equals(sampleInfoDnaVo.getEntity().getSampleFlag()) && null == sampleInfoDnaVo.getEntity().getInstoredType()) {
                    sampleInfoDnaVo.getEntity().setInstoredType(Constants.INSTORED_TYPE_01);
                }
            }

            //查询是否有分型
            for (LimsSampleInfoDnaVo sampleInfoDnaVo : sampleInfoDnaVos) {
                List<MatchAuditedGene> matchAuditedGeneList = matchAuditedGeneService.selectBySampleId(sampleInfoDnaVo.getEntity().getSampleId());
                if (matchAuditedGeneList.size() > 0) {
                    for (MatchAuditedGene matchAuditedGene : matchAuditedGeneList) {
                        sampleInfoDnaVo.getEntity().setAuditedGeneId(matchAuditedGene.getAuditedGeneId());
                    }
                }
            }


            view.addObject("limsSampleInfoDnaList", sampleInfoDnaVos);

            List<DictItem> relationList = DictUtil.getDictList(new DictItem(Constants.RELATION_TYPE));
            view.addObject("relationList", relationList);

        } catch (Exception ex) {
            logger.info("查询检材信息失败:" + ex);
        }
        //查询案件类型
        List<DictItem> instoredTypeList = DictUtil.getDictList(new DictItem(Constants.INSTORED_TYPE));
        view.addObject("instoredTypeList", instoredTypeList);
        view.addObject("caseId", caseId);
        view.addObject("consignmentId", consignmentId);
        view.addObject("returnStatus", 1);
        view.setViewName("caseCompare/warehouseCompare");
        return view;
    }

    /**
     * 本案比对列表入库按钮
     * 查询审核通过的基因型检材
     */
    @RequestMapping("/warehouseCompareBtn")
    public ModelAndView warehouseCompareBtn(HttpServletRequest request, HttpSession session, String caseId) {
        ModelAndView view = new ModelAndView();
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        try {
            //查询案件基本信息 根据案件id查询案件信息
            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
            view.addObject("limsCaseInfo", limsCaseInfo);
            LimsConsignmentInfo consignmentInfo = null;
            if (limsCaseInfo != null) {
            		consignmentInfo = limsConsignmentInfoService.selectByCaseNo(limsCaseInfo.getCaseNo());
            }
            view.addObject("consignmentInfo", consignmentInfo);
            
            List<LimsSampleInfoDna> limsSampleInfoDnaLists = limsSampleInfoDnaService.selectSampleDnaByCaseId(caseId);
/*
            //显示入库失败的检材信息，以进行重新入库操作
            List<LimsConsignmentInfo> limsConsignmentInfos = limsConsignmentInfoService.selectByCaseId(caseId);
            if(limsConsignmentInfos.size()>0) {
                for (int k = 0; k < limsConsignmentInfos.size(); k++) {
                    //查询入库操作插入队列检材数据 队列类型为15
                    List<QueueSampleVo> queueSampleList1 = queueSampleMapper.selectByConsignmentId(limsConsignmentInfos.get(k).getConsignmentId());
                    if (queueSampleList1.size() > 0) {
                        for (int i = 0; i < queueSampleList1.size(); i++) {
                            String sampleId = queueSampleList1.get(i).getSampleId();
                            LimsSampleInfoDna limsSampleInfoOne = limsSampleInfoDnaService.selectSampleInfoDnaById(sampleId);
                            limsSampleInfoDnaLists.add(limsSampleInfoOne);
                        }
                    }
                    //查询入库操作插入队列检材数据 队列类型为16
                    List<LimsSampleInfoDna> sampleInfoSampleNO = limsSampleInfoDnaService.selectByConsignmentId(limsConsignmentInfos.get(k).getConsignmentId());
                    if(sampleInfoSampleNO.size() > 0){
                        for(int j = 0;j < sampleInfoSampleNO.size(); j++){
                            List<QueueSampleVo> queueSampleList2 = queueSampleMapper.selectBySampleNo(sampleInfoSampleNO.get(j).getSampleNo());
                            if (queueSampleList2.size() > 0) {
                                for (int h = 0; h < queueSampleList2.size(); h++) {
                                    String sampleNo = queueSampleList2.get(h).getSampleNo();
                                    List<LimsSampleInfoDna> limsSampleInfoTwo = limsSampleInfoDnaService.selectBySampleNo(sampleNo);
                                    if(limsSampleInfoTwo.size() > 0){
                                        for(LimsSampleInfoDna sampleInfos :limsSampleInfoTwo){
                                            limsSampleInfoDnaLists.add(sampleInfos);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            */

            List<LimsSampleInfoDnaVo> sampleInfoDnaVos = new ArrayList<>();
            if (limsSampleInfoDnaLists.size() > 0) {
                for (LimsSampleInfoDna sampleInfo : limsSampleInfoDnaLists) {
                    LimsSampleInfoDnaVo sampleInfoDnaVo = new LimsSampleInfoDnaVo();
                    sampleInfo = limsSampleInfoDnaService.selectSampleInfoDnaById(sampleInfo.getSampleId());
                    if (sampleInfo.getInstoredType() != null &&
                            (sampleInfo.getInstoredType().equals("08") || sampleInfo.getInstoredType().equals("09") || sampleInfo.getInstoredType().equals("10"))) {
                        MatchRelativeLib matchRelativeLib = matchRelativeLibService.selectBySampleaId(sampleInfo.getSampleId());
                        if (matchRelativeLib != null) {
                            sampleInfo.setTargetSampleRole(matchRelativeLib.getSamplebRole());
                            sampleInfo.setSameGroupSample(matchRelativeLib.getSamplebId());
                            sampleInfo.setSameGroupSampleRole(matchRelativeLib.getSampleaRole());
                        }
                    }
                    sampleInfoDnaVo.setEntity(sampleInfo);

                    //如果是嫌疑人默认入库类型为违法犯罪人员
                    if (Constants.SAMPLE_FLAG_1.equals(sampleInfo.getSampleFlag()) && StringUtils.isNotBlank(sampleInfo.getLinkId())
                            && StringUtils.isBlank(sampleInfoDnaVo.getEntity().getInstoredType())) {
                        LimsPersonInfo personInfo = limsPersonInfoService.selectPersonInfoById(sampleInfo.getLinkId());
                        if (personInfo != null && Constants.PERSON_TYPE_01.equals(personInfo.getPersonType())) {
                            sampleInfoDnaVo.getEntity().setInstoredType(Constants.INSTORED_TYPE_03);
                        }
                    }
                    /*
                    入库页面不需要显示板孔位置   edit by  lizhihua 2019/09/06
                    List<LimsSampleGene> limsSampleGeneList = limsSampleGeneService.selectAuditedBySampleId(sampleInfo.getSampleId());
                    if (ListUtils.isNotNullAndEmptyList(limsSampleGeneList)) {
                        LimsSampleGene sampleGene = limsSampleGeneList.get(0);
                        sampleInfoDnaVo.setBoardPosition(sampleGene == null ? "" : sampleGene.getBoardPosition());
                    }
                    */
                    sampleInfoDnaVos.add(sampleInfoDnaVo);
                }
            }

            //如果样本为物证 入库类型默认选择现场物证
            for (LimsSampleInfoDnaVo sampleInfoDnaVo : sampleInfoDnaVos) {
                if ("0".equals(sampleInfoDnaVo.getEntity().getSampleFlag()) && null == sampleInfoDnaVo.getEntity().getInstoredType()) {
                    sampleInfoDnaVo.getEntity().setInstoredType(Constants.INSTORED_TYPE_01);
                }
            }

            //查询是否有分型
            for (LimsSampleInfoDnaVo sampleInfoDnaVo : sampleInfoDnaVos) {
                List<MatchAuditedGene> matchAuditedGeneList = matchAuditedGeneService.selectBySampleId(sampleInfoDnaVo.getEntity().getSampleId());
                if (matchAuditedGeneList.size() > 0) {
                    for (MatchAuditedGene matchAuditedGene : matchAuditedGeneList) {
                        sampleInfoDnaVo.getEntity().setAuditedGeneId(matchAuditedGene.getAuditedGeneId());
                    }
                }
            }

            view.addObject("limsSampleInfoDnaList", sampleInfoDnaVos);

            List<DictItem> relationList = DictUtil.getDictList(new DictItem(Constants.RELATION_TYPE));
            view.addObject("relationList", relationList);

        } catch (Exception ex) {
            logger.info("查询检材信息失败:" + ex);
        }

        //查询案件类型
        List<DictItem> instoredTypeList = DictUtil.getDictList(new DictItem(Constants.INSTORED_TYPE));
        view.addObject("instoredTypeList", instoredTypeList);

        view.setViewName("caseCompare/warehouseEditView");
        return view;
    }

    /**
     * 入国家库
     * 重新入库比对
     *
     * @param request
     * @param session
     * @param caseId
     * @return
     */
    @RequestMapping("/warehouseCompareAgain")
    public ModelAndView warehouseCompareAgain(HttpServletRequest request, HttpSession session, String caseId, String consignmentId) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        try {
            //查询案件基本信息 根据案件id查询案件信息
            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
            view.addObject("limsCaseInfo", limsCaseInfo);

            //获取检材列表
            List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaService.selectSampleDnaByCaseId(caseId);

            /*List<LimsConsignmentInfo> limsConsignmentInfos = limsConsignmentInfoService.selectByCaseId(caseId);
            if(limsConsignmentInfos.size()>0) {
                for (int k = 0; k < limsConsignmentInfos.size(); k++) {
                    //查询入库操作插入队列检材数据 队列类型为15
                    List<QueueSampleVo> queueSampleList1 = queueSampleMapper.selectByConsignmentId(limsConsignmentInfos.get(k).getConsignmentId());
                    if (queueSampleList1.size() > 0) {
                        for (int i = 0; i < queueSampleList1.size(); i++) {
                            String sampleId = queueSampleList1.get(i).getSampleId();
                            LimsSampleInfoDna limsSampleInfoOne = limsSampleInfoDnaService.selectSampleInfoDnaById(sampleId);
                            limsSampleInfoDnaList.add(limsSampleInfoOne);
                        }
                    }
                    //查询入库操作插入队列检材数据 队列类型为16
                    List<LimsSampleInfoDna> sampleInfoSampleNO = limsSampleInfoDnaService.selectByConsignmentId(limsConsignmentInfos.get(k).getConsignmentId());
                    if(sampleInfoSampleNO.size() > 0){
                        for(int j = 0;j < sampleInfoSampleNO.size(); j++){
                            List<QueueSampleVo> queueSampleList2 = queueSampleMapper.selectBySampleNo(sampleInfoSampleNO.get(j).getSampleNo());
                            if (queueSampleList2.size() > 0) {
                                for (int h = 0; h < queueSampleList2.size(); h++) {
                                    String sampleNo = queueSampleList2.get(h).getSampleNo();
                                    List<LimsSampleInfoDna> limsSampleInfoTwo = limsSampleInfoDnaService.selectBySampleNo(sampleNo);
                                    if(limsSampleInfoTwo.size() > 0){
                                        for(LimsSampleInfoDna sampleInfos :limsSampleInfoTwo){
                                            limsSampleInfoDnaList.add(sampleInfos);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }*/
            List<LimsSampleInfoDnaVo> sampleInfoDnaVos = new ArrayList<>();
            for (LimsSampleInfoDna sampleInfo : limsSampleInfoDnaList) {
                LimsSampleInfoDnaVo sampleInfoDnaVo = new LimsSampleInfoDnaVo();
                if (sampleInfo != null) {
                    sampleInfo = limsSampleInfoDnaService.selectSampleInfoDnaById(sampleInfo.getSampleId());
                    if (sampleInfo.getInstoredType() != null &&
                            (sampleInfo.getInstoredType().equals("08") || sampleInfo.getInstoredType().equals("09") || sampleInfo.getInstoredType().equals("10"))) {
                        MatchRelativeLib matchRelativeLib = matchRelativeLibService.selectBySampleaId(sampleInfo.getSampleId());
                        if (matchRelativeLib != null) {
                            sampleInfo.setTargetSampleRole(matchRelativeLib.getSamplebRole());
                            sampleInfo.setSameGroupSample(matchRelativeLib.getSamplebId());
                            sampleInfo.setSameGroupSampleRole(matchRelativeLib.getSampleaRole());
                        }
                    }
                    sampleInfoDnaVo.setEntity(sampleInfo);
                    List<LimsSampleGene> limsSampleGeneList = limsSampleGeneService.selectAuditedBySampleId(sampleInfo.getSampleId());
                    if (ListUtils.isNotNullAndEmptyList(limsSampleGeneList)) {
                        LimsSampleGene sampleGene = limsSampleGeneList.get(0);
                        sampleInfoDnaVo.setBoardPosition(sampleGene == null ? "" : sampleGene.getBoardPosition());
                    }
                    sampleInfoDnaVos.add(sampleInfoDnaVo);
                }
            }
            view.addObject("limsSampleInfoDnaList", sampleInfoDnaVos);

            List<DictItem> relationList = DictUtil.getDictList(new DictItem(Constants.RELATION_TYPE));
            view.addObject("relationList", relationList);

        } catch (Exception ex) {
            logger.info("查询检材信息失败:" + ex);
        }
        //查询案件类型
        List<DictItem> instoredTypeList = DictUtil.getDictList(new DictItem(Constants.INSTORED_TYPE));
        view.addObject("returnStatus", 3);
        view.addObject("instoredTypeList", instoredTypeList);
        view.setViewName("caseCompare/warehouseCompare");
        return view;
    }

    @ResponseBody
	@PostMapping(value = "/submitAndCompare")
	public Map<String, Object> submitAndCompare(@RequestBody List<LimsSampleInfoDna> sampleInfoList, 
			String caseId, String consignmentId) {
		//获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        
		// 案件基本信息
        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
        LimsConsignmentInfo consignmentInfo = null;
        if (limsCaseInfo != null) {
        		// 委托信息
        		consignmentInfo = limsConsignmentInfoService.selectByCaseNo(limsCaseInfo.getCaseNo());
        }
        // 人员物证信息
        JSONArray samplePersonList = new JSONArray();
        for (LimsSampleInfoDna sampleInfo : sampleInfoList) {
        		JSONObject json = new JSONObject();
        		
        		// 物证信息
        		LimsSampleInfoDna sampleInfoDna = limsSampleInfoDnaService.selectBySampleId(sampleInfo.getSampleId());
        		json.put("dnaSampleInfo", sampleInfoDna);
        		// 基因型信息
        		List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListBySampleId(sampleInfo.getSampleId());
        		
        		if (sampleInfo.isCheckStr()) {
	        		// str
	        		json.put("strGeneInfoList", sampleGeneList);
        		}
        		if (sampleInfo.isCheckYstr()) {
        			// ystr 
        			json.put("ystrGeneInfoList", sampleGeneList);
        		}
        		// 人员信息
        		LimsPersonInfo limsPersonInfo = null;
        		PersonDetail personDetail = null;
        		if (StringUtils.trimToNull(sampleInfoDna.getLinkId()) != null) {
        			limsPersonInfo = limsPersonInfoService.selectPersonInfoById(sampleInfoDna.getLinkId());
        			personDetail = personDetailService.selectByDetailId(limsPersonInfo.getPersonDetailId());
        		}
        		json.put("casePersonInfo", limsPersonInfo);
        		json.put("casePersonDetail", personDetail);
        		
        		
        		samplePersonList.add(json);
        }
        
        // 上报到DNA库
        JSONObject json = new JSONObject();
        json.put("caseInfo", limsCaseInfo);
        json.put("consignmentInfo", consignmentInfo);
        json.put("samplePersonList", samplePersonList);
        json.put("orgId", operateUser.getOrgId());
        logger.info("info:{}", json);
        String url = dnaUrl + "/database/converter/lims/save";
		HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(json);
		ResponseData responseData = restTemplate.postForObject(url, entity, ResponseData.class);
		
		Map<String, Object> result = new HashMap<String, Object>();
		if (responseData.getCode() == 1) {
			result.put("success", true);
			// TODO 更新上报状态
		} else { 
			result.put("success", false);
		}
		return result;
	}

    /**
     * 插入本地库
     * @param sampleInfoDnaList
     */
    private void insertLocalDatabase(List<LimsSampleInfoDna> sampleInfoDnaList) {
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();

        if (ListUtils.isNotNullAndEmptyList(sampleInfoDnaList)) {
            LimsCaseInfo caseInfo = null;
            LimsConsignmentInfo consignmentInfo = null;
            for (LimsSampleInfoDna lsid : sampleInfoDnaList) {
                LimsSampleInfoDna sampleInfoDna = limsSampleInfoDnaService.selectSampleInfoDnaById(lsid.getSampleId());
                if (sampleInfoDna == null) {
                    continue;
                }

                sampleInfoDna.setTargetSampleRole(lsid.getTargetSampleRole());
                sampleInfoDna.setSameGroupSample(lsid.getSameGroupSample());
                sampleInfoDna.setSameGroupSampleRole(lsid.getSameGroupSampleRole());

                caseInfo = limsCaseInfoService.selectByCaseId(sampleInfoDna.getCaseId());
                consignmentInfo = limsConsignmentInfoService.selectByConsignmentId(sampleInfoDna.getConsignmentId());

                SubmitInfoDomain submitInfoDomain = new SubmitInfoDomain();
                //案件信息
                if (caseInfo != null && consignmentInfo != null) {
                    submitInfoDomain.setCaseInfoId(caseInfo.getCaseId());
                    submitInfoDomain.setCaseName(caseInfo.getCaseName());
                    submitInfoDomain.setCaseSn(caseInfo.getCaseNo());
                    submitInfoDomain.setCaseInfoOrg(consignmentInfo.getDelegateOrgName());
                    String delegateOrgCode = null;
                    if (StringUtils.isNotBlank(consignmentInfo.getDelegateOrgCode()) && consignmentInfo.getDelegateOrgCode().length() > 6) {
                        delegateOrgCode = consignmentInfo.getDelegateOrgCode().substring(0, 6);
                    }
//                    else {
//                        delegateOrgCode = labServerNo;
//                    }
                    submitInfoDomain.setDelegateOrgCode(delegateOrgCode);
                    String delegator = "";
                    if (StringUtils.isNotBlank(consignmentInfo.getDelegator1Name())) {
                        delegator += consignmentInfo.getDelegator1Name() + "、";
                    }
                    if (StringUtils.isNotBlank(consignmentInfo.getDelegator2Name())) {
                        delegator += consignmentInfo.getDelegator2Name() + "、";
                    }
                    if (StringUtils.isNotBlank(delegator)) {
                        delegator = delegator.substring(0, delegator.length() - 1);
                    }
                    submitInfoDomain.setDelegator(delegator);
                    //案件性质
                    submitInfoDomain.setCaseType(TransferHelper.convertToCaseProperty(caseInfo.getCaseProperty()));
                    //案件类型
                    submitInfoDomain.setCaseHandleType(TransferHelper.convertToCaseType(caseInfo.getCaseType()));
                    submitInfoDomain.setCaseBaseInfoId(consignmentInfo.getConsignmentId());
                    submitInfoDomain.setCaseXkSn(caseInfo.getCaseXkNo());
                    submitInfoDomain.setCaseAddress(consignmentInfo.getAreaOrgCode());
                    submitInfoDomain.setCaseAddressFullName(caseInfo.getCaseLocation());
                    submitInfoDomain.setAssociateSystemName(consignmentInfo.getDelegateOrgName());
                    submitInfoDomain.setAssociateSystemSn(caseInfo.getCaseXkNo());
                    submitInfoDomain.setDelegateSn(caseInfo.getConsignationXkNo());
                    submitInfoDomain.setXk_caseProperty(null);
                    submitInfoDomain.setXk_caseType(null);
                    submitInfoDomain.setCaseTimeStr(DateUtils.dateToString(caseInfo.getCaseDatetime(), DateUtils.HOUR_MINUTES_SECOND));
                }else {
                    submitInfoDomain.setCaseInfoId("");
                    submitInfoDomain.setCaseName("");
                    submitInfoDomain.setCaseInfoOrg("");
                    submitInfoDomain.setDelegator("");
                }
                //基因型信息
                List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectAuditedBySampleId(sampleInfoDna.getSampleId());
                LimsSampleGene sampleGene = null;
                if (ListUtils.isNotNullAndEmptyList(sampleGeneList)) {
                    sampleGene = sampleGeneList.get(0);
                    if (sampleGene != null) {
                        //需要转换基因型
                        submitInfoDomain.setMarkerElements(TransferHelper.convertToGeneInfo(sampleGene.getGeneInfo()));
                        submitInfoDomain.setOperateDate(sampleGene.getUpdateDatetime());
                        submitInfoDomain.setPanelName(sampleGene.getPanelName());
                        submitInfoDomain.setPanelId(sampleGene.getPanelId());
                        submitInfoDomain.setSampleId(sampleInfoDna.getSampleNo());
                        submitInfoDomain.setSampleName(sampleInfoDna.getSampleName());
                        submitInfoDomain.setSampleType(TransferHelper.convertToInstoredType(sampleInfoDna.getInstoredType()));
                        submitInfoDomain.setOperator(caseInfo.getAcceptorName());
                        submitInfoDomain.setOperatorOrg(orgInfoService.selectLabNameById(consignmentInfo.getAcceptOrgId()));
                        submitInfoDomain.setSampleBoardBarcode(sampleGene.getBoardNo());
                        submitInfoDomain.setSampleLevel(Constants.SAMPLE_LEVEL_2);
                        submitInfoDomain.setXkNumber(sampleInfoDna.getEvidenceNo());
                        submitInfoDomain.setXkDelegateSN(caseInfo.getConsignationXkNo());
                        submitInfoDomain.setXkAddress(SystemUtil.getSystemConfig().getProperty("xkAddress"));

                        //todo
                        submitInfoDomain.setSampleNo(sampleInfoDna.getEvidenceNo());
                    }
                }
                /*String url = SystemUtil.getSystemConfig().getProperty("submitInfoWebService");
                SubmitInfoWebService submitInfoWebService = new SubmitInfoWebService(url);*/
                if (submitInfoDomain != null) {
                    //提交案件信息
//                    wzService.submitWsCase(submitInfoDomain);
                    //提交样本信息
//                    wzService.submitWsSampleInfo(submitInfoDomain);
                }
                /*url = SystemUtil.getSystemConfig().getProperty("libMatchRecordWebService");
                LibMatchRecordWebService libMatchRecordWebService = new LibMatchRecordWebService(url);*/
                //亲缘入库
                if (Constants.INSTORED_TYPE_08.equals(sampleInfoDna.getInstoredType()) || Constants.INSTORED_TYPE_09.equals(sampleInfoDna.getInstoredType())
                        || Constants.INSTORED_TYPE_10.equals(sampleInfoDna.getInstoredType())) {
                    //此入库不存在单亲入库
                    SubmitInfoDomain groupSample = new SubmitInfoDomain();
                    LimsSampleInfoDna groupSampleInfo = limsSampleInfoDnaService.selectBySampleId(sampleInfoDna.getSameGroupSample());
                        if (groupSampleInfo != null) {

                            //同组样本的案件信息
                            LimsCaseInfo groupCaseInfo = null;
                            LimsConsignmentInfo groupConsignmentInfo = null;
                            groupCaseInfo = limsCaseInfoService.selectByCaseId(groupSampleInfo.getCaseId());
                            groupConsignmentInfo = limsConsignmentInfoService.selectByConsignmentId(groupSampleInfo.getConsignmentId());
                            if (groupCaseInfo != null) {
                                groupSample.setCaseInfoId(groupCaseInfo.getCaseId());
                                groupSample.setCaseName(groupCaseInfo.getCaseName());
                                groupSample.setCaseSn(groupCaseInfo.getCaseNo());
                                groupSample.setCaseInfoOrg(groupConsignmentInfo.getDelegateOrgName());
                                groupSample.setDelegateOrgCode(groupConsignmentInfo.getDelegateOrgCode());
                                String delegator = "";
                                if (StringUtils.isNotBlank(groupConsignmentInfo.getDelegator1Name())) {
                                    delegator += groupConsignmentInfo.getDelegator1Name() + "、";
                                }
                                if (StringUtils.isNotBlank(groupConsignmentInfo.getDelegator2Name())) {
                                    delegator += groupConsignmentInfo.getDelegator2Name() + "、";
                                }
                                if (StringUtils.isNotBlank(delegator)) {
                                    delegator = delegator.substring(0, delegator.length() - 1);
                                }
                                groupSample.setDelegator(delegator);
                                //案件性质
                                groupSample.setCaseType(TransferHelper.convertToCaseProperty(groupCaseInfo.getCaseProperty()));
                                //案件类型
                                groupSample.setCaseHandleType(TransferHelper.convertToCaseType(groupCaseInfo.getCaseType()));
                                groupSample.setCaseBaseInfoId(groupConsignmentInfo.getConsignmentId());
                                groupSample.setCaseXkSn(groupCaseInfo.getCaseXkNo());
                                groupSample.setCaseAddress(groupConsignmentInfo.getAreaOrgCode());
                                groupSample.setCaseAddressFullName(groupCaseInfo.getCaseLocation());
                                groupSample.setAssociateSystemName(groupConsignmentInfo.getDelegateOrgName());
                                groupSample.setAssociateSystemSn(groupCaseInfo.getCaseXkNo());
                                groupSample.setDelegateSn(groupCaseInfo.getConsignationXkNo());
                                groupSample.setXk_caseType(null);
                                groupSample.setXk_caseProperty(null);
                                groupSample.setCaseTimeStr(DateUtils.dateToString(groupCaseInfo.getCaseDatetime(), DateUtils.HOUR_MINUTES_SECOND));
                            }else {
                                groupSample.setCaseInfoId("");
                                groupSample.setCaseName("");
                                groupSample.setDelegator("");
                                groupSample.setCaseInfoOrg("");
                            }

                            //同组样本信息
                            groupSample.setSampleId(groupSampleInfo.getSampleNo());
                            groupSample.setSampleName(groupSampleInfo.getSampleName());
                            groupSample.setSampleType(TransferHelper.convertToInstoredType(groupSampleInfo.getInstoredType()));
                            groupSample.setOperator(groupCaseInfo.getAcceptorName());
                            groupSample.setOperatorOrg(orgInfoService.selectLabNameById(consignmentInfo.getAcceptOrgId()));
                            groupSample.setSampleLevel(Constants.SAMPLE_LEVEL_2);
                            groupSample.setXkNumber(groupSampleInfo.getEvidenceNo());
                            groupSample.setXkDelegateSN(groupCaseInfo.getConsignationXkNo());
                            groupSample.setXkAddress(SystemUtil.getSystemConfig().getProperty("xkAddress"));

                            //todo
                            groupSample.setSampleNo(groupSampleInfo.getEvidenceNo());
                        }
                    //同组样本基因型信息
                    List<LimsSampleGene> groupSampleGeneList = limsSampleGeneService.selectAuditedBySampleId(sampleInfoDna.getSameGroupSample());
                    LimsSampleGene groupSampleGene = null;
                    if (ListUtils.isNotNullAndEmptyList(groupSampleGeneList)) {
                        groupSampleGene = groupSampleGeneList.get(0);
                        if (groupSampleGene != null) {
                            //需要转换基因型
                            groupSample.setMarkerElements(TransferHelper.convertToGeneInfo(groupSampleGene.getGeneInfo()));
                            groupSample.setSampleBoardBarcode(groupSampleGene.getBoardNo());
                            groupSample.setOperateDate(groupSampleGene.getUpdateDatetime());
                            groupSample.setPanelName(groupSampleGene.getPanelName());
                            groupSample.setPanelId(groupSampleGene.getPanelId());
                        }
                    }

                    if (groupSample != null) {
//                        wzService.submitWsSampleInfo(groupSample);
                    }
                    Map<String, Object> result = null;
//                    wzService.parentageSubmitAndMatch(
//                                    groupSample,
//                                    submitInfoDomain,
//                                    TransferHelper.convertToInstoredType(sampleInfoDna.getInstoredType()),
//                                    Integer.parseInt(TransferHelper.convertToMatchType(sampleInfoDna.getSameGroupSampleRole())),
//                                    0);

                    /*if(submit2AllLimsFlag != null && submit2AllLimsFlag.equals("true")){
                        queueSampleService.addQueueSample(QueueSample.TO_ALL_LIMS, submitInfoDomain.getSampleId());
                    }

                    //查询样本原有比中信息
                    isMatchFormer = submitToLimsWebService.chenckIsMatchFormer(submitInfoDomain.getSampleId());*/
                    if (Integer.valueOf(1).equals(result.get("resultKey")) || result.get("resultKey").equals(Integer.valueOf(2))) {
                        String resultKey2 = "";
                        String isHaveDNA = SystemUtil.getSystemConfig().getProperty("isHaveDNA");

                        if ("true".equals(isHaveDNA)) {
                            /*String url = SystemUtil.getSystemConfig().getProperty("submitInfoWebService");
                            SubmitInfoWebService submitInfoWebService = new SubmitInfoWebService(url);*/
                            /*url = SystemUtil.getSystemConfig().getProperty("libMatchRecordWebService");
                            LibMatchRecordWebService libMatchRecordWebService = new LibMatchRecordWebService(url);*/
//                            wzService.submitWsCase(submitInfoDomain);
//                            wzService.submitWsSampleInfo(submitInfoDomain);
//                            wzService.submitWsSampleInfo(groupSample);
                            Map<String, Object> result2 = null;//wzService.parentageSubmitAndMatch(groupSample,
//                                            submitInfoDomain,
//                                            TransferHelper.convertToInstoredType(sampleInfoDna.getInstoredType()),
//                                            Integer.parseInt(TransferHelper.convertToMatchType(sampleInfoDna.getSameGroupSampleRole())),
//                                            0);
                            resultKey2 = result2.get("resultKey").toString();

                            if ((resultKey2.equals("1")) || (resultKey2.equals("2"))) {
                                logger.info(result.get("resultValue").toString() + " 入库成功! +++\n");
                            } else {
                                LimsSampleInfoDna sample = new LimsSampleInfoDna();
                                sample.setSampleId(sampleInfoDna.getSampleId());
                                sample.setInstoredFlag(Constants.INSTORED_FAIL);
                                sample.setInstoredType(sampleInfoDna.getInstoredType());
                                sample.setInstoredDatetime(new Date());
                                if (operateUser != null) {
                                    sample.setInstoredPerson(operateUser.getLoginName());
                                }
                                limsSampleInfoDnaService.updateInstoredFlag(sample);
                                logger.info("入库本地库失败！");
                            }
                        }
                    }else {
                        logger.info(result.get("resultValue").toString() + " 入库失败! \n");
                    }
                }else {
                    //非亲属入库
                    Map<String, Object> result = null;
//                    wzService.submitAndMatch(submitInfoDomain,
//                            TransferHelper.convertToInstoredType(sampleInfoDna.getInstoredType()), 0);

                    /*if(submit2AllLimsFlag != null && submit2AllLimsFlag.equals("true")){
                        queueSampleService.addQueueSample(QueueSample.TO_ALL_LIMS, submitInfoDomain.getSampleId());
                    }

                    //查询样本原有比中信息
                    isMatchFormer = submitToLimsWebService.chenckIsMatchFormer(submitInfoDomain.getSampleId());*/
                    //1:入库成功 //2:入库成功并且比中
                    if (Integer.valueOf(1).equals(result.get("resultKey")) || Integer.valueOf(2).equals(result.get("resultKey"))) {
                        String resultKey = "";
                        String isHaveDNA = SystemUtil.getSystemConfig().getProperty("isHaveDNA");

                        if ("true".equals(isHaveDNA)) {
                            /*String url = SystemUtil.getSystemConfig().getProperty("submitInfoWebService");
                            SubmitInfoWebService submitInfoWebService = new SubmitInfoWebService(url);
                            url = SystemUtil.getSystemConfig().getProperty("libMatchRecordWebService");
                            LibMatchRecordWebService libMatchRecordWebService = new LibMatchRecordWebService(url);*/
//                            wzService.submitWsCase(submitInfoDomain);
//                            wzService.submitWsSampleInfo(submitInfoDomain);
                            Map<String, Object> result1 = null;
//                            wzService.submitAndMatch(submitInfoDomain, TransferHelper.convertToInstoredType(sampleInfoDna.getInstoredType()), 0);
                            resultKey = result1.get("resultKey").toString();

                            if ((resultKey.equals("1")) || (resultKey.equals("2"))) {
                                logger.info(result.get("resultValue").toString() + " 入库成功! \n");

                            } else {
                                LimsSampleInfoDna sample = new LimsSampleInfoDna();
                                sample.setSampleId(sampleInfoDna.getSampleId());
                                sample.setInstoredType(sampleInfoDna.getInstoredType());
                                sample.setInstoredFlag(Constants.INSTORED_FAIL);
                                sample.setInstoredDatetime(new Date());
                                if (operateUser != null) {
                                    sample.setInstoredPerson(operateUser.getLoginName());
                                }
                                limsSampleInfoDnaService.updateInstoredFlag(sample);
                                logger.info("入库本地库失败！");
                            }
                        }
                    }else {
                        logger.info(result.get("resultValue").toString() + " 入库失败! \n");
                    }
                }
            }
        }
    }

    /**
     * 插入本地库比对队列
     *
     * @author sunweiqiang
     * @date 2019/4/9
     */
    private void insertQueueSample(LimsSampleInfoDna sampleInfo, String queueType) {
        QueueSample queueSample = new QueueSample();
        queueSample.setId(UUID.randomUUID().toString());
        queueSample.setForeignId(sampleInfo.getSampleNo());
        queueSample.setStatus(0);
        queueSample.setQueueType(queueType);
        queueSample.setCreateDatetime(new Date());
        queueSampleService.insert(queueSample);
    }

    /**
     * 插入本地库比对队列
     *
     * @author sunweiqiang
     * @date 2019/4/9
     */
    private String insertQueueSample(String serverNo, String id, String queueType) {
        QueueSample queueSample = new QueueSample();
        String queueId = UUID.randomUUID().toString();
        queueSample.setId(queueId);
        queueSample.setForeignId(id);
        queueSample.setStatus(0);
        queueSample.setQueueType(queueType);
        queueSample.setCreateDatetime(new Date());
        queueSample.setServerNo(serverNo);
        queueSampleService.insert(queueSample);
        return queueId;
    }

    /**
     * 插入本地库比对队列
     *
     * @author sunweiqiang
     * @date 2019/4/9
     */
    private void insertQueueDetail(List<LimsSampleInfoDna> samples, String consignmentId, String queueId) {
        for (LimsSampleInfoDna sample : samples) {
            if(sample != null){
                QueueDetail queueDetail = new QueueDetail();
                queueDetail.setId(UUID.randomUUID().toString());
                queueDetail.setSampleId(sample.getSampleId());
                queueDetail.setSampleNo(sample.getSampleNo());
                queueDetail.setConsignmentId(consignmentId);
                queueDetail.setQueueId(queueId);
                queueDetail.setCreateDatetime(new Date());
                queueDetailService.insert(queueDetail);
            }
        }
    }

    /**
     * 插入亲缘关系列表
     *
     * @author sunweiqiang
     * @date 2019/4/9
     */
    private void insertMatchRelativeLib(LimsSampleInfoDna sampleInfo) {
        if (sampleInfo != null) {
            MatchRelativeLib lib = matchRelativeLibService.selectBySampleaId(sampleInfo.getSampleId());
            if (lib != null) {
                lib.setInstoredType(sampleInfo.getInstoredType());
                lib.setSamplebId(sampleInfo.getSameGroupSample());
                lib.setSampleaRole(sampleInfo.getSameGroupSampleRole());
                lib.setSamplebRole(sampleInfo.getTargetSampleRole());
                matchRelativeLibService.updateByPrimaryKey(lib);
            } else {
                lib = new MatchRelativeLib();
                lib.setId(UUID.randomUUID().toString());
                lib.setSampleaId(sampleInfo.getSampleId());
                lib.setSamplebId(sampleInfo.getSameGroupSample());
                lib.setInstoredType(sampleInfo.getInstoredType());
                lib.setSampleaRole(sampleInfo.getSameGroupSampleRole());
                lib.setSamplebRole(sampleInfo.getTargetSampleRole());
                matchRelativeLibService.insert(lib);
            }
        }
    }


    @RequestMapping(value = "/getPersonRelative", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPersonRelative(HttpServletRequest request, String sampleId) {
        Map<String, Object> result = new HashMap<String, Object>();

        //查询样本1的亲属关系,和人员类型
        String relationType = "";
        String personType = "";
        String personGender = "";
        LimsSampleInfoDna sampleInfo = limsSampleInfoDnaService.selectSampleInfoDnaById(sampleId);
        if (sampleInfo != null && StringUtils.isNotBlank(sampleInfo.getLinkId())) {
            LimsPersonInfo limsPersonInfo = limsPersonInfoService.selectPersonInfoById(sampleInfo.getLinkId());
            if (limsPersonInfo != null) {
                relationType = limsPersonInfo.getRelationType();
                personType = limsPersonInfo.getPersonType();
                personGender = limsPersonInfo.getPersonGender();
            }
        }
        result.put("relationType", relationType);
        result.put("personType", personType);
        result.put("personGender", personGender);
        return result;
    }

}
