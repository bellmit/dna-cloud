package com.bazl.dna.lims.core.controller.qualityControl;

import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import com.bazl.dna.lims.core.utils.IpAddressUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/quality")
public class QualityControlController {


    /**
     * 跳转到lims统计系统
     * @return
     */
    @RequestMapping("/qualityControl")
    public ModelAndView testConsignmentNo() {
        ModelAndView modelAndView = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo loginUser = (LoaUserInfo) subject.getPrincipal();
        if (loginUser == null) {
            modelAndView.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return modelAndView;
        }


        modelAndView.setViewName("qualityControl/controlInfo");
        return modelAndView;
    }


}
