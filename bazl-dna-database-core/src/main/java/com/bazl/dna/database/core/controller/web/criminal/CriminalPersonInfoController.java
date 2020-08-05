package com.bazl.dna.database.core.controller.web.criminal;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.qo.CriminalPersonInfoQuery;
import com.bazl.dna.database.service.model.vo.CriminalPersonInfoVo;
import com.bazl.dna.database.service.model.vo.DnaMixGeneInfoVo;
import com.bazl.dna.database.service.service.CriminalPersonInfoService;
import com.bazl.dna.database.service.service.CriminalSampleInfoService;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.DnaGeneInfoService;
import com.bazl.dna.database.service.service.RegionInfoService;
import com.bazl.dna.database.utils.ListUtils;
import com.bazl.dna.util.GeneTransFormUtils;

/**
 * <p>
 * 建库人员信息表（违法犯罪/前科人员） 前端控制器
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/criminal")
public class CriminalPersonInfoController extends BaseController {

    @Autowired
    CriminalPersonInfoService criminalPersonInfoService;
    @Autowired
    CriminalSampleInfoService criminalSampleInfoService;
    @Autowired
    DnaGeneInfoService dnaGeneInfoService;
    @Autowired
    DictItemService dictItemService;
    @Autowired
    RegionInfoService regionInfoService;

    /**
     * 查询建库人员信息
     * @return
     */
    @PostMapping(value = "/criminalQuery")
    @SuppressWarnings("all")
    public ResponseData criminalQuery(@RequestBody CriminalPersonInfoQuery query){
        try {
            List<CriminalPersonInfoVo> criminalPersonInfoVoList = criminalPersonInfoService.criminalPaginationQuery(query);
            //类型字典项
            if (criminalPersonInfoVoList!=null && !criminalPersonInfoVoList.isEmpty()){
                for (CriminalPersonInfoVo infoVo : criminalPersonInfoVoList){
                    //建库人员类型
                    String personType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_CRIMINAL_PERSON_TYPE, infoVo.getCriminalPersonType());
                    infoVo.setCriminalPersonTypeName(personType);
                    //性别
                    String personGender = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TYPE_PERSON_GENDER, infoVo.getPersonGender());
                    infoVo.setPersonGenderName(personGender);
                    //名族
                    String personRace = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_RACE, infoVo.getPersonRace());
                    infoVo.setPersonRaceName(personRace);
                    //检材类型
                    String sampleType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_SAMPLE_TYPE, infoVo.getSampleType());
                    infoVo.setSampleTypeName(sampleType);
                }
            }
            int totalCount = criminalPersonInfoService.criminalPaginationCount(query);
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put(PublicConstants.PARAM_PAGE, new PageInfo(query.getPageIndex(), query.getRows(), totalCount));
            resultObj.put("criminalList", criminalPersonInfoVoList);
            return new ResponseData(resultObj);
        }catch(Exception ex){
            logger.error("invoke CriminalPersonInfoController.criminalQuery error.", ex);
            return new ResponseData("查询建库人员信息出现异常！" + ex.getMessage());
        }
    }

    /**
     * 查看建库人员详情
     * @return
     */
    @GetMapping(value = "/personDetails")
    public ResponseData personDetails(Integer id){
        Map<String, Object> result = new HashMap<>();
        if (id != null){
            try {
                CriminalPersonInfoVo criminalPersonInfoVo = criminalPersonInfoService.selectVoById(id);
                if (criminalPersonInfoVo != null){
                    List<DnaMixGeneInfoVo> dnaMixGeneInfoVos = criminalSampleInfoService.selectVoListByBySampleId(criminalPersonInfoVo.getSampleId());
                    if (ListUtils.isNotNullAndEmptyList(dnaMixGeneInfoVos)){
                        for (DnaMixGeneInfoVo mixGeneInfoVo : dnaMixGeneInfoVos){
                            if (mixGeneInfoVo.getYstrFlag().equals(Constants.GENE_TYPE_YSTR)){
                                //基因格式转换
                                String gene = GeneTransFormUtils.geneFormatString(mixGeneInfoVo.getGeneInfo());
                                mixGeneInfoVo.setGeneInfo(gene);
                                result.put("YSTR",mixGeneInfoVo);
                            }else {
                                //基因格式转换
                                String gene = GeneTransFormUtils.geneFormatString(mixGeneInfoVo.getGeneInfo());
                                mixGeneInfoVo.setGeneInfo(gene);
                                result.put("STR",mixGeneInfoVo);
                            }
                        }
                    }
                    //建库人员类型
                    String personType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_CRIMINAL_PERSON_TYPE, criminalPersonInfoVo.getCriminalPersonType());
                    criminalPersonInfoVo.setCriminalPersonTypeName(personType);
                    //性别
                    String personGender = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TYPE_PERSON_GENDER, criminalPersonInfoVo.getPersonGender());
                    criminalPersonInfoVo.setPersonGenderName(personGender);
                    //名族
                    String personRace = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_RACE, criminalPersonInfoVo.getPersonRace());
                    criminalPersonInfoVo.setPersonRaceName(personRace);
                    //检材类型
                    String sampleType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_SAMPLE_TYPE, criminalPersonInfoVo.getSampleType());
                    criminalPersonInfoVo.setSampleTypeName(sampleType);
                }
                result.put("criminal",criminalPersonInfoVo);
                return new ResponseData(result);
            }catch(Exception ex){
                logger.error("invoke CurrencyQueryController.queryCaseById error.", ex);
                return new ResponseData("查询案件详情信息出现异常！" + ex.getMessage());
            }
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

}
