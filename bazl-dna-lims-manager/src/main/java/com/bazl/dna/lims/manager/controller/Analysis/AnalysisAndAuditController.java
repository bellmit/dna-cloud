package com.bazl.dna.lims.manager.controller.Analysis;


import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.manager.constants.ErrorCodes;
import com.bazl.dna.lims.manager.constants.ErrorInfo;
import com.bazl.dna.lims.manager.controller.BaseController;
import com.bazl.dna.lims.model.po.LabAnalysisInfo;
import com.bazl.dna.lims.model.vo.LabAnalysisInfoVo;
import com.bazl.dna.lims.model.vo.LabExtractInfoVo;
import com.bazl.dna.lims.service.LabAnalysisInfoService;
import com.bazl.dna.lims.utils.DateUtils;
import com.bazl.dna.lims.utils.InitializationData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/*
 * 类说明:分析和审核控制层
 * date:2020-08-05
 * Auth：liuchang
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisAndAuditController extends BaseController {


    @Autowired
    LabAnalysisInfoService labAnalysisInfoService;

    /**
     * 接口说明：根据登录机构查询待分析和待审核样本列表信息
     * Auth:liuchang
     * Date:2020-08-05
     * 如果提取的时间不为空，则已用时为当前系统时间-创建时间
     *
     * @param authUser:登录用户信息 LabExtractInfoVo 提取记录数
     * @return
     */
    @PostMapping(value = "/waitAnalysisInfo")
    public ResponseData waitAnalysisInfo(@RequestBody LabAnalysisInfoVo query, @CurrentUser AuthUser authUser) {
        String orgId = null;
        HashMap<String, Object> resultObj = new HashMap<>(); //返回对象
        Date date = new Date();//当前日期时间
        LabAnalysisInfoVo waitAuditQuery = new LabAnalysisInfoVo();
        if (authUser == null) {
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);//错误日志
        }
        if (StringUtils.isNotBlank(authUser.getOrgId())) {
            query.setDelegateOrgCode(InitializationData.resetOrgId(authUser.getOrgId())); //待分析委托单位
            waitAuditQuery.setDelegateOrgCode(InitializationData.resetOrgId(authUser.getOrgId()));//待审核委托单位
        }
        try {
            /*
             * query 待分析板号样本列表信息
             * queryTotalCnt 待分析版号样本列表总计数目信息
             * userTime 工作任务用时长
             */
            query.setAuditStatus(Constants.AUDIT_STATUS_0);//数据状态：待分析
            PageInfo pageInfo = InitializationData.resetPageInfo(query.getPageIndex());//分页查询参数
            List<LabAnalysisInfoVo> labAnalysisInfoVoList = labAnalysisInfoService.selectLabAnalysisInfoList(query, pageInfo);//分析信息查询
            if (labAnalysisInfoVoList != null && !labAnalysisInfoVoList.isEmpty()) {
                for (LabAnalysisInfoVo labAnalysisInfoVo : labAnalysisInfoVoList) {
                    labAnalysisInfoVo.setWorkTask(Constants.WORK_TASK_ANALYSIS);//工作任务 分析
                    if (labAnalysisInfoVo.getEntity().getCreateDatetime() != null) {
                        labAnalysisInfoVo.setUserTime(DateUtils.getDateDifference(date, labAnalysisInfoVo.getEntity().getCreateDatetime()));
                    }
                }
            }
            int queryTotalCnt = labAnalysisInfoService.selectLabAnalysisInfoCount(query);//待分析信息总计查询
            pageInfo.setAllRecordCount(queryTotalCnt); //总计信息
            resultObj.put(PublicConstants.PARAM_PAGE, new PageInfo(query.getPageIndex(), query.getRows(), queryTotalCnt));//分页信息
            resultObj.put("labAnalysisInfoVoList", labAnalysisInfoVoList);
            resultObj.put("queryTotalCnt", queryTotalCnt);
            /*
             * waitAuditQuery 待审核确认板号样本列表信息
             * waitAuditTotalCnt 待审核确认版号样本列表总计数目信息
             * userTime 工作任务用时长
             */
            waitAuditQuery.setAuditStatus(Constants.AUDIT_STATUS_1);//数据状态待分析
            PageInfo waitAuditPageInfo = InitializationData.resetPageInfo(query.getPageIndex());//分页查询参数
            List<LabAnalysisInfoVo> waitAuditLabAnalysisList = labAnalysisInfoService.selectLabAnalysisInfoList(waitAuditQuery, pageInfo);//分析信息查询
            if (waitAuditLabAnalysisList != null && !waitAuditLabAnalysisList.isEmpty()) {
                for (LabAnalysisInfoVo labAnalysisInfo : waitAuditLabAnalysisList) {
                    labAnalysisInfo.setWorkTask(Constants.WORK_TASK_SH);//工作任务 审核
                    if (labAnalysisInfo.getEntity().getCreateDatetime() != null) {
                        labAnalysisInfo.setUserTime(DateUtils.getDateDifference(date, labAnalysisInfo.getEntity().getCreateDatetime()));
                    }
                }
            }
            int waitAuditTotalCnt = labAnalysisInfoService.selectLabAnalysisInfoCount(waitAuditQuery);
            waitAuditPageInfo.setAllRecordCount(waitAuditTotalCnt);//待审核信息总计
            resultObj.put("waitAuditQueryPage", new PageInfo(waitAuditQuery.getPageIndex(), waitAuditQuery.getRows(), waitAuditTotalCnt));//分页信息
            resultObj.put("waitAuditLabAnalysisList", waitAuditLabAnalysisList);
            resultObj.put("waitAuditTotalCnt", waitAuditTotalCnt);
            return new ResponseData(resultObj);
        } catch (Exception ex) {
            logger.error("invoke AnalysisAndAuditController.waitAnalysisInfo error!!" + ex.getMessage());
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }
}
