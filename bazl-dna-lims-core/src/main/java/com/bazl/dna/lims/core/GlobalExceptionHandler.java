package com.bazl.dna.lims.core;

import com.bazl.dna.lims.core.controller.BaseController;
import com.bazl.dna.lims.core.utils.DateUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
