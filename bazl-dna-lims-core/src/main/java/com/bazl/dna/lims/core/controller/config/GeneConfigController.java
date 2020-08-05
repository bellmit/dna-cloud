package com.bazl.dna.lims.core.controller.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.lims.core.controller.BaseController;

/**
 * 基础配置管理
 */
@Controller
@RequestMapping("/geneconfig")
public class GeneConfigController extends BaseController {
	
	/**
	 * 试剂盒管理
	 * @return
	 */
	@RequestMapping("kitManage")
    public ModelAndView kitManage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("config/kitManage");
        return modelAndView;
    }
	
	/**
	 * 种群基因管理
	 * @return
	 */
	@RequestMapping("populationManage")
    public ModelAndView populationManage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("config/populationManage");
        return modelAndView;
    }
	
	/**
	 * str自动比对设置
	 * @return
	 */
	@RequestMapping("strConfig")
    public ModelAndView strConfig(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("config/strConfig");
        return modelAndView;
    }
	
	/**
	 * str基因座管理
	 * @return
	 */
	@RequestMapping("strGeneManage")
    public ModelAndView strGeneManage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("config/strGeneManage");
        return modelAndView;
    }
	
	/**
	 * 入库比对设置
	 * @return
	 */
	@RequestMapping("warehouseConfig")
    public ModelAndView warehouseConfig(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("config/warehouseConfig");
        return modelAndView;
    }
	
	/**
	 * ystr比对设置
	 * @return
	 */
	@RequestMapping("ystrConfig")
    public ModelAndView ystrConfig(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("config/ystrConfig");
        return modelAndView;
    }
	
	/**
	 * ystr基因座设置
	 * @return
	 */
	@RequestMapping("ystrGeneManage")
    public ModelAndView ystrGeneManage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("config/ystrGeneManage");
        return modelAndView;
    }

}
