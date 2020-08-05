package com.bazl.dna.mix.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.GeneListServerClient;
import com.bazl.dna.mix.client.SampleDataServicr;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.model.vo.SampleDnaGeneVo;
import com.bazl.dna.mix.service.ContributorInfoService;
import com.bazl.dna.mix.utils.GeneformatUtils;
import com.bazl.dna.mix.utils.ListUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/dnaParting")
public class DnaPartingController extends BaseController {

    @Autowired
    private SampleDataServicr sampleDataServicr;
    @Autowired
    private GeneListServerClient geneListServerClient;
    @Autowired
    GeneformatUtils geneformatUtils;
    @Autowired
    ContributorInfoService contributorInfoService;

     /*
    *    检材编号模糊查询
    * */
    @RequestMapping(value = "/selectSampleNoList", produces = "application/json;charset=UTF-8")
    public ResponseData selectSampleNoList(String sampleNo) throws Exception {
        //获取样本信息
        ResponseData responseData = sampleDataServicr.selectSampleNo(sampleNo);
        if (1 == responseData.getCode()){
            Gson gson = new Gson();
            List<SampleDnaGeneVo> sampleDnaGeneVos  = gson.fromJson(gson.toJson(responseData.getResult()), new TypeToken<List<SampleDnaGeneVo>>(){}. getType());
            //获取基因座信息
            ResponseData locusNameData = geneListServerClient.selectByLocusName();
            List<Map<String,String>> locusNameList  = gson.fromJson(locusNameData.getResult().toString(), new TypeToken<List<Map<String,String>>>(){}. getType());
            if (1 == locusNameData.getCode()){
                if (ListUtils.isNotNullAndEmptyList(locusNameList)){
                    //转换基因
                    for (SampleDnaGeneVo geneVo : sampleDnaGeneVos){
                        logger.info("基因信息转换开始----------------------");
                        String geneInfo = geneVo.getEntity().getGeneInfo();
                        String s = geneformatUtils.geneFormatList(geneInfo, locusNameList);
                        System.out.println(s);
                        logger.info("基因信息转换结束----------------------");
                        geneVo.getEntity().setGeneInfo(s);
                    }
                }
            }
            return new ResponseData(sampleDnaGeneVos);
//            List<SampleDnaGeneVo> list = map.get("list");
        }else if (2010 == responseData.getCode()){
            return new ResponseData(1,"该检材不存在！");
        }else {
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
    *    DNA分型快速比对---获取混合id  手工录入要自己生成混合id
    * */
    @RequestMapping(value = "/foundMixedSampleGeneId", produces = "application/json;charset=UTF-8")
    public ResponseData foundMixedSampleGeneId() throws Exception {
        String mixedSampleId = UUID.randomUUID().toString();
        HashMap<String, String> map = new HashMap<>();
        map.put("mixedSampleId",mixedSampleId);
        return new ResponseData(map);
    }

    /*
    *    DNA分型快速比对---获取混合id  手工录入要自己生成混合id
    * */
    @RequestMapping(value = "/deleteContributorInfo", produces = "application/json;charset=UTF-8")
    public ResponseData deleteContributorInfo(String id) throws Exception {
        if (StringUtils.isNotBlank(id)){
            try {
                contributorInfoService.deleteByPrimaryKey(id);
            } catch (Exception e) {
                logger.error("删除贡献者报错!"+e.getMessage());
                e.printStackTrace();
            }
            return new ResponseData(1);
        }else{
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }
}
