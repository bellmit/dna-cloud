package com.bazl.dna.caseinfo.accept.controller.quick;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.caseinfo.accept.controller.BaseController;
import com.bazl.dna.lims.utils.IpAddressUtils;

/**
 * Created by Administrator on 2019-09-08.
 */
@Controller
public class QuickCompareController extends BaseController {


    @RequestMapping("/quick/compare")
    public ModelAndView quickCompare(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("ipAddr", IpAddressUtils.getIpAddr(request));
        modelAndView.setViewName("quick/compare");

        return modelAndView;
    }

}
