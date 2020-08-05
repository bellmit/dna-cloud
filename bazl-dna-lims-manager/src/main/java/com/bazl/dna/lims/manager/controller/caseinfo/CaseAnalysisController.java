package com.bazl.dna.lims.manager.controller.caseinfo;

import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.manager.constants.ErrorCodes;
import com.bazl.dna.lims.manager.constants.ErrorInfo;
import com.bazl.dna.lims.manager.controller.BaseController;
import com.bazl.dna.lims.model.po.LabExtractSample;
import com.bazl.dna.lims.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.service.LabExtractSampleService;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.utils.DateUtils;
import com.bazl.dna.lims.utils.InitializationData;
import com.bazl.dna.lims.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案件分析
 * @author wanghaiyang
 * @date 2020/8/5 10:45
 */
@RestController
@RequestMapping("/caseAnalysis")
public class CaseAnalysisController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;
    @Autowired
    LabExtractSampleService labExtractSampleService;

    /**
     * 案件分析任务列表
     * @param query
     * @param authUser
     * @return
     */
    @GetMapping("caseAnalysisList")
    public ResponseData caseAnalysisList(@RequestBody LimsCaseInfoVo query, @CurrentUser AuthUser authUser) {
        try {
            if (authUser == null) {
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
            query.setAcceptorId(authUser.getId());
            query.setUserOrdId(InitializationData.resetOrgId(authUser.getOrgId()));
            query.setAcceptOrgId(InitializationData.resetOrgId(authUser.getOrgId()));
            PageInfo pageInfo = InitializationData.resetPageInfo(query.getPageIndex());
            List<LimsCaseInfoVo> caseInfoVoList = limsCaseInfoService.selectCaseInfoList(query, pageInfo);
            if (ListUtils.isNotNullAndEmptyList(caseInfoVoList)) {
                for (LimsCaseInfoVo lciVo : caseInfoVoList) {
                    LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
                    sampleInfoDna.setCaseId(lciVo.getEntity().getCaseId());
                    //查询当前案件的检材数量
                    List<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectList(sampleInfoDna);
                    lciVo.setSampleCnt(sampleInfoDnaList.size());
                    //查询当前案件的检材首次检验时间
                    LabExtractSample extractSample = new LabExtractSample();
                    extractSample.setOrderByClause("les.CREATE_DATETIME desc ");
                    List<LabExtractSample> extractSampleList = labExtractSampleService.selectList(extractSample);
                    if (ListUtils.isNotNullAndEmptyList(extractSampleList)) {
                        extractSample = extractSampleList.get(0);
                        if (extractSample != null) {
                            lciVo.setFirstSampleTesttime(extractSample.getCreateDatetime());
                        }
                    }
                    lciVo.setWorkTask(Constants.WORK_TASK_ANALYSIS);
                    lciVo.setUsedTime(DateUtils.getDateDifference(new Date(), lciVo.getAcceptDatetime()));
                }
            }
            int totalCount = limsCaseInfoService.selectVOCount(query);

            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put(PublicConstants.PARAM_PAGE, pageInfo.updatePageInfo(totalCount));
            resultObj.put("caseInfoVoList", caseInfoVoList);
            return new ResponseData(resultObj);
        } catch (Exception ex) {
            logger.error("invoke error.", ex);
            return new ResponseData("获取案件分析任务列表异常！" + ex.getMessage());
        }
    }

}
