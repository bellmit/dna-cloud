package com.bazl.dna.database.nation.converter.controller;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.dao.LocusInfoMapper;
import com.bazl.dna.database.nation.converter.dao.ReagentKitMapper;
import com.bazl.dna.database.nation.converter.model.po.CaseInfo;
import com.bazl.dna.database.nation.converter.model.po.LocusInfo;
import com.bazl.dna.database.nation.converter.model.po.PersonInfo;
import com.bazl.dna.database.nation.converter.model.po.SampleDnaGene;
import com.bazl.dna.database.nation.converter.model.po.SampleInfo;
import com.bazl.dna.database.nation.converter.model.po.newSampleInfo;
import com.bazl.dna.database.nation.converter.model.vo.SampleDnaGeneVo;
import com.bazl.dna.database.nation.converter.service.CaseInfoService;
import com.bazl.dna.database.nation.converter.service.PersonInfoService;
import com.bazl.dna.database.nation.converter.service.SampleDnaGeneService;
import com.bazl.dna.database.nation.converter.service.SampleInfoService;
import com.bazl.dna.database.nation.converter.utils.ErrorCode;
import com.bazl.dna.database.nation.converter.utils.ErrorCodes;
import com.bazl.dna.database.nation.converter.utils.ErrorInfo;
import com.bazl.dna.database.nation.converter.utils.IException;
import com.bazl.dna.database.nation.converter.utils.ListUtils;

@RestController
@RequestMapping("/nationalTreasury")
public class NationalTreasuryController extends  BaseController{

    @Autowired
    private LocusInfoMapper locusInfoMapper;

    @Autowired
    private ReagentKitMapper reagentKitMapper;

    @Autowired
    private CaseInfoService caseInfoService;

    @Autowired
    private SampleInfoService sampleInfoService;

    @Autowired
    private PersonInfoService personInfoService;

    @Autowired
    private SampleDnaGeneService sampleDnaGeneService;

    /**
     * 试剂盒列表  试剂盒id,名称
     * @return
     */
    @GetMapping(value = "/selectKitName")
    public ResponseData selectKitName(){
        try{
        		List<Map<String, Object>> list = reagentKitMapper.selectKitName();
            if(ListUtils.isNotNullAndEmptyList(list)){
                return  new ResponseData(list);
            }else{
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e) {
            logger.error("查询试剂盒失败！"+e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "查询试剂盒列表失败!");
        }
    }

    /**
     * 试剂盒基因座关联表   通过试剂盒id查到关联的基因座id，基因id查询基因座信息
     * @param reagentKitId
     * @return
     */
    @RequestMapping(value = "/selectByKitIdToLocusID/{reagentKitId}")
    public  ResponseData selectByKitIdToLocusID(@PathVariable("reagentKitId") String reagentKitId){
        try {
        		List<LocusInfo> list = locusInfoMapper.selectLocusInfoList(reagentKitId);
			if (ListUtils.isNotNullAndEmptyList(list)) {
				return new ResponseData(list);
			} else {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}
        }catch (Exception e) {
            logger.error("查询试剂盒失败！"+e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "查询试剂盒失败!");
        }
    }

    /**
     * 试剂盒基因座关联表   通过试剂盒名称查到关联的基因座名称，基因id查询基因座信息
     * @param reagentKitName
     * @return
     */
    @GetMapping(value = "/selectByKitNameToLocusName")
    public  ResponseData selectByKitNameToLocusName(@RequestParam("reagentKitName") String reagentKitName){
        try {
        		List<Map<String, Object>> list = locusInfoMapper.selectLocusNameList(reagentKitName);
    			if (ListUtils.isNotNullAndEmptyList(list)) {
    				return new ResponseData(list);
    			} else {
    				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    			}

        }catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "查询试剂盒失败!");
        }
    }

    /**
     * 根据登录名查询基因座名称
     * @return
     */
    @GetMapping(value = "/selectByLocusName")
    public ResponseData selectByLocusName(){
        try {
        		List<Map<String, Object>> list = locusInfoMapper.selectByLocusName();
            if(ListUtils.isNotNullAndEmptyList(list)){
                return new ResponseData(list);
			} else {
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "查询基因座名称失败!");
        }
    }

    /**
     * 根据基因座名称模糊查询基因座信息
     * @param locusName
     * @return
     */
    @RequestMapping(value = "/queryLocusNameVague")
    public ResponseData queryLocusNameVague(@RequestParam("locusName") String locusName){
        try {
            List<LocusInfo> list = locusInfoMapper.selectByNameVague(locusName);
			if (ListUtils.isNotNullAndEmptyList(list)) {
				return new ResponseData(list);
			} else {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}
        }catch (Exception e) {
            logger.error("试剂盒名称查询失败！"+e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "试剂盒名称查询失败！");
        }
    }

    /**
     * 根据案件编号获取国家库 案件下的基本信息信息
     * @param
     * @return
     * @throws IException
     */
    @RequestMapping(value = "/getCaseInfoByCaseNo")
    public ResponseData getCaseInfoByCaseNo(@RequestParam("caseNo") String caseNo)throws IException {
        try {
            CaseInfo caseInfo = new CaseInfo();
            logger.debug("|.获取案件信息已开始|"+"方法名称:"+this.getClass().getName()+"."+Thread.currentThread().getStackTrace()[1].getMethodName()+".|");
            if (StringUtils.isNotBlank(caseNo)) {
                try{
                    Map<String, Object> dataMap = null;
                    caseInfo = caseInfoService.selectByCaseNo(caseNo);
                    if (caseInfo != null) {
                        dataMap = new LinkedHashMap<>();
                        dataMap.put("caseInfo", caseInfo);

                        String caseId = caseInfo.getId();
                        if(StringUtils.isNotBlank(caseId)){
                            //获取案件下审核通过的检材信息
                            List<SampleInfo> sampleInfoList = sampleInfoService.selectAuditSampleByCaseId(caseId);
                            if (ListUtils.isNotNullAndEmptyList(sampleInfoList)) {
                                dataMap.put("sampleInfoList", sampleInfoList);
                                logger.debug("|.获取案件下审核通过的检材信息|结果result="+ sampleInfoList.toString() +".|");
                            }
                            //获取案件下人员信息
                            List<PersonInfo> personInfoList = personInfoService.selectAuditPersonByCaseId(caseId);
                            if (ListUtils.isNotNullAndEmptyList(personInfoList)) {
                                dataMap.put("personInfoList", personInfoList);
                                logger.debug("|.获取案件下审核通过的检材信息|结果result="+ sampleInfoList.toString() +".|");
                            }
                            //获取案件下审核通过的单一检材基因型信息
                            List<SampleDnaGene> singleSampleDnaGeneList =sampleDnaGeneService.selectByCaseToSingleGeneList(caseId);
                            if (ListUtils.isNotNullAndEmptyList(singleSampleDnaGeneList)) {
                                dataMap.put("singleSampleDnaGeneList", singleSampleDnaGeneList);
                                logger.debug("|.获取案件下审核通过的单一检材基因型信息|结果result="+ singleSampleDnaGeneList.toString() +".|");
                            }
                            //获取案件下审核通过的混合检材基因型信息
                            List<SampleDnaGene> mixSampleDnaGeneList =sampleDnaGeneService.selectByCaseToMixSampleDnaGeneList(caseId);
                            if (ListUtils.isNotNullAndEmptyList(mixSampleDnaGeneList)) {
                                dataMap.put("mixSampleDnaGeneList", mixSampleDnaGeneList);
                                logger.debug("|.获取案件下审核通过的混合检材基因型信息|结果result="+ mixSampleDnaGeneList.toString() +".|");
                            }
                        }
                        if (dataMap != null) {
                            logger.debug("|.获取案件信息已结束|结果result="+ dataMap.toString() +".|");
                            return new ResponseData(dataMap);
                        }

                    }else {
                        return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "1该案件不存在，获取信息失败！");
                    }

                }catch (Exception e) {
                    logger.error("获取案件信息错误"+e);
                    return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "获取案件信息错误！"+e.toString());
                }
            }
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "1案件编号为null");
        }catch (Exception e){
            logger.error("获取案件信息错误"+e);
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "获取案件信息错误！"+e.toString());
        }
    }

    @RequestMapping(value = "/getCaseInfoSampleNo", produces = "application/json; charset=utf-8")
    public ResponseData getCaseInfoSampleNo(@RequestParam("sampleNo") String sampleNo)throws IException {
        newSampleInfo singleGeneVosList = sampleDnaGeneService.selectSingleGene(sampleNo);
        return new ResponseData(singleGeneVosList);
    }



    //国家库查询所有混合样本(条查分页)
//    @ResponseBody
//    @RequestMapping(value = "/queryMixedSampleGene", method = RequestMethod.GET)
//    public ResultBean queryMixedSampleGene(HttpServletRequest request, String page,String size , String caseNo,String caseName,String sampleNo, String sampleName){
//        try{
//            //分页参数为空，设置默认值
//            int pg;
//            int sz;
//            if(StringUtils.isNotBlank(page) && !"0".equals(page)){
//                pg = Integer.valueOf(page);
//            }else{
//                pg = 1;
//            }
//            if(StringUtils.isNotBlank(size) && !"0".equals(size)){
//                sz = Integer.valueOf(size);
//            }else{
//                sz = 15;
//            }
//            Map<String, Object> dataMap = new LinkedHashMap<>();
//            SampleDnaGeneVo sampleDnaGeneVo = new SampleDnaGeneVo();
//
//            if(StringUtils.isNotBlank(caseNo)){
//                sampleDnaGeneVo.setCaseNo(caseNo);
//            }
//            if(StringUtils.isNotBlank(caseName)){
//                sampleDnaGeneVo.setCaseName(caseName);
//            }
//            if(StringUtils.isNotBlank(sampleNo)){
//                sampleDnaGeneVo.setSampleNo(sampleNo);
//            }
//            if(StringUtils.isNotBlank(sampleName)){
//                sampleDnaGeneVo.setSampleName(sampleName);
//            }
//
//            List<SampleDnaGeneVo> sampleDnaGeneVoMix = sampleDnaGeneService.queryByMixDnaGene(pg,sz,sampleDnaGeneVo);
//            if(ListUtils.isNotNullAndEmptyList(sampleDnaGeneVoMix)){
//               for (SampleDnaGeneVo dnaGeneVoMix : sampleDnaGeneVoMix) {
//                   if (StringUtils.isNotBlank(dnaGeneVoMix.getEntity().getGenePicture())){
//                       dnaGeneVoMix.getEntity().setGeneImagePath(dnaGeneVoMix.getEntity().getGenePicture());
//                   }else{
//                       dnaGeneVoMix.getEntity().setGeneImagePath("0");
//                   }
//               }
//            }
//            List<SampleDnaGeneVo> sampleDnaGeneVoList = sampleDnaGeneService.countMixDnaGene();
//            dataMap.put("count",sampleDnaGeneVoList.size());
//            dataMap.put("sampleDnaGeneVoMix",sampleDnaGeneVoMix);
//            return new ResultBean(ResultBean.CODE_SUCCESS, 0, dataMap, ResultBean.NAME_SUCCESS);
//        }catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "获取信息错误!");
//        }
//    }


    /**
     * 国家库条查接口
     * @param request
     * @param page
     * @param caseNo
     * @param caseName
     * @param sampleNo
     * @param sampleName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryMixedSampleGenes")
    public ResponseData queryMixedSampleGenes(@RequestParam("page") String page, @RequestParam("caseNo") String caseNo,
    		@RequestParam("caseName") String caseName, @RequestParam("sampleNo") String sampleNo, @RequestParam("sampleName") String sampleName) {
        try {
            Map<String, Object> dataMap = new LinkedHashMap<>();
            PageInfo pageInfo = new PageInfo();
            SampleDnaGeneVo sampleDnaGeneVo = new SampleDnaGeneVo();
            if(StringUtils.isEmpty(page) || "0".equals(page) || page == null ){
                sampleDnaGeneVo.setOffset(1);
                pageInfo.setPage(1);
            }else{
                sampleDnaGeneVo.setOffset(Integer.parseInt(page));
                pageInfo.setPage(Integer.parseInt(page));
            }
            if (StringUtils.isNotBlank(caseNo)) {
                sampleDnaGeneVo.setCaseNo(caseNo);
            }
            if (StringUtils.isNotBlank(caseName)) {
                sampleDnaGeneVo.setCaseName(caseName);
            }
            if (StringUtils.isNotBlank(sampleNo)) {
                sampleDnaGeneVo.setSampleNo(sampleNo);
            }
            if (StringUtils.isNotBlank(sampleName)) {
                sampleDnaGeneVo.setSampleName(sampleName);
            }


            List<SampleDnaGeneVo> sampleDnaGeneVoMix = sampleDnaGeneService.queryByMixDnaGenes(pageInfo, sampleDnaGeneVo);
            if (ListUtils.isNotNullAndEmptyList(sampleDnaGeneVoMix)) {
                for (SampleDnaGeneVo dnaGeneVoMix : sampleDnaGeneVoMix) {
                    if (StringUtils.isNotBlank(dnaGeneVoMix.getEntity().getGenePicture())) {
                        dnaGeneVoMix.getEntity().setGeneImagePath(dnaGeneVoMix.getEntity().getGenePicture());
                    } else {
                        dnaGeneVoMix.getEntity().setGeneImagePath("0");
                    }
                }
            }
            int count = sampleDnaGeneService.countMixDnaGenes(sampleDnaGeneVo);
            dataMap.put("count", count);
            dataMap.put("sampleDnaGeneVoMix", sampleDnaGeneVoMix);
            return new ResponseData(dataMap);
        }catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 国家库删除混合样本 （假删除）
     * @param sampleGeneId
     * @return
     */
    @GetMapping(value = "/updateSampleDnaGeneDF/{sampleGeneId}")
    public ResponseData updateSampleDnaGeneDF(@PathVariable("sampleGeneId") String sampleGeneId){
        try {
            sampleDnaGeneService.updateSampleDnaGeneDF(sampleGeneId);
            return new ResponseData(0);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "删除信息错误!");
        }
    }


    /**
     * 查询混合基因信息(关联案件，地区，样本，字典项) 总数
     * @return
     */
    @GetMapping(value = "/selectMixGeneByCwsd")
    public  ResponseData selectMixGeneByCwsd(){
        try{
            Map<String, Object> dataMap = new LinkedHashMap<>();
            dataMap.put("mixGeneCount", sampleDnaGeneService.selectMixGeneByCwsd());
            return new ResponseData(dataMap);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }


    /**
     * 查询单一基因信息(关联案件，地区，样本，字典项) 总数
     * @return
     */
    @RequestMapping(value = "/selectSingleGeneByCwsd")
    public ResponseData selectSingleGeneByCwsd(){
        try{
            Map<String, Object> dataMap = new LinkedHashMap<>();
            int i = sampleDnaGeneService.selectSingleGeneByCwsd( );
            dataMap.put("singleGeneCount",i);
            return new ResponseData(dataMap);

        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }


    /**
     * 查询混合基因信息(关联案件，地区，样本，字典项) 分页
     * @param page
     * @return
     */
    @RequestMapping(value = "/selectMixGeneByCwsdPage")
    public  ResponseData selectMixGeneByCwsdPage(@RequestParam("page") String page){
        try{
            int pg = Integer.valueOf(page);
            Map<String, Object> dataMap = new LinkedHashMap<>();
            SampleDnaGeneVo query = new SampleDnaGeneVo();
            List<SampleDnaGeneVo> mixGeneVosList = sampleDnaGeneService.selectMixGeneByCwsdPage(query,pg);
            if(ListUtils.isNotNullAndEmptyList(mixGeneVosList)){
                dataMap.put("mixGeneVosList",mixGeneVosList);
                return new ResponseData(dataMap);
			} else {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }


    /**
     * 查询单一基因信息(关联案件，地区，样本，字典项) 分页
     * @param page
     * @return
     */
    @RequestMapping(value = "/selectSingleGeneByCwsdPage")
    public  ResponseData selectSingleGeneByCwsdPage(@RequestParam("page") String page){
        try{
            int pg = Integer.valueOf(page);
            SampleDnaGeneVo query = new SampleDnaGeneVo();
            List<SampleDnaGeneVo> singleGeneVosList = sampleDnaGeneService.selectSingleGeneByCwsdPage(query,pg);
            if(ListUtils.isNotNullAndEmptyList(singleGeneVosList)){
                return new ResponseData(singleGeneVosList);
            }else{
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 根据样本编号 查询单一基因信息(关联案件，地区，样本，字典项)
     * @param sampleNo
     * @return
     */
    @RequestMapping(value = "/selectSingleGeneBySampleNo")
    public ResponseData selectSingleGeneBySampleNo(@RequestParam("sampleNo") String sampleNo){
        try{
            SampleDnaGeneVo query = new SampleDnaGeneVo();
            query.setSampleNo(sampleNo);
            List<SampleDnaGeneVo> singleGeneVosList = sampleDnaGeneService.selectSingleGeneBySampleNo(query);
            if(ListUtils.isNotNullAndEmptyList(singleGeneVosList)){
                return new ResponseData(singleGeneVosList);
            }else{
            	return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 根据案件id查询案件信息
     */
    @GetMapping(value = "/selectByCaseId")
    public ResponseData selectByCaseId(@RequestParam("caseId") String caseId){
        try{
            CaseInfo caseInfo = caseInfoService.selectByCaseId(caseId);
            if(caseInfo != null){
                return new ResponseData(caseInfo);
            }else{
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 根据案件id查询混合样本库信息
     */
    @GetMapping(value = "/selectMixedSampleGeneList")
    public ResponseData selectMixedSampleGeneList(@RequestParam("caseId") String caseId){
        try{
            List<SampleDnaGene> sampleDnaGeneList = sampleDnaGeneService.selectMixedSampleGeneList(caseId);
            if(ListUtils.isNotNullAndEmptyList(sampleDnaGeneList)){
                return new ResponseData(sampleDnaGeneList);
            }else{
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 根据案件id查询混合样本库信息
     */
    @GetMapping(value = "/selectBySampleId")
    public ResponseData selectBySampleId(@RequestParam("sampleId") String sampleId){
        try{
            CaseInfo caseInfo = caseInfoService.selectBySampleId(sampleId);
            if(caseInfo != null){
                return new ResponseData(caseInfo);
            }else{
                return new ResponseData(null);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

}
