package com.bazl.dna.lims.manager.controller.pc;

import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.manager.constants.ErrorCodes;
import com.bazl.dna.lims.manager.constants.ErrorInfo;
import com.bazl.dna.lims.manager.controller.BaseController;
import com.bazl.dna.lims.model.po.LabPcrSample;
import com.bazl.dna.lims.model.vo.LabPcrInfoVo;
import com.bazl.dna.lims.service.LabPcrInfoService;
import com.bazl.dna.lims.service.LabPcrSampleService;
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


@RestController
@RequestMapping("/labPcr")
public class LabPcrController extends BaseController {

    @Autowired
    LabPcrInfoService labPcrInfoService;
    @Autowired
    LabPcrSampleService labPcrSampleService;

    @RequestMapping("/finsAwaitLabPcr")
    public ResponseData finsAwaitLabPcrist(@RequestBody LabPcrInfoVo query, @CurrentUser AuthUser authUser) {
        if (query != null) {
            Map<String, Object> result = new HashMap<>();
            try {
                //获取用户信息
                if (authUser == null) {
                    return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
                }
                //获取当前用户的结构id
//                query.getEntity().setOrgId(InitializationData.resetOrgId(authUser.getOrgId()));
                //获取当前用户的登录名
                query.getEntity().setCreatePerson(authUser.getUsername());
                //分页信息
                PageInfo pageInfo = InitializationData.resetPageInfo(query.getPageIndex());
                pageInfo.setEvePageRecordCnt(query.getRows());
                //条件查询
                query.getEntity().setPcrStage(Constants.lIMS_STATS_NOSTART);
                List<LabPcrInfoVo> labPcrInfoVos = labPcrInfoService.selectVoList(query, pageInfo);
                if (ListUtils.isNotNullAndEmptyList(labPcrInfoVos)){
                    for (LabPcrInfoVo pcrInfoVo : labPcrInfoVos){
//                        List<LabPcrSample> labPcrSamples = labPcrSampleService.selectByPcrId(pcrInfoVo.getEntity().getPcrId());
                        //工作任务
                        pcrInfoVo.setWorkTask(Constants.WORK_TASK_KZ);
                        //用时
                        pcrInfoVo.setUsedTime(DateUtils.getDateDifference(new Date(), pcrInfoVo.getEntity().getCreateDatetime()));

                    }
                }
                //查询总数
                int count = labPcrInfoService.selectVOCount(query);
                pageInfo.setAllRecordCount(count);
                result.put("pageInfo",pageInfo);
                result.put("labPcrInfoVoList",labPcrInfoVos);
                return new ResponseData(result);
            } catch (Exception e) {
                logger.error("查询扩增未开始任务失败！",e);
                return new ResponseData(ErrorCodes.ERR_SERVICE_INTERNAL_EXCEPTION, "查询扩增未开始任务失败!");
            }
        }else {
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }
}
