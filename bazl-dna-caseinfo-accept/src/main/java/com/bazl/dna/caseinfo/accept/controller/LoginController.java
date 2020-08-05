package com.bazl.dna.caseinfo.accept.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.caseinfo.accept.LimsConfigure;
import com.bazl.dna.caseinfo.accept.common.Constants;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.LaboratoryInfo;
import com.bazl.dna.lims.model.po.LoaPermission;
import com.bazl.dna.lims.model.po.LoaRoleRelation;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.po.OrgInfo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.service.LaboratoryInfoService;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.service.LoaPermissionService;
import com.bazl.dna.lims.service.LoaRoleRelationService;
import com.bazl.dna.lims.service.OrgInfoService;
import com.bazl.dna.lims.utils.DateUtils;
import com.bazl.dna.lims.utils.IPsegmentUtil;
import com.bazl.dna.lims.utils.ListUtils;

/**
 * Created by Sun on 2018/12/19.
 */
@Controller
public class LoginController extends BaseController{

    @Autowired
    LimsConfigure limsConfigure;

    @Autowired
    OrgInfoService orgInfoService;
    @Autowired
    LoaPermissionService permissionService;

    @Autowired
    DictItemService dictItemService;

    @Autowired
    LoaRoleRelationService loaRoleRelationService;

    @Autowired
    LaboratoryInfoService laboratoryInfoService;
    @Autowired
    LimsCaseInfoService caseInfoService;


    /**
     * 平台登录入口
     * @return
     */
    @RequestMapping("/")
    public ModelAndView platformLogin(HttpServletRequest request){
        ModelAndView view=new ModelAndView();

        String remoteAddr = request.getRemoteAddr();
        String remoteHost = request.getRemoteHost();

        List<LaboratoryInfo> laboratoryInfoList = laboratoryInfoService.queryList();

        boolean isValidIpaddr = false;
        LaboratoryInfo accessLaboratoryInfo = null;
        for(LaboratoryInfo laboratoryInfo : laboratoryInfoList){
            try {
                if (IPsegmentUtil.isIpValid(remoteAddr, laboratoryInfo.getIpSegment())
                        || IPsegmentUtil.isIpValid(remoteHost, laboratoryInfo.getIpSegment())) {
                    isValidIpaddr = true;
                    accessLaboratoryInfo = laboratoryInfo;
                    break;
                }
            }catch(Exception ex){
                logger.error("验证访问用户IP地址异常。", ex);
            }
        }

        //如果不在可用ip范围内，返回默认的实验室信息
        if(!isValidIpaddr){
            for(LaboratoryInfo lab : laboratoryInfoList){
                if(limsConfigure.getDefaultDnaLabOrgId().equals(lab.getOrgId())){
                    accessLaboratoryInfo = lab;
                    break;
                }
            }
//            accessLaboratoryInfo = laboratoryInfoList.stream().filter(lab -> limsConfigure.getDefaultDnaLabOrgId().equals(lab.getOrgId())).findFirst().get();
        }

        /*
        LaboratoryInfo laboratoryInfo = null;
        if(StringUtils.isNotBlank(limsConfigure.getDefaultDnaLabOrgId())){
            LaboratoryInfo queryLaboratory = new LaboratoryInfo();
            queryLaboratory.setOrgId(limsConfigure.getDefaultDnaLabOrgId());
            laboratoryInfo = laboratoryInfoService.queryById(queryLaboratory);
        }

        if(laboratoryInfo == null) {
            //获取实验室信息数据
//            laboratoryInfo = laboratoryInfoService.queryByClientIP();
        }
        */
        //重新解析案件完成的总量
        List<String> caseNumber=new ArrayList<>();
        List<String> noNum=new ArrayList<>();

        char[] ca=String.valueOf(accessLaboratoryInfo.getLabTotalCase()).toCharArray();
        int caLen=0;
        if(accessLaboratoryInfo.getLabTotalCase().intValue() > 0){
            caLen=ca.length;
        }
        for(int i=caLen;i<5;i++){
            noNum.add("0");
        }
        for(int j=0;j<caLen;j++){
            caseNumber.add(String.valueOf(ca[j]));
        }

        //默认查询法医中心的案件数量
        int caseSum = caseInfoService.selectCountByCaseStatus(null,"110230000000");

        //今年案件件数
        String currentYear = DateUtils.getCurrentYear();//当前年份
        Date date = DateUtils.beginningTime(currentYear); //今年开始时间
        LimsCaseInfoVo query = new LimsCaseInfoVo();
        query.setDelegateStartDatetime(date);
        query.setAcceptOrgId("110230000000");
        query.setAppendFlag(Constants.APPEND_FLAG_0);//不补送
        int mainCaseCnt = caseInfoService.selectVOCount(query);

        //去年时期案件数
        String yearDate = Integer.toString(Integer.parseInt(currentYear) - 1);//去年年份
        Date yearStauatDate = DateUtils.beginningTime(yearDate); //去年开始时间
        Date yearTime = DateUtils.lastYearTime(yearDate);//去年今天时间
        query.setDelegateStartDatetime(yearStauatDate);//开始时间
        query.setDelegateEndDatetime(yearTime);//结束时间
        int caseFormerYearCount = caseInfoService.selectVOCount(query);

        //案件量同比
        int count = mainCaseCnt - caseFormerYearCount;
        Double bigDecimal1 = null;
        String countFlag = "1";
        if (count > 0){
            countFlag = "2";//增长
            bigDecimal1 = new Double(mainCaseCnt);
        }else if (count < 0){
            countFlag = "3";//下降
            count = -count;
            bigDecimal1 = new Double(caseFormerYearCount);
        }
        Double aDouble = new Double(count);
        double v = aDouble / bigDecimal1;
        int schedule = (int) Math.round(v * 100);
        if (count == 0){
            view.addObject("percentage", 0);
        }else {
            view.addObject("percentage", schedule);
        }

        view.addObject("caseSum", caseSum);
        view.addObject("caseYearCount", mainCaseCnt);

        view.addObject("countFlag", countFlag);
        view.addObject("caseFormerYearCount", caseFormerYearCount);
        view.addObject("laboratoryInfo", accessLaboratoryInfo);
        view.addObject("nocaseNum",noNum);
        view.addObject("caseNumber",caseNumber);
        view.addObject("commissionSystemUrL", limsConfigure.getCommissionSystemUrL());
        view.addObject("commissionAssesUrL", limsConfigure.getCommissionAssesUrL());

        view.setViewName("platformLogin");
        return view;
    }

    @RequestMapping( value = "/loginLabInfo",method = RequestMethod.POST)
    @ResponseBody
    public LaboratoryInfo obtainLabInfo(@RequestParam(defaultValue = "areaCode") String areaCode){
        LaboratoryInfo lab=new LaboratoryInfo();
        lab.setOrgId(areaCode);
        LaboratoryInfo laboratoryInfo=laboratoryInfoService.queryById(lab);
        //重新解析案件完成的总量
        List<String> caseNumber=new ArrayList<>();
        List<String> noNum=new ArrayList<>();
        if(laboratoryInfo!=null){
            char[] ca=String.valueOf(laboratoryInfo.getLabTotalCase()).toCharArray();
            int caLen=0;
            if(laboratoryInfo.getLabTotalCase().intValue() > 0){
                caLen=ca.length;
            }
            for(int i=caLen;i<5;i++){
                noNum.add("0");
            }
            for(int j=0;j<caLen;j++){
                caseNumber.add(String.valueOf(ca[j]));
            }
            laboratoryInfo.setNocaseNumStr(noNum);
            laboratoryInfo.setCaseNumberStr(caseNumber);
        }else{
            for(int m=0;m<5;m++){
                noNum.add("0");
            }
            laboratoryInfo=new LaboratoryInfo();
            laboratoryInfo.setNocaseNumStr(noNum);
        }
        //案件总数
        if("110230000004".equals(areaCode)){
            areaCode = "110230000000"; //法医中心账号登录查询
        }
        int caseSum = caseInfoService.selectCountByCaseStatus(null,areaCode); //查询所有案件数
        laboratoryInfo.setCaseSum(caseSum);

        //今年案件件数
        LimsCaseInfoVo query = new LimsCaseInfoVo();
        query.setAcceptOrgId(areaCode);

        String currentYear = DateUtils.getCurrentYear();//当前年份
        Date date = DateUtils.beginningTime(currentYear); //今年开始时间
        query.setDelegateStartDatetime(date);
        query.setAppendFlag(Constants.APPEND_FLAG_0);//不补送
        int mainCaseCnt = caseInfoService.selectVOCount(query);
        laboratoryInfo.setCaseYearCount(mainCaseCnt);

        //去年时期案件数
        String yearDate = Integer.toString(Integer.parseInt(currentYear) - 1);//去年年份
        Date yearStauatDate = DateUtils.beginningTime(yearDate); //去年开始时间
        Date yearTime = DateUtils.lastYearTime(yearDate);//去年今天时间
        query.setDelegateStartDatetime(yearStauatDate);//开始时间
        query.setDelegateEndDatetime(yearTime);//结束时间
        int caseFormerYearCount = caseInfoService.selectVOCount(query);
        laboratoryInfo.setCaseFormerYearCount(caseFormerYearCount);

        //案件量同比
        int percentage = mainCaseCnt - caseFormerYearCount;
        laboratoryInfo.setPercentage(percentage);

        //案件量同比
        int count = mainCaseCnt - caseFormerYearCount;
        Double bigDecimal1 = null;
        String countFlag = "1";
        if (count > 0){
            countFlag = "2";//增长
            bigDecimal1 = new Double(mainCaseCnt);
        }else if (count < 0){
            countFlag = "3";//下降
            count = -count;
            bigDecimal1 = new Double(caseFormerYearCount);
        }
        Double aDouble = new Double(count);
        double v = aDouble / bigDecimal1;
        int schedule = (int) Math.round(v * 100);
        if (count == 0){
            laboratoryInfo.setPercentage(0);
        }else {
            laboratoryInfo.setPercentage(schedule);
        }
        laboratoryInfo.setCountFlag(countFlag);

        return  laboratoryInfo;
    }


//    @RequestMapping({"/login"})
//    public ModelAndView login()
//    {
//        List orgInfos = this.orgInfoService.selectAll();
//        ModelAndView view = new ModelAndView();
//        view.addObject("orgInfos", orgInfos);
//        view.setViewName("login");
//        return view;
//    }


    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "areaCode") String areaCode, HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        if(areaCode!=null){
            LaboratoryInfo lab=new LaboratoryInfo();
            lab.setOrgId(areaCode);
            LaboratoryInfo laboratoryInfo=laboratoryInfoService.queryById(lab);
            List<OrgInfo> orgInfos = orgInfoService.selectAll();
            view.addObject("orgInfos", orgInfos);
            view.addObject("areaCode",areaCode);
            view.addObject("labName",(laboratoryInfo==null)? "":laboratoryInfo.getLabName());
            view.setViewName("/login");
        }else{
            view.setViewName("redirect:/");
        }
        return view;
    }

    @RequestMapping("/loginUser")
    public ModelAndView loginUser(String username, String password, HttpSession session, String areaCode) {
        ModelAndView view = new ModelAndView();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken();
        if(areaCode==null){
            view.setViewName("redirect:/");
            return view;
        }

        //登陆名拼接区域编码
        StringBuilder str=new StringBuilder();
        str.append(username);
        str.append("-");
        str.append(areaCode);
        //授权认证
        usernamePasswordToken=new UsernamePasswordToken(String.valueOf(str),password);

        Subject subject = SecurityUtils.getSubject();
        try {
            //完成登录
            subject.login(usernamePasswordToken);
            //获得用户对象
            LoaUserInfo user=(LoaUserInfo) subject.getPrincipal();

            String userId = user.getUserId();
            List<LoaRoleRelation> loaRoleRelation = loaRoleRelationService.listByUserId(userId);

            boolean loginFlag = false;
            if(loaRoleRelation.size()>0){
                for(int i = 0; i< loaRoleRelation.size();i++){
                    String roleLevel = loaRoleRelation.get(i).getRoleLevel();
                    //有受理权限的分局     ---内网实验员--有受理权限分局下的受理人
                    if(roleLevel.equals("4") || roleLevel.equals("3")) {
                        //有受理权限的分局
                        loginFlag = true;
                    }
                }
            }

            if(loginFlag){
                //存入session
                session.setAttribute("user", user);

                //字典项所有数据存入session
                List<DictItem> listDictItem = dictItemService.selectAllCode();
                session.setAttribute("listDictItem", listDictItem);
                String orgInfoId = user.getOrgId();
                if(StringUtils.isBlank(orgInfoId)) {
                    orgInfoId = limsConfigure.getDefaultDnaLabOrgId();
                }
                String dnaLabName = orgInfoService.selectLabNameById(orgInfoId);

                view.addObject("user", user);
                view.addObject("dnaLabName", dnaLabName);
                view.addObject("permissionList", getPermissionList());

                view.setViewName("redirect:/indexJsp");

            }
            return view;
        } catch(Exception e) {
            view.addObject("areaCode",areaCode);
            logger.error("登录失败。", e);
            view.addObject("meg", "账号密码错误或无登录权限！");
            view.setViewName("login");
            return view;
        }
    }

/*    @RequestMapping({"/loginUser"})
    public ModelAndView loginUser(@RequestParam(value = "areaCode") String areaCode,String username, String password, HttpSession session)
    {
        ModelAndView view = new ModelAndView();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(usernamePasswordToken);

            LoaUserInfo user = (LoaUserInfo)subject.getPrincipal();

            String userId = user.getUserId();
            List loaRoleRelation = this.loaRoleRelationService.listByUserId(userId);

            String loginFlag = null;
            if (loaRoleRelation.size() > 0) {
                for (int i = 0; i < loaRoleRelation.size(); i++) {
                    String roleLevel = ((LoaRoleRelation)loaRoleRelation.get(i)).getRoleLevel();

                    if ((!roleLevel.equals("4")) && (!roleLevel.equals("3")))
                        continue;
                    loginFlag = "true";
                }
            }

            if (loginFlag.equals("true"))
            {
                session.setAttribute("user", user);

                List listDictItem = this.dictItemService.selectAllCode();
                session.setAttribute("listDictItem", listDictItem);

                String orgInfoId = user.getOrgId();
                if (StringUtils.isBlank(orgInfoId)) {
                    orgInfoId = this.defaultDnaLabOrgId;
                }
                String dnaLabName = this.orgInfoService.selectLabNameById(orgInfoId);

                view.addObject("user", user);
                view.addObject("dnaLabName", dnaLabName);
                view.addObject("permissionList", getPermissionList());

                    view.setViewName("redirect:/indexJsp");


            }
            return view;
        } catch (Exception e) {
            view.addObject("areaCode",areaCode);
            this.logger.error(e.getMessage());
            view.addObject("meg", "账号密码错误或无登录权限！");
            view.setViewName("login");
        }return view;
    }*/

    @RequestMapping("/indexJsp")
    public ModelAndView indexJsp(){
        ModelAndView view=new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        //获得用户对象
        LoaUserInfo user=(LoaUserInfo) subject.getPrincipal();

        String orgInfoId = user.getOrgId();
        if(StringUtils.isBlank(orgInfoId)) {
            orgInfoId = limsConfigure.getDefaultDnaLabOrgId();
        }
        String dnaLabName = orgInfoService.selectLabNameById(orgInfoId);

        view.addObject("user", user);
        view.addObject("dnaLabName", dnaLabName);
        view.addObject("permissionList", getPermissionList());

        view.setViewName("index");
        return  view;
    }

    /**
     * 获取二级菜单列表
     * @return
     */
    private List<LoaPermission> getPermissionList(){

        Subject subject = SecurityUtils.getSubject();
        //获得用户对象
        LoaUserInfo user=(LoaUserInfo) subject.getPrincipal();
        String userId = user.getUserId();

        List<LoaRoleRelation> loaRoleRelation = loaRoleRelationService.listByUserId(userId);
//        ArrayList<String> role = new ArrayList<>();
//        for (int i = 0; i < loaRoleRelation.size(); i++) {
//            LoaRoleRelation loaRoleRelation1 = loaRoleRelation.get(i);
//            String roleId = loaRoleRelation1.getRoleId();
//            role.add(roleId);
//        }
//        if(role.size()==1){
//            List<LoaPermission> permissionList = permissionService.selectBySubSystem(Constants.SUBSYSTEM_ACCEPT);
            List<LoaPermission> permissionList = permissionService.selectBySubSystemAndUserId(Constants.SUBSYSTEM_ACCEPT, userId);
            List<LoaPermission> result = new ArrayList<>();
            for(LoaPermission permission : permissionList){
                if(permission.getRootFlag().equals(Constants.permission_root_flag)){
                    List<LoaPermission> nodes = new ArrayList<>();
                    for(LoaPermission permissionSec : permissionList){
                        if(StringUtils.isNotBlank(permissionSec.getParentId()) && permissionSec.getParentId().equals(permission.getPermissionId())){
                            nodes.add(permissionSec);
                        }
                    }
                    if(ListUtils.isNotNullAndEmptyList(nodes)){
                        permission.setPermissionList(nodes);
                    }
                    result.add(permission);
                }
            }
            return result;
//        }else{
//            List<LoaPermission> permissionList = permissionService.selectBy(role.get(1));
//
//            List<LoaPermission> result = new ArrayList<>();
//            for(LoaPermission permission : permissionList){
//                if(permission.getRootFlag().equals(Constants.permission_root_flag)){
//                    List<LoaPermission> nodes = new ArrayList<>();
//                    for(LoaPermission permissionSec : permissionList){
//                        if(StringUtils.isNotBlank(permissionSec.getParentId()) && permissionSec.getParentId().equals(permission.getPermissionId())){
//                            nodes.add(permissionSec);
//                        }
//                    }
//                    if(ListUtils.isNotNullAndEmptyList(nodes)){
//                        permission.setPermissionList(nodes);
//                    }
//                    result.add(permission);
//                }
//            }
//            return result;
//        }
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/loginOut")
    public ModelAndView loginOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        //获得用户对象
        LoaUserInfo user=(LoaUserInfo) subject.getPrincipal();
        String userId = user.getUserId();
        String name = user.getLoginName();
        String orgId = user.getOrgId();
        String userss = name + ":" + orgId;
        subject.logout();
        List<OrgInfo> orgInfos = orgInfoService.selectAll();
        ModelAndView view = new ModelAndView();
        view.addObject("orgInfos", orgInfos);
        /* view.setViewName("login");*/
        view.setViewName("redirect:/");
        return view;
    }

}
