package com.bazl.dna.caseinfo.reg.controller.delegate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.caseinfo.reg.common.Constants;
import com.bazl.dna.caseinfo.reg.controller.BaseController;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.po.FugitivesInfo;
import com.bazl.dna.lims.model.po.LimsPersonInfo;
import com.bazl.dna.lims.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.vo.FugitivesInfoVo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.service.AmPersonalInfoService;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.service.FugitivesInfoService;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.service.LimsPersonInfoService;
import com.bazl.dna.lims.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.service.OrgInfoService;
import com.bazl.dna.lims.utils.DownloadFileUtils;
import com.bazl.dna.lims.utils.InitializationData;
import com.bazl.dna.lims.utils.ListUtils;

/**
 * @author huawei
 * @date 2020/6/15.
 */
@Controller
@RequestMapping("/delegate")
public class FugitivesController extends BaseController {

    @Autowired
    FugitivesInfoService fugitivesInfoService;
    @Autowired
    DownloadFileUtils downloadFileUtils;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    AmPersonalInfoService amPersonalInfoService;
    @Autowired
    OrgInfoService orgInfoService;
    @Autowired
    LimsSampleInfoDnaService  limsSampleInfoDnaService;
    @Autowired
    LimsPersonInfoService  limsPersonInfoService;
    @Autowired
    DictItemService dictItemService;

    /**
     * 在逃人员列表（查询列表）
     * Author:Liuchang
     * Date：2020-7-16
     * tableType  类型
     * 说明：待送检 - tableType1 : 受理状态待受理，且受理时间小于72小时
     *      超时未送检-tableType2：受理状态待受理，且受理时间大于72小时
     *      已送检-tableType3：受理状态已受理
     * @return
     */
   /* @RequestMapping("/fugitivesRegManage")
    @SuppressWarnings("all")
    public ModelAndView pending(HttpServletRequest request, FugitivesInfoVo query, PageInfo pageInfo, String tableType) {
        ModelAndView view = new ModelAndView();
        //获取当前登录人信息，如果登录信息用户失效则跳转至登录页
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        //首页默认查询为 待送检
        if (StringUtils.isBlank(tableType)) {
            tableType = "1";
        }
        //在逃人员信息集合
        List<FugitivesInfoVo> fugitivesInfoList = null;

        //获取系统三天前的日期
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, -3);
        date = calendar.getTime();

        String loginOrgId = operateUser.getOrgId();//登录机构ID信息 loginOrgId

        //获取登陆机构编号
        try {
            String orgId = StringUtils.isBlank(operateUser.getSubOrgId()) ? operateUser.getOrgId() : operateUser.getSubOrgId();
            //查询去除空格条件
            query = resetCaseInfoQuery(query);

            int totalCnt = 0; //总计
            int tableType1Cnt = 0; //待送检
            int tableType2Cnt = 0;//超时未送检
            int tableType3Cnt = 0;//已送检

            if ("110105000000".equals(orgId)) {
                query.setAreaOrgCode("110105");
            } else if ("110230000002".equals(orgId)) {
                query.setDelegateOrgCode(null);
            } else {
                query.setDelegateOrgCode(orgId);//委托机构编码等于机构ID
            }
            if (tableType.equals("1")) {              //待送检的在逃人员信息 (不包含在超时未送检里面)
                query.setStatus(Constants.STATUS_01);//委托状态,待受理，待送检
                query.setWtStartDate(date);  //委托开始时间大于3天前
                fugitivesInfoList = fugitivesInfoService.selectFugitivesQueryList(query, pageInfo); //根据查询条件查询信息
                totalCnt = fugitivesInfoService.selectFugitivesCount(query); //查询总计信息，待送检
                tableType1Cnt = totalCnt;
                query.setStatus(Constants.STATUS_01);
                query.setWtStartDate(null);        //委托开始时间
                query.setDelegateEndDatetime(date);
                tableType2Cnt = fugitivesInfoService.selectFugitivesCount(query); //查询总计信息，超时待送检
                query.setStatus(Constants.STATUS_02);
                query.setWtStartDate(null);
                query.setDelegateEndDatetime(null);
                tableType3Cnt = fugitivesInfoService.selectFugitivesCount(query); //查询总计信息，已送检
            }else if (tableType.equals("2")){        //超时未送检
                //查询超时未送检委托案件数量
                //系统当前时间，72小时之前的时间
                //设置系统当前时间 ,如果选择时间大于超时时间，则更改为超时时间
                query.setStatus(Constants.STATUS_01);//委托状态,待受理
                query.setDelegateEndDatetime(date); //委托结束时间
                fugitivesInfoList = fugitivesInfoService.selectFugitivesQueryList(query, pageInfo); //根据查询条件查询信息
                totalCnt = fugitivesInfoService.selectFugitivesCount(query); //查询总计信息 （超时待送检）
                tableType2Cnt = totalCnt;          //超时未送检(总计信息)
                query.setStatus(Constants.STATUS_01);
                query.setDelegateEndDatetime(null);
                query.setWtStartDate(date);
                tableType1Cnt = fugitivesInfoService.selectFugitivesCount(query); //查询总计信息 （待送检）
                query.setStatus(Constants.STATUS_02);
                query.setWtStartDate(null);
                tableType3Cnt = fugitivesInfoService.selectFugitivesCount(query); //查询总计信息 （已送检）
            }else if (tableType.equals("3")){       //已送检
                query.setStatus(Constants.STATUS_02);//委托状态,已受理，已送
                fugitivesInfoList = fugitivesInfoService.selectFugitivesQueryList(query, pageInfo); //根据查询条件查询信息
                totalCnt = fugitivesInfoService.selectFugitivesCount(query); //查询总计信息 （已送检）
                tableType3Cnt  = totalCnt;
                query.setStatus(Constants.STATUS_01);
                query.setWtStartDate(date);
                tableType1Cnt = fugitivesInfoService.selectFugitivesCount(query); //查询总计信息（待送检）
                query.setStatus(Constants.STATUS_01);
                query.setWtStartDate(null);
                query.setDelegateEndDatetime(date);
                tableType2Cnt =  fugitivesInfoService.selectFugitivesCount(query); //查询总计信息（超时待送检）
            }

            //查询案件总计信息
            view = InitializationData.initDictList();
            view.addObject("query", query);
            view.addObject("caseInfoCount",totalCnt); //委托总数
            view.addObject("fugitivesInfoList", fugitivesInfoList);//案件信息集合
            view.addObject("tableType", tableType);
            view.addObject("tableType1Cnt", tableType1Cnt);//待送检
            view.addObject("tableType2Cnt", tableType2Cnt);//超时未送检
            view.addObject("tableType3Cnt", tableType3Cnt);//已送检
            view.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }
        view.setViewName("delegationReg/fugitivesRegManage");
        return view;
    }*/

    @RequestMapping("/fugitivesRegManage")
    @SuppressWarnings("all")
    public ModelAndView fugitivesRegManage(HttpServletRequest request, LimsCaseInfoVo query, PageInfo pageInfo, String tableType) {
        ModelAndView view = new ModelAndView();
        //获取当前登录人信息，如果登录信息用户失效则跳转至登录页
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        //首页默认查询为 待送检
        if (StringUtils.isBlank(tableType)) {
            tableType = "1";
        }

        //获取系统三天前的日期
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, -3);
        date = calendar.getTime();

        String loginOrgId = operateUser.getOrgId();//登录机构ID信息 loginOrgId

        //获取登陆机构编号
        try {
            String orgId = StringUtils.isBlank(operateUser.getSubOrgId()) ? operateUser.getOrgId() : operateUser.getSubOrgId();
            int totalCnt = 0; //总计
            int tableType1Cnt = 0; //待送检
            int tableType2Cnt = 0;//超时未送检
            int tableType3Cnt = 0;//已送检

            List<LimsCaseInfoVo> caseInfoList = null;
            if ("110105000000".equals(orgId)) {
                query.setAreaOrgCode("110105");
            } else if ("110230000002".equals(orgId)) {
                query.setDelegateOrgCode(null);
            } else {
                query.setDelegateOrgCode(orgId);//委托机构编码等于机构ID
            }
            query.setConsignmentType(Constants.CONSIGNMENT_TYPE_1);
            if (Constants.TABLE_TYPE_1.equals(tableType)) {              //待送检的在逃人员信息 (不包含在超时未送检里面)
                query.setStatus(Constants.STATUS_01);//委托状态,待受理，待送检
                query.setWtStartDate(date);  //委托开始时间大于3天前
                caseInfoList =limsCaseInfoService.selectCaseQueryInfoList(query, pageInfo);
                totalCnt = limsCaseInfoService.selectCaseQueryVOCount(query);
                tableType1Cnt = totalCnt;
                query.setStatus(Constants.STATUS_01);
                query.setWtStartDate(null);        //委托开始时间
                query.setDelegateEndDatetime(date);
                tableType2Cnt = limsCaseInfoService.selectCaseQueryVOCount(query); //查询总计信息，超时待送检
                query.setStatus(Constants.STATUS_02);
                query.setWtStartDate(null);
                query.setDelegateEndDatetime(null);
                tableType3Cnt = limsCaseInfoService.selectCaseQueryVOCount(query); //查询总计信息，已送检
            }else if (Constants.TABLE_TYPE_2.equals(tableType)){        //超时未送检
                //查询超时未送检委托案件数量
                //系统当前时间，72小时之前的时间
                //设置系统当前时间 ,如果选择时间大于超时时间，则更改为超时时间
                query.setStatus(Constants.STATUS_01);//委托状态,待受理
                query.setDelegateEndDatetime(date); //委托结束时间
                caseInfoList =limsCaseInfoService.selectCaseQueryInfoList(query, pageInfo);
                totalCnt = limsCaseInfoService.selectCaseQueryVOCount(query);
                tableType2Cnt = totalCnt;          //超时未送检(总计信息)
                query.setStatus(Constants.STATUS_01);
                query.setDelegateEndDatetime(null);
                query.setWtStartDate(date);
                tableType1Cnt = limsCaseInfoService.selectCaseQueryVOCount(query); //查询总计信息 （待送检）
                query.setStatus(Constants.STATUS_02);
                query.setWtStartDate(null);
                tableType3Cnt = limsCaseInfoService.selectCaseQueryVOCount(query); //查询总计信息 （已送检）
            }else if (Constants.TABLE_TYPE_3.equals(tableType)){       //已送检
                query.setStatus(Constants.STATUS_02);//委托状态,已受理，已送
                caseInfoList =limsCaseInfoService.selectCaseQueryInfoList(query, pageInfo);
                totalCnt = limsCaseInfoService.selectCaseQueryVOCount(query);
                tableType3Cnt  = totalCnt;
                query.setStatus(Constants.STATUS_01);
                query.setWtStartDate(date);
                tableType1Cnt = limsCaseInfoService.selectCaseQueryVOCount(query); //查询总计信息（待送检）
                query.setStatus(Constants.STATUS_01);
                query.setWtStartDate(null);
                query.setDelegateEndDatetime(date);
                tableType2Cnt =  limsCaseInfoService.selectCaseQueryVOCount(query); //查询总计信息（超时待送检）
            }

            //获取在逃人员信息
            if (ListUtils.isNotNullAndEmptyList(caseInfoList)) {
                for (LimsCaseInfoVo lciVo : caseInfoList) {
                    List<FugitivesInfo> fugitivesInfoList = fugitivesInfoService.selectListByConsignmentId(lciVo.getConsignmentId());
                    if (ListUtils.isNotNullAndEmptyList(fugitivesInfoList)) {
                        FugitivesInfo fugitivesInfo = fugitivesInfoList.get(0);
                        if (fugitivesInfo != null) {
                            lciVo.setFugitivesName(fugitivesInfo.getPersonName());
                            lciVo.setFugitivesGenderName(fugitivesInfo.getPersonGenderName());
                            lciVo.setFugitivesCard(fugitivesInfo.getPersonCard());
                            lciVo.setFugitivesRace(fugitivesInfo.getNation());
                            lciVo.setFugitivesNativePlace(fugitivesInfo.getNativePlace());
                        }
                    }
                    LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
                    limsPersonInfo.setCaseId(lciVo.getEntity().getCaseId());
                    limsPersonInfo.setConsignmentId(lciVo.getConsignmentId());
                    List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfo);
                    if (ListUtils.isNotNullAndEmptyList(limsPersonInfoList)) {
                        //判断是否存在亲属人员信息
                        int relation = 0;
                        for (LimsPersonInfo lpi : limsPersonInfoList) {
                            if (Constants.PERSON_TYPE_08.equals(lpi.getPersonType())) {
                                relation ++;
                            }
                        }
                        lciVo.setRelationFlag(String.valueOf(relation));
                    }
                    //判断是否存在疑似人员物品
                    List<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectByCaseId(lciVo.getEntity().getCaseId());
                    if (ListUtils.isNotNullAndEmptyList(sampleInfoDnaList)) {
                        lciVo.setSuspectSampleFlag(Constants.FLAG_TRUE);
                    }else {
                        lciVo.setSuspectSampleFlag(Constants.FLAG_FAlSE);
                    }
                }
            }

            //查询案件总计信息
            view = InitializationData.initDictList();
            view.addObject("query", query);
            view.addObject("caseInfoCount",totalCnt); //委托总数
            view.addObject("caseInfoList", caseInfoList);//案件信息集合
            view.addObject("tableType", tableType);
            view.addObject("tableType1Cnt", tableType1Cnt);//待送检
            view.addObject("tableType2Cnt", tableType2Cnt);//超时未送检
            view.addObject("tableType3Cnt", tableType3Cnt);//已送检
            view.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }
        view.setViewName("delegationReg/fugitivesRegManage");
        return view;
    }

    /**
     * 导入在逃人员
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value="/uploadFugitives",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadFugitives(HttpServletRequest request, @RequestParam(value = "upFile") MultipartFile[] file){
        Map<String,Object> result = new HashMap<>();
        try {
            if (file.length > 0) {
                List<FugitivesInfo> allList = new ArrayList<>();
                for (int i = 0; i < file.length; i++) {
                    List<FugitivesInfo> fugitivesInfoList = downloadFileUtils.importFugitivesInfo(request, file[i]);
                    allList.addAll(fugitivesInfoList);
                }
                //遍历解析出来的list
                if (ListUtils.isNotNullAndEmptyList(allList)) {
                    List<FugitivesInfo> newFugitivesInfoList = new ArrayList<>();
                    List<FugitivesInfo> repeatFugitivesInfoList = new ArrayList<>();
                    //筛选出新导入和已经存在的在逃人员信息
                    for (FugitivesInfo fi : allList) {
                        List<FugitivesInfo> fugitivesInfos = fugitivesInfoService.selectInfoByPersonNameAndCard(fi.getPersonName(), fi.getPersonCard());
                        //如果为空，表示之前没有插入这条记录
                        if (ListUtils.isNullOrEmptyList(fugitivesInfos)) {
                            newFugitivesInfoList.add(fi);
                        }else {
                            //不为空，表示此人已经存在
                            repeatFugitivesInfoList.add(fi);
                        }
                    }
                    //批量插入在逃人员信息
                    if (ListUtils.isNotNullAndEmptyList(newFugitivesInfoList)) {
                        fugitivesInfoService.insertBatchFugitives(newFugitivesInfoList);
                    }
                    //重复人员在前台展示
                    if (ListUtils.isNotNullAndEmptyList(repeatFugitivesInfoList)) {
                        result.put("repeatFugitivesInfoList", repeatFugitivesInfoList);
                    }else {
                        result.put("repeatFugitivesInfoList", null);
                    }
                    result.put("success",true);
                }else {
                    result.put("success", "no");
                }
            }else {
                result.put("success",false);
            }
        }catch (Exception e){
            result.put("success",false);
            logger.error("uploadSampleTable error",e);
        }

        return result;
    }

    /**
     * 插入数据
     * @param fugitivesInfo
     */
    public void insertData (FugitivesInfo fugitivesInfo) throws Exception {
        if (fugitivesInfo != null) {
            fugitivesInfo.setId(UUID.randomUUID().toString());
            fugitivesInfo.setCreateDatetime(new Date());

            fugitivesInfoService.insert(fugitivesInfo);
        }
    }

    /**
     * 增加或修改在逃人员信息
     * @param request
     * @param fugitivesInfo
     * @return
     */
    @RequestMapping(value = "operateFugitives", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> operateFugitives(HttpServletRequest request, @RequestBody FugitivesInfo fugitivesInfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            if(operateUser == null){
                result.put("success",false);
                result.put("","/login.html?timeoutFlag=1");
                return  result;
            }
            //判断是否已经存在此信息，不存在添加在逃人员信息，否则修改在逃人员信息
            String repeat = null;
            if (StringUtils.isBlank(fugitivesInfo.getId())) {
                fugitivesInfo.setId(UUID.randomUUID().toString());
                fugitivesInfo.setCreateDatetime(new Date());
                fugitivesInfo.setCreatePerson(operateUser.getLoginName());

                //判断此在逃人员是否已经存在
                List<FugitivesInfo> fugitivesInfoList = fugitivesInfoService.selectInfoByPersonNameAndCard(fugitivesInfo.getPersonName(), fugitivesInfo.getPersonCard());
                if (ListUtils.isNullOrEmptyList(fugitivesInfoList)) {
                    //插入在逃人员信息
                    fugitivesInfoService.insert(fugitivesInfo);
                }else {
                    repeat = "repeat";
                }
            }else {
                FugitivesInfo fugitives = fugitivesInfoService.selectByPrimaryKey(fugitivesInfo.getId());
                fugitives.setPersonName(fugitivesInfo.getPersonName());
                fugitives.setPersonType(fugitivesInfo.getPersonType());
                fugitives.setPersonGender(fugitivesInfo.getPersonGender());
                fugitives.setPersonCard(fugitivesInfo.getPersonCard());
                fugitives.setPersonAge(fugitivesInfo.getPersonAge());
                fugitives.setFugitiveNo(fugitivesInfo.getFugitiveNo());
                fugitives.setUpdateDatetime(new Date());
                fugitives.setUpdatePerson(operateUser.getLoginName());

                //更新在逃人员信息
                fugitivesInfoService.updateByPrimaryKey(fugitives);
            }
            result.put("repeat", repeat);
            result.put("success", true);
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("添加失败!", e);
            result.put("success", false);
        }
        return result;
    }

    /**
     * 删除在逃人员信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/delFugitivesInfo")
    @ResponseBody
    public Map<String, Object> delFugitivesInfo(HttpServletRequest request,String id) {
        Map<String, Object> result = new HashMap<>();

        try {
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            if(operateUser == null){
                result.put("success",false);
                result.put("","/login.html?timeoutFlag=1");
                return  result;
            }

            FugitivesInfo fugitivesInfo = new FugitivesInfo();
            fugitivesInfo.setId(id);
            fugitivesInfo.setDeleteFlag(Constants.DELETE_FLAG_1);
            fugitivesInfo.setDeleteDatetime(new Date());
            fugitivesInfo.setDeletePerson(operateUser.getLoginName());

            fugitivesInfoService.deleteFugitivesInfo(fugitivesInfo);
            result.put("success", true);
        }catch (Exception e) {
            e.getStackTrace();
            logger.error("删除失败！", e);
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("/fugitivesSearch")
    @ResponseBody
    public Map<String, Object> fugitivesSearch(HttpServletRequest request, String searchFugitives) {
        Map<String, Object> result = new HashMap<>();

        try {
            List<FugitivesInfoVo> fugitivesInfoVoList = fugitivesInfoService.selectFugitivesList(searchFugitives.trim());
            result.put("fugitivesInfoVoList", fugitivesInfoVoList);
            result.put("success", true);
        }catch (Exception e) {
            e.getStackTrace();
            logger.error("查询失败！", e);
            result.put("success", false);
        }

        return result;
    }

    /**
     * 初始化查询条件
     * @param query
     * @return
     */
    private FugitivesInfoVo resetQueryParams(FugitivesInfoVo query){
        if (StringUtils.isBlank(query.getEntity().getDeleteFlag())) {
            query.getEntity().setDeleteFlag(Constants.DELETE_FLAG_0);
        } else {
            query.getEntity().setDeleteFlag(query.getEntity().getDeleteFlag().trim());
        }

        if (StringUtils.isBlank(query.getEntity().getPersonName())) {
            query.getEntity().setPersonName(null);
        } else {
            query.getEntity().setPersonName(query.getEntity().getPersonName().trim());
        }

        if (StringUtils.isBlank(query.getEntity().getPersonCard())) {
            query.getEntity().setPersonCard(null);
        } else {
            query.getEntity().setPersonCard(query.getEntity().getPersonCard().trim());
        }

        if (StringUtils.isBlank(query.getEntity().getFugitiveNo())) {
            query.getEntity().setFugitiveNo(null);
        } else {
            query.getEntity().setFugitiveNo(query.getEntity().getFugitiveNo().trim());
        }

        return query;
    }

    @SuppressWarnings("all")
    private FugitivesInfoVo resetCaseInfoQuery (FugitivesInfoVo query) {
        if (StringUtils.isNotBlank(query.getCaseNo())){
            query.setCaseNo(query.getCaseNo().replaceAll("\\s*","")); //案件受理编号
        }else {
            query.setCaseNo(null);
        }
        if(StringUtils.isNotBlank(query.getCaseName())){
            query.setCaseName(query.getCaseName().replaceAll("\\s*","")); //案件名称
        }else{
            query.setCaseName(null);
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
            query.setPhone(query.getPhone().replaceAll("\\s*",""));//委托人联系电话
        }else {
            query.setPhone(null);
        }
        if(StringUtils.isNotBlank(query.getFugitivesRelationName())){
            query.setFugitivesRelationName(query.getFugitivesRelationName().replaceAll("\\s*",""));//在逃人员亲属姓名
        }else{
            query.setFugitivesRelationName(null);
        }
        if(StringUtils.isNotBlank(query.getFugitivesRelationCard())){
            query.setFugitivesRelationCard(query.getFugitivesRelationCard().replaceAll("\\s*",""));//在逃人员亲属证件信息
        }else{
            query.setFugitivesRelationCard(null);
        }
        if (StringUtils.isNotBlank(query.getFugitivesName())){
            query.setFugitivesName(query.getFugitivesName().replaceAll("\\s*",""));//在逃人员姓名
        }else{
            query.setFugitivesName(null);
        }
        if (StringUtils.isNotBlank(query.getFugitivesCard())){
            query.setFugitivesCard(query.getFugitivesCard().replaceAll("\\s*",""));//在逃人员证件号码
        }else{
            query.setFugitivesCard(null);
        }
        return query;
    }
}
