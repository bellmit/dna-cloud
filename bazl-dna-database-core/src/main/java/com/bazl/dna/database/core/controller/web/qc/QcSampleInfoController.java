package com.bazl.dna.database.core.controller.web.qc;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.database.algorithm.comparator.StrSameComparator;
import com.bazl.dna.database.algorithm.params.StrSameCompareParams;
import com.bazl.dna.database.algorithm.result.StrSameCompareResult;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.core.helper.codis.CodisFileParser;
import com.bazl.dna.database.core.helper.codis.CodisQcModel;
import com.bazl.dna.database.service.model.bo.QcCompareResultModel;
import com.bazl.dna.database.service.model.bo.QcCompareSampleModel;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.model.qo.QcSampleInfoQuery;
import com.bazl.dna.database.service.model.vo.QcSampleInfoVo;
import com.bazl.dna.database.service.service.AlleleFrequencyInfoService;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.PopulationFrequencyInfoService;
import com.bazl.dna.database.service.service.QcSampleInfoService;
import com.bazl.dna.database.utils.ListUtils;
import com.bazl.dna.util.GeneTransFormUtils;
import com.google.common.collect.Lists;

/**
 * 质控比对 相关
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/qc")
public class QcSampleInfoController extends BaseController {

    @Autowired
    QcSampleInfoService qcSampleInfoService;
    @Autowired
    PopulationFrequencyInfoService populationFrequencyInfoService;
    @Autowired
    AlleleFrequencyInfoService alleleFrequencyInfoService;
    @Autowired
    DictItemService dictItemService;

    /**
     * 质控防污染比对
     *
     * @param request
     * @param qcCompareSampleModelList      待比对的样本STR基因信息列表
     * @return  返回比中质控样本的数据记录
     *
     *  无比中时，返回json:
     *  {"code":200, "msg":"success", "data":[]}
     *
     *  有比中时，返回json：
     *      {
     *          "code":200,
     *          "msg":"success",
     *          "data":[
     *              {
     *                  "qcCompareSampleModel":{},
     *                  "qcSampleInfo":{},
     *                  "strSameCompareResult":{}
     *              },
     *              {
     *                  "qcCompareSampleModel":{},
     *                  "qcSampleInfo":{},
     *                  "strSameCompareResult":{}
     *              }
     *          ]
     *      }
     */
	@PostMapping(value = "/compare")
	public ResponseData compare(HttpServletRequest request, @RequestParam String labServerNo,
			@RequestBody List<QcCompareSampleModel> qcCompareSampleModelList) {
		// 获取默认种群的等位基因频率集合
		List<AlleleFrequencyInfo> alleleFrequencyInfoList = alleleFrequencyInfoService.listOfDefaultPopulation();
		List<QcSampleInfo> qcSampleInfoList = qcSampleInfoService.list();
		
		List<QcCompareResultModel> qcCompareResultModelList = Lists.newArrayList();
		QcCompareResultModel qcCompareResultModel = null;
		StrSameCompareResult strSameCompareResult = null;
		for (QcCompareSampleModel qcCompareSampleModel : qcCompareSampleModelList) {
			for (QcSampleInfo qcSampleInfo : qcSampleInfoList) {
				if (ListUtils.isNotNullAndEmptyList(qcSampleInfo.getGeneInfoDetail())) {
					strSameCompareResult = StrSameComparator.sameCompareIncludeLikelihood(
							qcCompareSampleModel.getSampleGeneInfo(), qcSampleInfo.getGeneInfoDetail(),
							StrSameCompareParams.DEFAULT_PARAMS(), alleleFrequencyInfoList);
					if (strSameCompareResult != null) {
						qcCompareResultModel = new QcCompareResultModel();
						qcCompareResultModel.setQcCompareSampleModel(qcCompareSampleModel);
						qcCompareResultModel.setQcSampleInfo(qcSampleInfo);
						qcCompareResultModel.setStrSameCompareResult(strSameCompareResult);

						qcCompareResultModelList.add(qcCompareResultModel);
					}
				}
			}
		}

		return new ResponseData(qcCompareResultModelList);
	}


    /**
     * 质控数据列表查询 by renyunpeng
     */
    @PostMapping(value = "/selectList")
    public ResponseData selectList(@RequestBody QcSampleInfoQuery query){
        Map<String, Object> resultObj = new HashMap<>();
        try {
            //分页条件查询列表
            List<QcSampleInfoVo> qcSampleInfoList = qcSampleInfoService.selectListPaging(query);
            if (ListUtils.isNotNullAndEmptyList(qcSampleInfoList)){
                for (QcSampleInfoVo infoVo : qcSampleInfoList){
                    //质控样本类型
                    String qcSampleType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TYPE_QC_SAMPLE_TYPE, infoVo.getQcSampleType());
                    infoVo.setQcSampleTypeName(qcSampleType);
                    //质控人员类型
                    String qcPersonType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TYPE_QC_PERSON_TYPE, infoVo.getQcPersonType());
                    infoVo.setQcPersonTypeName(qcPersonType);
                    //性别
                    String qcPersonGender = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TYPE_PERSON_GENDER, infoVo.getQcPersonGender());
                    infoVo.setQcPersonGenderName(qcPersonGender);
                }
            }
            //分页总数
            int totalCount = qcSampleInfoService.selectListPagingCount(query);
            //分页
            PageInfo pageInfo = new PageInfo();
            pageInfo.setAllRecordCount(totalCount);
            resultObj.put("pageInfo", pageInfo);
            resultObj.put("qcSampleInfoList", qcSampleInfoList);
            return new ResponseData(resultObj);
        }catch(Exception ex){
            logger.error("invoke QcSampleInfoController.selectList error.", ex);
            return new ResponseData("查询质控人员信息出现异常！" + ex.getMessage());
        }
    }


    /**
     * 添加质控样本信息 by renyunpeng
     */
    @PostMapping(value = "/insertAdd")
    public ResponseData insertAdd(@CurrentUser AuthUser user, @RequestBody QcSampleInfo qcSampleInfo) throws Exception {
        //获取用户信息

        Boolean bool = null;
        if (null != qcSampleInfo.getId()) {
            try {
                QcSampleInfo sampleInfo = qcSampleInfoService.selectById(qcSampleInfo.getId());
                if(sampleInfo != null){
                    sampleInfo.setLabServerNo(qcSampleInfo.getLabServerNo());
                    sampleInfo.setQcSampleType(qcSampleInfo.getQcSampleType());
                    sampleInfo.setQcSampleName(qcSampleInfo.getQcSampleName());
                    sampleInfo.setQcSampleNo(qcSampleInfo.getQcSampleNo());
                    sampleInfo.setQcPersonType(qcSampleInfo.getQcPersonType());
                    sampleInfo.setQcPersonName(qcSampleInfo.getQcPersonName());
                    sampleInfo.setQcPersonIdcardNo(qcSampleInfo.getQcPersonIdcardNo());
                    sampleInfo.setQcPersonGender(qcSampleInfo.getQcPersonGender());
                    sampleInfo.setQcPersonOrgId(qcSampleInfo.getQcPersonOrgId());
                    sampleInfo.setQcPersonOrgName(qcSampleInfo.getQcPersonOrgName());
                    sampleInfo.setStrPanelId(qcSampleInfo.getStrPanelId());
                    sampleInfo.setYStrPanelId(qcSampleInfo.getYStrPanelId());
                    sampleInfo.setStrPanelName(qcSampleInfo.getStrPanelName());
                    sampleInfo.setYStrPanelName(qcSampleInfo.getYStrPanelName());
                    sampleInfo.setStrGeneInfo(qcSampleInfo.getStrGeneInfo());
                    sampleInfo.setYStrGeneInfo(qcSampleInfo.getYStrGeneInfo());
                    qcSampleInfo.setUpdateDatetime(LocalDateTime.now());
                    qcSampleInfo.setUpdatePersonName(user.getUsername());
                    bool = qcSampleInfoService.updateByIdOverride(sampleInfo);
                }
            } catch (Exception ex) {
                logger.error("invoke QcSampleInfoController.updateQcSampleById error.", ex);
                return new ResponseData("修改质控信息出现异常！" + ex.getMessage());
            }
        }else {
            try {
                qcSampleInfo.setCreateDatetime(LocalDateTime.now());
                qcSampleInfo.setCreatePersonName(user.getUsername());
                bool = qcSampleInfoService.insertAdd(qcSampleInfo);
            }catch(Exception ex){
                logger.error("invoke QcSampleInfoController.insertQcSampleInfo error.", ex);
                return new ResponseData("新增质控信息出现异常！" + ex.getMessage());
            }
        }
        return new ResponseData(bool);
    }


    /**
     *  进入修改页面 by renyunpeng
     */
    @GetMapping(value = "/updatePageList")
    public ResponseData updatePageList(Integer id){
        if (id != null){
            try {
                QcSampleInfo qcSampleInfo = qcSampleInfoService.selectById(id);
                if (qcSampleInfo != null){
                    String geneSTRInfo = GeneTransFormUtils.geneFormatList(qcSampleInfo.getStrGeneInfo());
                    qcSampleInfo.setStrGeneInfo(geneSTRInfo);
                    String geneYSTRInfo = GeneTransFormUtils.geneFormatList(qcSampleInfo.getYStrGeneInfo());
                    qcSampleInfo.setYStrGeneInfo(geneYSTRInfo);
                }
                return new ResponseData(qcSampleInfo);
            }catch(Exception ex){
                logger.error("invoke QcSampleInfoController.queryQcSampleById error.", ex);
                return new ResponseData("进入质控修改页面出现异常！" + ex.getMessage());
            }
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }


    /**
     *  导入codis文件 获取基因座和等位基因信息 by renyunpeng
     */
    @PostMapping(value = "/uploadCodis", produces = "application/json; charset=utf-8")
    public ResponseData uploadCodis(@RequestParam("file") List<MultipartFile> codisFiles, String reagentName) {
        Map<String, Object> resultMap = new HashMap<>();

        if (StringUtils.isNotBlank(reagentName)) {
            try {
                reagentName = URLDecoder.decode(reagentName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        List<CodisQcModel> codisContentModelLists = new ArrayList<CodisQcModel>();
        for (MultipartFile codisFile:codisFiles) {

            List<CodisQcModel> codisContentModelList = null;
            try {//解析
                codisContentModelList = CodisFileParser.getDataCodisFile(codisFile.getInputStream(), codisFile.getOriginalFilename(), reagentName);
            } catch (Exception ex) {
                resultMap.put("success", false);
                resultMap.put("errMsg", ex.getMessage());
                return new ResponseData(resultMap);
            }

            if (ListUtils.isNullOrEmptyList(codisContentModelList)) {
                resultMap.put("success", false);
                resultMap.put("errMsg", "CODIS文件中未检索到数据！");
                return new ResponseData(resultMap);
            }

            codisContentModelLists.addAll(codisContentModelList);

        }
        resultMap.put("success", true);
        resultMap.put("codisContentModelLists", codisContentModelLists);
        return new ResponseData(resultMap);
    }



    /**
     *  根据id删除 by renyunpeng
     */
    @GetMapping(value = "/deleteById")
    public ResponseData deleteById(Integer id){
        if (id != null){
            try {
                Boolean bool = qcSampleInfoService.deleteByIdOverride(id);
                return new ResponseData(bool);
            }catch(Exception ex){
                logger.error("invoke QcSampleInfoController.deleteById error.", ex);
                return new ResponseData("删除质控信息出现异常！" + ex.getMessage());
            }
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }
}