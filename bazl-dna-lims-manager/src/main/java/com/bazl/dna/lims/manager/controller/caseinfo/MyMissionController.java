package com.bazl.dna.lims.manager.controller.caseinfo;

import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.manager.constants.ErrorCodes;
import com.bazl.dna.lims.manager.constants.ErrorInfo;
import com.bazl.dna.lims.manager.controller.BaseController;
import com.bazl.dna.lims.model.ParameterQuery;
import com.bazl.dna.lims.model.po.LaboratoryInfo;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.vo.AmPersonalInfoVo;
import com.bazl.dna.lims.model.vo.CaseEvidenceDetectionRateVo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.service.AmPersonalInfoService;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.utils.DateUtils;
import com.bazl.dna.lims.utils.InitializationData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的任务
 * @author wanghaiyang
 * @date 2020/8/4 10:45
 */
@RestController
@RequestMapping("/mission")
public class MyMissionController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    AmPersonalInfoService amPersonalInfoService;

    /**
     * 我的任务
     * @param authUser
     * @return
     */
    @GetMapping("myMission")
    public ResponseData myMission(@CurrentUser AuthUser authUser) {
        try {
            if (authUser == null) {
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
            Map<String, Object> resultObj = new HashMap<>();
            //查询当前登录人的未完成的案件数量
            LimsCaseInfoVo caseInfoVo = new LimsCaseInfoVo();
            caseInfoVo.setAcceptorId(authUser.getId());
            caseInfoVo.setStatus(Constants.CASE_STATUS_02);
            int incompleteCount = limsCaseInfoService.selectCountByCaseStatusAndAcceptorId(caseInfoVo);

            //查询本月已完成的的案件数量
            caseInfoVo.setStatus(Constants.CASE_STATUS_03);
            String startDate = DateUtils.getFirstDayOfMonth(DateUtils.getMonth());
            caseInfoVo.setAcceptStartDatetime(DateUtils.stringToDate(startDate, DateUtils.MIN));
            caseInfoVo.setAcceptEndDatetime(new Date());
            int completedThisMonthCount = limsCaseInfoService.selectCountByCaseStatusAndAcceptorId(caseInfoVo);

            //查询本年度完成案件数量
            caseInfoVo.setAcceptStartDatetime(DateUtils.beginningTime(DateUtils.getCurrentYear()));
            caseInfoVo.setAcceptEndDatetime(new Date());
            int completedThisYearCount = limsCaseInfoService.selectCountByCaseStatusAndAcceptorId(caseInfoVo);

            resultObj.put("incompleteCount", incompleteCount);
            resultObj.put("completedThisMonthCount", completedThisMonthCount);
            resultObj.put("completedThisYearCount", completedThisYearCount);
            return new ResponseData(resultObj);
        } catch (Exception ex) {
            logger.error("invoke error.", ex);
            return new ResponseData("获取任务数据异常！" + ex.getMessage());
        }
    }

    /**
     * 选择实验人
     * @param authUser
     * @return
     */
    @GetMapping("chooseExperimenter")
    public ResponseData chooseExperimenter(@CurrentUser AuthUser authUser) {
        try {
            if (authUser == null) {
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
            Map<String, Object> resultObj = new HashMap<>();
            //获取受理人信息
            String orgId = InitializationData.resetOrgId(authUser.getOrgId());
            List<AmPersonalInfoVo> amPersonalInfoVoList = amPersonalInfoService.queryAmPersonalInfoVoList(orgId);
            resultObj.put("amPersonalInfoVoList", amPersonalInfoVoList);
            return new ResponseData(resultObj);
        } catch (Exception ex) {
            logger.error("invoke error.", ex);
            return new ResponseData("获取选择实验人数据异常！" + ex.getMessage());
        }
    }

}
