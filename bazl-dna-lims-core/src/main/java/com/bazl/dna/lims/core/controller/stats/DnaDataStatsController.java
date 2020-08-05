package com.bazl.dna.lims.core.controller.stats;

import com.bazl.dna.lims.core.LimsConfigure;
import com.bazl.dna.lims.core.controller.BaseController;
import com.bazl.dna.lims.core.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import com.bazl.dna.lims.core.service.LimsConsignmentInfoService;
import com.bazl.dna.lims.core.utils.IpAddressUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lizhihua on 2020/7/23.
 */
@Controller
@RequestMapping("/stats")
public class DnaDataStatsController extends BaseController {

    @Autowired
    LimsConfigure limsConfigure;


    /**
     * 跳转到lims统计系统
     * @return
     */
    @RequestMapping("/limsDataStats")
    public ModelAndView testConsignmentNo() {
        ModelAndView modelAndView = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo loginUser = (LoaUserInfo) subject.getPrincipal();
        if (loginUser == null) {
            modelAndView.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return modelAndView;
        }

        modelAndView.addObject("loginUser", loginUser);
        String requestIpaddr = IpAddressUtils.getRealIP();//获得本机IP
        modelAndView.addObject("requestIpaddr", requestIpaddr);
        modelAndView.addObject("limsDataStatsUrl", limsConfigure.getLimsDataStatsUrl());

        modelAndView.setViewName("statistics/limsDataStats");

/*
        Map<String,Object> map=new HashMap();
        String url="";
        try {
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            if(operateUser == null){
                map.put("success",false);
                map.put(url,"/login.html?timeoutFlag=1");
                return  map;
            }

            LimsConsignmentInfo limsConsignmentInfo = limsConsignmentInfoService.selectByConsignmentNo(consignmentNo);
            if (limsConsignmentInfo != null){
                map.put("success",true);
            }else{
                map.put("success",false);
            }

        }catch(Exception ex){
            map.put("error",false);
            logger.error("判断委托编号重复失败"+ex.getMessage());
        }
*/

        return modelAndView;
    }


}
