package com.bazl.dna.lims.manager.controller.sy;

import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.manager.constants.ErrorCodes;
import com.bazl.dna.lims.manager.constants.ErrorInfo;
import com.bazl.dna.lims.manager.controller.BaseController;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.vo.LabSyInfoVo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.service.LabSyInfoService;
import com.bazl.dna.lims.utils.DateUtils;
import com.bazl.dna.lims.utils.InitializationData;
import com.bazl.dna.lims.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
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
 * @author wanghaiyang
 * @date 2020/8/4 14:15
 */
@RestController
@RequestMapping("/sy")
public class SyController extends BaseController {

    @Autowired
    LabSyInfoService labSyInfoService;

    /**
     * 电泳任务列表
     * @param query
     * @param authUser
     * @return
     */
    @GetMapping("syList")
    public ResponseData syList(@RequestBody LabSyInfoVo query, @CurrentUser AuthUser authUser) {
        try {
            if (authUser == null) {
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
            //获取当前用户的id
            query.getEntity().setCreatePerson(authUser.getUsername());
            query.getEntity().setOrgId(InitializationData.resetOrgId(authUser.getOrgId()));
            PageInfo pageInfo = InitializationData.resetPageInfo(query.getPageIndex());
            List<LabSyInfoVo> labSyInfoVoList = labSyInfoService.selectVOPaginationList(query, pageInfo);
            if (ListUtils.isNotNullAndEmptyList(labSyInfoVoList)) {
                for (LabSyInfoVo lsiVo : labSyInfoVoList) {
                    lsiVo.setWorkTask(Constants.WORK_TASK_SY);
                    lsiVo.setUsedTime(DateUtils.getDateDifference(new Date(), lsiVo.getEntity().getCreateDatetime()));
                }
            }
            int totalCount = labSyInfoService.selectVOCnt(query);

            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put(PublicConstants.PARAM_PAGE, pageInfo.updatePageInfo(totalCount));
            resultObj.put("labSyInfoVoList", labSyInfoVoList);
            return new ResponseData(resultObj);
        } catch (Exception ex) {
            logger.error("invoke error.", ex);
            return new ResponseData("获取电泳任务列表异常！" + ex.getMessage());
        }
    }

}
