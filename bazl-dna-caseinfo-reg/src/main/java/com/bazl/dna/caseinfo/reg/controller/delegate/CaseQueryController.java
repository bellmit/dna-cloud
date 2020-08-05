package com.bazl.dna.caseinfo.reg.controller.delegate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.caseinfo.reg.HttpClient.GetXkCaseService;
import com.bazl.dna.caseinfo.reg.common.Constants;
import com.bazl.dna.caseinfo.reg.controller.BaseController;
import com.bazl.dna.caseinfo.reg.webservices.XckyWebService;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.dao.LimsConsignmentInfoMapper;
import com.bazl.dna.lims.model.bo.DelegateDataModel;
import com.bazl.dna.lims.model.po.AmPersonalInfo;
import com.bazl.dna.lims.model.po.DelegateCenterConfig;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.FugitivesInfo;
import com.bazl.dna.lims.model.po.LimsCaseInfo;
import com.bazl.dna.lims.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.model.po.LimsPersonInfo;
import com.bazl.dna.lims.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.po.OrgInfo;
import com.bazl.dna.lims.model.po.XckyAddressInfo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.service.AmPersonalInfoService;
import com.bazl.dna.lims.service.DelegateCenterConfigService;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.service.FugitivesInfoService;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.service.LimsConsignmentInfoService;
import com.bazl.dna.lims.service.LimsPerosnRelationService;
import com.bazl.dna.lims.service.LimsPersonInfoService;
import com.bazl.dna.lims.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.service.OrgInfoService;
import com.bazl.dna.lims.service.PersonDetailService;
import com.bazl.dna.lims.service.XckyAddressInfoService;
import com.bazl.dna.lims.utils.DictUtil;
import com.bazl.dna.lims.utils.ListUtils;

/**
 * Created by Sun on 2018/12/20.
 */
@Controller
@RequestMapping("/caseQuery")
public class CaseQueryController extends BaseController{

    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;

    @Autowired
    LimsCaseInfoService limsCaseInfoService;

    @Autowired
    PersonDetailService personDetailService;

    @Autowired
    LimsPersonInfoService limsPersonInfoService;

    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;

    @Autowired
    LimsPerosnRelationService limsPerosnRelationService;

    @Autowired
    AmPersonalInfoService amPersonalInfoService;

    @Autowired
    OrgInfoService orgInfoService;

    @Autowired
    GetXkCaseService getXkCaseService;

    @Autowired
    XckyAddressInfoService xckyAddressInfoService;
    @Autowired
    XckyWebService xckyWebService;

    @Autowired
    LimsConsignmentInfoMapper limsConsignatioInfoMapper;
    @Autowired
    DelegateCenterConfigService delegateCenterConfigService;
    @Autowired
    FugitivesInfoService fugitivesInfoService;
    @Autowired
    private DictItemService dictItemService;
//    @Autowired
//    MobileNewsMapper mobileNewsMapper;

//    @Autowired
//    LogRecordMapper logRecordMapper;

//    @Value("${Is_App_Inform}")
//    private int isAppInform;
//    @Value("${Is_App_Url}")
//    private String AppUrl;

    /**
     * 查询与补送
     * @return
     */
    @RequestMapping("/caseAndBring")
    public ModelAndView caseAndBring (HttpServletRequest request, LimsCaseInfoVo query, PageInfo pageInfo){
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if(operateUser == null){
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return  view;
        }

        try {

            String orgId = StringUtils.isBlank(operateUser.getSubOrgId()) ? operateUser.getOrgId() : operateUser.getSubOrgId();

            //如果不为空并且是北京市公安司法鉴定中心 就把所有分局只要是送检单位选择的是北京市公安司法鉴定中心的都查询出来
            //如果不为空并且选择的是当前登录人的单位的送检中心  就查询当前登录人的送检单位的案件
            String acceptOrgId = null;
            if(StringUtils.isNotBlank(query.getAcceptOrgId()) && ("110230000000").equals(query.getAcceptOrgId())){
                acceptOrgId = "110230000000";
                orgId = null;
            }else if(StringUtils.isNotBlank(query.getAcceptOrgId()) && (StringUtils.isBlank(operateUser.getSubOrgId()) ? operateUser.getOrgId() : operateUser.getSubOrgId()).equals(query.getAcceptOrgId())){
                acceptOrgId = StringUtils.isBlank(operateUser.getSubOrgId()) ? operateUser.getOrgId() : operateUser.getSubOrgId();
            }else if(StringUtils.isBlank(query.getAcceptOrgId())){
                acceptOrgId = null;
            }

            query = resetCaseInfoQuery(query);
            if(StringUtils.isBlank(query.getStatus())){
                query.setStatus(Constants.CASE_STATUS_02);
            }else{
                query.setStatus(query.getStatus());
            }
            query.setDelegateOrgCode(orgId);
            if (orgId!= null){
                if(orgId.contains("110024")){
                    query.setAcceptOrgId(query.getAcceptOrgId());
                }else {
                    query.setAcceptOrgId(acceptOrgId);
                }
            }

            //查询查询与补送数据
            List<LimsCaseInfoVo> caseInfoList =limsCaseInfoService.selectCaseInfoList(query, pageInfo);
            if(null != caseInfoList && caseInfoList.size() > 0){
                for(LimsCaseInfoVo limsCaseInfoVo:caseInfoList){
                    if(StringUtils.isNotBlank(limsCaseInfoVo.getAcceptorId())){
                        //查询受理单位
                        OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(limsCaseInfoVo.getAcceptOrgId());
                        if(StringUtils.isNotBlank(orgInfo.getOrgQualification())){
                            limsCaseInfoVo.setOrgQualification(orgInfo.getOrgQualification());
                        }
                    }
                }
            }

            int caseInfoCount = limsCaseInfoService.selectVOCount(query);
            view = initModelAndView();

            //查询补送次数
            if(null != caseInfoList && caseInfoList.size() > 0){
                for(LimsCaseInfoVo limsCaseInfoVo:caseInfoList){
                    if(StringUtils.isNotBlank(limsCaseInfoVo.getEntity().getCaseId())){
                        LimsConsignmentInfo limsConsignmentInfo = limsConsignatioInfoMapper.selectMaxReplacementNum(limsCaseInfoVo.getEntity().getCaseId());
                        if(limsConsignmentInfo != null){
                            limsCaseInfoVo.getEntity().setReplacementNum(limsConsignmentInfo.getReplacementNum());//replacementNum
                        }
                    }
                }
            }


            //查询分局单位
            OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(operateUser.getOrgId());
            //查询法医中心
            OrgInfo forensicCenterorg = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
            view.addObject("forensicCenterorg",forensicCenterorg);
            //创建orgInfoList点击鉴定中心的选择，可以多选
//            List<OrgInfo> orgInfos = new ArrayList<>();
//            if(StringUtils.isNotBlank(orgInfo.getOrgQualification())){
//                orgInfos.add(orgInfo);
//                orgInfos.add(forensicCenterorg);
//            }else if(orgInfo.getOrgId().indexOf("110024") != -1){
//                orgInfo = orgInfoService.selectByPrimaryKey("110115000000");
//                orgInfos.add(orgInfo);
//
//                orgInfo = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
//                orgInfos.add(orgInfo);
//            } else{
//                orgInfos.add(forensicCenterorg);
//            }
            String orgId1 = operateUser.getOrgId();
            String substring = orgId1.substring(0, 6);
            List<DelegateCenterConfig> delegateCenterConfigs = delegateCenterConfigService.selectQualification(substring);

            view.addObject("orgInfos",delegateCenterConfigs);


            view.addObject("query", query);
            view.addObject("caseInfoList", caseInfoList);
            view.addObject("pageInfo", pageInfo.updatePageInfo(caseInfoCount));
        }catch (Exception ex){
            logger.info("查询失败:"+ex);
        }
        view.setViewName("caseQuery/caseAndBring");
        return view;
    }

    /**
     * 类说明：案件鉴定委托页面-案件鉴定委托列表-案件鉴定登记（查询接口）
     * tableType  类型
     * 说明：待送检 - tableType1 : 受理状态待受理，且受理时间小于72小时
     *      超时未送检-tableType2：受理状态待受理，且受理时间大于72小时
     *      已送检-tableType3：受理状态已受理
     * Author: liuchang
     * Date :2020-7-15
     * @param request
     * @param query
     * @param pageInfo
     * @param survey
     * @return
     */
    @RequestMapping("/caseQuery")
    @SuppressWarnings("all")
    public ModelAndView caseQuery (HttpServletRequest request, LimsCaseInfoVo query, PageInfo pageInfo, String survey,String tableType){
        ModelAndView view = new ModelAndView();
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if(operateUser == null){
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return  view;
        }

        if(StringUtils.isBlank(tableType)){
            tableType = "1";
        }

        List<LimsCaseInfoVo> caseInfoList  = null;

        DictItem dictItem = new DictItem();
        //案件性质
        dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
        dictItem.setParentId(null); //一级菜单不包含parentId
        List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
        view.addObject("casePropertyList",casePropertyList);
        List<DictItem> casePropertyListlv1 = new ArrayList<>();//初始化一级案件性质
        List<DictItem> casePropertyListlv2 = new ArrayList<>();//初始化二级案件性质
        for (DictItem dictItem1: casePropertyList){
            if (StringUtils.isBlank(dictItem1.getParentId())){
                casePropertyListlv1.add(dictItem1);
            }else{
                casePropertyListlv2.add(dictItem1);
            }
        }
        view.addObject("casePropertyList1", casePropertyListlv1);//一级案件性质
        //获取系统三天前的日期
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, -3);
        date = calendar.getTime();

        try {
            String orgId = StringUtils.isBlank(operateUser.getSubOrgId()) ? operateUser.getOrgId() : operateUser.getSubOrgId();
            if ("110105000000".equals(orgId)) {
                query.setAreaOrgCode("110105");
            } else if ("110230000002".equals(orgId)) {
                query.setDelegateOrgCode(null);
            } else {
                query.setDelegateOrgCode(orgId);
            }
            //查询查询与补送数据
            if (query.getEntity().getEntrustStatus() == "") {
                query.getEntity().setEntrustStatus(null);
            }

            query = resetCaseInfoQuery(query);    //查询去重条件

            int totalCnt = 0; //总计
            int tableType1Cnt = 0; //待送检
            int tableType2Cnt = 0;//超时未送检
            int tableType3Cnt = 0;//已送检

            //待送检的案件信息 (不包含在超时未送检里面)
            if(tableType.equals("1")) {
                query.setStatus(Constants.STATUS_01);//委托状态,待受理，待送检
                query.setDelegateStartDatetime(date); //开始时间
                caseInfoList = limsCaseInfoService.selectCaseQueryInfoList(query, pageInfo);  //查询案件列表信息，根据查询条件
                totalCnt = limsCaseInfoService.selectCaseQueryVOCount(query);   //总计信息
                tableType1Cnt = totalCnt; //待受理总计信息
                query.setStatus(Constants.STATUS_02);//委托状态,已受理，已送检
                query.setDelegateStartDatetime(null);//不记开始时间
                tableType3Cnt = limsCaseInfoService.selectCaseQueryVOCount(query); //已送检，总计信息
                //超时未送检总计信息
                query.setStatus(Constants.STATUS_01);//委托状态,待受理，待送检
                query.setDelegateEndDatetime(date); //结束时间
                tableType2Cnt = limsCaseInfoService.selectCaseQueryVOCount(query);   //超时未送检，总计信息
             //超时未送检
            }else if (tableType.equals("2")){
                query.setStatus(Constants.STATUS_01);//委托状态_未受理，未送检
                query.setDelegateStartDatetime(date); //开始时间
                tableType1Cnt = limsCaseInfoService.selectCaseQueryVOCount(query); //未送检，总计信息
                query.setStatus(Constants.STATUS_02);//委托状态,已受理，已送检
                query.setDelegateStartDatetime(null);//不记委托开始时间
                tableType3Cnt = limsCaseInfoService.selectCaseQueryVOCount(query); //已送检，总计信息
                //设置系统当前时间 ,如果选择时间大于超时时间，则更改为超时时间
                query.setStatus(Constants.STATUS_01);//委托状态,待受理
                query.setDelegateEndDatetime(date);   //委托结束日期
                caseInfoList = limsCaseInfoService.selectCaseQueryInfoList(query, pageInfo);  //查询案件列表信息，根据查询条件
                totalCnt = limsCaseInfoService.selectCaseQueryVOCount(query);   //总计信息
                tableType2Cnt = totalCnt;
            //已送检
            }else if (tableType.equals("3")){
                query.setStatus(Constants.STATUS_02);//委托状态,已受理，已送检
                caseInfoList = limsCaseInfoService.selectCaseQueryInfoList(query, pageInfo);  //查询案件列表信息，根据查询条件
                totalCnt = limsCaseInfoService.selectCaseQueryVOCount(query);   //总计信息
                tableType3Cnt = totalCnt;
                query.setStatus(Constants.STATUS_01);//委托状态_未受理，未送检
                query.setDelegateStartDatetime(date); //委托开始时间
                tableType1Cnt = limsCaseInfoService.selectCaseQueryVOCount(query); //未送检，总计信息
                //设置系统当前时间 ,如果选择时间大于超时时间，则更改为超时时间
                query.setStatus(Constants.STATUS_01);//委托状态,待受理
                query.setDelegateStartDatetime(null); //不记委托开始日期
                query.setDelegateEndDatetime(date);   //委托结束日期
                tableType2Cnt = limsCaseInfoService.selectCaseQueryVOCount(query);   //总计信息
            }
            view = initModelAndView();
            if (query.getEntity().getCaseProperty()!=null) {
                //根据dickCode 查当前字典
                DictItem case_property = dictItemService.selectDictNameByTypeCode("CASE_PROPERTY", query.getEntity().getCaseProperty());
                //根据一级查二级
                /*if (case_property.getParentId() != null) {
                    DictItem dictItem1 = dictItemService.selectById(case_property.getParentId());
                    String dictCode = dictItem1.getDictCode();
                    query.getEntity().setCaseProperty(dictCode);
                    query.getEntity().setCasePropertylv2(case_property.getDictCode());
                }*/
                //根据当前字典id  查子目录
                casePropertyListlv2 = dictItemService.selectListByParentId(case_property.getDictItemId());
                view.addObject("casePropertyListLv2", casePropertyListlv2);
            }

            view.addObject("query", query);
            view.addObject("caseInfoCount",totalCnt); //委托总数
            view.addObject("survey", survey);
            view.addObject("caseInfoList", caseInfoList);//案件信息集合
            view.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
            view.addObject("tableType", tableType);
            view.addObject("tableType1Cnt", tableType1Cnt);//待送检
            view.addObject("tableType2Cnt", tableType2Cnt);//超时未送检
            view.addObject("tableType3Cnt", tableType3Cnt);//已送检
        }catch (Exception ex){
            logger.info("查询失败:"+ex);
        }

        view.setViewName("caseQuery/caseQuery");
        return view;
    }

    @RequestMapping("/updateCaseWord")
    public ModelAndView updateCaseWord (HttpServletRequest request, String caseId, String consignmentId){
        ModelAndView view = new ModelAndView();
        LimsConsignmentInfo consignmentInfo = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
        view.addObject("caseId", caseId);
        view.addObject("consignmentId", consignmentId);
        view.addObject("consignmentInfo", consignmentInfo);
        view.setViewName("delegationReg/print");
        return view;
    }


    /**
     * 专家指导查询
     * @return
     */
    @RequestMapping("/consultationQuery")
    public ModelAndView consultationQuery (){
        ModelAndView view = new ModelAndView();
        view.setViewName("caseQuery/consultationQuery");
        return view;
    }

    /**
     * 删除查询与补送
     * @param request
     * @param consignmentId
     * @return
     */
    @RequestMapping("/delCaseAndBring")
    @ResponseBody
    public Map<String,Object> delCaseAndBring(HttpServletRequest request, String consignmentId, String caseId) {
        Map<String,Object> map = new HashMap<>();
        try {
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            if(operateUser == null){
                map.put("date","redirect:/login.html?timeoutFlag=1");
                return  map;
            }
            limsConsignmentInfoService.delCaseAndBring(consignmentId, caseId, operateUser);
            map.put("code", 0);
            map.put("message", "删除成功！");
        }catch(Exception ex){
            logger.info("删除失败"+ex);
            map.put("code", 1);
            map.put("message", "删除失败！");
        }
        return map;
    }

    /**
     * 查询委托登记详情信息
     * @param request
     * @param consignmentId
     * @param caseId
     * @return
     */
    @RequestMapping("/editCaseAndBring")
    public ModelAndView editCaseAndBring(HttpServletRequest request, String consignmentId, String caseId,HttpSession session, String survey) {
        ModelAndView modelAndView = initModelAndView();
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        operateUser.setStatusId("2");

        //根据委托id查询委托信息
        LimsConsignmentInfo consignatioInfo = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
        //根据案件id查询案件信息
        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);

        //根据案件id查询人员信息
        LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
        limsPersonInfo.setConsignmentId(consignmentId);
        limsPersonInfo.setCaseId(caseId);
        List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfo);
        //根据案件id查询样本信息
        LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
        sampleInfoDna.setCaseId(caseId);
        sampleInfoDna.setConsignmentId(consignmentId);
        List<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectByCaseIdAndRy(sampleInfoDna);
        if (null != limsPersonInfoList && limsPersonInfoList.size() > 0 && null != sampleInfoDnaList && sampleInfoDnaList.size() > 0) {
            for (LimsPersonInfo limsPersonInfo1 : limsPersonInfoList) {
                for (LimsSampleInfoDna sampleInfoDna2 : sampleInfoDnaList) {
                    //样本linkId 存在
                    if (limsPersonInfo1.getPersonId().equals(sampleInfoDna2.getLinkId())) {
                        if (sampleInfoDna2 != null) {
                            LimsPersonInfo person = limsPersonInfoService.selectPersonInfoById(sampleInfoDna2.getLinkId());
                            if (person != null && StringUtils.isNotBlank(person.getPersonType())) {
                                if (person.getPersonType().equals("03")) {
                                    sampleInfoDna2.setCoreVictimStats(Constants.CORE_VICTIM_STATS_TRUE);
                                }
                                //根据人员关联样本信息获取数据展示--liuchang
                                limsPersonInfo1.setSampleTypeName(sampleInfoDna2.getSampleTypeName() == null? "":sampleInfoDna2.getSampleTypeName());//样本类型名称
                                limsPersonInfo1.setSampleName(sampleInfoDna2.getSampleName() == null?"":sampleInfoDna2.getSampleName());//样本名称
                                limsPersonInfo1.setExtractDatetime(sampleInfoDna2.getExtractDatetime() == null? limsPersonInfo1.getCreateDatetime():sampleInfoDna2.getExtractDatetime());//提取时间
                                limsPersonInfo1.setSamplePackingName(sampleInfoDna2.getSamplePackingName() == null?"":sampleInfoDna2.getSamplePackingName());//包装方法
                            }
                        }
                        sampleInfoDna2.setPersonName(limsPersonInfo1.getPersonName());
                        //样本Linkid 不存在的时候
                    }
                }
            }
        }

        //根据案件id查询物证信息
        List<LimsSampleInfoDna> sampleInfoDnaList1 = limsSampleInfoDnaService.selectByCaseIdAndWz(sampleInfoDna);

        //查询分局单位
        OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(operateUser.getOrgId());
        if(null != orgInfo){
            modelAndView.addObject("orgId", orgInfo.getOrgId());
        }

        OrgInfo subOrgInfo = new OrgInfo();
        subOrgInfo.setOrgCode(consignatioInfo.getDelegateOrgCode());
        subOrgInfo.setOrgName(consignatioInfo.getDelegateOrgName());

        //创建orgInfoList点击鉴定中心的选择，可以多选
        List<OrgInfo> orgInfos = new ArrayList<>();
        if(StringUtils.isNotBlank(orgInfo.getOrgQualification())){
            //查询鉴定中心
            orgInfos.add(orgInfo);

            orgInfo = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
            orgInfos.add(orgInfo);
        } else if(orgInfo.getOrgId().indexOf("110024") != -1){
            orgInfo = orgInfoService.selectByPrimaryKey("110115000000");
            orgInfos.add(orgInfo);

            orgInfo = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
            orgInfos.add(orgInfo);
        }
        else{
            //查询法医鉴定中心
            orgInfo = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
            orgInfos.add(orgInfo);
        }

        modelAndView.addObject("orgInfos",orgInfos);
        //案件性质
        DictItem dictItem = new DictItem();
        dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
        dictItem.setParentId(null); //一级菜单不包含parentId
        List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
        modelAndView.addObject("casePropertyList",casePropertyList);
        List<DictItem> casePropertyListlv1 = new ArrayList<>();//初始化一级案件性质
        List<DictItem> casePropertyListlv2 = new ArrayList<>();//初始化二级案件性质
        for (DictItem dictItem1: casePropertyList){
            if (StringUtils.isBlank(dictItem1.getParentId())){
                casePropertyListlv1.add(dictItem1);
            }else{
                casePropertyListlv2.add(dictItem1);
            }
        }
        if(limsCaseInfo.getCaseProperty()!=null){
            //根据dickCode 查当前字典
            DictItem case_property = dictItemService.selectDictNameByTypeCode("CASE_PROPERTY", limsCaseInfo.getCaseProperty());
            if (case_property.getParentId()!=null){
                DictItem dictItem1 = dictItemService.selectByPrimaryKey(case_property.getParentId());
                String dictCode = dictItem1.getDictCode();
                limsCaseInfo.setCaseProperty(dictCode);
                limsCaseInfo.setCasePropertylv2(case_property.getDictCode());
                modelAndView.addObject("casePropertyListLv2", casePropertyListlv2);
            }
        }
        modelAndView.addObject("checkedOrgId", consignatioInfo.getAcceptOrgId());
        modelAndView.addObject("subOrgInfo", subOrgInfo);
        modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
        modelAndView.addObject("consignatioInfo", consignatioInfo);
        modelAndView.addObject("limsCaseInfo", limsCaseInfo);
        modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
        modelAndView.addObject("limsPersonInfoList", limsPersonInfoList);
        modelAndView.addObject("sampleInfoDnaList", sampleInfoDnaList);
        modelAndView.addObject("limsSampleInfoList", sampleInfoDnaList1);
        session.setAttribute("user", operateUser);
        session.setAttribute("survey", survey);
        modelAndView.setViewName("delegationReg/dnaReg");
        return modelAndView;
    }

    @RequestMapping("/refresh")
    @ResponseBody
    public Map<String, Object> refresh(HttpServletRequest request,
                                       HttpServletResponse response, String xkNo,String consignmentId,String caseId){
        Map<String, Object> result = new HashMap<String, Object>();
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if(operateUser == null){
            result.put("date","redirect:/login.html?timeoutFlag=1");
            return  result;
        }

        //根据现勘编号查询现勘数据
        XckyAddressInfo xckyAddressInfo = xckyAddressInfoService.selectByOrgId(operateUser.getOrgId());
        if(xckyAddressInfo == null){
            xckyAddressInfo = xckyAddressInfoService.selectDefault();
        }

        Map<String, Object> caseSampleInfoList = null;
        try {
            caseSampleInfoList = xckyWebService.getXckyInfoByKno(xckyAddressInfo, xkNo);
            //caseSampleInfoList = getXkCaseService.getCaseByXkNo(xkNo);
        }catch(Exception ex){
            logger.error("根据现勘号调取案件信息时出现错误！", ex);
        }


        List<LimsSampleInfoDna> limsSampleInfoListXk = (List<LimsSampleInfoDna>)caseSampleInfoList.get("limsSampleInfoList");

        //根据案件id查询物证检材信息  并和现堪返回的物证信息作对比
        List<LimsSampleInfoDna> limsSampleInfoDnaListWz = new ArrayList<>();
        LimsSampleInfoDna limsSampleInfoDna = new LimsSampleInfoDna();
        limsSampleInfoDna.setCaseId(caseId);
        limsSampleInfoDna.setConsignmentId(consignmentId);
        List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaService.selectByCaseIdAndWz(limsSampleInfoDna);

        List<String> l1=new ArrayList<>();
        Map<String,Integer> map=new HashMap<>();
        for(int i=0;i<limsSampleInfoListXk.size();i++){
            l1.add(limsSampleInfoListXk.get(i).getEvidenceNo());
            map.put(limsSampleInfoListXk.get(i).getEvidenceNo(),i);
        }
        List<String> l2=new ArrayList<>();
        for(int j=0;j<limsSampleInfoDnaList.size();j++){
            l2.add(limsSampleInfoDnaList.get(j).getEvidenceNo());
        }
        l1.removeAll(l2);
        for(int m=0;m<l1.size();m++){
            limsSampleInfoDnaListWz.add(limsSampleInfoListXk.get(map.get(l1.get(m))));
        }

        //对物证检材进行排序
        if(null != limsSampleInfoDnaListWz && limsSampleInfoDnaListWz.size() > 0){
            Collections.sort(limsSampleInfoDnaListWz , new Comparator<LimsSampleInfoDna>() {
                @Override
                public int compare(LimsSampleInfoDna l1, LimsSampleInfoDna l2) {
                    String evidenceNo1 = l1.getEvidenceNo().substring(8);
                    String evidenceNo2 = l2.getEvidenceNo().substring(8);
                    BigDecimal no1 = new BigDecimal(evidenceNo1);
                    BigDecimal no2 = new BigDecimal(evidenceNo2);
                    //相减
                    BigDecimal diff = no1.subtract(no2);
                    if (diff.intValue() > 0) {
                        return 1;
                    }else if (diff.intValue() < 0) {
                        return -1;
                    }
                    return 0; //相等为0
                }
            });
        }
        limsSampleInfoDnaListWz.addAll(limsSampleInfoDnaList);

        //根据案件id查询被鉴定人样本信息  并和现堪返回的被鉴定人样本信息作对比
        List<LimsSampleInfoDna> LimsSampleInfoDnaybXk = (List<LimsSampleInfoDna>)caseSampleInfoList.get("limsPersonSampleInfoList");
        //根据案件id和委托id查询被鉴定人信息
        LimsPersonInfo limsPersonInfos = new LimsPersonInfo();
        limsPersonInfos.setCaseId(caseId);
        limsPersonInfos.setConsignmentId(consignmentId);
        List<LimsPersonInfo> limsPersonInfoList2 = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfos);
        LimsSampleInfoDna limsSampleInfoDnayb = new LimsSampleInfoDna();
        limsSampleInfoDnayb.setCaseId(caseId);
        limsSampleInfoDnayb.setConsignmentId(consignmentId);
        //根据案件id和委托id查询样本信息
        List<LimsSampleInfoDna> limsSampleInfoDnaybList1 = limsSampleInfoDnaService.selectByCaseIdAndRy(limsSampleInfoDnayb);
        //获取样本对应的被鉴定人的样本关联人名称
        if(null != limsPersonInfoList2 && limsPersonInfoList2.size() > 0 && null != limsSampleInfoDnaybList1 && limsSampleInfoDnaybList1.size() > 0){
            for(LimsPersonInfo limsPersonInfo1:limsPersonInfoList2){
                for(LimsSampleInfoDna sampleInfoDna2:limsSampleInfoDnaybList1){
                    if(limsPersonInfo1.getPersonId().equals(sampleInfoDna2.getLinkId())){
                        sampleInfoDna2.setPersonName(limsPersonInfo1.getPersonName());
                    }
                }
            }
        }
        //判断样本list不为空
        if(null != limsSampleInfoDnaybList1 && limsSampleInfoDnaybList1.size() > 0){
            for(int g=0; g<limsSampleInfoDnaybList1.size();g++){
                String sampleName = limsSampleInfoDnaybList1.get(g).getSampleName();
                String sampleType = limsSampleInfoDnaybList1.get(g).getSampleType();
                for(int h=0; h<LimsSampleInfoDnaybXk.size();h++){
                    String sampleName1 = LimsSampleInfoDnaybXk.get(h).getSampleName();
                    String sampleType1 = LimsSampleInfoDnaybXk.get(h).getSampleType();
                    //根据样本类型和样本名称进行筛选,
                    if(StringUtils.isNotBlank(sampleName) && StringUtils.isNotBlank(sampleName1)){
                        if(StringUtils.isNotBlank(sampleType) && StringUtils.isNotBlank(sampleType1)){
                            if((sampleName).equals(sampleName1) && (sampleType).equals(sampleType1)){
                                LimsSampleInfoDnaybXk.remove(h);
                                break;
                            }
                        }
                    }
                }
            }
            if(null != LimsSampleInfoDnaybXk && LimsSampleInfoDnaybXk.size()>0){
                limsSampleInfoDnaybList1.addAll(LimsSampleInfoDnaybXk);
            }
        }else{
            limsSampleInfoDnaybList1 = LimsSampleInfoDnaybXk;
        }


        //根据案件id查询被鉴定人信息  并和现堪返回的被鉴定人信息作对比
        //根据现堪编号获取被鉴定人信息list
        List<LimsPersonInfo> limsPersonInfoListXk = (List<LimsPersonInfo>)caseSampleInfoList.get("limsPersonInfoList");
        LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
        limsPersonInfo.setCaseId(caseId);
        limsPersonInfo.setConsignmentId(consignmentId);
        //根据案件id和委托id查询被鉴定人信息list
        List<LimsPersonInfo> limsPersonInfoList1 = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfo);
        //根据用户身份证号，用户名，用户类型筛选
        if(null != limsPersonInfoList1 && limsPersonInfoList1.size() > 0 ){
            for(int i=0;i<limsPersonInfoList1.size();i++){
                String xkPersonName = limsPersonInfoList1.get(i).getPersonName();
                String xkPersonType = limsPersonInfoList1.get(i).getPersonType();
                String xkPersonIdCard = limsPersonInfoList1.get(i).getPersonIdCard();
                for(int j=0;j<limsPersonInfoListXk.size();j++){
                    String xkPersonName1 = limsPersonInfoListXk.get(j).getPersonName();
                    String xkPersonType1 = limsPersonInfoListXk.get(j).getPersonType();
                    String xkPersonIdCard1 = limsPersonInfoListXk.get(j).getPersonIdCard();
                    String personId1 = limsPersonInfoListXk.get(j).getPersonId();

                    //先根据身份证号筛选,
                    if(StringUtils.isNotBlank(xkPersonIdCard) && StringUtils.isNotBlank(xkPersonIdCard1)){
                        if(xkPersonIdCard.equals(xkPersonIdCard1)){
                            for(LimsSampleInfoDna limsSampleInfoDna1:limsSampleInfoDnaybList1){
                                if(null != limsSampleInfoDna1){
                                    if(StringUtils.isNotBlank(limsSampleInfoDna1.getLinkId())){
                                        if(StringUtils.isNotBlank(personId1)){
                                            if((personId1).equals(limsSampleInfoDna1.getLinkId())){
                                                limsSampleInfoDna1.setLinkId(limsPersonInfoList1.get(i).getPersonId());
                                            }
                                        }
                                    }
                                }
                            }
                            limsPersonInfoListXk.remove(j);
                            break;
                        }
                    }else{
                        if(StringUtils.isNotBlank(xkPersonName) && StringUtils.isNotBlank(xkPersonName1)){
                            if(StringUtils.isNotBlank(xkPersonType) && StringUtils.isNotBlank(xkPersonType1)){
                                if(xkPersonName.equals(xkPersonName1) && xkPersonType.equals(xkPersonType1)){
                                    for(LimsSampleInfoDna limsSampleInfoDna1:limsSampleInfoDnaybList1){
                                        if(null != limsSampleInfoDna1){
                                            if(StringUtils.isNotBlank(limsSampleInfoDna1.getLinkId())){
                                                if((limsPersonInfoListXk.get(j).getPersonId()).equals(limsSampleInfoDna1.getLinkId())){
                                                    limsSampleInfoDna1.setLinkId(limsPersonInfoList1.get(i).getPersonId());
                                                }
                                            }
                                        }
                                    }
                                    limsPersonInfoListXk.remove(j);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if(null != limsPersonInfoListXk && limsPersonInfoListXk.size() > 0 ){
                limsPersonInfoList1.addAll(limsPersonInfoListXk);
            }
        }else{
            limsPersonInfoList1=limsPersonInfoListXk;
        }


        result.put("limsSampleInfoDnaybList", limsSampleInfoDnaybList1);
        result.put("limscaseInfo", caseSampleInfoList.get("limsCaseInfo"));
        result.put("limsPersonInfoList", limsPersonInfoList1);
        result.put("limsSampleInfoDnaListWz", limsSampleInfoDnaListWz);
        result.put("date", limsSampleInfoDnaListWz.size());
        return result;
    }


    /**
     * 查询添加补送的案件记录并调到添加补送页面
     * Auth:liuchang
     * Date:2020-07-28
     * @param request
     * @param response
     * @param caseId
     * @return
     */
    @RequestMapping("/replacementByCaseId")
    public ModelAndView replacementByCaseId(HttpServletRequest request, HttpServletResponse response, String caseId,HttpSession session) {
        ModelAndView modelAndView = initModelAndView();
        try {
            Subject subject = SecurityUtils.getSubject(); //获取当前登录人信息 SecurityUtils:shiro 安全认证
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            operateUser.setStatusId("1");
            if(operateUser == null){
                modelAndView.setViewName("redirect:/login.html?timeoutFlag=1");
                return  modelAndView;
            }

            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);//根据案件id查询案件信息
            List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectByCaseId(caseId); //物证信息
            List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaService.selectByCaseId(limsCaseInfo.getCaseId());
            List<LimsSampleInfoDna> limsSampleInfoDnaList2 = limsSampleInfoDnaService.selectYbByCaseId(limsCaseInfo.getCaseId());//人员样本信息
            List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoService.selectByCaseId(limsCaseInfo.getCaseId());//人员信息

            LimsConsignmentInfo consignmentInfo = null;
            for(LimsConsignmentInfo limsConsignmentInfo : consignmentInfoList){
                if(limsConsignmentInfo.getAppendFlag().equals("0")){
                    consignmentInfo = limsConsignmentInfo;
                }
            }
            modelAndView.addObject("limsCaseInfo", limsCaseInfo);//案件信息
            modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());//现勘编号
            modelAndView.addObject("areaOrgCode", consignmentInfo == null ? "" : consignmentInfo.getAreaOrgCode());//所属辖区code

            //创建orgInfoList鉴定中心的选择，可以多选
            List<OrgInfo> orgInfos = new ArrayList<>();
            if(consignmentInfo != null && StringUtils.isNotBlank(consignmentInfo.getAcceptOrgId())){
                OrgInfo orgInfo1 = orgInfoService.selectByPrimaryKey(consignmentInfo.getAcceptOrgId());
                orgInfos.add(orgInfo1);
            }
            modelAndView.addObject("orgInfos",orgInfos);

            LimsConsignmentInfo limsConsignmentInfo = new LimsConsignmentInfo();
            limsConsignmentInfo.setConsignmentNo("委托书编号自动生成");

            //补送的时候委托人默认和主案件一样
            limsConsignmentInfo.setDelegator1Id(consignmentInfo.getDelegator1Id());
            limsConsignmentInfo.setDelegator2Id(consignmentInfo.getDelegator2Id());
            limsConsignmentInfo.setDelegator1Position(consignmentInfo.getDelegator1Position());
            limsConsignmentInfo.setDelegator2Position(consignmentInfo.getDelegator2Position());
            limsConsignmentInfo.setDelegator1PaperworkNo(consignmentInfo.getDelegator1PaperworkNo());
            limsConsignmentInfo.setDelegator2PaperworkNo(consignmentInfo.getDelegator2PaperworkNo());
            limsConsignmentInfo.setDelegator1PhoneNumber(consignmentInfo.getDelegator1PhoneNumber());
            limsConsignmentInfo.setDelegator2PhoneNumber(consignmentInfo.getDelegator2PhoneNumber());

            modelAndView.addObject("consignatioInfo", limsConsignmentInfo);

            //根据现勘编号查询现勘数据
        /*
            1、判断该现勘号是否已经获取；
            2、根据当前登录人所在单位获取对应的现勘接口信息；
            3、根据[2]中获取的现勘信息以及待调取信息的K号，获取数据；
            4、如果指定K号在现勘系统中不存在，则跳转到错误提示页面：提示现勘号不存在，让用户重新输入现勘号。
         */

            OrgInfo xcOrg = orgInfoService.selectByPrimaryKey(operateUser.getOrgId());
            //如果登录人时派出所级别，则获取对应分局
//            if(orgInfo.getOrgLevel().equals(Constants.ORG_LEVEL_PAICHUSUO)){
//                xcOrg = orgInfoService.selectByPrimaryKey(orgInfo.getParentId());
//            }

            XckyAddressInfo xckyAddressInfo = xckyAddressInfoService.selectByOrgId(xcOrg.getOrgId());
            if(xckyAddressInfo == null){
                xckyAddressInfo = xckyAddressInfoService.selectDefault();
            }

            Map<String, Object> caseSampleInfoList = null;
            try {
                caseSampleInfoList = xckyWebService.getXckyInfoByKno(xckyAddressInfo, limsCaseInfo.getCaseXkNo());
            }catch(Exception ex){
                logger.error("根据现勘号调取案件信息时出现错误！", ex);
            }

            if (caseSampleInfoList == null) {
                /*// 跳转到错误提示页面：提示现勘号不存在，让用户重新输入现勘号。
                modelAndView.addObject("errMsg", "现勘号" + limsCaseInfo.getCaseXkNo() + "不存在，请核实后重新获取！");
                modelAndView.setViewName("delegationReg/dnaXkErr");
                return modelAndView;*/

//                modelAndView.addObject("limsSampleInfoList", limsSampleInfoDnaList);
//                modelAndView.addObject("sampleInfoDnaList", limsSampleInfoDnaList2);
//                modelAndView.addObject("limsPersonInfoList", limsPersonInfoList);
            }else {

                LimsCaseInfo limsCaseInfoXk = (LimsCaseInfo) caseSampleInfoList.get("limsCaseInfo");
                List<LimsSampleInfoDna> limsSampleInfoListXk = (List<LimsSampleInfoDna>) caseSampleInfoList.get("limsSampleInfoList");
                List<LimsSampleInfoDna> limsPersonSampleInfoListXk = (List<LimsSampleInfoDna>) caseSampleInfoList.get("limsPersonSampleInfoList");
                List<LimsPersonInfo> limsPersonInfoListXk = (List<LimsPersonInfo>) caseSampleInfoList.get("limsPersonInfoList");

                modelAndView.addObject("limsCaseInfoXk", limsCaseInfoXk);
                modelAndView.addObject("limsSampleInfoListXk", limsSampleInfoListXk);


                //根据案件id查询物证检材信息  并和现堪返回的物证信息作对比
                List<LimsSampleInfoDna> limsSampleInfoDnaListWz = new ArrayList<>();

                List<String> l1 = new ArrayList<>();
                Map<String, Integer> map = new HashMap<>();
                if (null != limsSampleInfoListXk && limsSampleInfoListXk.size() > 0) {
                    for (int i = 0; i < limsSampleInfoListXk.size(); i++) {
                        l1.add(limsSampleInfoListXk.get(i).getEvidenceNo());
                        map.put(limsSampleInfoListXk.get(i).getEvidenceNo(), i);
                    }
                    List<String> l2 = new ArrayList<>();
                    for (int j = 0; j < limsSampleInfoDnaList.size(); j++) {
                        l2.add(limsSampleInfoDnaList.get(j).getEvidenceNo());
                    }
                    l1.removeAll(l2);
                    for (int m = 0; m < l1.size(); m++) {
                        limsSampleInfoDnaListWz.add(limsSampleInfoListXk.get(map.get(l1.get(m))));
                    }
                }
                modelAndView.addObject("limsSampleInfoList", limsSampleInfoDnaListWz);

                //根据案件id查询人员样本信息  并和现堪返回的物证信息作对比
                List<LimsSampleInfoDna> limsSampleInfoDnaListYb = new ArrayList<>();

                List<String> l3 = new ArrayList<>();
                Map<String, Integer> map1 = new HashMap<>();
                if (null != limsPersonSampleInfoListXk && limsPersonSampleInfoListXk.size() > 0) {
                    for (int i = 0; i < limsPersonSampleInfoListXk.size(); i++) {
                        l3.add(limsPersonSampleInfoListXk.get(i).getSampleName());
                        map1.put(limsPersonSampleInfoListXk.get(i).getSampleName(), i);
                    }

                    List<String> l4 = new ArrayList<>();
                    if (null != limsSampleInfoDnaList2 && limsSampleInfoDnaList2.size() > 0) {
                        for (int j = 0; j < limsSampleInfoDnaList2.size(); j++) {
                            l4.add(limsSampleInfoDnaList2.get(j).getSampleName());
                        }
                    }
                    l3.removeAll(l4);
                    for (int m = 0; m < l3.size(); m++) {
                        limsSampleInfoDnaListYb.add(limsPersonSampleInfoListXk.get(map1.get(l3.get(m))));
                    }
                    //如果人员类型为03 则默认为事主样本
                    for (LimsPersonInfo  limsPersonInfo :limsPersonInfoListXk){
                        if (limsPersonInfo!=null && StringUtils.isNotBlank(limsPersonInfo.getPersonType())){
                            for (LimsSampleInfoDna  sampleInfoDna2: limsSampleInfoDnaListYb){
                                if (sampleInfoDna2!=null && sampleInfoDna2.getPersonName().equals(limsPersonInfo.getPersonName())){
                                    if (limsPersonInfo.getPersonType().equals("03")){
                                        sampleInfoDna2.setCoreVictimStats(Constants.CORE_VICTIM_STATS_TRUE);
                                    }else{
                                        sampleInfoDna2.setCoreVictimStats(Constants.CORE_VICTIM_STATS_FLASE);
                                    }
                                }
                            }
                        }
                    }
                }
                modelAndView.addObject("sampleInfoDnaList", limsSampleInfoDnaListYb);


                //根据案件id查询人员信息  并和现堪返回的人员信息作对比
                List<LimsPersonInfo> limsPersonInfoListK = new ArrayList<>();

                List<String> l5 = new ArrayList<>();
                Map<String, Integer> map2 = new HashMap<>();
                if (null != limsPersonInfoListXk && limsPersonInfoListXk.size() > 0) {
                    for (int i = 0; i < limsPersonInfoListXk.size(); i++) {
                        l5.add(limsPersonInfoListXk.get(i).getPersonName());
                        map2.put(limsPersonInfoListXk.get(i).getPersonName(), i);
                    }

                    List<String> l6 = new ArrayList<>();
                    for (int j = 0; j < limsPersonInfoList.size(); j++) {
                        l6.add(limsPersonInfoList.get(j).getPersonName());
                    }
                    l5.removeAll(l6);
                    for (int m = 0; m < l5.size(); m++) {
                        limsPersonInfoListK.add(limsPersonInfoListXk.get(map2.get(l5.get(m))));
                    }
                }

                modelAndView.addObject("limsPersonInfoList", limsPersonInfoListK);
            }

            session.setAttribute("user",operateUser);
            modelAndView.setViewName("caseQuery/replacement");
        } catch (Exception ex) {
            logger.error("添加补送页面案件信息查询失败："+ex);
        }
        return modelAndView;
    }

    /**
     * 添加委托补送信息
     * @param paramsData
     * @return
     */
    @RequestMapping(value="/submitReplacement", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> submitReplacement(@RequestParam(value = "paramsData")String paramsData,HttpServletRequest request) {
        Map<String,Object> map=new HashMap();
        String url="";
        try {
            JSONObject str = JSON.parseObject(paramsData);
            //委托信息
            LimsConsignmentInfo consignatioInfo = new LimsConsignmentInfo();
            //案件信息
            LimsCaseInfo caseInfoDna = new LimsCaseInfo();
            //被鉴定人信息已经对应的样本信息
            List<LimsPersonInfo> limsPersonInfoList = new ArrayList<>();
            //物证检材信息
            List<LimsSampleInfoDna> sampleInfoDnaList = new LinkedList<>();
            //鉴定中心主键id
            String evaluationCenterId = "";
            String orgname = "";

            //解析前台传来的数据
            if(str.containsKey("evaluationCenter")){
                evaluationCenterId = str.get("evaluationCenter").toString();
                logger.info("evaluationCenterId = "+evaluationCenterId);
            }
            if(str.containsKey("consignmentInfo")) {
                consignatioInfo = JSON.parseObject(str.get("consignmentInfo").toString(), LimsConsignmentInfo.class);
                logger.info("consignatioInfo = "+consignatioInfo);
            }

            if(str.containsKey("caseInfoDna")) {
                caseInfoDna = JSON.parseObject(str.get("caseInfoDna").toString(), LimsCaseInfo.class);
                logger.info("caseInfoDna = "+caseInfoDna);
            }

            if(str.containsKey("limsPersonInfoList")) {
                limsPersonInfoList = (List<LimsPersonInfo>) JSON.parseArray(str.get("limsPersonInfoList").toString(), LimsPersonInfo.class);
            }

            if(str.containsKey("sampleInfoDnaList")) {
                sampleInfoDnaList = (List<LimsSampleInfoDna>) JSON.parseArray(str.get("sampleInfoDnaList").toString(), LimsSampleInfoDna.class);
                logger.info("sampleInfoDnaList = "+sampleInfoDnaList);

            }
//            if(str.containsKey("orgname")) {
//                orgname = str.get("orgname").toString();
//            }

            DelegateDataModel delegateDataModel = new DelegateDataModel();
            delegateDataModel.setCaseInfoDna(caseInfoDna);
            delegateDataModel.setLimsPersonInfoList(limsPersonInfoList);
            delegateDataModel.setSampleInfoDnaList(sampleInfoDnaList);
            delegateDataModel.setConsignatioInfo(consignatioInfo);

            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            if(operateUser == null){
                map.put("success",false);
                map.put(url,"/login.html?timeoutFlag=1");
                return  map;
            }

            //添加委托信息
            Map<String, String> result = limsCaseInfoService.submitReplacement(delegateDataModel, operateUser,evaluationCenterId);
            map.put("caseId",result == null ? "" : (result.get("caseId")==null?"":result.get("caseId")));
            map.put("consignmentId",result == null ? "" : (result.get("consignmentId")==null?"":result.get("consignmentId")));
            map.put("success",true);

//            if (0 == isAppInform){ //1开启 0默认关闭
//                String date = DateUtils.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
//                CallThirdpartyInfo bean = new CallThirdpartyInfo();
//                //发起人信息
//                bean.setUserid(operateUser.getUserId());
//                bean.setUserName(operateUser.getLoginName());
//                bean.setOrgid(operateUser.getOrgId());
//                bean.setIp(IpAddressUtils.getIpAddr(request));
//                //接收人
//                List<String> phones = new ArrayList<>();
//                if (null != consignatioInfo.getDelegator1PhoneNumber())
//                    phones.add(consignatioInfo.getDelegator1PhoneNumber());
//                if (null != consignatioInfo.getDelegator2PhoneNumber())
//                    phones.add(consignatioInfo.getDelegator2PhoneNumber());
//                bean.setPhones(phones);
//                String countName = consignatioInfo.getDelegator1Name()+","+consignatioInfo.getDelegator2Name();
//                ArrayList<MobileNews> mobileNewsList = new ArrayList<>();
//                MobileNews count = new MobileNews();
//                count.setId(UuidUtil.generateUUID());
//                count.setTitle(caseInfoDna.getCaseName());
//                count.setContent(CallThirdpartyTool.Content(5,countName,consignatioInfo.getDelegateOrgName(),orgname));
//                count.setState(0);
//                count.setCreateDatetime(date);
//                count.setUpdateDatetime(date);
//                count.setType(5);
//                count.setUserid(consignatioInfo.getDelegator1Id());
//                count.setUsername(consignatioInfo.getDelegator1Name());
//                count.setMessageType(1);
//                count.setUserOrg(consignatioInfo.getDelegateOrgCode());
//                count.setCaseId(consignatioInfo.getCaseId());
//                MobileNews count2 = new MobileNews();
//                count2.setId(UuidUtil.generateUUID());
//                count2.setTitle(caseInfoDna.getCaseName());
//                count2.setContent(CallThirdpartyTool.Content(5,countName,consignatioInfo.getDelegateOrgName(),orgname));
//                count2.setState(0);
//                count2.setCreateDatetime(date);
//                count2.setUpdateDatetime(date);
//                count2.setType(5);
//                count2.setUserid(consignatioInfo.getDelegator2Id());
//                count2.setUsername(consignatioInfo.getDelegator2Name());
//                count2.setUserOrg(consignatioInfo.getDelegateOrgCode());
//                count2.setMessageType(1);
//                count2.setCaseId(consignatioInfo.getCaseId());
//                MobileNews count3 = new MobileNews();
//                count3.setId(UuidUtil.generateUUID());
//                count3.setTitle(caseInfoDna.getCaseName());
//                count3.setContent(CallThirdpartyTool.Content(5,countName,consignatioInfo.getDelegateOrgName(),orgname));
//                count3.setState(0);
//                count3.setCreateDatetime(date);
//                count3.setUpdateDatetime(date);
//                count3.setType(5);
//                count3.setUserid("pc");
//                count3.setUserOrg(consignatioInfo.getDelegateOrgCode());
//                count3.setCaseId(consignatioInfo.getCaseId());
//                count3.setMessageType(2);
//                mobileNewsList.add(count);
//                mobileNewsList.add(count2);
//                mobileNewsList.add(count3);
//                //信息存储到消息表
//                for (MobileNews MobileNews: mobileNewsList) {
//                    int insert = mobileNewsMapper.insert(MobileNews);
//                    logger.info("===成功存储消息:"+insert+"条.");
//                }
//                bean.setContent(mobileNewsList);
//                //第三方唤醒app
//                LogRecordInfo logRecordInfo = CallThirdpartyTool.CallThirdpartyTool(AppUrl, bean);
//                logger.info("===唤醒app结束开始存储日志");
//                logRecordMapper.insert(logRecordInfo);
//
//            }


        }catch(Exception ex){
            map.put("error",false);
            logger.error("委托登记保存失败"+ex.getMessage());
        }
        return map;
    }


    /**
     * 查询补送记录
     * @param request
     * @param caseId
     * @return
     */
    @RequestMapping("/queryReplacementRecord")
    @ResponseBody
    public Map<String,Object> queryReplacementRecord(HttpServletRequest request, String caseId) {
        Map<String,Object> map = new HashMap<>();
        try {
            LimsCaseInfoVo limsCaseInfoVo = new LimsCaseInfoVo();
            limsCaseInfoVo.getEntity().setCaseId(caseId);
            List<LimsCaseInfoVo> limsCaseInfoVoList = limsCaseInfoService.selectReplacementRecord(limsCaseInfoVo);
            map.put("limsCaseInfoVoList", limsCaseInfoVoList);
            map.put("code", 0);
            map.put("message", "补送记录查询成功！");
        }catch(Exception ex){
            logger.info("补送记录查询失败"+ex);
            map.put("code", 1);
            map.put("message", "补送记录查询失败！");
        }
        return map;
    }

    /**
     * 查询补送记录详情
     * @param request
     * @param consignmentId
     * @param caseId
     * @return
     */
    @RequestMapping("/queryReplacementRecordDetail")
    public ModelAndView queryReplacementRecordDetail(HttpServletRequest request, String consignmentId, String caseId) {
        ModelAndView modelAndView = initModelAndView();

        //根据委托id查询委托信息
        LimsConsignmentInfo consignatioInfo = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
        //根据案件id查询案件信息
        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
        //根据案件id查询人员信息
        LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
        limsPersonInfo.setConsignmentId(consignmentId);
        limsPersonInfo.setCaseId(caseId);
        List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfo);
        //根据案件id查询样本信息
        LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
        sampleInfoDna.setCaseId(caseId);
        sampleInfoDna.setConsignmentId(consignmentId);
        List<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectByCaseIdAndRy(sampleInfoDna);
        if(null != limsPersonInfoList && limsPersonInfoList.size() > 0 && null != sampleInfoDnaList && sampleInfoDnaList.size() > 0){
            for(LimsPersonInfo limsPersonInfo1:limsPersonInfoList){
                for(LimsSampleInfoDna sampleInfoDna2:sampleInfoDnaList){
                    if(limsPersonInfo1.getPersonId().equals(sampleInfoDna2.getLinkId())){
                        sampleInfoDna2.setPersonName(limsPersonInfo1.getPersonName());
                    }
                }
            }
        }

        //根据案件id查询物证信息
        List<LimsSampleInfoDna> sampleInfoDnaList1 = limsSampleInfoDnaService.selectByCaseIdAndWz(sampleInfoDna);

        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setOrgName(consignatioInfo.getDelegateOrgName());
        modelAndView.addObject("orgInfo", orgInfo);
        modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
        modelAndView.addObject("consignatioInfo", consignatioInfo);
        modelAndView.addObject("limsCaseInfo", limsCaseInfo);
        modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
        modelAndView.addObject("limsPersonInfoList", limsPersonInfoList);
        modelAndView.addObject("sampleInfoDnaList", sampleInfoDnaList);
        modelAndView.addObject("limsSampleInfoList", sampleInfoDnaList1);
        modelAndView.setViewName("delegationReg/replacementRecordDetail");
        return modelAndView;
    }


    /**
     * 类说明：查询非案件委托登记详情信息--liuchang
     * @Date:2020-07-28
     * @param request
     * @param consignmentId
     * @param caseId
     * @return
     */
    @RequestMapping("/editNonCaseAndBring")
    @SuppressWarnings("all")
    public ModelAndView editNonCaseAndBring(HttpServletRequest request, String consignmentId, String caseId,HttpSession session) {
        ModelAndView modelAndView = initModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        operateUser.setStatusId("2");

        DictItem dictItem = new DictItem();

        //案件性质
        dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
        dictItem.setParentId(null); //一级菜单不包含parentId
        List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
        modelAndView.addObject("casePropertyList",casePropertyList);
        List<DictItem> casePropertyListlv1 = new ArrayList<>();//初始化一级案件性质
        List<DictItem> casePropertyListlv2 = new ArrayList<>();//初始化二级案件性质
        for (DictItem dictItem1: casePropertyList){
            if (StringUtils.isBlank(dictItem1.getParentId())){
                casePropertyListlv1.add(dictItem1);
            }else{
                casePropertyListlv2.add(dictItem1);
            }
        }
        modelAndView.addObject("casePropertyList1", casePropertyListlv1);//一级案件性质
//        List<DictItem> casePropertyList1 = new LinkedList<>();
//            dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
//        List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
//        for(DictItem dictItem1:casePropertyList){
//            if(("06").equals(dictItem1.getDictCode())){
//                casePropertyList1.add(dictItem1);
//            }else if(("09").equals(dictItem1.getDictCode())){
//                casePropertyList1.add(dictItem1);
//            } else if(("10").equals(dictItem1.getDictCode())){
//                casePropertyList1.add(dictItem1);
//            } else if(("11").equals(dictItem1.getDictCode())) {
//                casePropertyList1.add(dictItem1);
//            }else  if(("19").equals(dictItem1.getDictCode())) {
//                casePropertyList1.add(dictItem1);
//            }else if(("20").equals(dictItem1.getDictCode())){
//                casePropertyList1.add(dictItem1);
//            }
//        }
 //       modelAndView.addObject("casePropertyList",casePropertyList1);

        //根据委托id查询委托信息
        LimsConsignmentInfo consignatioInfo = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
        //根据案件id查询案件信息
        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
        //根据案件id查询人员信息
        LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
        limsPersonInfo.setConsignmentId(consignmentId);
        limsPersonInfo.setCaseId(caseId);
        List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfo);
        //根据案件id查询样本信息
        LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
        sampleInfoDna.setCaseId(caseId);
        sampleInfoDna.setConsignmentId(consignmentId);
        List<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectByCaseIdAndRy(sampleInfoDna);
        if(null != limsPersonInfoList && limsPersonInfoList.size() > 0 && null != sampleInfoDnaList && sampleInfoDnaList.size() > 0){
            for(LimsPersonInfo limsPersonInfo1:limsPersonInfoList){
                for(LimsSampleInfoDna sampleInfoDna2:sampleInfoDnaList){
                    if(limsPersonInfo1.getPersonId().equals(sampleInfoDna2.getLinkId())){
                        sampleInfoDna2.setPersonName(limsPersonInfo1.getPersonName());
                    }
                    //根据人员关联样本信息获取数据展示--liuchang
                    limsPersonInfo1.setSampleTypeName(sampleInfoDna2.getSampleTypeName() == null? "":sampleInfoDna2.getSampleTypeName());//样本类型名称
                    limsPersonInfo1.setSampleName(sampleInfoDna2.getSampleName() == null?"":sampleInfoDna2.getSampleName());//样本名称
                    limsPersonInfo1.setExtractDatetime(sampleInfoDna2.getExtractDatetime() == null? limsPersonInfo1.getCreateDatetime():sampleInfoDna2.getExtractDatetime());//提取时间
                    limsPersonInfo1.setSamplePackingName(sampleInfoDna2.getSamplePackingName() == null?"":sampleInfoDna2.getSamplePackingName());//包装方法
                }
            }
        }

        //根据案件id查询物证信息
        List<LimsSampleInfoDna> sampleInfoDnaList1 = limsSampleInfoDnaService.selectByCaseIdAndWzNon(sampleInfoDna);

        OrgInfo orgInfoYn = orgInfoService.selectByPrimaryKey(operateUser.getOrgId());

        OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(operateUser.getOrgId());
        //创建orgInfoList点击鉴定中心的选择，可以多选
//        List<OrgInfo> orgInfos = new ArrayList<>();
        if(StringUtils.isNotBlank(orgInfo.getOrgQualification())){
            //查询鉴定中心
//            orgInfos.add(orgInfo);

            orgInfo = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
//            orgInfos.add(orgInfo);
        }
        else if(orgInfo.getOrgId().indexOf("110024") != -1){
            orgInfo = orgInfoService.selectByPrimaryKey("110115000000");
//            orgInfos.add(orgInfo);

//            orgInfo = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
//            orgInfos.add(orgInfo);
        }
        else{
            //查询法医鉴定中心
            orgInfo = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
//            orgInfos.add(orgInfo);
        }
        if(limsCaseInfo.getCaseProperty()!=null){
            //根据dickCode 查当前字典
            DictItem case_property = dictItemService.selectDictNameByTypeCode("CASE_PROPERTY", limsCaseInfo.getCaseProperty());
            if (case_property.getParentId()!=null){
                DictItem dictItem1 = dictItemService.selectByPrimaryKey(case_property.getParentId());
                String dictCode = dictItem1.getDictCode();
                limsCaseInfo.setCaseProperty(dictCode);
                limsCaseInfo.setCasePropertylv2(case_property.getDictCode());
                casePropertyListlv2 = dictItemService.selectListByParentId(dictItem1.getDictItemId());
                modelAndView.addObject("casePropertyListLv2", casePropertyListlv2);
            }
        }
        String orgId = operateUser.getOrgId();
        String substring = orgId.substring(0, 6);
        List<DelegateCenterConfig> delegateCenterConfigs = delegateCenterConfigService.selectQualification(substring);

        modelAndView.addObject("orgInfos",delegateCenterConfigs);

        modelAndView.addObject("checkedOrgId", consignatioInfo.getAcceptOrgId());
        modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
        modelAndView.addObject("consignatioInfo", consignatioInfo);
        modelAndView.addObject("limsCaseInfo", limsCaseInfo);
        modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
        modelAndView.addObject("limsPersonInfoList", limsPersonInfoList);
        modelAndView.addObject("sampleInfoDnaList", sampleInfoDnaList);
        modelAndView.addObject("limsSampleInfoList", sampleInfoDnaList1);
        session.setAttribute("user",operateUser);
        modelAndView.setViewName("delegationReg/nonDnaReg");
        return modelAndView;
    }

    /**
     * 查询在逃人员案件委托登记详情信息
     * @param request
     * @param consignmentId
     * @param caseId
     * @return
     */
    @RequestMapping("/editFugitivesReg")
    public ModelAndView editFugitivesReg(HttpServletRequest request, String consignmentId, String caseId,HttpSession session) {
        ModelAndView modelAndView = initModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        operateUser.setStatusId("2");

        DictItem dictItem = new DictItem();

        //案件性质
        dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
        dictItem.setParentId(null); //一级菜单不包含parentId
        List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
        modelAndView.addObject("casePropertyList",casePropertyList);
        List<DictItem> casePropertyListlv1 = new ArrayList<>();//初始化一级案件性质
        List<DictItem> casePropertyListlv2 = new ArrayList<>();//初始化二级案件性质
        for (DictItem dictItem1: casePropertyList){
            if (StringUtils.isBlank(dictItem1.getParentId())){
                casePropertyListlv1.add(dictItem1);
            }else{
                casePropertyListlv2.add(dictItem1);
            }
        }
        modelAndView.addObject("casePropertyList1", casePropertyListlv1);//一级案件性质

        //根据委托id查询委托信息
        LimsConsignmentInfo consignatioInfo = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
        //根据案件id查询案件信息
        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
        //根据案件id查询人员信息
        LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
        limsPersonInfo.setConsignmentId(consignmentId);
        limsPersonInfo.setCaseId(caseId);
        List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfo);
        //区分出在逃人员和在逃人员亲属
        if (ListUtils.isNotNullAndEmptyList(limsPersonInfoList)) {
            List<LimsPersonInfo> fugitivesInfoList = new ArrayList<>();
            List<LimsPersonInfo> fugitivesInfoRelationList = new ArrayList<>();
            for (LimsPersonInfo lpi : limsPersonInfoList) {
                if (Constants.PERSON_TYPE_07.equals(lpi.getPersonType())) {
                    fugitivesInfoList.add(lpi);
                }else if (Constants.PERSON_TYPE_08.equals(lpi.getPersonType())) {
                    //判断目标样本id是否存在
                    if (StringUtils.isNotBlank(lpi.getTargetPersonId())) {
                        FugitivesInfo fugitivesInfo = fugitivesInfoService.selectByPrimaryKey(lpi.getTargetPersonId());
                        //判断在逃人员信息是否存在
                        if (fugitivesInfo != null) {
                            lpi.setFugitivesName(fugitivesInfo.getPersonName());
                            lpi.setFugitivesCard(fugitivesInfo.getPersonCard());
                            lpi.setFugitiveNo(fugitivesInfo.getFugitiveNo());
                        }
                    }
                    fugitivesInfoRelationList.add(lpi);
                }
            }
            modelAndView.addObject("fugitivesInfoList",fugitivesInfoList);
            modelAndView.addObject("fugitivesInfoRelationList",fugitivesInfoRelationList);
        }
        //根据案件id查询样本信息
        LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
        sampleInfoDna.setCaseId(caseId);
        sampleInfoDna.setConsignmentId(consignmentId);
        List<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectByCaseIdAndRy(sampleInfoDna);
        if(null != limsPersonInfoList && limsPersonInfoList.size() > 0 && null != sampleInfoDnaList && sampleInfoDnaList.size() > 0){
            for(LimsPersonInfo limsPersonInfo1:limsPersonInfoList){
                for(LimsSampleInfoDna sampleInfoDna2:sampleInfoDnaList){
                    if(limsPersonInfo1.getPersonId().equals(sampleInfoDna2.getLinkId())){
                        sampleInfoDna2.setPersonName(limsPersonInfo1.getPersonName());
                    }
                }
            }
        }

        //根据案件id查询物证信息
        List<LimsSampleInfoDna> sampleInfoDnaList1 = limsSampleInfoDnaService.selectByCaseIdAndWzNon(sampleInfoDna);

        OrgInfo orgInfoYn = orgInfoService.selectByPrimaryKey(operateUser.getOrgId());

        OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(operateUser.getOrgId());
        //创建orgInfoList点击鉴定中心的选择，可以多选
//        List<OrgInfo> orgInfos = new ArrayList<>();
        if(StringUtils.isNotBlank(orgInfo.getOrgQualification())){
            //查询鉴定中心
//            orgInfos.add(orgInfo);

            orgInfo = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
//            orgInfos.add(orgInfo);
        }
        else if(orgInfo.getOrgId().indexOf("110024") != -1){
            orgInfo = orgInfoService.selectByPrimaryKey("110115000000");
//            orgInfos.add(orgInfo);

//            orgInfo = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
//            orgInfos.add(orgInfo);
        }
        else{
            //查询法医鉴定中心
            orgInfo = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
//            orgInfos.add(orgInfo);
        }
        String orgId = operateUser.getOrgId();
        String substring = orgId.substring(0, 6);
        List<DelegateCenterConfig> delegateCenterConfigs = delegateCenterConfigService.selectQualification(substring);

        modelAndView.addObject("orgInfos",delegateCenterConfigs);

        modelAndView.addObject("checkedOrgId", consignatioInfo.getAcceptOrgId());
        modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
        modelAndView.addObject("consignatioInfo", consignatioInfo);
        modelAndView.addObject("limsCaseInfo", limsCaseInfo);
        modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
        modelAndView.addObject("limsPersonInfoList", limsPersonInfoList);
        modelAndView.addObject("sampleInfoDnaList", sampleInfoDnaList);
        modelAndView.addObject("limsSampleInfoList", sampleInfoDnaList1);
        session.setAttribute("user",operateUser);
        modelAndView.setViewName("delegationReg/fugitivesReg");
        return modelAndView;
    }

    /**
     * 查询添加补送的非案件案件委托记录并调到添加补送页面
     * @param request
     * @param response
     * @param caseId
     * @return
     */
    @RequestMapping("/replacementNonCaseByCaseId")
    public ModelAndView replacementNonCaseByCaseId(HttpServletRequest request, HttpServletResponse response, String caseId,HttpSession session) {
        ModelAndView modelAndView = initModelAndView();
        try {
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            operateUser.setStatusId("1");

            DictItem dictItem = new DictItem();
            //案件性质
//            List<DictItem> casePropertyList1 = new LinkedList<>();
//            dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
//            List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
//            for(DictItem dictItem1:casePropertyList){
//                if(("06").equals(dictItem1.getDictCode())){
//                    casePropertyList1.add(dictItem1);
//                }else if(("09").equals(dictItem1.getDictCode())){
//                    casePropertyList1.add(dictItem1);
//                } else if(("10").equals(dictItem1.getDictCode())){
//                    casePropertyList1.add(dictItem1);
//                } else if(("11").equals(dictItem1.getDictCode())) {
//                    casePropertyList1.add(dictItem1);
//                }else  if(("19").equals(dictItem1.getDictCode())) {
//                    casePropertyList1.add(dictItem1);
//                }else if(("20").equals(dictItem1.getDictCode())){
//                    casePropertyList1.add(dictItem1);
//                }
//            }
//            modelAndView.addObject("casePropertyList",casePropertyList1);

            //案件性质
            dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
            dictItem.setParentId(null); //一级菜单不包含parentId
            List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("casePropertyList",casePropertyList);
            List<DictItem> casePropertyListlv1 = new ArrayList<>();//初始化一级案件性质
            List<DictItem> casePropertyListlv2 = new ArrayList<>();//初始化二级案件性质
            for (DictItem dictItem1: casePropertyList){
                if (StringUtils.isBlank(dictItem1.getParentId())){
                    casePropertyListlv1.add(dictItem1);
                }else{
                    casePropertyListlv2.add(dictItem1);
                }
            }
            modelAndView.addObject("casePropertyList1", casePropertyListlv1);//一级案件性质


            //根据案件id查询案件信息
            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
            List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectByCaseId(caseId);
            modelAndView.addObject("limsCaseInfo", limsCaseInfo);
            modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());

            LimsConsignmentInfo consignmentInfo = null;
            for(LimsConsignmentInfo limsConsignmentInfo : consignmentInfoList){
                if(limsConsignmentInfo.getAppendFlag().equals("0")){
                    consignmentInfo = limsConsignmentInfo;
                }
            }
            modelAndView.addObject("areaOrgCode", consignmentInfo == null ? "" : consignmentInfo.getAreaOrgCode());

            OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(consignmentInfo.getAcceptOrgId());
            //创建orgInfoList点击鉴定中心的选择，可以多选
            List<OrgInfo> orgInfos = new ArrayList<>();
            orgInfos.add(orgInfo);
            modelAndView.addObject("orgInfos",orgInfos);

            LimsConsignmentInfo limsConsignmentInfo = new LimsConsignmentInfo();
            limsConsignmentInfo.setConsignmentNo("委托书编号自动生成");

            //补送的时候委托人默认和主案件一样
            limsConsignmentInfo.setDelegator1Id(consignmentInfo.getDelegator1Id());
            limsConsignmentInfo.setDelegator2Id(consignmentInfo.getDelegator2Id());
            limsConsignmentInfo.setDelegator1Position(consignmentInfo.getDelegator1Position());
            limsConsignmentInfo.setDelegator2Position(consignmentInfo.getDelegator2Position());
            limsConsignmentInfo.setDelegator1PaperworkNo(consignmentInfo.getDelegator1PaperworkNo());
            limsConsignmentInfo.setDelegator2PaperworkNo(consignmentInfo.getDelegator2PaperworkNo());
            limsConsignmentInfo.setDelegator1PhoneNumber(consignmentInfo.getDelegator1PhoneNumber());
            limsConsignmentInfo.setDelegator2PhoneNumber(consignmentInfo.getDelegator2PhoneNumber());

            modelAndView.addObject("consignatioInfo",limsConsignmentInfo);
            session.setAttribute("user",operateUser);
            modelAndView.setViewName("caseQuery/replacementNonCase");
        } catch (Exception ex) {
            logger.error("添加补送页面案件信息查询失败："+ex);
        }
        return modelAndView;
    }


    /**
     * 查询添加补送的在逃人员案件案件委托记录并调到添加补送页面
     * @param request
     * @param response
     * @param caseId
     * @return
     */
    @RequestMapping("/replacementFugitivesCaseByCaseId")
    public ModelAndView replacementFugitivesCaseByCaseId(HttpServletRequest request, HttpServletResponse response, String caseId,HttpSession session) {
        ModelAndView modelAndView = initModelAndView();
        try {
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            operateUser.setStatusId("1");

            DictItem dictItem = new DictItem();
            //案件性质
            dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
            List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("casePropertyList",casePropertyList);

            //根据案件id查询案件信息
            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
            List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectByCaseId(caseId);
            modelAndView.addObject("caseInfo", limsCaseInfo);
            modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());

            LimsConsignmentInfo consignmentInfo = null;
            for(LimsConsignmentInfo limsConsignmentInfo : consignmentInfoList){
                if(limsConsignmentInfo.getAppendFlag().equals("0")){
                    consignmentInfo = limsConsignmentInfo;
                }
            }
            modelAndView.addObject("areaOrgCode", consignmentInfo == null ? "" : consignmentInfo.getAreaOrgCode());

            OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(consignmentInfo.getAcceptOrgId());
            //创建orgInfoList点击鉴定中心的选择，可以多选
            List<OrgInfo> orgInfos = new ArrayList<>();
            orgInfos.add(orgInfo);
            modelAndView.addObject("orgInfos",orgInfos);

            LimsConsignmentInfo limsConsignmentInfo = new LimsConsignmentInfo();
            limsConsignmentInfo.setConsignmentNo("委托书编号自动生成");

            //补送的时候委托人默认和主案件一样
            limsConsignmentInfo.setDelegator1Id(consignmentInfo.getDelegator1Id());
            limsConsignmentInfo.setDelegator2Id(consignmentInfo.getDelegator2Id());
            limsConsignmentInfo.setDelegator1Position(consignmentInfo.getDelegator1Position());
            limsConsignmentInfo.setDelegator2Position(consignmentInfo.getDelegator2Position());
            limsConsignmentInfo.setDelegator1PaperworkNo(consignmentInfo.getDelegator1PaperworkNo());
            limsConsignmentInfo.setDelegator2PaperworkNo(consignmentInfo.getDelegator2PaperworkNo());
            limsConsignmentInfo.setDelegator1PhoneNumber(consignmentInfo.getDelegator1PhoneNumber());
            limsConsignmentInfo.setDelegator2PhoneNumber(consignmentInfo.getDelegator2PhoneNumber());
            limsConsignmentInfo.setPreIdentifyDesc(consignmentInfo.getPreIdentifyDesc());
            limsConsignmentInfo.setReidentifyReason(consignmentInfo.getReidentifyReason());

            modelAndView.addObject("consignmentInfo",limsConsignmentInfo);
            modelAndView.addObject("appendFlag",Constants.APPEND_FLAG_1);

            //在逃人员信息
            FugitivesInfo fugitivesInfo = new FugitivesInfo();
            List<FugitivesInfo> fugitivesInfoList =fugitivesInfoService.selectListByConsignmentId(consignmentInfo.getConsignmentId());
            if (ListUtils.isNotNullAndEmptyList(fugitivesInfoList)) {
                fugitivesInfo = fugitivesInfoList.get(0);
            }
            modelAndView.addObject("fugitivesInfo",fugitivesInfo);
            session.setAttribute("user",operateUser);
            modelAndView.setViewName("delegationReg/fugitivesReg");
        } catch (Exception ex) {
            logger.error("添加补送页面案件信息查询失败："+ex);
        }
        return modelAndView;
    }


    /**
     * 添加非案件委托补送信息
     * @param paramsData
     * @return
     */
    @RequestMapping(value="/submitNonCaseReplacement", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> submitNonCaseReplacement(@RequestParam(value = "paramsData")String paramsData) {
        Map<String,Object> map=new HashMap();
        String url="";
        try {
            JSONObject str = JSON.parseObject(paramsData);
            //委托信息
            LimsConsignmentInfo consignatioInfo = new LimsConsignmentInfo();
            //案件信息
            LimsCaseInfo caseInfoDna = new LimsCaseInfo();
            //被鉴定人信息已经对应的样本信息
            List<LimsPersonInfo> limsPersonInfoList = new ArrayList<>();
            //鉴定中心主键id
            String evaluationCenterId = "";
            //物证检材信息
            List<LimsSampleInfoDna> sampleInfoDnaList = new LinkedList<>();

            //解析前台传来的数据
            if(str.containsKey("evaluationCenter")){
                evaluationCenterId = str.get("evaluationCenter").toString();
            }
            if(str.containsKey("consignmentInfo")) {
                consignatioInfo = JSON.parseObject(str.get("consignmentInfo").toString(), LimsConsignmentInfo.class);
            }

            if(str.containsKey("caseInfoDna")) {
                caseInfoDna = JSON.parseObject(str.get("caseInfoDna").toString(), LimsCaseInfo.class);
            }

            if(str.containsKey("limsPersonInfoList")) {
                limsPersonInfoList = (List<LimsPersonInfo>) JSON.parseArray(str.get("limsPersonInfoList").toString(), LimsPersonInfo.class);
            }

            if(str.containsKey("sampleInfoDnaList")) {
                sampleInfoDnaList = (List<LimsSampleInfoDna>) JSON.parseArray(str.get("sampleInfoDnaList").toString(), LimsSampleInfoDna.class);
            }

            DelegateDataModel delegateDataModel = new DelegateDataModel();
            delegateDataModel.setCaseInfoDna(caseInfoDna);
            delegateDataModel.setLimsPersonInfoList(limsPersonInfoList);
            delegateDataModel.setConsignatioInfo(consignatioInfo);
            delegateDataModel.setSampleInfoDnaList(sampleInfoDnaList);

            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            if(operateUser == null){
                map.put("success",false);
                map.put(url,"/login.html?timeoutFlag=1");
                return  map;
            }

            //添加委托信息
            Map<String, String> result = limsCaseInfoService.submitNonCaseReplacement(delegateDataModel, operateUser,evaluationCenterId);
            map.put("caseId",result == null ? "" : (result.get("caseId")==null?"":result.get("caseId")));
            map.put("consignmentId",result == null ? "" : (result.get("consignmentId")==null?"":result.get("consignmentId")));
            map.put("success",true);
        }catch(Exception ex){
            map.put("error",false);
            logger.error("非案件委托补送信息保存失败"+ex.getMessage());
        }
        return map;
    }


    /**
     * 查询补送记录详情
     * @param request
     * @param consignmentId
     * @param caseId
     * @return
     */
    @RequestMapping("/queryNonCaseReplacementRecordDetail")
    public ModelAndView queryNonCaseReplacementRecordDetail(HttpServletRequest request, String consignmentId, String caseId) {
        ModelAndView modelAndView = initModelAndView();

        //根据委托id查询委托信息
        LimsConsignmentInfo consignatioInfo = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
        //根据案件id查询案件信息
        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
        //根据案件id查询人员信息
        LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
        limsPersonInfo.setConsignmentId(consignmentId);
        limsPersonInfo.setCaseId(caseId);
        List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfo);
        //根据案件id查询样本信息
        LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
        sampleInfoDna.setCaseId(caseId);
        sampleInfoDna.setConsignmentId(consignmentId);
        List<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectByCaseIdAndRy(sampleInfoDna);
        if(null != limsPersonInfoList && limsPersonInfoList.size() > 0 && null != sampleInfoDnaList && sampleInfoDnaList.size() > 0){
            for(LimsPersonInfo limsPersonInfo1:limsPersonInfoList){
                for(LimsSampleInfoDna sampleInfoDna2:sampleInfoDnaList){
                    if(limsPersonInfo1.getPersonId().equals(sampleInfoDna2.getLinkId())){
                        sampleInfoDna2.setPersonName(limsPersonInfo1.getPersonName());
                    }
                }
            }
        }

        //根据案件id查询物证信息
        List<LimsSampleInfoDna> sampleInfoDnaList1 = limsSampleInfoDnaService.selectByCaseIdAndWz(sampleInfoDna);

        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setOrgName(consignatioInfo.getDelegateOrgName());
        modelAndView.addObject("orgInfo", orgInfo);
        modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
        modelAndView.addObject("consignatioInfo", consignatioInfo);
        modelAndView.addObject("limsCaseInfo", limsCaseInfo);
        modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
        modelAndView.addObject("limsPersonInfoList", limsPersonInfoList);
        modelAndView.addObject("sampleInfoDnaList", sampleInfoDnaList);
        modelAndView.addObject("limsSampleInfoList", sampleInfoDnaList1);
        modelAndView.setViewName("delegationReg/nonCaseReplacementRecordDetail");
        return modelAndView;
    }

    /**
     * 查询添加补送的案件记录并调到添加补送页面
     * @param request
     * @param response
     * @param caseId
     * @return
     */
    @RequestMapping("/replacementByCaseIdAndConsignmentId")
    public ModelAndView replacementByCaseIdAndConsignmentId(HttpServletRequest request, HttpServletResponse response, String caseId, String consignmentId) {
        ModelAndView modelAndView = initModelAndView();
        try {

            //根据案件id查询案件信息
            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
            List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectByCaseId(caseId);
            modelAndView.addObject("limsCaseInfo", limsCaseInfo);
            modelAndView.addObject("xkNo", limsCaseInfo.getCaseXkNo());
            modelAndView.addObject("areaOrgCode", ListUtils.isNullOrEmptyList(consignmentInfoList) ? "" : consignmentInfoList.get(0).getAreaOrgCode());

            //根据委托id查询委托信息
            LimsConsignmentInfo consignatioInfo = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
            //根据案件id查询人员信息
            LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
            limsPersonInfo.setConsignmentId(consignmentId);
            limsPersonInfo.setCaseId(caseId);
            List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfo);
            //根据案件id查询样本信息
            LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
            sampleInfoDna.setCaseId(caseId);
            sampleInfoDna.setConsignmentId(consignmentId);
            List<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectByCaseIdAndRy(sampleInfoDna);
            if(null != limsPersonInfoList && limsPersonInfoList.size() > 0 && null != sampleInfoDnaList && sampleInfoDnaList.size() > 0){
                for(LimsPersonInfo limsPersonInfo1:limsPersonInfoList){
                    for(LimsSampleInfoDna sampleInfoDna2:sampleInfoDnaList){
                        if(limsPersonInfo1.getPersonId().equals(sampleInfoDna2.getLinkId())){
                            sampleInfoDna2.setPersonName(limsPersonInfo1.getPersonName());
                        }
                    }
                }
            }
            modelAndView.addObject("consignatioInfo", consignatioInfo);
            modelAndView.addObject("limsPersonInfoList", limsPersonInfoList);
            modelAndView.addObject("sampleInfoDnaList", sampleInfoDnaList);

            //根据现堪编号查询现堪数据
//            Map<String, Object> caseSampleInfoList = getXkCaseService.getCaseByXkNo(limsCaseInfo.getCaseXkNo());
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            //根据现勘编号查询现勘数据
            XckyAddressInfo xckyAddressInfo = xckyAddressInfoService.selectByOrgId(operateUser.getOrgId());
            if(xckyAddressInfo == null){
                xckyAddressInfo = xckyAddressInfoService.selectDefault();
            }

            Map<String, Object> caseSampleInfoList = null;
            try {
                caseSampleInfoList = xckyWebService.getXckyInfoByKno(xckyAddressInfo, limsCaseInfo.getCaseXkNo());
            }catch(Exception ex){
                logger.error("根据现勘号调取案件信息时出现错误！", ex);
            }
            LimsCaseInfo limsCaseInfoXk = (LimsCaseInfo)caseSampleInfoList.get("limsCaseInfo");
            List<LimsSampleInfoDna> limsSampleInfoListXk = (List<LimsSampleInfoDna>)caseSampleInfoList.get("limsSampleInfoList");
            modelAndView.addObject("limsCaseInfoXk", limsCaseInfoXk);
            modelAndView.addObject("limsSampleInfoListXk", limsSampleInfoListXk);

            //根据案件id查询物证检材信息  并和现堪返回的物证信息作对比
            List<LimsSampleInfoDna> limsSampleInfoDnaListWz = new ArrayList<>();
            List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaService.selectByCaseId(limsCaseInfo.getCaseId());

            List<String> l1=new ArrayList<>();
            Map<String,Integer> map=new HashMap<>();
            for(int i=0;i<limsSampleInfoListXk.size();i++){
                l1.add(limsSampleInfoListXk.get(i).getEvidenceNo());
                map.put(limsSampleInfoListXk.get(i).getEvidenceNo(),i);
            }
            List<String> l2=new ArrayList<>();
            for(int j=0;j<limsSampleInfoDnaList.size();j++){
                l2.add(limsSampleInfoDnaList.get(j).getEvidenceNo());
            }
            l1.removeAll(l2);
            for(int m=0;m<l1.size();m++){
                limsSampleInfoDnaListWz.add(limsSampleInfoListXk.get(map.get(l1.get(m))));
            }


            modelAndView.addObject("limsSampleInfoList", limsSampleInfoDnaListWz);
            modelAndView.setViewName("caseQuery/replacement");
        } catch (Exception ex) {
            logger.error("添加补送页面案件信息查询失败："+ex);
        }
        return modelAndView;
    }

    @SuppressWarnings("all")
    private LimsCaseInfoVo resetCaseInfoQuery (LimsCaseInfoVo query) {
        LimsCaseInfo entity = query.getEntity();
        if (StringUtils.isNotBlank(entity.getCaseXkNo())){
            entity.setCaseXkNo(entity.getCaseXkNo().replaceAll("\\s*","")); //案件现勘编号
        }else{
            entity.setCaseXkNo(null);
        }
        if (StringUtils.isNotBlank(entity.getXkANo())){
            entity.setXkANo(entity.getXkANo().replaceAll("\\s*", ""));//案件三版本编号
        }else {
            entity.setXkANo(null);
        }
        if (StringUtils.isNotBlank(query.getConsignationXkNo())){
            query.setConsignationXkNo(query.getConsignationXkNo().replaceAll("\\s*",""));//现勘系统委托编号
        }else{
            entity.setConsignationXkNo(null);
        }
        if (StringUtils.isNotBlank(entity.getCaseNo())){
            entity.setCaseNo(entity.getCaseNo().replaceAll("\\s*","")); //案件受理编号
        }else {
            entity.setCaseNo(null);
        }
        if(StringUtils.isNotBlank(entity.getCaseName())){
            entity.setCaseName(entity.getCaseName().replaceAll("\\s*","")); //案件名称
        }else{
            entity.setCaseName(null);
        }
        if(StringUtils.isNotBlank(entity.getCaseProperty())){
            entity.setCaseProperty(entity.getCaseProperty().replaceAll("\\s*",""));//案件性质
        }else {
            entity.setCaseProperty(null);
        }
        if(StringUtils.isNotBlank(entity.getCasePropertylv2())){
            entity.setCasePropertylv2(entity.getCasePropertylv2().replaceAll("\\s*",""));//案件性质二级
        }else {
            entity.setCasePropertylv2(null);
        }
        if (entity.getCaseDatetime()!=null){
            entity.setCaseDatetime(entity.getCaseDatetime());//案发时间
        }else {
            entity.setCaseDatetime(null);
        }
        if(StringUtils.isNotBlank(entity.getCaseLocation())){
            entity.setCaseLocation(entity.getCaseLocation().replaceAll("\\s*",""));//案发地点
        }else {
            entity.setCaseLocation(null);
        }
        if(StringUtils.isNotBlank(query.getCasePersonName())){
            query.setCasePersonName(query.getCasePersonName().replaceAll("\\s*",""));//案件人员名称
        }else {
            query.setCasePersonName(null);
        }
        if (StringUtils.isNotBlank(query.getCasePersonCard())){
            query.setCasePersonCard(query.getCasePersonCard().replaceAll("\\s",""));//案件人员证件号码
        }else {
            query.setCasePersonCard(null);
        }
        if (StringUtils.isNotBlank(query.getDelegator1Name())){
            query.setDelegator1Name(query.getDelegator1Name().replaceAll("\\s*",""));//委托人员姓名
        }else {
            query.setDelegator1Name(null);
        }
        if (StringUtils.isNotBlank(query.getPhone())){
            query.setPhone(query.getPhone().replaceAll("//s*",""));//委托人联系电话
        }else {
            query.setPhone(null);
        }
        query.setEntity(entity);
        return query;
    }

    /**
     * 查询字典项
     * @return
     */
    private ModelAndView  initModelAndView () {
        ModelAndView modelAndView = new ModelAndView();
        /*  字典 */

        DictItem dictItem = new DictItem();
        //案件类型
        dictItem.setDictTypeCode(Constants.CASE_TYPE);
        List<DictItem> caseTypeList = DictUtil.getDictList(dictItem);

        //案件性质
        //List<DictItem> casePropertyList1 = new LinkedList<>();
       // dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
       // dictItem.setParentId(null);
       // List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
        /*for(DictItem dictItem1:casePropertyList){
            if(("06").equals(dictItem1.getDictCode())){
                casePropertyList1.add(dictItem1);
            }else if(("09").equals(dictItem1.getDictCode())){
                casePropertyList1.add(dictItem1);
            } else if(("10").equals(dictItem1.getDictCode())){
                casePropertyList1.add(dictItem1);
            } else if(("11").equals(dictItem1.getDictCode())) {
                casePropertyList1.add(dictItem1);
            }else  if(("19").equals(dictItem1.getDictCode())) {
                casePropertyList1.add(dictItem1);
            }else if(("20").equals(dictItem1.getDictCode())){
                casePropertyList1.add(dictItem1);
            }
        }*/

        //案件性质
        dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
        dictItem.setParentId(null); //一级菜单不包含parentId
        List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
        modelAndView.addObject("casePropertyList",casePropertyList);
        List<DictItem> casePropertyListlv1 = new ArrayList<>();//初始化一级案件性质
        List<DictItem> casePropertyListlv2 = new ArrayList<>();//初始化二级案件性质
        for (DictItem dictItem1: casePropertyList){
            if (StringUtils.isBlank(dictItem1.getParentId())){
                casePropertyListlv1.add(dictItem1);
            }else{
                casePropertyListlv2.add(dictItem1);
            }
        }

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

        //人员类型
        dictItem.setDictTypeCode(Constants.PERSON_TYPE);
        List<DictItem> personTypeList = DictUtil.getDictList(dictItem);

        //亲缘关系
        dictItem.setDictTypeCode(Constants.RELATION_TYPE);
        List<DictItem> relationTypeList = DictUtil.getDictList(dictItem);
        //民族
        dictItem.setDictTypeCode(Constants.NATIONALITY);
        List<DictItem> raceList = DictUtil.getDictList(dictItem);

        //证件类型
        dictItem.setDictTypeCode(Constants.CERTIFICATE_TYPE);
        List<DictItem> cardTypeList = DictUtil.getDictList(dictItem);
        //提取方法
        dictItem.setDictTypeCode(Constants.EXTRACT_METHOD);
        List<DictItem> extractMethodList = DictUtil.getDictList(dictItem);

        //载体
        dictItem.setDictTypeCode(Constants.SAMPLE_CARRIER);
        List<DictItem> sampleCarrierList = DictUtil.getDictList(dictItem);
        for (DictItem dictItem1: casePropertyList){
            if (StringUtils.isBlank(dictItem1.getParentId())){
                casePropertyListlv1.add(dictItem1);
            }else{
                casePropertyListlv2.add(dictItem1);
            }
        }
        modelAndView.addObject("casePropertyListLv1", casePropertyListlv1);//一级案件性质
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseLevelList", caseLevelList);
        modelAndView.addObject("memberTypeList", memberTypeList);
        modelAndView.addObject("sampleTypeList", sampleTypeList);
        modelAndView.addObject("genderList", genderList);
        modelAndView.addObject("packMethodList", packMethodList);
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("positionList", positionList);
        modelAndView.addObject("personTypeList",personTypeList);
        modelAndView.addObject("relationTypeList",relationTypeList);
        modelAndView.addObject("extractMethodList",extractMethodList);
        modelAndView.addObject("sampleCarrierList",sampleCarrierList);
        modelAndView.addObject("raceList",raceList);
        modelAndView.addObject("cardTypeList",cardTypeList);

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if(operateUser == null){
            modelAndView.setViewName("redirect:/login.html?timeoutFlag=1");
            return  modelAndView;
        }

        String orgId = StringUtils.isBlank(operateUser.getSubOrgId()) ? operateUser.getOrgId() : operateUser.getSubOrgId();
        //查询委托单位
        OrgInfo subOrgInfo = orgInfoService.selectByPrimaryKey(orgId);
        modelAndView.addObject("subOrgInfo",subOrgInfo);

        //获取委托人管理数据
        if(operateUser.getOrgId().contains("110230")){
            List<AmPersonalInfo> personalInfoList =amPersonalInfoService.queryAmPersonalInfoLIst("");
            System.out.println(personalInfoList);
            modelAndView.addObject("personalInfoList",personalInfoList);
        }else{
            List<AmPersonalInfo> personalInfoList =amPersonalInfoService.queryAmPersonalInfoLIst(operateUser.getOrgId());
            System.out.println(personalInfoList);
            modelAndView.addObject("personalInfoList",personalInfoList);
        }

        //分局
        List<OrgInfo> orgInfoList = null;
        if(StringUtils.isNotBlank(operateUser.getQueryFlag()) && operateUser.getQueryFlag().equals(Constants.QUERY_FLAG_1)){
            orgInfoList = orgInfoService.selectAll();
        }else{
            orgInfoList = orgInfoService.selectDelegateByParentsId(operateUser.getOrgId());
        }
        modelAndView.addObject("orgInfoList",orgInfoList);

        //查询分局单位
        OrgInfo orgInfo1 = orgInfoService.selectByPrimaryKey(operateUser.getOrgId());
        modelAndView.addObject("orgInfo",orgInfo1);
        //查询法医中心
        OrgInfo forensicCenterorg = orgInfoService.selectByPrimaryKey(Constants.FORENSIC_CENTER_ORG_ID);
        modelAndView.addObject("forensicCenterorg",forensicCenterorg);
        //创建orgInfoList点击鉴定中心的选择，可以多选
        List<OrgInfo> orgInfos = new ArrayList<>();
        if(StringUtils.isNotBlank(orgInfo1.getOrgQualification())){
            orgInfos.add(orgInfo1);
            orgInfos.add(forensicCenterorg);
        }
        modelAndView.addObject("orgInfos",orgInfos);

        return modelAndView;
    }
}
