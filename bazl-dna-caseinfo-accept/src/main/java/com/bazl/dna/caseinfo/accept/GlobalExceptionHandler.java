package com.bazl.dna.caseinfo.accept;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.caseinfo.accept.controller.BaseController;
import com.bazl.dna.lims.utils.DateUtils;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defautErrorHandler(HttpServletRequest request, Exception e) {
        logger.error("错误--" + e.getMessage() + "-----" + DateUtils.getCurrentYearStr(), e);
        ModelAndView view = new ModelAndView();
        //view.setViewName("404");
        view.setViewName("redirect:/");
        return view;
    }

}
