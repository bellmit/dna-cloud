package com.bazl.dna.lims.core.controller.instore;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.lims.core.controller.BaseController;
import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import com.bazl.dna.lims.core.service.OrgInfoService;

/**
 * Created by Administrator on 2019-09-08.
 */
@Controller
@RequestMapping("/instoreCompare")
public class InstoreCompareController extends BaseController {

	@Autowired
    private OrgInfoService  orgInfoService;

    @RequestMapping("/same")
    public ModelAndView same(){
        ModelAndView modelAndView = new ModelAndView();
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        //根据登录机构ID获取实验室机构名称
        String dnaLabName = orgInfoService.selectLabNameById(operateUser.getOrgId());
        modelAndView.addObject("dnaLabName",dnaLabName);
        modelAndView.setViewName("warehouseCompar/same");

        return modelAndView;
    }
    
    @RequestMapping("/ystr")
    public ModelAndView ystr(){
        ModelAndView modelAndView = new ModelAndView();
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        //根据登录机构ID获取实验室机构名称
        String dnaLabName = orgInfoService.selectLabNameById(operateUser.getOrgId());
        modelAndView.addObject("dnaLabName",dnaLabName);
        modelAndView.setViewName("warehouseCompar/ystr");

        return modelAndView;
    }
    
    @RequestMapping("/mix")
    public ModelAndView mix(){
        ModelAndView modelAndView = new ModelAndView();
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        //根据登录机构ID获取实验室机构名称
        String dnaLabName = orgInfoService.selectLabNameById(operateUser.getOrgId());
        modelAndView.addObject("dnaLabName",dnaLabName);
        modelAndView.setViewName("warehouseCompar/mixStr");

        return modelAndView;
    }
    
    @RequestMapping("/kinShip")
    public ModelAndView kinShip(){
        ModelAndView modelAndView = new ModelAndView();
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        //根据登录机构ID获取实验室机构名称
        String dnaLabName = orgInfoService.selectLabNameById(operateUser.getOrgId());
        modelAndView.addObject("dnaLabName",dnaLabName);
        modelAndView.setViewName("warehouseCompar/kinShip");

        return modelAndView;
    }

}
