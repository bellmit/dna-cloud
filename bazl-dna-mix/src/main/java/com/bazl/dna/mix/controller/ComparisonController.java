package com.bazl.dna.mix.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.controller.base.ResultBean;
import com.bazl.dna.mix.controller.base.error.ErrorCode;
import com.bazl.dna.mix.service.CompareQueueService;
import com.bazl.dna.mix.service.PersonInfoService;
import com.bazl.dna.mix.service.QualtyPersonnelService;
import com.bazl.dna.mix.service.SingleSampleGeneService;
import com.bazl.dna.mix.utils.GeneMixCompareUtil;
import com.bazl.dna.mix.utils.GeneSameCompareUtil;

/**
 * Created by Administrator on 2019/11/8.
 */
@RestController
@RequestMapping("/comparison")
public class ComparisonController extends BaseController {

    @Autowired
    CompareQueueService compareQueueService;
    @Autowired
    SingleSampleGeneService singleSampleGeneService;
    @Autowired
    QualtyPersonnelService qualtyPersonnelService;
    @Autowired
    PersonInfoService personInfoService;
    @Autowired
    GeneMixCompareUtil geneMixCompareUtil;
    @Autowired
    GeneSameCompareUtil geneSameCompareUtil;

    /*
    *    6.2    查看比对任务列表
    * */
    @RequestMapping(value = "/findcomparisonList", produces = "application/json;charset=UTF-8")
    public ResultBean findcomparisonList(@CurrentUser AuthUser authUser, int page) throws Exception {
        if (page != 0) {
            Map<String, Object> resultMap = new HashMap<>();
            //获得用户对象
//            CompareContributionVo query = new CompareContributionVo();
            //查询当前用户比对过的任务信息
//            query.getEntity().setCreatePerson(user.getId());
//            query.getEntity().setStatus(Constants.MATCH_STATUS_01);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPage(page);
//            List<CompareContribution> compareContributions = compareContributionService.selectByQueryList(query, pageInfo);
//            if (ListUtils.isNotNullAndEmptyList(compareContributions)){
//                for (CompareContribution contribution : compareContributions){
//                    //根据任务id查询改任务比对出的结果
//                    MatchResultSinglsQualty singlsQualty = new MatchResultSinglsQualty();
//                    singlsQualty.setContributionGeneId(contribution.getId());
//                    List<MatchResultSinglsQualty> matchList = matchResultSinglsQualtyService.selectList(singlsQualty);
//                    contribution.setSumCount(matchList.size()+"");
//                    if (contribution.getStatus().equals(Constants.MATCH_STATUS_01)){
//                        contribution.setStatus("已比对");
//                    }else if(contribution.getStatus().equals(Constants.MATCH_STATUS_02)){
//                        contribution.setStatus("比对中");
//                    }
//                }
//            }
            //查询条数
//            int count = compareContributionService.selectByQueryListCount(query);
//            pageInfo.setAllRecordCount(count);
            resultMap.put("pageInfo", pageInfo);
//            resultMap.put("infoList", compareContributions);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } else {
            logger.error("传入参数为空");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }

    }

    /*
    *       6.3	    查看比中结果列表
    * */
    @RequestMapping(value = "/findresultList", produces = "application/json;charset=UTF-8")
    public ResultBean findresultList(String id) throws Exception {
        if (null != id) {
            //根据任务id查询改任务比对出的结果
//            MatchResultSinglsQualty singlsQualty = new MatchResultSinglsQualty();
//            singlsQualty.setContributionGeneId(id);
//            List<MatchResultSinglsQualty> matchList = matchResultSinglsQualtyService.selectList(singlsQualty);
//            List<SingleSampleGeneVo> singleSampleGeneLists = new ArrayList<>();
//            if (ListUtils.isNotNullAndEmptyList(matchList)) {
//                for (MatchResultSinglsQualty qualty : matchList) {
//                    //比中单一
//                    if (qualty.getSampleGeneResultType().equals(Constants.SAMPLEGENERESULT_TYPE_3)) {
//                        SingleSampleGeneVo query = new SingleSampleGeneVo();
//                        query.getEntity().setId(qualty.getSingleGeneId());
//                        List<SingleSampleGeneVo> singleList = singleSampleGeneService.selectComparisonAll(query);
//                        SingleSampleGeneVo singleSampleGeneVo = null;
//                        if (ListUtils.isNotNullAndEmptyList(singleList)) {
//                            singleSampleGeneVo = singleList.get(0);
//                            //设置人员类型
//                            PersonInfo personInfo = personInfoService.selectByPersonType(singleSampleGeneVo.getRefPersonId());
//                            if (personInfo != null) {
//                                singleSampleGeneVo.setSampleFlag(personInfo.getDictName());
//                            }
//                            //图片
//                            if (null != singleSampleGeneVo.getEntity().getGenePicture()){
//                                singleSampleGeneVo.setRatioSampleGeneImagePath(singleSampleGeneVo.getEntity().getGenePicture());//基因图片
//                            }else {
//                                singleSampleGeneVo.setRatioSampleGeneImagePath("0");
//                            }
//                            logger.info("比中数:"+qualty.getRatio());
//                            //比中数
//                            singleSampleGeneVo.setSameCount(qualty.getRatio());
//                            //处理传进来的金银信息
//                            Map<String, Object> result = new HashMap<>();
//                            Map<String, List<String>> mixedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(singleSampleGeneVo.getGeneInfo());
//                            //判空
//                            if (mixedSampleGeneInfo != null) {
//                                //解析基因型信息
//                                result = geneSameCompareUtil.analysisGeneList(mixedSampleGeneInfo);
//                                singleSampleGeneVo.setGeneMap(result);
//                            }
//                        }
//                        singleSampleGeneLists.add(singleSampleGeneVo);
//                        //比中质控
//                    } else if (qualty.getSampleGeneResultType().equals(Constants.SAMPLEGENERESULT_TYPE_4)) {//比中质控
//                        QualtyPersonnel personnel = qualtyPersonnelService.selectMixedSampleQualityDetails(qualty.getQusltyId());
//                        if (personnel != null) {
//                            SingleSampleGeneVo geneVo = new SingleSampleGeneVo();
//                            geneVo.getEntity().setId(personnel.getId());
//                            geneVo.setSampleNo(personnel.getSampleNo());
//                            geneVo.setSampleFlag("质控人员");
//                            geneVo.setGeneInfo(personnel.getGeneInfo());
//                            geneVo.setSampleName(personnel.getSampleName());
//                            geneVo.setSameCount(qualty.getRatio());
//                            if (null != personnel.getGenePicture()){
//                                geneVo.setRatioSampleGeneImagePath(personnel.getGenePicture());//基因图片
//                            }else {
//                                geneVo.setRatioSampleGeneImagePath("0");
//                            }
//                            //处理传进来的金银信息
//                            Map<String, Object> result = new HashMap<>();
//                            Map<String, List<String>> mixedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(geneVo.getGeneInfo());
//                            //判空
//                            if (mixedSampleGeneInfo != null) {
//                                //解析基因型信息
//                                result = geneSameCompareUtil.analysisGeneList(mixedSampleGeneInfo);
//                                geneVo.setGeneMap(result);
//                            }
//                            singleSampleGeneLists.add(geneVo);
//                        }
//                    }
//                }
//            }
//            return new ResultBean(ResultBean.CODE_SUCCESS, 0, singleSampleGeneLists, ResultBean.NAME_SUCCESS);
            return null;
        } else {
            logger.error("传入参数为空");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }
    }

    /*
    *   6.4	查看比中详情
    * */
    @RequestMapping(value = "/details", produces = "application/json;charset=UTF-8")
    public ResultBean details(String id, String geneInfo) throws IOException {
        if (null != id && null != geneInfo) {
            Map<String, Object> resultGenne = new HashMap<>();
           /* CompareContribution contribution = compareContributionService.selectByPrimaryKey(id);
            if (contribution != null) {
                String contributionGeneInfo = contribution.getGeneInfo();
                //基因判空
                Map<String, List<String>> singleSampleGeneInfo = null;
                if (StringUtils.isNotBlank(contributionGeneInfo)) {
                    singleSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(contributionGeneInfo);
                }
                //判空
                if (singleSampleGeneInfo != null && StringUtils.isNotBlank(geneInfo)) {
                    try {
                        resultGenne = geneMixCompareUtil.comparisonResult(singleSampleGeneInfo, geneInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    }
                }
            }*/
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultGenne, ResultBean.NAME_SUCCESS);
        } else {
            logger.error("传入参数为空");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }
    }

}
