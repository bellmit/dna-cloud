
package com.bazl.dna.caseinfo.accept.controller.center;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.caseinfo.accept.compare.GeneSameCompareUtil;
import com.bazl.dna.caseinfo.accept.controller.BaseController;
import com.bazl.dna.lims.model.po.LimsSampleGene;
import com.bazl.dna.lims.service.LimsSampleGeneService;

/**
 * Created by Administrator on 2019/5/7.
 */
@Controller
@RequestMapping("LimsSampleGeneController")
public class LimsSampleGeneController extends BaseController {
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    GeneSameCompareUtil  geneSameCompareUtil;

    @RequestMapping("queryLimsSampleGeneByCeneId")
    @ResponseBody
    public ModelAndView queryLimsSampleGeneByCeneId(String ceneId){
        ModelAndView modelAndView = new ModelAndView();
        try {
            if(Strings.isNotBlank(ceneId)){
            LimsSampleGene limsSampleGene =limsSampleGeneService.selectByPrimaryKey(ceneId);
                if(null != limsSampleGene){
                    if(Strings.isNotBlank(limsSampleGene.getGeneInfo())){
                        List<Map<String, Object>> resultList = geneSameCompareUtil.analysisGene(limsSampleGene);
                        modelAndView.addObject("result",resultList);
                    }

                    if(Strings.isNotBlank(limsSampleGene.getImagePath())) {
                        modelAndView.addObject("imagePath", limsSampleGene.getImagePath());
                    }
                }
            }
        }catch (Exception ex){
            logger.error("查看基因信息失败"+ex);
        }
        modelAndView.setViewName("caseCompare/CommonComparison");
        return modelAndView;
    }

}
