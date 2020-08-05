package com.bazl.dna.caseinfo.reg.controller.delegate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.caseinfo.reg.common.Constants;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.po.AmPersonalInfo;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.LoaRole;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.vo.AmPersonalInfoVo;
import com.bazl.dna.lims.service.AmPersonalInfoService;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.service.LoaRoleService;
import com.bazl.dna.lims.service.LoaUserInfoService;
import com.bazl.dna.lims.utils.DictUtil;

/**
 * Created by Sun on 2018/12/20.
 */
@Controller
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    private LoaRoleService loaRoleService;
    @Autowired
    AmPersonalInfoService amPersonalInfoService;
    @Autowired
    LoaUserInfoService loaUserInfoService;
    @Autowired
    private DictItemService dictItemService;

    /**
     * 委托人管理查询
     * @return
     */
    @RequestMapping("/delegatorManage")
    public ModelAndView query (HttpServletRequest request, AmPersonalInfoVo query, PageInfo pageInfo){
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if(operateUser == null){
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return  view;
        }
        //去除空格
        query = trimQueryPrams(query);
        query.getEntity().setOrgId(operateUser.getOrgId());

        /**
         *查询角色信息表Loa_Role
         * 回显委托人添加权限设定
         */
        List<LoaRole>LoaRoleList = loaRoleService.queryLoaRole();
        view.addObject("LoaRoleList",LoaRoleList);
        /**
         *查询字典信息表DICT_ITEM
         * 回显委托人添加职务
         */
        DictItem dictItem = new DictItem();
        dictItem.setDictTypeCode(Constants.POSITION_LIST);
        List<DictItem> listDictItem = DictUtil.getDictList(dictItem);
        view.addObject("listDictItem",listDictItem);
        /**
         * 查询Am_Personal_Info ap , Loa_User_Info lu  ,LOA_ROLE_RELATION lrr , Loa_Role lr
         * 返回到委托管理展示页面
         */
        List<AmPersonalInfoVo> amPersonalInfoVoList = amPersonalInfoService.selectVOList(query, pageInfo);
        int totalCount = amPersonalInfoService.selectVOCnt(query);

        view.addObject("query", query);
        view.addObject("pageInfo", pageInfo.updatePageInfo(totalCount));
        view.addObject("amPersonalInfoVoList",amPersonalInfoVoList);
        view.setViewName("manage/delegatorManage");
        return view;
    }


    /**
     * 个人信息管理
     * @return
     */
    @RequestMapping("/personalInformation")
    public ModelAndView personalInformation (HttpSession session){
        LoaUserInfo user = (LoaUserInfo) session.getAttribute("user");
        ModelAndView view = new ModelAndView();
        //根据用户主键ID查询回显到委托人个人中心
        AmPersonalInfo amPersonalInfo =amPersonalInfoService.queryAmPersonalInfo(user.getPersonalId());
        /**
         *查询字典信息表DICT_ITEM
         * 回显个人中心职务
         */
        DictItem dictItem = new DictItem();
        dictItem.setDictTypeCode(Constants.POSITION_LIST);
        List<DictItem> listDictItem = DictUtil.getDictList(dictItem);
        view.addObject("listDictItem",listDictItem);
        view.addObject("amObject",amPersonalInfo);
        view.setViewName("manage/personalInformation");
        return view;
    }
    /**
     * 个人信息管理
     * @return
     */
    @RequestMapping("/updateAmPersonalInfoAndLoaUserInfo")
    public String updatepersonalInformation (AmPersonalInfo amPersonalInfo){
        //根据用户主键ID查询修改委托人个人中心信息
        amPersonalInfoService.updateAmPersonalInfoById(amPersonalInfo);
        loaUserInfoService.updateloaUserInfoByUserId(amPersonalInfo);
        return "redirect:/manage/personalInformation";
    }

    /**
     *  查询检索项，去除空格
     * @param query
     * @return
     */
    private AmPersonalInfoVo trimQueryPrams(AmPersonalInfoVo query) {
            AmPersonalInfo entity = query.getEntity();
        if (StringUtils.isNotBlank(entity.getFullName())){
            entity.setFullName(entity.getFullName().replaceAll("//*",""));//委托人姓名
        }
        if (StringUtils.isNotBlank(entity.getGender())){
            entity.setGender(entity.getGender().replaceAll("//*",""));//性别
        }
        if (StringUtils.isNotBlank(entity.getPoliceNo())){
            entity.setPoliceNo(entity.getPoliceNo().replaceAll("//*",""));//警号
        }
        if (StringUtils.isNotBlank(entity.getPhoneNumber())){
            entity.setPhoneNumber(entity.getPhoneNumber().replaceAll("//*",""));//联系电话
        }
        query.setEntity(entity);
        return  query;
    }
}
