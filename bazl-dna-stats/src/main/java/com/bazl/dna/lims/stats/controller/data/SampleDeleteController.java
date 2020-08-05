package com.bazl.dna.lims.stats.controller.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.LimsSampleGene;
import com.bazl.dna.lims.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.vo.LimsSampleGeneVo;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.service.LimsSampleGeneService;
import com.bazl.dna.lims.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.stats.controller.BaseController;
import com.bazl.dna.lims.utils.CommonUtils;

/**
 * Created by zhangbo on 2019/11/1.
 */
@Controller
@RequestMapping("/sample")
public class SampleDeleteController extends BaseController {

    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    DictItemService dictItemService;

    @RequestMapping("/sampleDelete")
    public ModelAndView caseAndBring(HttpServletRequest request, String sampleNo) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        List<LimsSampleInfoDna> limsSampleInfoDnaList = new ArrayList<>();

        try {
            if (null != sampleNo) {
                limsSampleInfoDnaList = limsSampleInfoDnaService.selectSampleBySampleNoList(sampleNo);
            }
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }

        List<DictItem> dictItemList = dictItemService.selectListByDictTypeCode(Constants.SAMPLE_TYPE);

        if (limsSampleInfoDnaList.size() != 0) {
            for (LimsSampleInfoDna limsSampleInfo : limsSampleInfoDnaList) {
                if (StringUtils.isNotBlank(limsSampleInfo.getSampleNo())) {
                    List<LimsSampleInfoDna> limsSampleInfoList = limsSampleInfoDnaService.selectLimsSampleBySampleNoList(limsSampleInfo.getSampleNo());
                    for (LimsSampleInfoDna sampleInfo : limsSampleInfoList) {
                        if (sampleInfo.getSampleNo().equals(limsSampleInfo.getSampleNo())) {
                            limsSampleInfo.setSampleType(limsTurnToAlims(sampleInfo.getSampleType()));
                            limsSampleInfo.setSampleTypeName(sampleInfo.getSampleTypeName());
                        }
                    }
                }
            }
        }

        view.addObject("dictItemList", dictItemList);
        view.addObject("sampleNo", sampleNo);
        view.addObject("limsSampleInfoDnaList", limsSampleInfoDnaList);
        view.setViewName("query/sampleDelete");
        return view;
    }

    @RequestMapping("/deleteSample")
    @ResponseBody
    public Map<String, Object> deleteSample(HttpServletRequest request, String sampleNo,String delegateOrg) {

        Map<String, Object> result = new HashMap<>();
        try {
            if (null != sampleNo) {
                limsSampleInfoDnaService.deleteSampleBySampleNo(sampleNo,delegateOrg);
                result.put("success", true);
            }
        } catch (Exception ex) {
            logger.info("删除失败:" + ex);
            result.put("success", false);
        }

        return result;
    }


    /**
     * 验证样本编号是否已存在
     * @param request
     * @param sampleNo
     * @return
     */
    @RequestMapping("/verificationSampleNo")
    @ResponseBody
    public Map<String, Object> verificationSampleNo(HttpServletRequest request, String sampleNo) {

        Map<String, Object> result = new HashMap<>();
        try {
            if (null != sampleNo) {
                List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaService.selectLimsSampleBySampleNoList(sampleNo);
                if (limsSampleInfoDnaList.size() != 0) {
                    result.put("success", true);
                } else {
                    result.put("success", false);
                }
            }
        } catch (Exception ex) {
            logger.info("判重失败:" + ex);
            result.put("success", true);
        }

        return result;
    }

    /**
     * 保存样本信息
     *
     * @param request
     * @param sampleNo
     * @return
     */
    @RequestMapping("/saveSampleInfo")
    @ResponseBody
    public Map<String, Object> saveSampleInfo(HttpServletRequest request, String sampleNo, String sampleName, String sampleType, String oldSampleNo) {

        Map<String, Object> result = new HashMap<>();
        try {
            if (null != sampleNo && null != oldSampleNo) {
                LimsSampleInfoDna limsSampleInfoDna = new LimsSampleInfoDna();
                limsSampleInfoDna.setSampleType(sampleType);
                limsSampleInfoDna.setSampleName(sampleName);
                limsSampleInfoDna.setOldSampleNo(oldSampleNo);
                limsSampleInfoDna.setSampleNo(sampleNo);
                limsSampleInfoDnaService.updateSampleInfo(limsSampleInfoDna);
                result.put("success", true);
            }
        } catch (Exception ex) {
            logger.info("更新样本信息失败:" + ex);
            result.put("success", false);
        }

        return result;
    }

    /**
     * 查询lims中样本的基因型信息
     * @param request
     * @param sampleNo
     * @return
     */
    @RequestMapping("/querySampleGene")
    @ResponseBody
    public Map<String, Object> querySampleGene(HttpServletRequest request, String sampleNo) {
        Map<String, Object> result = new HashMap<>();
        Map<String, List<String>> geneResult = new HashMap<>();
        try {
            if (null != sampleNo) {
                String geneId = null;
                //查询lims中的基因型
                List<LimsSampleGeneVo> limsSampleGeneVoList = limsSampleGeneService.selectSampleGeneBySampleNo(sampleNo);
                if(limsSampleGeneVoList.size()!=0){
                    for(LimsSampleGeneVo limsSampleGeneVo:limsSampleGeneVoList){
                        //解析基因型并转换基因型格式
                        String geneInfo = CommonUtils.main(limsSampleGeneVo.getGeneMaeker());
                        LimsSampleGene limsSampleGene = new LimsSampleGene();
                        limsSampleGene.setGeneInfo(geneInfo);
                        geneResult = analysisGene(limsSampleGene);
                        geneId = limsSampleGeneVo.getEntity().getGeneId();
                    }

                }
                result.put("success",true);
                result.put("geneId",geneId);
                result.put("sampleNo",sampleNo);
                result.put("geneResult",geneResult);
            }
        } catch (Exception ex) {
            logger.info("查询基因型并解析失败:" + ex);
            result.put("success",false);
        }

        return result;
    }

    /**
     * 更新基因型信息
     * @param request
     * @return
     */
    @RequestMapping("/saveSampleGene")
    @ResponseBody
    public Map<String, Object> saveSampleGene(HttpServletRequest request, @RequestBody LimsSampleGeneVo sampleGeneVo) {
        Map<String, Object> result = new HashMap<>();
        Map<String, List<String>> geneResult = new HashMap<>();
        try {
            if (null != sampleGeneVo.getSampleNo()) {
                int rusult = limsSampleGeneService.updateGene(sampleGeneVo);
                if (rusult==1){
                    result.put("success",true);
                }else{
                    result.put("success",false);
                }
            }
        } catch (Exception ex) {
            logger.info("更新基因型失败:" + ex);
            result.put("success",false);
        }

        return result;
    }


    public Map<String, List<String>> analysisGene(LimsSampleGene limsSampleGene) {
        Map<String, List<String>> result = null;
        Map<String, Object> map = new HashMap<>();
        result = (Map) JSON.parse(limsSampleGene.getGeneInfo(), Feature.OrderedField);

        for(String re: result.keySet()){
            List<String> list = result.get(re);
            if (list.size()==2){
                list.add("");
                list.add("");
            }else if (list.size()==3){
                list.add("");
            }
        }

        return result;
    }

    /**
     * 样本类型转换（lims TurnTo alims）
     *
     * @param sampleType
     * @return
     */
    private String limsTurnToAlims(String sampleType) {
        if (sampleType.equals("1")) {
            sampleType = "01";
        } else if (sampleType.equals("2")) {
            sampleType = "02";
        } else if (sampleType.equals("3")) {
            sampleType = "09";
        } else if (sampleType.equals("4")) {
            sampleType = "07";
        } else if (sampleType.equals("5")) {
            sampleType = "04";
        } else if (sampleType.equals("6")) {
            sampleType = "03";
        } else if (sampleType.equals("7")) {
            sampleType = "99";
        } else {
            return sampleType;
        }
        return sampleType;
    }


}
