package com.bazl.dna.lims.core.controller;

import com.bazl.dna.lims.core.LimsConfigure;
import com.bazl.dna.lims.core.common.Constants;
import com.bazl.dna.lims.core.dao.LoaPermissionMapper;
import com.bazl.dna.lims.core.model.*;
import com.bazl.dna.lims.core.model.po.*;
import com.bazl.dna.lims.core.service.*;
import com.bazl.dna.lims.core.utils.IPsegmentUtil;
import com.bazl.dna.lims.core.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

        view.addObject("laboratoryInfo", accessLaboratoryInfo);
        view.addObject("nocaseNum",noNum);
        view.addObject("caseNumber",caseNumber);
//        view.addObject("commissionSystemUrL", limsConfigure.getCommissionSystemUrL());
//        view.addObject("commissionAssesUrL", limsConfigure.getCommissionAssesUrL());

        view.setViewName("/platformLogin");
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
        List<LoaPermission> permissionList = permissionService.selectBySubSystemAndUserId(Constants.SUBSYSTEM_LIMS, userId);
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
