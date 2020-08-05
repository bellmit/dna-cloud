package com.bazl.dna.database.core.controller.web.qc;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.model.po.QcSamplePolluteRecord;
import com.bazl.dna.database.service.model.qo.QcSamplePolluteRecordQuery;
import com.bazl.dna.database.service.model.vo.QcSamplePolluteRecordVo;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.QcSampleInfoService;
import com.bazl.dna.database.service.service.QcSamplePolluteRecordService;
import com.bazl.dna.database.utils.ListUtils;
import com.bazl.dna.util.GeneTransFormUtils;

/**
 * <p>
 * 质控污染记录 前端控制器
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Controller
@RequestMapping("/qcPollute")
public class QcSamplePolluteRecordController extends BaseController {

    @Autowired
    QcSamplePolluteRecordService qcSamplePolluteRecordService;
    @Autowired
    QcSampleInfoService qcSampleInfoService;
    @Autowired
    DictItemService dictItemService;

    /**
     * 根据 质控样本信息id 获取其对应的质控样本污染记录 by renyunpeng
     */
    @PostMapping(value = "/selectListByQcSampleId")
    @ResponseBody
    public ResponseData selectListByQcSampleId(@RequestBody QcSamplePolluteRecordQuery query){
        Map<String, Object> resultObj = new HashMap<>();
        if (null != query.getId()){
            try {
                //分页条件查询列表
                List<QcSamplePolluteRecordVo> qcSamplePolluteRecordVoList = qcSamplePolluteRecordService.selectListPaging(query);
                HashSet<String> caseList = new HashSet<>();
                if(ListUtils.isNotNullAndEmptyList(qcSamplePolluteRecordVoList)){
                    for(QcSamplePolluteRecordVo q: qcSamplePolluteRecordVoList){
                        //计算出污染了多少案件
                        caseList.add(q.getMatchCaseNo());
                        //获取基因信息
                        QcSampleInfo qcSampleInfo = qcSampleInfoService.selectById(q.getQcSampleId());
                        QcSamplePolluteRecord qcSampleRecord = qcSamplePolluteRecordService.selectByPrimaryKey(q.getId());
                        if (qcSampleInfo != null && qcSampleRecord != null){
                            String srcGene = GeneTransFormUtils.geneExchange(qcSampleRecord.getQcSampleGeneInfo());
                            String tarGene = GeneTransFormUtils.geneExchange(qcSampleRecord.getMatchSampleGeneInfo());
                            Map<String, Object> map = GeneTransFormUtils.compareResultFlag(srcGene, tarGene, 0);
                            q.setGeneMap(map);
                        }
                        //案件人员类型
                        String personType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_CASE_PERSON_TYPE, q.getPersonType());
                        q.setPersonTypeName(personType);
                        //质控样本类型
                        String sampleType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TYPE_QC_SAMPLE_TYPE, q.getSampleType());
                        q.setSampleTypeName(sampleType);
                    }
                }
                //分页总数
                int totalCount = qcSamplePolluteRecordService.selectListPagingCount(query);
                resultObj.put("totalCount", totalCount);      //也是共比中了多少条的值  比中了多少个样本的值
                resultObj.put("qcSamplePolluteRecordVoList", qcSamplePolluteRecordVoList);
                resultObj.put("caseCount", caseList.size()); //比中案件数
                resultObj.put(PublicConstants.PARAM_PAGE, new PageInfo(query.getPageIndex(), query.getRows(), totalCount));
                return new ResponseData(resultObj);
            }catch(Exception ex) {
                logger.error("invoke QcSamplePolluteRecordController.selectList error.", ex);
                return new ResponseData("查询质控污染记录信息出现异常！" + ex.getMessage());
            }
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

}
