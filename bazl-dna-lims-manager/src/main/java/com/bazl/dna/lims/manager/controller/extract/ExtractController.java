package com.bazl.dna.lims.manager.controller.extract;


import com.baomidou.mybatisplus.extension.api.R;
import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.manager.constants.ErrorCodes;
import com.bazl.dna.lims.manager.constants.ErrorInfo;
import com.bazl.dna.lims.manager.controller.BaseController;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.LabExtractInfo;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.vo.LabExtractInfoVo;
import com.bazl.dna.lims.service.*;
import com.bazl.dna.lims.utils.InitializationData;
import com.bazl.dna.lims.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 类说明:提取记录控制层
 * date:2020-08-04
 * Auth：liuchang
 */
@RestController
@RequestMapping("/extract")
public class ExtractController extends BaseController {

    @Autowired
    LabExtractInfoService labExtractInfoService;
    @Autowired
    LabExtractSampleService labExtractSampleService;
    @Autowired
    LabTaskInfoService labTaskInfoService;
    @Autowired
    DictItemService dictItemService;

    /**
     * 接口说明：根据登录机构查询待提取的记录数
     * Auth:liuchang
     * Date:2020-08-04
     * 如果提取的时间不为空，则已用时为当前系统时间-创建时间
     *
     * @param authUser:登录用户信息 extractCount 提取记录数
     * @return
     */
    //TODO 首页任务看板
    @PostMapping(value = "/waitExtractInfo")
    public ResponseData waitExtractInfo(@CurrentUser AuthUser authUser, @RequestBody LabExtractInfoVo query) {
        String orgId = null;
        PageInfo pageInfo = InitializationData.resetPageInfo(query.getPageIndex());//分页查询参数
        Date date = new Date();//获取当前时间
        if (authUser == null) {
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
        if (StringUtils.isNotBlank(authUser.getOrgId())) {
            query.getEntity().setOrgId(InitializationData.resetOrgId(authUser.getOrgId()));//登录机构信息
        }
        //查询字典信息 DICT_ITEM
        List<DictItem> dictItemlist = dictItemService.selectListByDictTypeCode(Constants.SAMPLE_TYPE);
        query.getEntity().setExtractStage(Constants.lIMS_STATS_NOSTART);//实验未开始
        Map<String, Object> resultObj = new HashMap<>();//返回对象
        try {
            List<LabExtractInfoVo> labExtractInfoVoList = labExtractInfoService.selectVoList(query, pageInfo);
            if (labExtractInfoVoList != null && !labExtractInfoVoList.isEmpty()) {
                for (LabExtractInfoVo labExtractInfoVo : labExtractInfoVoList) {
                    labExtractInfoVo.setWorkTask(Constants.WORK_TASK_TQ);//工作任务提取
                    if (StringUtils.isNotBlank(labExtractInfoVo.getSampleType())) {
                        for (DictItem dictItem : dictItemlist) {
                            if (dictItem.getDictCode().equals(labExtractInfoVo.getSampleType())) {
                                labExtractInfoVo.setSampleType(dictItem.getDictName()); //样本类型
                            }
                        }
                    }
                    /**
                     * 先把俩个时间，转换为毫秒数，在计算出俩个时间段之间的毫秒差，根据毫秒差转换成小时数
                     */
                    if (labExtractInfoVo.getEntity().getCreateDatetime() != null) {
                        Long currentTime = date.getTime();//将当前时间转换为毫秒值
                        Long startTime = labExtractInfoVo.getEntity().getCreateDatetime().getTime();//将开始日期转换为毫秒数
                        Long between = currentTime - startTime;
                        long day = between / (24 * 60 * 60 * 1000);
                        long hour = (day * 24);
                        int time = new Long(hour).intValue(); //long 类型时间转换为Int
                        labExtractInfoVo.setUserTime(time);
                    }
                }
            }
            int extractCount = labExtractInfoService.selectVOCount(query); /*根据机构编号，提取状态查询提取数据*/
            resultObj.put(PublicConstants.PARAM_PAGE, new PageInfo(query.getPageIndex(), query.getRows(), extractCount));//分页信息
            resultObj.put("labExtractInfoVoList", labExtractInfoVoList);//提取记录信息列表
            resultObj.put("extractCount", extractCount);//总计数目
            return new ResponseData(resultObj);
        } catch (Exception ex) {
            logger.error("invoke ExtractController.ExtractController error!! " + ex.getMessage());
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }
}
