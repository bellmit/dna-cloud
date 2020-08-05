package com.bazl.dna.lims.core.controller.center;

import static com.bazl.dna.lims.core.utils.DateUtils.addDays;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.core.LimsConfigure;
import com.bazl.dna.lims.core.client.IFileServiceClient;
import com.bazl.dna.lims.core.common.Constants;
import com.bazl.dna.lims.core.controller.BaseController;
import com.bazl.dna.lims.core.controller.DownloadFileUtils;
import com.bazl.dna.lims.core.model.PageInfo;
import com.bazl.dna.lims.core.model.po.AmPersonalInfo;
import com.bazl.dna.lims.core.model.po.DictItem;
import com.bazl.dna.lims.core.model.po.ErsFile;
import com.bazl.dna.lims.core.model.po.Examiner;
import com.bazl.dna.lims.core.model.po.LimsCaseInfo;
import com.bazl.dna.lims.core.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.core.model.po.LimsPersonInfo;
import com.bazl.dna.lims.core.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import com.bazl.dna.lims.core.model.po.OrgInfo;
import com.bazl.dna.lims.core.model.vo.AlimsObjVo;
import com.bazl.dna.lims.core.model.vo.AmPersonalInfoVo;
import com.bazl.dna.lims.core.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.core.service.AmPersonalInfoService;
import com.bazl.dna.lims.core.service.ErsFileService;
import com.bazl.dna.lims.core.service.ExaminerService;
import com.bazl.dna.lims.core.service.LimsCaseInfoService;
import com.bazl.dna.lims.core.service.LimsConsignmentInfoService;
import com.bazl.dna.lims.core.service.LimsPersonInfoService;
import com.bazl.dna.lims.core.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.core.service.OrgInfoService;
import com.bazl.dna.lims.core.utils.DateUtils;
import com.bazl.dna.lims.core.utils.DictUtil;
import com.bazl.dna.lims.core.utils.FileUtil;
import com.bazl.dna.lims.core.utils.GeneratePathUtil;
import com.bazl.dna.lims.core.utils.HttpRequestUtil;
import com.bazl.dna.lims.core.utils.IpAddressUtils;
import com.bazl.dna.lims.core.utils.JsonUtils;
import com.bazl.dna.lims.core.utils.ListUtils;

/**
 * @author wanghaiyang
 * @date 2019/2/21.
 */
@Controller
@RequestMapping("/center")
public class IdentifyBookController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    OrgInfoService orgInfoService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    DownloadFileUtils downloadFileUtils;
    @Autowired
    AmPersonalInfoService amPersonalInfoService;
    @Autowired
    ExaminerService examinerService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;
    @Autowired
    LimsConfigure limsConfigure;
    @Autowired
    ErsFileService ersFileService;
    @Autowired
    IFileServiceClient fastDFSClient;


    /**
     * 鉴定书管理
     *
     * @param request
     * @param query
     * @param pageInfo
     * @return
     */
    @RequestMapping("/appraisalManage")
    public ModelAndView caseManage(HttpServletRequest request, LimsCaseInfoVo query, PageInfo pageInfo, String isAppraisal) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }

        try {
            query = initializationData.resetCaseInfoQuery(query);

            //查询委托单位（分局）
            String orgParentsId = "110000000000";
            List<OrgInfo> orgInfoList = orgInfoService.selectDelegateByParentsId(orgParentsId);

            //获取当前用户的id
            String userOrgId = operateUser.getOrgId();
            //将当前用户org_id设置进query
            if (StringUtils.isNotBlank(userOrgId) && userOrgId.contains("110230")) {
                userOrgId = "110230000000";
            }
            query.setUserOrdId(userOrgId);
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
            //查询主案件信息
            if (null != query.getDelegateEndDatetime()) {//解决时间无时分秒造成的00:00:00查询
                query.setDelegateEndDatetime(addDays(query.getDelegateEndDatetime(), 1));//增加一天
            }
            List<LimsCaseInfoVo> caseInfoList = limsCaseInfoService.selectCaseInfoList(query, pageInfo);
            if (ListUtils.isNotNullAndEmptyList(caseInfoList)) {
                for (LimsCaseInfoVo lciVo : caseInfoList) {
                    ErsFile ersFile = new ErsFile();
                    ersFile.setCaseId(lciVo.getEntity().getCaseId());
                    ersFile.setFileCategory(Constants.FILE_CATEGORY_11);
                    List<ErsFile> ersFileList = ersFileService.selectList(ersFile);
                    if (ListUtils.isNotNullAndEmptyList(ersFileList)) {
                        ersFile = ersFileList.get(0);
                        lciVo.setOriginalFileDir(ersFile.getOriginalFileDir());
                        lciVo.setNewFileDir(ersFile.getNewFileDir());
                        lciVo.setExistFlag(Constants.DELETE_FLAG_1);
                    }else {
                        lciVo.setExistFlag(Constants.DELETE_FLAG_0);
                    }
                }
            }

            //查询案件数量
            int caseInfoCount = limsCaseInfoService.selectVOCount(query);
            if (null != query.getDelegateEndDatetime()) {//解决时间无时分秒造成的00:00:00查询
                query.setDelegateEndDatetime(addDays(query.getDelegateEndDatetime(), -1));//减少一天
            }
            view = initializationData.initDictList();
            view.addObject("isAppraisal", isAppraisal);
            view.addObject("query", query);
            view.addObject("caseInfoList", caseInfoList);
            view.addObject("orgInfoList", orgInfoList);
            view.addObject("amPersonalInfoVoList", amPersonalInfoVoList);
            view.addObject("pageInfo", pageInfo.updatePageInfo(caseInfoCount));
            view.addObject("operateUser", operateUser);
            view.addObject("limsConfigure", limsConfigure);
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }

        view.setViewName("identifyBook/appraisalManage");
        return view;
    }

    /**
     * 查看案情详情
     */
    @RequestMapping("/vewDetailsCase")
    public ModelAndView vewDetailsCase(HttpServletRequest request, String caseId, String consignmentId) {
        ModelAndView modelAndView = initDictList();

        try {
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            if (operateUser == null) {
                modelAndView.setViewName("redirect:/login.html?timeoutFlag=1");
                return modelAndView;
            }

            DictItem dictItem = new DictItem();
            //案件类型
            dictItem.setDictTypeCode(Constants.CASE_TYPE);
            List<DictItem> caseTypeList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("caseTypeList", caseTypeList);

            //案件性质
            dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
            List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("casePropertyList", casePropertyList);

            //案件级别
            dictItem.setDictTypeCode(Constants.CASE_LEVEL);
            List<DictItem> caseLevelList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("caseLevelList", caseLevelList);

            //人员类型
            dictItem.setDictTypeCode(Constants.PERSON_TYPE);
            List<DictItem> personTypeList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("personTypeList", personTypeList);

            //性别
            dictItem.setDictTypeCode(Constants.GENDER);
            List<DictItem> genderList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("genderList", genderList);

            //检材类型
            dictItem.setDictTypeCode(Constants.SAMPLE_TYPE);
            List<DictItem> sampleTypeList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("sampleTypeList", sampleTypeList);

            //包装方法PACK_METHOD
            dictItem.setDictTypeCode(Constants.PACK_METHOD);
            List<DictItem> packMethodList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("packMethodList", packMethodList);

            //案件状态CASE_STATUS
            dictItem.setDictTypeCode(Constants.CASE_STATUS);
            List<DictItem> caseStatusList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("caseStatusList", caseStatusList);

            //职务
            dictItem.setDictTypeCode(Constants.POSITION_LIST);
            List<DictItem> positionList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("positionList", positionList);

            //亲缘关系
            dictItem.setDictTypeCode(Constants.RELATION_TYPE);
            List<DictItem> relationTypeList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("relationTypeList", relationTypeList);

            //提取方法
            dictItem.setDictTypeCode(Constants.EXTRACT_METHOD);
            List<DictItem> extractMethodList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("extractMethodList", extractMethodList);

            //载体
            dictItem.setDictTypeCode(Constants.SAMPLE_CARRIER);
            List<DictItem> sampleCarrierList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("sampleCarrierList", sampleCarrierList);


            //根据委托id查询委托信息
            LimsConsignmentInfo consignatioInfo = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
            //根据案件id查询案件信息
            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);

            //分局
            List<OrgInfo> orgInfoList = new ArrayList<>();
            List<AmPersonalInfo> personalInfoList = new ArrayList<>();
            if (StringUtils.isBlank(operateUser.getOrgId()) || operateUser.getOrgId().contains("110230") || operateUser.getOrgId().contains("11023")) {
                orgInfoList = orgInfoService.selectAll();
                personalInfoList = amPersonalInfoService.queryAmPersonalInfoLIst(null);
            } else {
                orgInfoList = orgInfoService.selectDelegateByParentsId(operateUser.getOrgId());
                personalInfoList = amPersonalInfoService.queryAmPersonalInfoLIst(operateUser.getOrgId());
            }
//            List<OrgInfo> orgInfoList = orgInfoService.selectDelegateByParentsId(operateUser.getOrgId());
            //获取委托人管理数据
//            List<AmPersonalInfo> personalInfoList = amPersonalInfoService.queryAmPersonalInfoLIst(operateUser.getOrgId());
            modelAndView.addObject("personalInfoList", personalInfoList);
            modelAndView.addObject("orgInfoList", orgInfoList);

            //根据案件id查询人员信息
            LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
            limsPersonInfo.setConsignmentId(consignmentId);
            limsPersonInfo.setCaseId(caseId);
            List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfo);
            //根据案件id查询样本信息
            LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
            sampleInfoDna.setCaseId(caseId);
            sampleInfoDna.setConsignmentId(consignmentId);
            LinkedList<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectByCaseIdAndRy(sampleInfoDna);
            if (null != limsPersonInfoList && limsPersonInfoList.size() > 0 && null != sampleInfoDnaList && sampleInfoDnaList.size() > 0) {
                for (LimsPersonInfo limsPersonInfo1 : limsPersonInfoList) {
                    for (LimsSampleInfoDna sampleInfoDna2 : sampleInfoDnaList) {
                        if (limsPersonInfo1.getPersonId().equals(sampleInfoDna2.getLinkId())) {
                            sampleInfoDna2.setPersonName(limsPersonInfo1.getPersonName());
                        }
                    }
                }
            }

            //根据案件id查询物证信息
            LinkedList<LimsSampleInfoDna> sampleInfoDnaList1 = limsSampleInfoDnaService.selectByCaseIdAndWz(sampleInfoDna);

            //查询委托的单位code和名称
            OrgInfo orgInfo = new OrgInfo();
            orgInfo.setOrgCode(consignatioInfo.getDelegateOrgCode());
            orgInfo.setOrgName(consignatioInfo.getDelegateOrgName());
            modelAndView.addObject("orgInfo", orgInfo);

            modelAndView.addObject("consignatioInfo", consignatioInfo);
            modelAndView.addObject("limsCaseInfo", limsCaseInfo);
            modelAndView.addObject("limsPersonInfoList", limsPersonInfoList);
            modelAndView.addObject("sampleInfoDnaList", sampleInfoDnaList);
            modelAndView.addObject("limsSampleInfoList", sampleInfoDnaList1);
            modelAndView.addObject("ipAddr", IpAddressUtils.getIpAddr(request));
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }
        modelAndView.setViewName("identifyBook/vewDetailsCase");
        return modelAndView;
    }

    /**
     * 生成鉴定书
     *
     * @param request
     * @param caseId
     * @return
     */
    @RequestMapping("/generateIdentifyBook")
    @ResponseBody
    public Map<String, Object> generateIdentifyBook(HttpServletRequest request, String caseId) {
        Map<String, Object> result = new HashMap<>();
        String tandemStatus = "0";
        result = downloadFileUtils.generateIdentifyBook(request, caseId,tandemStatus);
        return result;
    }

    @RequestMapping("/getTokenUrl")
    @ResponseBody
    public Map<String, Object> getTokenUrl(HttpServletRequest request,@RequestBody String alimsId) {
        Map<String, Object> requestMap = new HashMap<>();

        try {
            String result = HttpRequestUtil.sendRequest(limsConfigure.getGetTokenUrl(), alimsId);
            System.out.println(result);
            HashMap MapResult = JsonUtils.string2Obj(result,HashMap.class);
            if (!MapResult.isEmpty() ){
                if ((boolean)MapResult.get("success")){
                    requestMap.putAll(MapResult);
                }else{
                    requestMap.put("success", false);
                }
            }else{
                requestMap.put("success", false);
            }
        }catch (Exception e) {
            requestMap.put("success", false);
            e.getStackTrace();
            logger.debug("调用签发平台token报错！", e);
        }

        return requestMap;
    }

    /**
     * 调用签发平台上传接口
     * @param alimsObjVo
     * @return
     */
    @RequestMapping(value = "/callUploadInterface")
    @ResponseBody
    public Map<String, Object> callUploadInterface(HttpServletRequest request,@RequestBody AlimsObjVo alimsObjVo) {
        Map<String, Object> requestMap = new HashMap<>();
        try {
            String result = HttpRequestUtil.sendRequest(limsConfigure.getUploadIdentifyBookUrl(), JsonUtils.obj2String(alimsObjVo));
            System.out.println(result);
            HashMap MapResult = JsonUtils.string2Obj(result,HashMap.class);
            if (!MapResult.isEmpty() ){
                requestMap.putAll(MapResult);
                if ((boolean)MapResult.get("success")){
                    requestMap.put("success", true);
                }else{
                    requestMap.put("success", false);
                }
            }else{
                requestMap.put("success", false);
            }
        }catch (Exception e) {
            e.getStackTrace();
            requestMap.put("success", false);
            requestMap.put("message", e.getMessage());
            logger.debug("调用上传接口报错！", e);
        }

        return requestMap;
    }

    @RequestMapping(value = "/getFileByte", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> getFileByte(HttpServletRequest request,@RequestBody AlimsObjVo alimsObjVo) {
        Map<String, Object> requestMap = new HashMap<>();
        try {
            /*byte[] bytes = FileUtil.getBytesFromFile(new File("D:\\identifyBookFile\\202007\\rebuildFile\\FILE_1594881224963.pdf"));
            alimsObjVo.setCaseId("897de453-91fb-4469-8dc7-b5c8f4b71901");
            alimsObjVo.setFileBinary(bytes);*/
            String filename = alimsObjVo.getCaseId() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".pdf";
            String filePath = GeneratePathUtil.generatePath();
            //把字节转成文件
            if (alimsObjVo.getFileBinary() != null) {
                FileUtil.getFile(alimsObjVo.getFileBinary(), filePath, filename);

                //上传到dfs服务器
                String file = filePath + "\\" + filename;
                if (new File(file).exists()) {
                    ResponseData responseData = fastDFSClient.upload(new File(file));
                    String dfsFilePath = (String)responseData.getResult();

                    ErsFile ersFile = new ErsFile();
                    ersFile.setCaseId(alimsObjVo.getCaseId());
                    List<ErsFile> ersFileList = ersFileService.selectList(ersFile);
                    if (ListUtils.isNotNullAndEmptyList(ersFileList)) {
                        for (ErsFile ef : ersFileList) {
                            ef.setNewFileDir(dfsFilePath);
                            ersFileService.updateByPrimaryKey(ef);
                        }
                    }
                }
                requestMap.put("message", "成功了！");
                requestMap.put("success", true);
            }else {
                requestMap.put("message", "失败了！");
            }
        }catch (Exception e) {
            e.getStackTrace();
            requestMap.put("success", false);
            requestMap.put("message", e.getMessage());
            logger.debug("调用上传接口报错！", e);
        }

        return requestMap;
    }

    /**
     * 生成串并鉴定书
     *
     * @param request
     * @param caseId
     * @return
     */
    @RequestMapping("/generateChuanBingIdentifyBook")
    @ResponseBody
    public Map<String, Object> generateChuanBingIdentifyBook(HttpServletRequest request, String caseId, String groupId) {
        Map<String, Object> result = new HashMap<>();
        String tandemStatus = "1";
        result = downloadFileUtils.generateChuanBingIdentifyBook(request, caseId, groupId,tandemStatus);

        return result;
    }

    /**
     * 保存比对结果
     *
     * @param request
     * @param examiner
     * @return
     */
    @RequestMapping(value = "/saveExaminer", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveExaminer(HttpServletRequest request, @RequestBody Examiner examiner) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<Examiner> examinerList = examinerService.selectListByCaseId(examiner.getCaseId());

        try {
            if (ListUtils.isNullOrEmptyList(examinerList)) {
                examiner.setId(UUID.randomUUID().toString());

                examinerService.insert(examiner);
            } else {
                Examiner exa = examinerList.get(0);
                exa.setInspector1(examiner.getInspector1());
                exa.setInspector2(examiner.getInspector2());
                exa.setInspector3(examiner.getInspector3());

                //法医师职称
                exa.setTitleOne(examiner.getTitleOne());
                exa.setTitleTwo(examiner.getTitleTwo());
                exa.setTitleThree(examiner.getTitleThree());
                exa.setCnasSeal(examiner.getCnasSeal());

                examinerService.update(exa);
            }
            result.put("success", true);
        } catch (Exception e) {
            e.getStackTrace();
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("/selectExaminer")
    @ResponseBody
    public Map<String, Object> selectExaminer(HttpServletRequest request, String caseId) {
        Map<String, Object> result = new HashMap<>();

        try {
            List<Examiner> examinerList = examinerService.selectListByCaseId(caseId);
            if (ListUtils.isNotNullAndEmptyList(examinerList)) {
                result.put("examiner", examinerList.get(0));
                result.put("success", true);
            }else {
                result.put("success", false);
            }
        }catch (Exception e) {
            result.put("success", false);
            e.getStackTrace();
            logger.debug("查询失败!" + e);
        }

        return result;
    }

    /**
     * 生成本案比对鉴定书
     *
     * @param request
     * @return
     */
    @RequestMapping("/generateTestimonial")
    public ModelAndView caseCompareBook(HttpServletRequest request, String caseId) {
        ModelAndView view = new ModelAndView();

        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);

        view.addObject("limsCaseInfo", limsCaseInfo);

        view.setViewName("identifyBook/generateTestimonial");
        return view;
    }


    /**
     * 案卷打印
     *
     * @param request
     * @param query
     * @param pageInfo
     * @return
     */
    @RequestMapping("/booksPrinted")
    public ModelAndView booksPrinted(HttpServletRequest request, LimsCaseInfoVo query, PageInfo pageInfo) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }

        try {
            query = initializationData.resetCaseInfoQuery(query);

            //查询委托单位（分局）
            String orgParentsId = "110000000000";
            List<OrgInfo> orgInfoList = orgInfoService.selectDelegateByParentsId(orgParentsId);

            //获取当前用户的id
            String userOrgId = operateUser.getOrgId();
            //将当前用户org_id设置进query
            if (StringUtils.isNotBlank(userOrgId) && userOrgId.contains("110230")) {
                userOrgId = "110230000000";
            }
            query.setUserOrdId(userOrgId);
            query.setAcceptOrgId(userOrgId);

            //案件状态在检验和已完成
            query.setCaseStatusList(Arrays.asList(Constants.CASE_STATUS_EXAMINING, Constants.CASE_STATUS_FINISHED));
            //不补送
            query.setAppendFlag(Constants.APPEND_FLAG_NON);

            //排序条件设置
            query.setOrderByClause("lci.ACCEPT_DATETIME desc nulls last,lc.case_no desc");
            //查询主案件信息
            List<LimsCaseInfoVo> caseInfoList = limsCaseInfoService.selectCaseInfoList(query, pageInfo);
            //查询案件数量
            int caseInfoCount = limsCaseInfoService.selectVOCount(query);
            view = initializationData.initDictList();
            view.addObject("query", query);
            view.addObject("caseInfoList", caseInfoList);
            view.addObject("orgInfoList", orgInfoList);
            view.addObject("pageInfo", pageInfo.updatePageInfo(caseInfoCount));
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }

        view.setViewName("identifyBook/booksPrinted");
        return view;
    }


    /**
     * 查询字典项
     *
     * @return
     */
    private ModelAndView initDictList() {
        ModelAndView modelAndView = new ModelAndView();
        /*  字典 */

        DictItem dictItem = new DictItem();
        //案件类型
        dictItem.setDictTypeCode(Constants.CASE_TYPE);
        List<DictItem> caseTypeList = DictUtil.getDictList(dictItem);

        //案件性质
        dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
        List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);

        //案件级别
        dictItem.setDictTypeCode(Constants.CASE_LEVEL);
        List<DictItem> caseLevelList = DictUtil.getDictList(dictItem);

        //人员类型
        dictItem.setDictTypeCode(Constants.PERSON_TYPE);
        List<DictItem> memberTypeList = DictUtil.getDictList(dictItem);

        //性别
        dictItem.setDictTypeCode(Constants.GENDER);
        List<DictItem> genderList = DictUtil.getDictList(dictItem);

        //检材类型
        dictItem.setDictTypeCode(Constants.SAMPLE_TYPE);
        List<DictItem> sampleTypeList = DictUtil.getDictList(dictItem);

        //包装方法PACK_METHOD
        dictItem.setDictTypeCode(Constants.PACK_METHOD);
        List<DictItem> packMethodList = DictUtil.getDictList(dictItem);

        //案件状态CASE_STATUS
        dictItem.setDictTypeCode(Constants.CASE_STATUS);
        List<DictItem> caseStatusList = DictUtil.getDictList(dictItem);

        dictItem.setDictTypeCode(Constants.POSITION_LIST);
        List<DictItem> positionList = DictUtil.getDictList(dictItem);

        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseLevelList", caseLevelList);
        modelAndView.addObject("memberTypeList", memberTypeList);
        modelAndView.addObject("sampleTypeList", sampleTypeList);
        modelAndView.addObject("genderList", genderList);
        modelAndView.addObject("packMethodList", packMethodList);
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("positionList", positionList);

        return modelAndView;
    }
}
